package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
				for (int i = firstIndex; i < Math.max(indexA, indexB); i++) {
					sb.append(ls.getStations().get(i).getName());
					sb.append("\n");
				}
				break;
			} else {
				int onlyIndex = Math.max(indexA, indexB);
				if (onlyIndex > -1) {
					if (!foundFirst) {
						for (int i = onlyIndex; i < -1; i--) {
							sb.append(ls.getStations().get(i).getName());
							sb.append("\n");
						}
					} else {
						for (int i = 0; i < onlyIndex + 1; i++) {
							sb.append(ls.getStations().get(i).getName());
							sb.append("\n");
						}
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
