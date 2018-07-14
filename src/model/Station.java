package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;

public class Station {

	private String name;
	private HashSet<String> lines;
	private HashMap<String, Integer> connectedStations;
	
	public Station(String name) {
		this.name = name;
		this.lines = new HashSet<String>();
		this.connectedStations = new HashMap<String, Integer>();
	}
	
	public void addConnection(String station, Integer distance) {
		connectedStations.put(station, distance);
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t\t\t");
		sb.append(name);
		sb.append("\n\t\t\t\t\t\t Lines: \n");
		Iterator<String> i = lines.iterator();
		while (i.hasNext()) {
			sb.append("\t\t\t\t\t\t");
			sb.append(i.next());
			sb.append("\n");
		}
		sb.append("\t\t\t\t\t\t\t\t Connected Stations: \n");
		i = connectedStations.keySet().iterator();
		while (i.hasNext()) {
			String station = i.next();
			sb.append("\t\t\t\t\t\t\t\t");
			sb.append(station);
			sb.append(" ");
			sb.append(connectedStations.get(station));
			sb.append("\n");
		}
		return sb.toString();
	}
}
