package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class Line {

	private String name;
	private HashSet<Station> stations;
	private HashSet<Station> termini;
	private HashMap<Line, Station> intersections;

	public Line(String name) {
		this.name = name;
		stations = new HashSet<Station>();
		termini = new HashSet<Station>();
	}

	public void addStation(Station s, boolean isTerminus) {
		stations.add(s);
		if (isTerminus) {
			termini.add(s);
		}
	}

	public Station[] getTermini() {
		return (Station[]) termini.toArray();
	}

	public String getName() {
		return name;
	}

	public String toString() {
		Map<String, List<Station>> map = getLine(new HashSet<Station>(), termini.iterator().next(),
				new HashMap<String, List<Station>>(), "start");
		Object[] keySet = map.keySet().toArray();
		String[] keys = new String[keySet.length - 1];
		for (int i = 0; i < keySet.length; i++) {
			int j = 0;
			if(!keySet[i].equals("start")) {
				keys[j] = (String) keySet[i];
				j++;
			}
		}

		int maxSize = 0;
		for (List<Station> line : map.values()) {
			maxSize = line.size() > maxSize ? line.size() : maxSize;
		}
		maxSize += map.get("start").size(); // worst case scenario
		String[][] arrayMap = new String[maxSize][map.keySet().size()];
		List<Station> mainLine = map.get("start");
		for (int i = 0; i < mainLine.size(); i++) {
			arrayMap[i][0] = mainLine.get(i).getName();
			for (int j = 0; j < keys.length; j++) {
				String startPoint = (String) keys[j];
				if (startPoint.equals(arrayMap[i][0])) {
					int index = 0;
					for (Station s : map.get(startPoint)) {
						arrayMap[i + index][j + 1] = s.getName();
						index++;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < maxSize; i++) {
			for (int j = 0; j < keys.length; j++) {
				sb.append(arrayMap[i][j]);
				sb.append("\t");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public HashMap<String, List<Station>> getLine(HashSet<Station> previous, Station initial,
			HashMap<String, List<Station>> lineMap, String branchPoint) {
		previous.add(initial);
		if (!lineMap.containsKey(branchPoint)) {
			List<Station> line = new LinkedList<Station>();
			line.add(initial);
			lineMap.put(branchPoint, line);
		}
		List<Link> nextLinks = initial.getNextLinks(name, previous);
		while (!nextLinks.isEmpty()) {
			Iterator<Link> i = nextLinks.iterator();
			Link mainLink = i.next();
			Station current = mainLink.getToStation();
			lineMap.get(branchPoint).add(current);
			previous.add(current);
			while (i.hasNext()) {
				HashSet<Station> subLinePrevious = new HashSet<Station>();
				subLinePrevious.add(mainLink.getFromStation());
				lineMap = getLine(subLinePrevious, i.next().getToStation(), lineMap, current.getName());
			}
			nextLinks = current.getNextLinks(name, previous);
		}

		return lineMap;
	}

	public HashSet<Station> getStations() {
		return stations;
	}
}
