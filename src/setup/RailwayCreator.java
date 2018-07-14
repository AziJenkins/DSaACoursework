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
import model.RailLine;
import model.RailwayMap;
import model.Station;
import model.StationReturnPacket;

public class RailwayCreator {

	private String file = "src/WestMidlandsRailway.csv";
	private BufferedReader br;
	private String[] input;
	private LinkedList<String[]> northFacingSubLines;
	private RailwayMap map;
	private HashMap<String, String> stationNames; // <Station, Line>

	public RailwayCreator() throws InvalidFormatException {
		try {
			br = new BufferedReader(new FileReader(file));
			checkFormat(br.readLine().split(","));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map = new RailwayMap();
		stationNames = new HashMap<String, String>();
		northFacingSubLines = new LinkedList<String[]>();
	}

	public boolean processInputLine()
			throws LineNotFoundException, BothStationsAlreadyPresentException, IOException, InvalidSubLineException {

		if (!getLine()) {
			return false;
		}
		if(input[2].equals("Lapworth") && input[1].equals("Dorridge")) {
			System.out.println("break");
		}
		
		Station[] stations = new Station[2];
		RailLine l = map.getLine(input[0]);
		boolean isNewSubLine = false;
		boolean isOldSubline = false;

		// does line exist?
		if (l == null) {
			// no, create new line and two stations
			l = new RailLine(input[0]);
			for (int i = 0; i < 2; i++) {
				stations[i] = new Station(input[i + 1]);
				stationNames.put(input[i+1], input[0]);
			}
		} else {
			// yes, get station 1
				
			StationReturnPacket srp1 = map.getLine(input[0]).getStation(input[1]);
			if (srp1 == null) {
				northFacingSubLines.add(input);
				return true;
			}
			isOldSubline = srp1.isOldSubLine;
			isNewSubLine = srp1.isNewSubLine;
			stations[0] = srp1.station;
			// does station 2 exist?
			if (stationNames.containsKey(input[2])) {
				// yes, get it
				StationReturnPacket srp2 = map.getLine(stationNames.get(input[2])).getStation(input[2]);
				isNewSubLine = false;
				stations[1] = srp2.station;
			} else {
				// no, create it
				stations[1] = new Station(input[2]);
				stationNames.put(input[2], input[0]);
			}
		}

		for (int i = 0; i < 2; i++) {
			stations[i].addConnection(input[(i * 2 + 2) % 3], Integer.parseInt(input[3]));
		}
		l.addStation(stations[0]);

		if (isNewSubLine) {
			l.addNewSubLineStation(input[1], stations[1]);
		} else if (isOldSubline) {
			l.addSubLineStation(input[1], stations[1]);
		} else {
			l.addStation(stations[1]);
		}

	map.addLine(l);
	return true;

	}
	
	public boolean processNorthFacingSubLines() throws InvalidSubLineException {
		Iterator<String[]> i = northFacingSubLines.descendingIterator();
		while (i.hasNext()) {
			String[] input = i.next();
			Station[] stations = new Station[2];
			RailLine l = map.getLine(input[0]);
			boolean isNewSubLine = false;
			boolean isOldSubline = false;
			StationReturnPacket srp1 = map.getLine(input[0]).getStation(input[1]);
			isNewSubLine = srp1.isNewSubLine;
			stations[0] = srp1.station;
			// does station 2 exist?
			if (stationNames.containsKey(input[2])) {
				// yes, get it
				StationReturnPacket srp2 = map.getLine(stationNames.get(input[2])).getStation(input[2]);
				isOldSubline = srp2.isOldSubLine;
				isNewSubLine = false;
				stations[1] = srp2.station;
			} else {
				// no, create it
				stations[1] = new Station(input[2]);
				stationNames.put(input[2], input[0]);
			}
			for (int j = 0; j < 2; j++) {
				stations[j].addConnection(input[(j * 2 + 2) % 3], Integer.parseInt(input[3]));
			}
			l.addStation(stations[0]);

			if (isNewSubLine) {
				l.addNewSubLineStation(input[1], stations[1]);
			} else if (isOldSubline) {
				l.addSubLineStation(input[1], stations[1]);
			} else {
				l.addStation(stations[1]);
			}

		map.addLine(l);
		}
		return true;
	}

	public boolean getLine() throws IOException {
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
	
	public RailwayMap getMap() {
		return map;
	}
}
