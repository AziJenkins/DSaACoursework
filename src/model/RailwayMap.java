package model;

import java.util.HashSet;
import java.util.Iterator;

import exceptions.LineNotFoundException;

public class RailwayMap {
	
	private HashSet<RailLine> lines;
	
	public RailwayMap() {
		lines = new HashSet<RailLine>();
	}
	
	public void addLine(RailLine line) {
		lines.add(line);
	}
	
	public RailLine getLine(String line) {
		Iterator<RailLine> i = lines.iterator();
		RailLine current = null;
		while (i.hasNext()) {
			current = i.next();
			if (current.getName().equals(line)) {
				return current;
			}
		}
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<RailLine> i = lines.iterator();
		while (i.hasNext()) {
			sb.append(i.next().toString());
		}
		return sb.toString();
	}

	public void getLineByStation(String station) {
		for (RailLine line: lines) {
			if (line.)
		}
	}
}
