package model;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author azira
 *
 */
public class Station {

	/**
	 * The name of the Station
	 */
	private String name;
	/**
	 * A Set of all Lines this Station resides on
	 */
	private HashSet<Line> lines;
	/**
	 * A Map between the name of a Station connected to this and the travel time to that Station
	 */
	private HashMap<String, Integer> distanceTo;
	
	/**
	 * @param name The name of the Station
	 */
	public Station(String name) {
		this.name = name;
		lines = new HashSet<Line>();
		distanceTo = new HashMap<String, Integer>();
	}
	
	/**
	 * Record that this Station belongs to a Line
	 * @param line The Line to be added
	 */
	public void addLine(Line line) {
		lines.add(line);
	}
	
	/**
	 * @return The name of this Station
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return A set of the Lines this Station resides on
	 */
	public HashSet<Line> getLines() {
		return lines;
	}
	
	/**
	 * @param toStation The name of the Station to be connected
	 * @param distance The travel time to the Station
	 */
	public void addConntection(String toStation, Integer distance) {
		distanceTo.put(toStation, distance);
	}
	
	/**
	 * @param station The station to get the travel time to
	 * @return the travel time to the specified station or null if it is not connected to this Station
	 */
	public int getDistanceTo(Station station) {
		return distanceTo.get(station.name);
	}
	
	/**
	 * @return A Map between names of Stations connected to this and the travel time to them
	 */
	public HashMap<String, Integer> getConnections() {
		return distanceTo;
	}
}
