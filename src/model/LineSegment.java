package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LineSegment {

	private ArrayList<Station> stations;
	private HashSet<Station> endPoints;

	public LineSegment(Station start) {
		stations = new ArrayList<Station>();
		stations.add(start);
		endPoints = new HashSet<Station>();
		endPoints.add(start);
	}

	public void add(Station s) {
		if (stations.size() > 1) {
			endPoints.remove(getLast());
		}
		stations.add(s);
		endPoints.add(s);
	}

	public HashSet<Station> getEndPoints() {
		return endPoints;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public Station getLast() {
		return stations.get(stations.size() - 1);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Station> it = stations.iterator();
		it.next();
		while (it.hasNext()) {
			sb.append(" -- ");
			sb.append(it.next().getName());
		}

		return sb.toString();
	}
}
