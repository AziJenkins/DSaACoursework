package setup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
import model.Line;
import model.RailwayMap;
import model.Station;

/**
 * @author azira
 *
 */
public class RailwayCreator {

	/**
	 * The file containing the railway data
	 */
	private String file = "WestMidlandsRailway.csv";
	/**
	 * A reader for the file
	 */
	private BufferedReader br;
	/**
	 * The current line of input
	 */
	private String[] input;
	/**
	 * Lines of data that specify Stations in reverse order
	 */
	private LinkedList<String[]> northFacingSubLines;
	/**
	 * The railway map to be returned
	 */
	private RailwayMap map;
	/**
	 * A Map between names of Stations and those Stations
	 */
	private HashMap<String, Station> stations;
	/**
	 * A Map between names of Line and those Lines
	 */
	private HashMap<String, Line> lines;

	/**
	 * @param file Path to the file
	 * @throws InvalidFormatException Thrown when the data file does not have
	 *                                correct headings
	 * @throws FileNotFoundException  Thrown when the specified file was not found
	 * @throws IOException            Thrown if there is a problem reading a line
	 *                                from the file
	 */
	public RailwayCreator(String file) throws InvalidFormatException, FileNotFoundException, IOException {
		if (!file.isEmpty()) {
			this.file = file;
		} else {
			System.out.println("Using default file : WestMidlandsRailway.csv");
		}

		br = new BufferedReader(new FileReader(this.file));
		checkFormat(br.readLine().split(",")); // Check format of headings line at top of file

		map = new RailwayMap();
		stations = new HashMap<String, Station>();
		northFacingSubLines = new LinkedList<String[]>();
		lines = new HashMap<String, Line>();
	}

	/**
	 * @return A RailwayMap created using the data in file
	 * @throws IOException               Thrown if there is a problem reading a line
	 *                                   from the file
	 * @throws PreviousNotFoundException Thrown when attempting to add a Station to
	 *                                   a line and the specified previous Station
	 *                                   does not already exist on that Line
	 */
	public RailwayMap processFile() throws IOException, PreviousNotFoundException {
		while (processInputLine())
			; // Process lines as long as there is a next line

		processNorthFacingSubLines(); // Process the lines that were in reverse order
		close();
		map.recordIntersections(); // Calculate intersections on all Lines
		return map;
	}

	/**
	 * @return true if there was a next line, false indicates the whole file has
	 *         been processed
	 * @throws IOException               Thrown if there was an issue with reading a
	 *                                   line from the file
	 * @throws PreviousNotFoundException Thrown when attempting to add a Station to
	 *                                   a line and the specified previous Station
	 *                                   does not already exist on that Line
	 */
	private boolean processInputLine() throws IOException, PreviousNotFoundException {

		if (!getLine()) { // if there is no next line
			return false;
		}
		for (int i = 0; i < input.length; i++) {
			input[i] = input[i].trim();
		}

		Station fromStation = stations.get(input[1]); // Check if the station exists already
		if (fromStation == null) {
			fromStation = new Station(input[1]);
		}
		Station toStation = stations.get(input[2]); // Check if the station exists already
		if (toStation == null) {
			toStation = new Station(input[2]);
		}
		fromStation.addConntection(toStation.getName(), Integer.parseInt(input[3])); // Record the connection between the two Stations
		toStation.addConntection(fromStation.getName(), Integer.parseInt(input[3]));
		Line l = lines.get(input[0]); // Check if the Line already exists
		if (l == null) {
			l = new Line(input[0], fromStation);
			lines.put(l.getName(), l);
		} else if (!l.contains(input[1])) { // If the line exists but the fromStation is not yet on the line that implies
			northFacingSubLines.add(input); // the lines in the file are in 'reverse' order
			return true;
		}
		l.addStation(toStation, fromStation.getName());
		stations.put(fromStation.getName(), fromStation);
		stations.put(toStation.getName(), toStation);
		map.addLine(l);
		return true;
	}

	/**
	 * Loops through all entries in northFacingSubLines and process' them in reverse order
	 * @throws PreviousNotFoundException Thrown when attempting to add a Station to
	 *                                   a line and the specified previous Station
	 *                                   does not already exist on that Line
	 */
	private void processNorthFacingSubLines() throws PreviousNotFoundException {
		Iterator<String[]> i = northFacingSubLines.descendingIterator();
		while (i.hasNext()) {
			input = i.next();

			Station fromStation = stations.get(input[2]); //Check if the station exists
			if (fromStation == null) {
				fromStation = new Station(input[2]);
				stations.put(fromStation.getName(), fromStation);
			}
			Station toStation = stations.get(input[1]); //Check if the station exists
			if (toStation == null) {
				toStation = new Station(input[1]);
				stations.put(toStation.getName(), toStation);
			}
			fromStation.addConntection(toStation.getName(), Integer.parseInt(input[3])); //Record the connection between the stations
			toStation.addConntection(fromStation.getName(), Integer.parseInt(input[3]));
			Line l = lines.get(input[0]); //Check if the Line exists
			if (l == null) {
				l = new Line(input[0], fromStation);
				lines.put(l.getName(), l);
			}
			l.addStation(toStation, fromStation.getName());
			map.addLine(l);
		}
	}

	/**
	 * Gets the next line of the file and stores the tokenised version in input
	 * @return true if there was a next line, false when there are no more lines in the file
	 * @throws IOException 
	 */
	private boolean getLine() throws IOException {
		String line = br.readLine();
		if (line == null) {
			return false;
		}
		input = line.split(",");
		return true;

	}

	/**
	 * Checks that the headings in the file match specification
	 * @param line A tokenised header from the file
	 * @throws InvalidFormatException Thrown if the file was in an invalid format
	 */
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

	/**
	 * @throws IOException
	 */
	private void close() throws IOException {
		br.close();
	}

}
