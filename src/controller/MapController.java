package controller;

import model.Map;
import model.Station;

public class MapController implements Controller {

	private Map map = new Map();
	
	@Override
	public String listAllTermini(String line) {
		StringBuilder sb = new StringBuilder();
		for (Station s: map.getLine(line).getTermini()) {
			sb.append(s.getName());
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String listStationsInLine(String line) {
		return map.getLine(line).toString();
	}

	@Override
	public String showPathBetween(String stationA, String stationB) {
		return map.getPathBetween(stationA, stationB).toString();
	}

}
