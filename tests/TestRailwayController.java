import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import controller.RailwayController;
import exceptions.BothStationsNotPresentException;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
import model.Line;
import model.Map;
import model.Station;
import setup.RailwayCreator;

public class TestRailwayController {

	private RailwayController rwc;

	public void setUp() throws PreviousNotFoundException, InvalidFormatException, IOException {
		RailwayCreator creator = new RailwayCreator("");
		rwc = new RailwayController(creator.processFile());
	}

	@Test
	public void testGetTermini() throws PreviousNotFoundException, InvalidFormatException, IOException {
		setUp();
		String[] t0 = rwc.listAllTermini("Birmingham -- Dorridge -- Leamington Spa").split("\n");
		HashSet<String> t0ex = new HashSet<String>();
		t0ex.add("Birmingham Snow Hill");
		t0ex.add("Stratford-upon-Avon");
		t0ex.add("Leamington Spa");
		for (String s : t0) {
			assertTrue(t0ex.contains(s));
		}
		assertEquals(t0ex.size(), t0.length);
		String[] t1 = rwc.listAllTermini("Cross City Line").split("\n");
		HashSet<String> t1ex = new HashSet<String>();
		t1ex.add("Lichfield Trent Valley");
		t1ex.add("Redditch");
		for (String s : t1) {
			assertTrue(t1ex.contains(s));
		}
		assertEquals(t1ex.size(), t1.length);
		String[] t2 = rwc.listAllTermini("Birmingham -- Rugby -- Northampton -- London").split("\n");
		HashSet<String> t2ex = new HashSet<String>();
		t2ex.add("Birmingham New Street");
		t2ex.add("London Euston");
		for (String s : t2) {
			assertTrue(t2ex.contains(s));
		}
		assertEquals(t2ex.size(), t2.length);
		String[] t3 = rwc.listAllTermini("Nuneaton -- Coventry").split("\n");
		HashSet<String> t3ex = new HashSet<String>();
		t3ex.add("Nuneaton");
		t3ex.add("Coventry");
		for (String s : t3) {
			assertTrue(t3ex.contains(s));
		}
		assertEquals(t3ex.size(), t3.length);
		String[] t4 = rwc.listAllTermini("Watford -- St Albans Abbey").split("\n");
		HashSet<String> t4ex = new HashSet<String>();
		t4ex.add("Watford Junction");
		t4ex.add("St Albans Abbey");
		for (String s : t4) {
			assertTrue(t4ex.contains(s));
		}
		assertEquals(t4ex.size(), t4.length);
		String[] t5 = rwc.listAllTermini("Bletchley -- Bedford").split("\n");
		HashSet<String> t5ex = new HashSet<String>();
		t5ex.add("Bletchley");
		t5ex.add("Bedford");
		for (String s : t5) {
			assertTrue(t5ex.contains(s));
		}
		assertEquals(t5ex.size(), t5.length);
		String[] t6 = rwc.listAllTermini("Crewe -- Stoke -- Stafford -- London").split("\n");
		HashSet<String> t6ex = new HashSet<String>();
		t6ex.add("Crewe");
		t6ex.add("London Euston");
		for (String s : t6) {
			assertTrue(t6ex.contains(s));
		}
		assertEquals(t6ex.size(), t6.length);
		String[] t7 = rwc.listAllTermini("Worcester -- Birmingham").split("\n");
		HashSet<String> t7ex = new HashSet<String>();
		t7ex.add("Worcester Foregate Street");
		t7ex.add("Dorridge");
		t7ex.add("Stratford-upon-Avon");
		for (String s : t7) {
			assertTrue(t7ex.contains(s));
		}
		assertEquals(t7ex.size(), t7.length);
		String[] t8 = rwc.listAllTermini("Smethwick Galton Bridge Connections").split("\n");
		HashSet<String> t8ex = new HashSet<String>();
		t8ex.add("Liverpool Lime Street");
		t8ex.add("Birmingham International");
		for (String s : t8) {
			assertTrue(t8ex.contains(s));
		}
		assertEquals(t8ex.size(), t8.length);
		String[] t9 = rwc.listAllTermini("Birmingham -- Stratford-upon-Avon").split("\n");
		HashSet<String> t9ex = new HashSet<String>();
		t9ex.add("Worcester Foregate Street");
		t9ex.add("Stratford-upon-Avon");
		for (String s : t9) {
			assertTrue(t9ex.contains(s));
		}
		assertEquals(t9ex.size(), t9.length);
		String[] t10 = rwc.listAllTermini("Birmingham -- Wolverhampton -- Telford -- Shrewsbury").split("\n");
		HashSet<String> t10ex = new HashSet<String>();
		t10ex.add("Birmingham International");
		t10ex.add("Shrewsbury");
		for (String s : t10) {
			assertTrue(t10ex.contains(s));
		}
		assertEquals(t10ex.size(), t10.length);
		String[] t11 = rwc.listAllTermini("Birmingham -- Worcester -- Hereford").split("\n");
		HashSet<String> t11ex = new HashSet<String>();
		t11ex.add("Birmingham Moor Street");
		t11ex.add("Birmingham New Street");
		t11ex.add("Hereford");
		for (String s : t11) {
			assertTrue(t11ex.contains(s));
		}
		assertEquals(t11ex.size(), t11.length);
	}

