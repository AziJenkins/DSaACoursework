package model;

public class Link {

	private Line line;
	private Station fromStation;
	private Station toStation;
	private int distance;
	
	public Link(Line line, Station fromStation, Station toStation, int distance) {
		this.line = line;
		this.toStation = toStation;
		this.distance = distance;
		this.fromStation = fromStation;
	}
	
	public Line getLine() {
		return line;
	}
	public Station getToStation() {
		return toStation;
	}
	public int getDistance() {
		return distance;
	}
	public Station getFromStation() {
		return fromStation;
	}
}
