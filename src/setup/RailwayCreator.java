package setup;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import exceptions.BothStationsAlreadyPresentException;
import exceptions.InvalidFormatException;
import exceptions.InvalidSubLineException;
import exceptions.LineNotFoundException;
import exceptions.PreviousNotFoundException;
import model.Line;
import model.Map;
import model.Station;

public class RailwayCreator {

	private String file = "src/WestMidlandsRailway.csv";
	private BufferedReader br;
	private String[] input;
	private LinkedList<String[]> northFacingSubLines;
	private Map map;
	private HashMap<String, Station> stations;
	private HashMap<String, Line> lines;

	/*
	 * 
	 * Java passes references!!!!!!!!!!!!!!!!!
	 * 
	 * 
	 * 
	 */

	public RailwayCreator() throws InvalidFormatException {
		try {
			br = new BufferedReader(new FileReader(file));
			checkFormat(br.readLine().split(","));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map = new Map();
		stations = new HashMap<String, Station>();
		northFacingSubLines = new LinkedList<String[]>();
		lines = new HashMap<String, Line>();
	}
	
	public Map processFile() throws IOException, PreviousNotFoundException {
		while(processInputLine());
		processNorthFacingSubLines();
		close();
		return map;
	}

	private boolean processInputLine() throws IOException, PreviousNotFoundException {

		if (!getLine()) {
			return false;
		}
		for (int i = 0; i < input.length; i++) {
			input[i] = input[i].trim();
		}
		
		Station fromStation = stations.get(input[1]);
		if (fromStation == null) {
			fromStation = new Station(input[1]);
		}
		Station toStation = stations.get(input[2]);
		if (toStation == null) {
			toStation = new Station(input[2]);
		}
		fromStation.addConntection(toStation.getName(), Integer.getInteger(input[3]));
		toStation.addConntection(fromStation.getName(), Integer.getInteger(input[3]));
		Line l = lines.get(input[0]);
		if (l == null) {
			l = new Line(input[0], fromStation);
			lines.put(l.getName(), l);
		} else if (!l.contains(input[1])) {
			northFacingSubLines.add(input);
			return true;
		}
		l.addStation(toStation, fromStation.getName());
		stations.put(fromStation.getName(), fromStation);
		stations.put(toStation.getName(), toStation);
		map.addLine(l);
		return true;
	}

	private boolean processNorthFacingSubLines() throws PreviousNotFoundException {
		Iterator<String[]> i = northFacingSubLines.descendingIterator();
		while (i.hasNext()) {
			input = i.next();

			Station fromStation = stations.get(input[2]);
			if (fromStation == null) {
				fromStation = new Station(input[2]);
				stations.put(fromStation.getName(), fromStation);
			}
			Station toStation = stations.get(input[1]);
			if (toStation == null) {
				toStation = new Station(input[1]);
				stations.put(toStation.getName(), toStation);
			}
			fromStation.addConntection(toStation.getName(), Integer.getInteger(input[3]));
			toStation.addConntection(fromStation.getName(), Integer.getInteger(input[3]));
			Line l = lines.get(input[0]);
			if (l == null) {
				l = new Line(input[0], fromStation);
				lines.put(l.getName(), l);
			}
			l.addStation(toStation, fromStation.getName());
			map.addLine(l);
		}
		return true;
	}

	private boolean getLine() throws IOException {
		String line = br.readLine();
		if (line == null) {
			return false;
		}
		input = line.split(",");
		return true;

	}

	private void checkFormat(String[] line) throws InvalidFormatException {
		if (line.length != 4) {
			throw new InvalidFormatException("Incorrect number of columns");
		}
		if (!line[0].equals("TRAIN LINE")) {
			throw new InvalidFormatException("First heading must equal TRAIN LINE");
		}
		if (!line[1].equals("FROM/TO STATION")) {
			throw new InvalidFormatException("Second heading must equal FROM/TO STATION");
		}
		if (!line[2].equals("TO/FROM STATION")) {
			throw new InvalidFormatException("Third heading must equal TO/FROM STATION");
		}
		if (!line[3].equals("TRAVEL TIME (MINS)")) {
			throw new InvalidFormatException("Fourth heading must equal TRAVEL TIME (MINS)");
		}
	}

	private void close() throws IOException {
		br.close();
	}
}