	@Test
	public void testListStations() throws PreviousNotFoundException, InvalidFormatException, IOException {
		setUp();

		String s0 = rwc.listStationsInLine("Birmingham -- Dorridge -- Leamington Spa");
		String s0x = "Birmingham Snow Hill\nBirmingham Moor Street\nBordesley\nSmall Heath\nTyseley\nAcocks Green\nOlton\nSolihull\nWidney Manor\nDorridge -- Lapworth -- Hatton -- Warwick -- Leamington Spa\nStratford-upon-Avon Parkway\nStratford-upon-Avon\n";

		String s1 = rwc.listStationsInLine("Cross City Line");
		String s1x = "Lichfield Trent Valley\nLichfield City\nShenstone\nBlake Street\nButlers Lane\nFour Oaks\nSutton Coldfield\nWylde Green\nChester Road\nErdington\nGravelly Hill\nAston\nDuddeston\nBirmingham New Street\nFive Ways\nUniversity\nSelly Oak\nBournville\nKings Norton\nNorthfield\nLongbridge\nBarnt Green\nAlvechurch\nRedditch\n";

		String s2 = rwc.listStationsInLine("Birmingham -- Rugby -- Northampton -- London");
		String s2x = "Birmingham New Street\nBirmingham International\nCoventry\nRugby\nLong Buckby\nNorthampton\nWolverton\nMilton Keynes Central\nBletchley\nLeighton Buzzard\nBerkhamsted\nHemel Hempstead\nWatford Junction\nLondon Euston\n";

		String s3 = rwc.listStationsInLine("Nuneaton -- Coventry");
		String s3x = "Nuneaton\nBermuda Park\nBedworth\nCoventry Arena\nCoventry\n";

		String s4 = rwc.listStationsInLine("Watford -- St Albans Abbey");
		String s4x = "Watford Junction\nWatford North\nGarston\nBricket Wood\nHow Wood\nPark Street\nSt Albans Abbey\n";

		String s5 = rwc.listStationsInLine("Bletchley -- Bedford");
		String s5x = "Bletchley\nFenny Stratford\nBow Brickhill\nWoburn Sands\nAspley Guise\nRidgmont\nLidlington\nMillbrook\nStewartby\nKempston Hardwick\nBedford St Johns\nBedford\n";

		String s6 = rwc.listStationsInLine("Crewe -- Stoke -- Stafford -- London");
		String s6x = "Crewe\nAlsager\nKidsgrove\nStoke-on-Trent\nStone\nStafford\nRugeley Trent Valley\nLichfield Trent Valley\nTamworth\nPolesworth\nAtherstone\nNuneaton\nRugby\nNorthampton\nMilton Keynes Central\nWatford Junction\nLondon Euston\n";

		String s7 = rwc.listStationsInLine("Worcester -- Birmingham");
		String s7x = "Worcester Foregate Street\nWorcester Shrub Hill\nDroitwich Spa\nHartlebury\nKidderminster\nBlakedown\nHagley\nStourbridge Junction\nLye\nCradley Heath\nOld Hill\nRowley Regis\nLangley Green\nSmethwick Galton Bridge\nThe Hawthorns\nJewellery Quarter\nBirmingham Snow Hill\nBirmingham Moor Street -- Whitlocks End -- Stratford-upon-Avon\nSolihull\nDorridge\n";

		String s8 = rwc.listStationsInLine("Smethwick Galton Bridge Connections");
		String s8x = "Liverpool Lime Street\nLiverpool South Parkway\nCrewe\nShrewsbury\nTelford Central\nWolverhampton\nSmethwick Galton Bridge\nBirmingham New Street\n";

		String s9 = rwc.listStationsInLine("Birmingham -- Stratford-upon-Avon");
		String s9x = "Worcester Foregate Street\nKidderminster\nStourbridge Junction\nBirmingham Snow Hill\nBirmingham Moor Street\nSmall Heath\nTyseley\nSpring Road\nHall Green\nYardley Wood\nShirley\nWhitlocks End\nWythall\nEarlswood\nThe Lakes\nWood End\nDanzey\nHenley-in-Arden\nWootton Wawen\nWilmcote\nStratford-upon-Avon Parkway\nStratford-upon-Avon\n";

		String s10 = rwc.listStationsInLine("Birmingham -- Wolverhampton -- Telford -- Shrewsbury");
		String s10x = "Birmingham International\nBirmingham New Street -- Sandwell & Dudley\nSmethwick Galton Bridge\nSandwell & Dudley\nWolverhampton\nBilbrook\nCodsall\nAlbrighton\nCosford\nShifnal\nTelford Central\nOakengates\nWellington\n";

		String s11 = rwc.listStationsInLine("Birmingham -- Worcester -- Hereford");
		String s11x = "Birmingham Moor Street\nBirmingham Snow Hill\nStourbridge Junction\nHagley\nKidderminster\nHartlebury\nDroitwich Spa -- Bromsgrove -- University -- Birmingham New Street\nWorcester Shrub Hill\nWorcester Foregate Street\nMalvern Link\nGreat Malvern\nColwall\nLedbury\nHereford\n";

	}

