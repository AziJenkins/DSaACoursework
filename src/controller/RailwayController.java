package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import exceptions.BothStationsAlreadyPresentException;
import exceptions.InvalidFormatException;
import exceptions.InvalidSubLineException;
import exceptions.LineFormatException;
import exceptions.LineNotFoundException;
import model.RailLine;
import model.RailwayMap;
import model.Station;
import setup.RailwayCreator;

public class RailwayController implements Controller {

	private RailwayMap map;
	
	public RailwayController() {
		try {
			RailwayCreator rwc = new RailwayCreator();
			boolean cont = true;
			while(cont) {
				rwc.processInputLine();
			}
			map = rwc.getMap();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (LineNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BothStationsAlreadyPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidSubLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	} 
	
	@Override
	public String listAllTermini(String line) {
		StringBuilder sb = new StringBuilder();
		RailLine l = map.getLine(line);
		List<Station> termini = null;
		try {
			termini = l.getTermini();
		} catch (LineFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Station> i = termini.iterator();
		while(i.hasNext()) {
			sb.append(i.next().getName());
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public String listStationsInLine(String line) {
		RailLine l = map.getLine(line);
		return l.toString();
	}

	@Override
	public String showPathBetween(String stationA, String stationB) {
		Station sA;
		Station sB;
		Set<RailLine> aLines;
		Set<RailLine> bLines;
		
		Set<RailLine> intersectLines = aLines;
		intersectLines.retainAll(bLines);
		
		if(intersectLines.isEmpty()) {
			for(RailLine line: aLines) {
				
			}
		}

	}

}
