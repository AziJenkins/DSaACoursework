package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Station {

	private String name;
	private HashSet<Line> lines;
	private HashMap<String, Integer> distanceTo;
	
	public Station(String name) {
		this.name = name;
		lines = new HashSet<Line>();
		distanceTo = new HashMap<String, Integer>();
	}
	
	public void addLine(Line l) {
		lines.add(l);
	}
	
	public String getName() {
		return name;
	}
	
	public HashSet<Line> getLines() {
		return lines;
	}
	
	public void addConntection(String toStation, Integer distance) {
		distanceTo.put(toStation, distance);
	}
	
	public int getDistanceTo(Station s) {
		return distanceTo.get(s.name);
	}
	
	public HashMap<String, Integer> getConnections() {
		return distanceTo;
	}
}
