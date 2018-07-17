package model;

import java.util.HashMap;
import java.util.Set;

public class Map {

	private HashMap<String, Line> lines;
	private HashMap<String, Station> stations;
	
	public Map() {
		lines = new HashMap<String, Line>();
		stations = new HashMap<String, Station>();
	}
	
	public Line getLine(String line) {
		return lines.get(line);
	}

	public Object getPathBetween(String stationA, String stationB) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addLine(Line line) {
		lines.put(line.getName(), line);
		for (Station s: line.getStations()) {
			stations.put(s.getName(), s);
		}
	}
}
