package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import exceptions.PreviousNotFoundException;

/**
 * @author azira
 *
 */
public class Line {

	/**
	 * The name of the line
	 */
	private String name;
	/**
	 * A list of this Line's LineSegments where the LineSegment at index 0 is the
	 * Main Line. All Lines connect to the Main Line
	 */
	private ArrayList<LineSegment> segments;
	/**
	 * A Map between the Station names and the Stations included in this Line
	 */
	private HashMap<String, Station> stations;
	/**
	 * A Set of the Stations which terminate this Line
	 */
	private HashSet<Station> termini;
	/**
	 * A Map between the Lines that intersect this Line and the Stations where the
	 * intersection happens
	 */
	private HashMap<Line, Station> intersections;

	/**
	 * @param name  The name of the Line
	 * @param start The first station on this Line. It is assumed that this is a
	 *              terminus station
	 */
	public Line(String name, Station start) {
		this.name = name;
		segments = new ArrayList<LineSegment>();
		segments.add(new LineSegment(start));

		stations = new HashMap<String, Station>();
		stations.put(start.getName(), start);

		termini = new HashSet<Station>();
		termini.add(start);

		intersections = new HashMap<Line, Station>();
		start.addLine(this);
	}

	/**
	 * Add a station to the Line
	 * @param station  The Station to add
	 * @param previous The name of the Station in this Line that directly connects
	 *                 to the new Station
	 * @throws PreviousNotFoundException The Station previous was not found on this
	 *                                   Line
	 */
	public void addStation(Station station, String previous) throws PreviousNotFoundException {
		for (LineSegment ls : segments) {
			if (ls.getLast().getName().equals(previous)) { // Find which LineSegment the station should be added to
				if (ls.hasEnd()) { // Dont remove the first Station on the Line
					termini.remove(ls.getLast()); // The Station previous is no longer a terminus
				}
				ls.add(station);
				stations.put(station.getName(), station);
				termini.add(station);
				station.addLine(this);
				return;
			}
		} // Previous was not at the end of a LineSegment

		Station previousStation = stations.get(previous);
		if (previousStation == null)
			throw new PreviousNotFoundException();
		LineSegment ls = new LineSegment(previousStation); // Previous was the start of a new LineSegment
		ls.add(station);
		segments.add(ls);
		stations.put(station.getName(), station);
		termini.add(station);
		station.addLine(this);

	}

	/**
	 * @return This Lines LineSegments
	 */
	public ArrayList<LineSegment> getSegments() {
		return segments;
	}

	/**
	 * @return A Set of this Lines Termini
	 */
	public HashSet<Station> getTermini() {
		return termini;
	}

	/**
	 * @return The name of this Line
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return A Map Between the Lines that intersect this Line and the Stations at
	 *         the location of the intersection point
	 */
	public HashMap<Line, Station> getIntersections() {
		return intersections;
	}

	/**
	 * @return A Collection of all the Stations on this Line
	 */
	public Collection<Station> getStations() {
		return stations.values();
	}

	/**
	 * @param station The name of the Station
	 * @return True if this Line contains the Station
	 */
	public boolean contains(String station) {
		return stations.keySet().contains(station);
	}

