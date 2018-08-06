package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author azira
 *
 */
public class LineSegment {

	/**
	 * An ordered List of the Stations on this LineSegment
	 */
	private ArrayList<Station> stations;
	/**
	 * The last station on this LineSegment.
	 * Cannot be the only Station on the LineSegment
	 */
	private Station endPoint;

	/**
	 * @param start The first station on this LineSegment
	 */
	public LineSegment(Station start) {
		stations = new ArrayList<Station>();
		stations.add(start);
	}

	/**
	 * @param station The Station to be added
	 */
	public void add(Station station) {
		stations.add(station);
		endPoint = station;
	}

	/**
	 * @return true if this LineSegment has more than one Station
	 */
	public boolean hasEnd() {
		return endPoint == null ? false : true;
	}

	/**
	 * @return	A List of the Stations on this LineSegment
	 */
	public ArrayList<Station> getStations() {
		return stations;
	}

	/**
	 * @return	The last segment on this LineSegment
	 */
	public Station getLast() {
		return stations.get(stations.size() - 1);
	}

	/* 
	 * A String representation of this LineSegment delimited by " -- "
	 */
	@Override
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
