package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Station {

	private String name;
	private List<Link> links;

	public Station(String name) {
		this.name = name;
		links = new LinkedList<Link>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addLink(Link l) {
		links.add(l);
	}

	public List<Link> getNextLinks(String line, Set<Station> previousStations) {
		List<Link> nextLinks = new LinkedList<Link>();
		for (Link l : links) {
			if (l.getLine().getName().equals(line) && !previousStations.contains(l.getToStation())) {
				nextLinks.add(l);
			}
		}
		return nextLinks;
	}
}
