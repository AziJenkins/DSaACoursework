package controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import exceptions.BothStationsNotPresentException;
import model.Line;
import model.Map;
import model.Station;

public class RailwayController implements Controller {

	private Map map;

	public RailwayController(Map map) {
		this.map = map;
	}

	@Override
	public String listAllTermini(String line) {
		StringBuilder sb = new StringBuilder();
		Iterator<Station> it = map.getLineByName(line).getTermini().iterator();
		while (it.hasNext()) {
			sb.append(it.next().getName());
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String listStationsInLine(String line) {
		return map.getLineByName(line).toString();
	}

	@Override
	public String showPathBetween(String stationA, String stationB) {
		Station stA = map.getStationByName(stationA);
		Station stB = map.getStationByName(stationB);
		HashSet<Line> linesA = stA.getLines();
		HashSet<Line> linesB = stB.getLines();				
		StringBuilder sb = new StringBuilder();

		// check if they share a line
		@SuppressWarnings("unchecked")
		Collection<Line> paths = checkForCommonLine(linesA, linesB);
		if (paths != null) {
			return paths.iterator().next().getPathBetween(stA, stB) + stationB;
		}

		for (Line l : linesA) {
			paths = checkForCommonLine(l.getIntersections().keySet(), linesB);
			if (paths != null) {
				Line path = paths.iterator().next();
				Station iPoint = l.getIntersections().get(path);

				sb.append(l.getPathBetween(stA, iPoint));
				sb.append(path.getPathBetween(iPoint, stB));
				sb.append(stationB);
				return sb.toString();
			}
		}
		
		paths = checkForCommonIntersection(linesA, linesB);
		if (paths != null) {
			Line path = paths.iterator().next();
			Station iPointA = null;
			Station iPointB = null;
			for (Line l: linesA) {
				iPointA = l.getIntersections().get(path);
				if (iPointA != null) {
					sb.append(l.getPathBetween(stA, iPointA));
					break;
				}
			}
			for (Line l: linesB) {
				iPointB = l.getIntersections().get(path);
				if(iPointB != null) {
					sb.append(path.getPathBetween(iPointA, iPointB));
					sb.append(l.getPathBetween(iPointB, stB));
					sb.append(stationB);
					break;
				}
			}
			return sb.toString();
		}
		
		for (Line line: linesA) {
			paths = checkForCommonIntersection(line.getIntersections().keySet(), linesB);
			if (paths != null) {
				Line path = paths.iterator().next();
				Station iPointA = null;
				Station iPointB = null;
				for (Line l: line.getIntersections().keySet()) {
					iPointA = l.getIntersections().get(path);
					if (iPointA != null) {
						sb.append(line.getPathBetween(stA, line.getIntersections().get(l)));
						sb.append(l.getPathBetween(line.getIntersections().get(l), iPointA));
						break;
					}
				}
				for (Line l: linesB) {
					iPointB = l.getIntersections().get(path);
					if (iPointB != null) {
						sb.append(path.getPathBetween(iPointA, iPointB));
						sb.append(l.getPathBetween(iPointB, stB));
						sb.append(stB.getName());
						break;
					}
				}
				return sb.toString();
			}
		}

		return "Could not find a path";
	}

	public Collection<Line> checkForCommonLine(Collection<Line> linesA, Collection<Line> linesB) {
		Collection<Line> commonLines = new HashSet<Line>();
		commonLines.addAll(linesA);
		commonLines.retainAll(linesB);
		if (!commonLines.isEmpty()) {
			return commonLines;
		}
		return null;
	}
	
	public Collection<Line> checkForCommonIntersection(Collection<Line> linesA, Collection<Line> linesB) {
		Collection<Line> linesAI = new HashSet<Line>();
		Collection<Line> linesBI = new HashSet<Line>();
		for (Line l: linesA) {
			linesAI.addAll(l.getIntersections().keySet());
		}
		for (Line l: linesB) {
			linesBI.addAll(l.getIntersections().keySet());
		}
		return checkForCommonLine(linesAI, linesBI);
	}

	public Map getMap() {
		return map;
	}
}
