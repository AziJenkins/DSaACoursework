package OLDmodel;

public class StationReturnPacket {
	public Station station;
	public boolean isNewSubLine;
	public boolean isOldSubLine;

	public StationReturnPacket(Station station, boolean isNewSubLine, boolean isOldSubLine) {
		this.station = station;
		this.isNewSubLine = isNewSubLine;
		this.isOldSubLine = isOldSubLine;
	}
}