	/**
	 * @param stationA The first Station
	 * @param stationB The second Station
	 * @return A String representation of the path between the two Stations. Each
	 *         Station is separated by a new line followed by the travel time to the
	 *         next Station and another new line
	 */
	public String getPathBetween(Station stationA, Station stationB) {
		LineSegment mainSegment = segments.get(0);
		StringBuilder sb = new StringBuilder();
		int finalIndexA = -1; // The index that stationA holds on segA
		LineSegment segA = null;// The segment that contains stationA
		int finalIndexB = -1; // The index that stationB holds on segB
		LineSegment segB = null;// The segment that contains stationB
		for (LineSegment ls : segments) {
			int indexA = ls.getStations().indexOf(stationA);
			int indexB = ls.getStations().indexOf(stationB);
			if (indexA > -1 && indexB > -1) { // both found on same segment
				return sb.append(tracePathBetween(indexA, indexB, ls)).toString();
			} else {
				if (finalIndexA == -1) { // stationA has not been found yet
					finalIndexA = indexA;
					if (finalIndexA > -1) { // have we found stationA
						segA = ls;
					}
				}
				if (finalIndexB == -1) { // stationB has not been found yet
					finalIndexB = indexB;
					if (finalIndexB > -1) { // have we found stationB
						segB = ls;
					}
				}
			}
		}
		if (segA == mainSegment) { // starts at mainSegment
			sb.append(tracePathBetween(finalIndexA, segA.getStations().indexOf(segB.getStations().get(0)), segA)); // Path from stationA to the fork onto the segment that conatins stationB
			sb.append(tracePathBetween(0, finalIndexB, segB)); // Path from the fork to stationB
		} else {
			sb.append(tracePathBetween(finalIndexA, 0, segA)); // Path from stationA to the fork onto the mainSegment
			if (segB == mainSegment) {
				sb.append(tracePathBetween(segB.getStations().indexOf(segA.getStations().get(0)), finalIndexB, segB)); // Path from the fork to stationB
			} else {
				sb.append(tracePathBetween(mainSegment.getStations().indexOf(segA.getStations().get(0)), mainSegment.getStations().indexOf(segB.getStations().get(0)), mainSegment)); // Path between both forks on the main segment
				sb.append(tracePathBetween(0, finalIndexB, segB)); // Path from fork to stationB
			}
		}
		return sb.toString();
	}

	/**
	 * @param fromIndex The index of the first Station on ls
	 * @param toIndex   The index of the last Station on ls
	 * @param ls        The LineSegment to trace a path from
	 * @return A String representation of the path from the station at fromIndex to
	 *         toIndex on the LineSegment ls
	 */
	public String tracePathBetween(int fromIndex, int toIndex, LineSegment ls) {
		StringBuilder sb = new StringBuilder();
		if (fromIndex < toIndex) { // which direction are we going
			for (int i = fromIndex; i < toIndex; i++) {
				sb.append(traceStep(i, ls, false));
			}
		} else {
			for (int i = fromIndex; i > toIndex; i--) {
				sb.append(traceStep(i, ls, true));
			}
		}
		return sb.toString();
	}

	/**
	 * @param index     The index of the Station in this step
	 * @param ls        The LineSegment to trace a step from
	 * @param backwards Is the next Station on the route backwards on this
	 *                  LineSegment
	 * @return A String representation of the path from the Station on ls at index
	 *         in a direction denoted by backwards
	 */
	public String traceStep(int index, LineSegment ls, boolean backwards) {
		StringBuilder sb = new StringBuilder();
		sb.append(ls.getStations().get(index).getName());
		sb.append("\n");
		sb.append(ls.getStations().get(index).getDistanceTo(ls.getStations().get(backwards ? index - 1 : index + 1)));
		sb.append(" (mins) to");
		sb.append("\n");
		return sb.toString();
	}

	/*
	 * A String representation of the Line The Main segment is displayed vertically
	 * While the others are displayed horizontally
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Station s : segments.get(0).getStations()) {
			sb.append(s.getName());
			for (int i = 1; i < segments.size(); i++) {
				LineSegment seg = segments.get(i);
				if (seg.getStations().get(0).getName().equals(s.getName())) {
					sb.append(seg.toString());
					if (!termini.contains(seg.getStations().get(seg.getStations().size() - 1))) {
						// TODO needs joining to main line again
					}
				}
			}
			sb.append("\r\n");
			sb.append("|");
			sb.append("\r\n");
		}
		sb.delete(sb.length() - 5, sb.length()); // remove the hanging |
		return sb.toString();
	}

	/**
	 * Loop through all of this Lines Stations to find the location of any
	 * intersections and record them in intersections
	 */
	public void recordIntersections() {
		for (Station s : stations.values()) {
			for (Line l : s.getLines()) {
				if (l != this) {
					intersections.put(l, s);
				}
			}
		}

	}
}
