package model;

import java.util.HashMap;


/**
 * @author azira
 *
 */
public class Map {

	/**
	 * A Map between each Name and its Line
	 */
	public HashMap<String, Line> lines;
	/**
	 * A Mapo between each Name and its Station
	 */
	public HashMap<String, Station> stations;

	public Map() {
		lines = new HashMap<String, Line>();
		stations = new HashMap<String, Station>();
	}

	/**
	 * Add a line to the Map
	 * All Stations on the Line will be recorded
	 * @param line the Line to add
	 */
	public void addLine(Line line) {
		lines.put(line.getName(), line);
		for (Station s : line.getStations()) {
			stations.put(s.getName(), s);
		}
	}

	/**
	 * @param name the name of the Line
	 * @return A line with the specified name or null if it was not found
	 */
	public Line getLineByName(String name) {
		for (Line l : lines.values()) {
			if (l.getName().equals(name)) {
				return l;
			}
		}
		return null;
	}

	/**
	 * @param name the name of the Station
	 * @return A Station with the specified name or null if it was not found
	 */
	public Station getStationByName(String name) {
		return stations.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Line l : lines.values()) {
			sb.append(l.toString());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/**
	 * @return A map between the name of Stations and the Stations
	 */
	public HashMap<String, Station> getStations() {
		return stations;
	}

	/**
	 * Calculate the intersections on each line in the Map
	 */
	public void recordIntersections() {
		for (Line l : lines.values()) {
			l.recordIntersections();
		}

	}
}