	@Test
	public void testGetPath() throws PreviousNotFoundException, InvalidFormatException, IOException, BothStationsNotPresentException {
		setUp();

		List<String[]> inputs = new LinkedList<String[]>();
		inputs.add(new String[]{"Liverpool Lime Street", "Stratford-upon-Avon"});
		inputs.add(new String[]{"Olton", "Birmingham Moor Street"});
		inputs.add(new String[]{"Birmingham Snow Hill", "Leamington Spa"});
		inputs.add(new String[]{"Hagley", "Hatton"});
		inputs.add(new String[]{"Nuneaton", "Stourbridge Junction"});
		inputs.add(new String[]{"Stratford-upon-Avon", "Acocks Green"});
		inputs.add(new String[]{"Acocks Green", "Stratford-upon-Avon"});
		for (String[] s : inputs) {
			String stationA = s[0];
			String stationB = s[1];
			String path = rwc.showPathBetween(stationA, stationB);
			String[] route = path.split("\n");
			assertTrue(stationA.equals(route[0]) || stationA.equals(route[route.length -1]));
			assertTrue(stationB.equals(route[0]) || stationB.equals(route[route.length - 1]));
			for (int i = 0; i < route.length - 2; i += 2) {
				assertTrue(rwc.getMap().stations.get(route[i]).getConnections().keySet().contains(route[i + 2]));
				assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1]));
			}
		}
	}
	
	@Test
	public void testGetPathRandomStations() throws PreviousNotFoundException, InvalidFormatException, IOException {
		setUp();
		Random rand = new Random();
		Station[] stations = rwc.getMap().getStations().values().toArray(new Station[rwc.getMap().getStations().values().size()]);
		List<String[]> inputs = new LinkedList<String[]>();
		for (int i = 0; i < 20; i++) {
			inputs.add(new String[] {stations[rand.nextInt(stations.length)].getName(), stations[rand.nextInt(stations.length)].getName()});
		}
		for (String[] s : inputs) {
			String stationA = s[0];
			System.out.println(stationA);
			String stationB = s[1];
			System.out.println(stationB);
			String path = rwc.showPathBetween(stationA, stationB);
			String[] route = path.split("\n");
			assertTrue(stationA.equals(route[0]) || stationA.equals(route[route.length -1]));
			assertTrue(stationB.equals(route[0]) || stationB.equals(route[route.length - 1]));
			for (int i = 0; i < route.length - 2; i += 2) {
				assertTrue(rwc.getMap().stations.get(route[i]).getConnections().keySet().contains(route[i + 2]));
				assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1]));
			}
		}
	}
	
	@Test
	public void testGetPathSpecific() throws PreviousNotFoundException, InvalidFormatException, IOException {
		setUp();
		String stationA = "Stewartby";
		String stationB = "Stratford-upon-Avon Parkway";
		String path = rwc.showPathBetween(stationA, stationB);
		String[] route = path.split("\n");
		assertTrue(stationA.equals(route[0]) || stationA.equals(route[route.length -1]));
		assertTrue(stationB.equals(route[0]) || stationB.equals(route[route.length - 1]));
		for (int i = 0; i < route.length - 2; i += 2) {
			assertTrue(rwc.getMap().stations.get(route[i]).getConnections().keySet().contains(route[i + 2]));
			assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1]));
		}
	}

}
