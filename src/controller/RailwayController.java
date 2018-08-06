package controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import model.Line;
import model.RailwayMap;
import model.Station;

/**
 * @author azira
 *
 */
public class RailwayController implements Controller {

	/**
	 * A RailwayMap
	 */
	private RailwayMap map;

	/**
	 * @param map The map for the controller to operate on
	 */
	public RailwayController(RailwayMap map) {
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#listAllTermini(java.lang.String)
	 */
	@Override
	public String listAllTermini(String line) {
		StringBuilder sb = new StringBuilder();
		Iterator<Station> it = map.getLineByName(line).getTermini().iterator();
		while (it.hasNext()) {
			sb.append(it.next().getName());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#listStationsInLine(java.lang.String)
	 */
	@Override
	public String listStationsInLine(String line) {
		return map.getLineByName(line).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.Controller#showPathBetween(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String showPathBetween(String stationA, String stationB) {
		Station stA = map.getStationByName(stationA);
		Station stB = map.getStationByName(stationB);
		HashSet<Line> linesA = stA.getLines(); // All of the lines stationA is on
		HashSet<Line> linesB = stB.getLines(); // All of the lines stationB is on
		StringBuilder sb = new StringBuilder();

		// check if they share a line 
		// A---B
		Collection<Line> paths = checkForCommonLine(linesA, linesB);
		if (paths != null) {
			return paths.iterator().next().getPathBetween(stA, stB) + stationB;
		}
		// Check if the intersections from lines that contain stationA are any of the
		// lines that contain stationB 
		// A-----C
		//    \_B
		for (Line l : linesA) {
			paths = checkForCommonLine(l.getIntersections().keySet(), linesB);
			if (paths != null) {
				Line path = paths.iterator().next(); // The line that connects stationB to some intersection point that then leads to stationA
				Station iPoint = l.getIntersections().get(path); // A station that is connected to both stationA and stationB on different lines
				sb.append(l.getPathBetween(stA, iPoint));
				sb.append(path.getPathBetween(iPoint, stB));
				sb.append(stationB);
				return sb.toString();
			}
		}
		// Check if a line containing stationA intersects a line that also intersects a
		// line that contains stationB
		// A--iA-----
		//   /
		// iB----B
		paths = checkForCommonIntersection(linesA, linesB);
		if (paths != null) {
			Line path = paths.iterator().next(); //The line between two lines containing a station each
			Station iPointA = null;
			Station iPointB = null;
			for (Line l : linesA) {
				iPointA = l.getIntersections().get(path);
				if (iPointA != null) {
					sb.append(l.getPathBetween(stA, iPointA));
					break;
				}
			}
			for (Line l : linesB) {
				iPointB = l.getIntersections().get(path);
				if (iPointB != null) {
					sb.append(path.getPathBetween(iPointA, iPointB));
					sb.append(l.getPathBetween(iPointB, stB));
					sb.append(stationB);
					break;
				}
			}
			return sb.toString();
		}
		//Check each line intersecting with a line that contains stationA
		// A-----iA------
		//      /
		// ---iAi---iB---
		// 	  /		 \
		//            B
		for (Line line : linesA) {
			paths = checkForCommonIntersection(line.getIntersections().keySet(), linesB);
			if (paths != null) {
				Line path = paths.iterator().next(); //The line with intersections iB and iAi from the diagram
				Station iPointAi = null;
				Station iPointB = null;
				for (Line l : line.getIntersections().keySet()) { //find the second line in the route
					iPointAi = l.getIntersections().get(path);
					if (iPointAi != null) {
						sb.append(line.getPathBetween(stA, line.getIntersections().get(l)));
						sb.append(l.getPathBetween(line.getIntersections().get(l), iPointAi));
						break;
					}
				}
				for (Line l : linesB) {
					iPointB = l.getIntersections().get(path);
					if (iPointB != null) {
						sb.append(path.getPathBetween(iPointAi, iPointB));
						sb.append(l.getPathBetween(iPointB, stB));
						sb.append(stationB);
						break;
					}
				}
				return sb.toString();
			}
		}

		return "Could not find a path"; //Can only find paths that at most contain 4 lines
		// There should be a way to make this recursive but i cant find it
	}

	/**
	 * @param linesA The first collection of lines
	 * @param linesB The second collection of lines
	 * @return An intersect of the collections passed to it, null if there are no common lines
	 */
	private Collection<Line> checkForCommonLine(Collection<Line> linesA, Collection<Line> linesB) {
		Collection<Line> commonLines = new HashSet<Line>();
		commonLines.addAll(linesA);
		commonLines.retainAll(linesB);
		if (!commonLines.isEmpty()) {
			return commonLines;
		}
		return null;
	}

	/**
	 * Checks if there are any lines that are present in the sets of all lines intersecting two collection of lines
	 * @param linesA The first collection of lines
	 * @param linesB The second collection of lines
	 * @return A collection of lines that intersect a Line in linesA and linesB
	 */
	private Collection<Line> checkForCommonIntersection(Collection<Line> linesA, Collection<Line> linesB) {
		Collection<Line> linesAI = new HashSet<Line>();
		Collection<Line> linesBI = new HashSet<Line>();
		for (Line l : linesA) {
			linesAI.addAll(l.getIntersections().keySet());
		}
		for (Line l : linesB) {
			linesBI.addAll(l.getIntersections().keySet());
		}
		return checkForCommonLine(linesAI, linesBI);
	}

	/**
	 * @return The RailwayMap this controller is contoling
	 */
	private RailwayMap getMap() {
		return map;
	}
}
