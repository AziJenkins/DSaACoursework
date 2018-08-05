package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import exceptions.BothStationsNotPresentException;
import exceptions.PreviousNotFoundException;

public class Line {

	private String name;
	private ArrayList<LineSegment> segments;
	private HashMap<String, Station> stations;
	private HashSet<Station> termini;
	private HashMap<Line, Station> intersections;

	public Line(String name, Station start) {
		this.name = name;
		segments = new ArrayList<LineSegment>();
		segments.add(new LineSegment(start));
		stations = new HashMap<String, Station>();
		stations.put(start.getName(), start);
		termini = new HashSet<Station>();
		termini.add(start);
		intersections = new HashMap<Line, Station>();
		start.addLine(this);
	}

	public void addStation(Station s, String previous) throws PreviousNotFoundException {
		for (LineSegment ls : segments) {
			if (ls.getLast().getName().equals(previous)) {
				if (ls.getEndPoints().size() > 1) {
					termini.remove(ls.getLast());
				}
				ls.add(s);
				stations.put(s.getName(), s);
				termini.add(s);
				s.addLine(this);
				return;
			}
		}
		LineSegment ls = new LineSegment(stations.get(previous));
		ls.add(s);
		segments.add(ls);
		stations.put(s.getName(), s);
		termini.add(s);
		s.addLine(this);

	}

	public ArrayList<LineSegment> getSegments() {
		return segments;
	}

	public HashSet<Station> getTermini() {
		return termini;
	}

	public String getName() {
		return name;
	}

	public HashMap<Line, Station> getIntersections() {
		return intersections;
	}

	public Collection<Station> getStations() {
		return stations.values();
	}

	public boolean contains(String station) {
		return stations.keySet().contains(station);
	}

	public String getPathBetween(Station sA, Station sB) {
		LineSegment mainSegment = segments.get(0);
		StringBuilder sb = new StringBuilder();
		int finalIndexA = -1;
		LineSegment segA = null;
		int finalIndexB = -1;
		LineSegment segB = null;
		for (LineSegment ls : segments) {
			int indexA = ls.getStations().indexOf(sA);
			int indexB = ls.getStations().indexOf(sB);
			if (indexA > -1 && indexB > -1) { // both found on same segment
				return sb.append(tracePathBetween(indexA, indexB, ls)).toString();
			} else {
				if (finalIndexA == -1) {
					finalIndexA = indexA;
					if (finalIndexA > -1) {
						segA = ls;
					}
				}
				if (finalIndexB == -1) {
					finalIndexB = indexB;
					if (finalIndexB > -1) {
						segB = ls;
					}
				}
			}
		}
		if (segA == mainSegment) {
			sb.append(tracePathBetween(finalIndexA, segA.getStations().indexOf(segB.getStations().get(0)), segA));
			sb.append(tracePathBetween(0, finalIndexB, segB));
		} else {
			sb.append(tracePathBetween(finalIndexA, 0, segA));
			if (segB == mainSegment) {
				sb.append(tracePathBetween(segB.getStations().indexOf(segA.getStations().get(0)), finalIndexB, segB));
			} else {
				sb.append(tracePathBetween(mainSegment.getStations().indexOf(segA.getStations().get(0)), mainSegment.getStations().indexOf(segB.getStations().get(0)), mainSegment));
				sb.append(tracePathBetween(0, finalIndexB, segB));
			}
		}
		return sb.toString();
	}

	public String tracePathBetween(int fromIndex, int toIndex, LineSegment ls) {
		StringBuilder sb = new StringBuilder();
		if (fromIndex < toIndex) {
			for (int i = fromIndex; i < toIndex; i++) {
				sb.append(traceStep(i, ls, false));
			}
		} else {
			for (int i = fromIndex; i > toIndex; i--) {
				sb.append(traceStep(i, ls, true));
			}
		}
		return sb.toString();
	}

	public String traceStep(int i, LineSegment ls, boolean backwards) {
		StringBuilder sb = new StringBuilder();
		sb.append(ls.getStations().get(i).getName());
		sb.append("\n");
		sb.append(ls.getStations().get(i).getDistanceTo(ls.getStations().get(backwards ? i - 1 : i + 1)));
		sb.append(" (mins) to");
		sb.append("\n");
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Station s : segments.get(0).getStations()) {
			sb.append(s.getName());
			for (int i = 1; i < segments.size(); i++) {
				LineSegment seg = segments.get(i);
				if (seg.getStations().get(0).getName().equals(s.getName())) {
					sb.append(seg.toString());
					if (!termini.contains(seg.getStations().get(seg.getStations().size() - 1))) {
						// TODO needs joining to main line again
					}
				}
			}
			sb.append("\r\n");
			sb.append("|");
			sb.append("\r\n");
		}
		sb.delete(sb.length() - 5, sb.length());
		return sb.toString();
	}

	public void recordIntersections() {
		for (Station s : stations.values()) {
			for (Line l : s.getLines()) {
				if (l != this) {
					intersections.put(l, s);
				}
			}
		}

	}
}
