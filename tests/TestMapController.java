import org.junit.Before;
import org.junit.Test;

import controller.MapController;
import model.Line;
import model.Link;
import model.Station;

public class TestMapController {

	MapController mc;

	@Before
	public void setUp() {
		mc = new MapController();
	}

	@Test
	public void testListStations() {
		Line l = new Line("l1");
		Station[] stations = new Station[10];
		for (int i = 0; i < stations.length; i++) {
			stations[i] = new Station("s" + i);
		}
		for (int i = 0; i < stations.length; i++) {
			if (i != 0) {
				stations[i].addLink(new Link(l, stations[i], stations[i - 1], 1));
			}
			if (i != stations.length - 1) {
				stations[i].addLink(new Link(l, stations[i], stations[i + 1], 1));
			}
		}

		Station[] subStations = new Station[5];
		for (int i = 0; i < subStations.length; i++) {
			subStations[i] = new Station("s" + i + "b");
		}
		for (int i = 0; i < subStations.length; i++) {
			if (i != 0) {
				subStations[i].addLink(new Link(l, subStations[i], subStations[i - 1], 1));
			} else {
				subStations[i].addLink(new Link(l, subStations[i], stations[3], 1));
				stations[3].addLink(new Link(l, subStations[i], subStations[i], 1));
			}
			if (i != subStations.length - 1) {
				subStations[i].addLink(new Link(l, subStations[i], subStations[i + 1], 1));
			}
		}

		Station[] subStations2 = new Station[2];
		for (int i = 0; i < subStations2.length; i++) {
			subStations2[i] = new Station("s" + i + "c");
		}
		for(int i = 0;i<subStations2.length;i++) {
			if (i != 0) {
				subStations2[i].addLink(new Link(l, subStations2[i], subStations2[i - 1], 1));
			} else {
				subStations2[i].addLink(new Link(l, subStations2[i], stations[5], 1));
				stations[5].addLink(new Link(l, subStations2[i], subStations2[i], 1));
			}
			if (i != subStations2.length - 1) {
				subStations2[i].addLink(new Link(l, subStations2[i], subStations2[i + 1], 1));
			}
		}

		for (int i = 0; i < stations.length; i++) {
			boolean terminus = false;
			if (i == 0 || i == stations.length - 1) {
				terminus = true;
			}
			l.addStation(stations[i], terminus);
		}
		for (int i = 0; i < subStations.length; i++) {
			boolean terminus = false;
			if (i == 0 || i == subStations.length - 1) {
				terminus = true;
			}
			l.addStation(subStations[i], terminus);
		}
		for (int i = 0; i < subStations2.length; i++) {
			boolean terminus = false;
			if (i == 0 || i == subStations2.length - 1) {
				terminus = true;
			}
			l.addStation(subStations2[i], terminus);
		}

		System.out.println(l.toString());

	}

}
