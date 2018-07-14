package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import exceptions.InvalidSubLineException;

public class RailLine {

	private String name;
	private LinkedHashSet<Station> primaryLine;
	private HashMap<String, LinkedHashSet<Station>> subLines;

	public RailLine(String name) {
		this.name = name;
		this.primaryLine = new LinkedHashSet<Station>();
		this.subLines = new HashMap<String, LinkedHashSet<Station>>();
	}

	public void addStation(Station station) {
		this.primaryLine.add(station);
	}
	
	public void addNewSubLineStation(String fromStation, Station station) {
		LinkedHashSet<Station> subLine = new LinkedHashSet<Station>();
		subLine.add(station);
		subLines.put(fromStation, subLine);
	}
	
	public void addSubLineStation(String fromStation, Station station) throws InvalidSubLineException {
		StationReturnPacket result = null;
		boolean flag = false;
		for(LinkedHashSet<Station> subLine: subLines.values()) {
			result = getFromIterator(subLine.iterator(), fromStation);
			if (result != null) {
				if(result.isNewSubLine) {
					throw new InvalidSubLineException("SubLines cannot have SubLines");
				}
				subLine.add(station);
				flag = true;
			}
		}
		if(!flag) {
			throw new InvalidSubLineException(fromStation + " did not appear in any subLine");
		}
	}

	public String getName() {
		return name;
	}

	public StationReturnPacket getStation(String station) {
		Collection<LinkedHashSet<Station>> allLines = new LinkedList<LinkedHashSet<Station>>();
		allLines.add(primaryLine);
		allLines.addAll(subLines.values());
		boolean onMainLine = true;
		StationReturnPacket result = null;
		for (LinkedHashSet<Station> subLine: allLines) {
			result = getFromIterator(subLine.iterator(), station);
			if (result != null) {
				result.isOldSubLine = !onMainLine;
				return result;
			}
			onMainLine = false;
		}
		return null;
	}

	private StationReturnPacket getFromIterator(Iterator<Station> i, String station) {
		Station current = null;
		while (i.hasNext()) {
			current = i.next();
			if (current.getName().equals(station)) {
				return new StationReturnPacket(current, i.hasNext(), false);
			}
		}
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t Line : ");
		sb.append(name);
		sb.append("\n \t\t Primary: \n");
		Iterator<Station> i = primaryLine.iterator();
		while (i.hasNext()) {
			sb.append("\t\t");
			sb.append(i.next().toString());
			sb.append("\n");
		}
		sb.append("\n\n\n\n");
		Iterator<String> subLine = subLines.keySet().iterator();
		while (subLine.hasNext()) {
			String currentSubLine = subLine.next();
			sb.append("\t\t Subline. Start: ");
			sb.append(currentSubLine);
			sb.append("\n");
			i = subLines.get(currentSubLine).iterator();
			while (i.hasNext()) {
				sb.append("\t\t ");
				sb.append(i.next().toString());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
