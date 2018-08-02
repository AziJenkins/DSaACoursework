package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import exceptions.PreviousNotFoundException;

public class Map {

	public HashSet<Line> lines;
	public HashMap<String, Station> stations;
	
	public Map() {
		lines = new HashSet<Line>();
		stations = new HashMap<String, Station>();
	}
	
	public void addLine(Line l) {
		lines.add(l);
		for (Station s: l.getStations()) {
			stations.put(s.getName(), s);
		}
	}
	
	public Line getLineByName(String name) {
		for (Line l: lines) {
			if (l.getName().equals(name)) {
				return l;
			}
		}
		return null;
	}
	
	public Station getStationByName(String name) {
		return stations.get(name);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Line l: lines) {
			sb.append(l.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}
}
