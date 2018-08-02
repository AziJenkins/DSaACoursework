package controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

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
		HashSet<Line> linesA = (HashSet<Line>) stA.getLines().clone();
		HashSet<Line> linesB = (HashSet<Line>) stB.getLines().clone();

		// check if they share a line
		if (!Collections.disjoint(linesA, linesB)) {
			for (Line l : linesA) {
				if (linesB.contains(l)) {
					return l.getPathBetween(stA, stB);
				}
			}
		}

		for (Line l : linesA) {
			if (!Collections.disjoint(l.getIntersections().values(), linesB)) {
				for (String s: l.getIntersections().keySet()) {
					if (linesB.contains(l.getIntersections().get(s))) {
						return l.getPathBetween(stA, map.getStationByName(s)) + l.getIntersections().get(s).getPathBetween(map.getStationByName(s), stB);
					}
				}
			}
		}
		for (Line l : linesB) {
			if (!Collections.disjoint(l.getIntersections().values(), linesA)) {
				for (String s: l.getIntersections().keySet()) {
					if (linesA.contains(l.getIntersections().get(s))) {
						return l.getPathBetween(stA, map.getStationByName(s)) + l.getIntersections().get(s).getPathBetween(map.getStationByName(s), stB);
					}
				}
			}
		}
		return "Could not find a path";
	}

}
