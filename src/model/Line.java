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
	private HashMap<String, Line> intersections;

	public Line(String name, Station start) {
		this.name = name;
		segments = new ArrayList<LineSegment>();
		segments.add(new LineSegment(start));
		stations = new HashMap<String, Station>();
		stations.put(start.getName(), start);
		termini = new HashSet<Station>();
		termini.add(start);
		intersections = new HashMap<String, Line>();
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
				for (Line l: s.getLines()) {
					if (l != this) {
						intersections.put(s.getName(), l);
					}
				}
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

	public HashMap<String, Line> getIntersections() {
		return intersections;
	}

	public Collection<Station> getStations() {
		return stations.values();
	}
	
	public boolean contains(String station) {
		return stations.keySet().contains(station);
	}

	public String getPathBetween(Station sA, Station sB) {
		StringBuilder sb = new StringBuilder();
		boolean foundFirst = false;
		for (LineSegment ls : segments) {
			int indexA = ls.getStations().indexOf(sA);
			int indexB = ls.getStations().indexOf(sB);
			if (indexA > -1 && indexB > -1) {
				int firstIndex = Math.min(indexA, indexB);
				int lastIndex = Math.max(indexA,  indexB);
				for (int i = firstIndex; i < lastIndex; i++) {
					sb.append(ls.getStations().get(i).getName());
					sb.append("\n");
					sb.append(ls.getStations().get(i).getDistanceTo(ls.getStations().get(i+1)));
					sb.append("\n");
				}
				sb.append(ls.getStations().get(lastIndex).getName());
				break;
			} else {
				int onlyIndex = Math.max(indexA, indexB);
				if (onlyIndex > -1) {
					if (!foundFirst) {
						for (int i = onlyIndex; i > 0; i--) { //TODO this should not apply if its on the main line. need to wait until subline is found then append to that station
							sb.append(ls.getStations().get(i).getName());
							sb.append("\n");
							sb.append(ls.getStations().get(i).getDistanceTo((ls.getStations().get(i-1))));
							sb.append("\n");
						}
						sb.append(ls.getStations().get(0).getName());
						sb.append("\n");
						//TODO append distance here
					} else {
						for (int i = 0; i < onlyIndex; i++) {
							sb.append(ls.getStations().get(i).getName());
							sb.append("\n");
							sb.append(ls.getStations().get(i).getDistanceTo((ls.getStations().get(i+1))));
							sb.append("\n");
						}
						sb.append(ls.getStations().get(onlyIndex).getName());
					}
					foundFirst = true;
				}
			}
		}
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
		}
		return sb.toString();
	}
}
