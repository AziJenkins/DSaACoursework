import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import controller.RailwayController;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
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
		String s0x = "Birmingham Snow Hill\r\n|\r\nBirmingham Moor Street\r\n|\r\nBordesley\r\n|\r\nSmall Heath\r\n|\r\nTyseley\r\n|\r\nAcocks Green\r\n|\r\nOlton\r\n|\r\nSolihull\r\n|\r\nWidney Manor\r\n|\r\nDorridge -- Lapworth -- Hatton -- Warwick -- Leamington Spa\r\n|\r\nStratford-upon-Avon Parkway\r\n|\r\nStratford-upon-Avon";
		assertTrue(s0x.equals(s0));
		String s1 = rwc.listStationsInLine("Cross City Line");
		String s1x = "Lichfield Trent Valley\r\n|\r\nLichfield City\r\n|\r\nShenstone\r\n|\r\nBlake Street\r\n|\r\nButlers Lane\r\n|\r\nFour Oaks\r\n|\r\nSutton Coldfield\r\n|\r\nWylde Green\r\n|\r\nChester Road\r\n|\r\nErdington\r\n|\r\nGravelly Hill\r\n|\r\nAston\r\n|\r\nDuddeston\r\n|\r\nBirmingham New Street\r\n|\r\nFive Ways\r\n|\r\nUniversity\r\n|\r\nSelly Oak\r\n|\r\nBournville\r\n|\r\nKings Norton\r\n|\r\nNorthfield\r\n|\r\nLongbridge\r\n|\r\nBarnt Green\r\n|\r\nAlvechurch\r\n|\r\nRedditch";
		assertEquals(s1x,s1);
		String s2 = rwc.listStationsInLine("Birmingham -- Rugby -- Northampton -- London");
		String s2x = "Birmingham New Street\r\n|\r\nBirmingham International\r\n|\r\nCoventry\r\n|\r\nRugby\r\n|\r\nLong Buckby\r\n|\r\nNorthampton\r\n|\r\nWolverton\r\n|\r\nMilton Keynes Central\r\n|\r\nBletchley\r\n|\r\nLeighton Buzzard\r\n|\r\nBerkhamsted\r\n|\r\nHemel Hempstead\r\n|\r\nWatford Junction\r\n|\r\nLondon Euston";
		assertEquals(s2x,s2);
		String s3 = rwc.listStationsInLine("Nuneaton -- Coventry");
		String s3x = "Nuneaton\r\n|\r\nBermuda Park\r\n|\r\nBedworth\r\n|\r\nCoventry Arena\r\n|\r\nCoventry";
		assertEquals(s3x,s3);
		String s4 = rwc.listStationsInLine("Watford -- St Albans Abbey");
		String s4x = "Watford Junction\r\n|\r\nWatford North\r\n|\r\nGarston\r\n|\r\nBricket Wood\r\n|\r\nHow Wood\r\n|\r\nPark Street\r\n|\r\nSt Albans Abbey";
		assertEquals(s4x,s4);
		String s5 = rwc.listStationsInLine("Bletchley -- Bedford");
		String s5x = "Bletchley\r\n|\r\nFenny Stratford\r\n|\r\nBow Brickhill\r\n|\r\nWoburn Sands\r\n|\r\nAspley Guise\r\n|\r\nRidgmont\r\n|\r\nLidlington\r\n|\r\nMillbrook\r\n|\r\nStewartby\r\n|\r\nKempston Hardwick\r\n|\r\nBedford St Johns\r\n|\r\nBedford";
		assertEquals(s5x,s5);
		String s6 = rwc.listStationsInLine("Crewe -- Stoke -- Stafford -- London");
		String s6x = "Crewe\r\n|\r\nAlsager\r\n|\r\nKidsgrove\r\n|\r\nStoke-on-Trent\r\n|\r\nStone\r\n|\r\nStafford\r\n|\r\nRugeley Trent Valley\r\n|\r\nLichfield Trent Valley\r\n|\r\nTamworth\r\n|\r\nPolesworth\r\n|\r\nAtherstone\r\n|\r\nNuneaton\r\n|\r\nRugby\r\n|\r\nNorthampton\r\n|\r\nMilton Keynes Central\r\n|\r\nWatford Junction\r\n|\r\nLondon Euston";
		assertEquals(s6x,s6);
		String s7 = rwc.listStationsInLine("Worcester -- Birmingham");
		String s7x = "Worcester Foregate Street\r\n|\r\nWorcester Shrub Hill\r\n|\r\nDroitwich Spa\r\n|\r\nHartlebury\r\n|\r\nKidderminster\r\n|\r\nBlakedown\r\n|\r\nHagley\r\n|\r\nStourbridge Junction\r\n|\r\nLye\r\n|\r\nCradley Heath\r\n|\r\nOld Hill\r\n|\r\nRowley Regis\r\n|\r\nLangley Green\r\n|\r\nSmethwick Galton Bridge\r\n|\r\nThe Hawthorns\r\n|\r\nJewellery Quarter\r\n|\r\nBirmingham Snow Hill\r\n|\r\nBirmingham Moor Street -- Whitlocks End -- Stratford-upon-Avon\r\n|\r\nSolihull\r\n|\r\nDorridge";
		assertEquals(s7x,s7);
		String s8 = rwc.listStationsInLine("Smethwick Galton Bridge Connections");
		String s8x = "Liverpool Lime Street\r\n|\r\nLiverpool South Parkway\r\n|\r\nCrewe\r\n|\r\nShrewsbury\r\n|\r\nTelford Central\r\n|\r\nWolverhampton\r\n|\r\nSmethwick Galton Bridge\r\n|\r\nBirmingham New Street\r\n|\r\nBirmingham International";
		assertEquals(s8x,s8);
		String s9 = rwc.listStationsInLine("Birmingham -- Stratford-upon-Avon");
		String s9x = "Worcester Foregate Street\r\n|\r\nKidderminster\r\n|\r\nStourbridge Junction\r\n|\r\nBirmingham Snow Hill\r\n|\r\nBirmingham Moor Street\r\n|\r\nSmall Heath\r\n|\r\nTyseley\r\n|\r\nSpring Road\r\n|\r\nHall Green\r\n|\r\nYardley Wood\r\n|\r\nShirley\r\n|\r\nWhitlocks End\r\n|\r\nWythall\r\n|\r\nEarlswood\r\n|\r\nThe Lakes\r\n|\r\nWood End\r\n|\r\nDanzey\r\n|\r\nHenley-in-Arden\r\n|\r\nWootton Wawen\r\n|\r\nWilmcote\r\n|\r\nStratford-upon-Avon Parkway\r\n|\r\nStratford-upon-Avon";
		assertEquals(s9x,s9);
		String s10 = rwc.listStationsInLine("Birmingham -- Wolverhampton -- Telford -- Shrewsbury");
		String s10x = "Birmingham International\r\n|\r\nBirmingham New Street -- Sandwell & Dudley\r\n|\r\nSmethwick Galton Bridge\r\n|\r\nSandwell & Dudley\r\n|\r\nWolverhampton\r\n|\r\nBilbrook\r\n|\r\nCodsall\r\n|\r\nAlbrighton\r\n|\r\nCosford\r\n|\r\nShifnal\r\n|\r\nTelford Central\r\n|\r\nOakengates\r\n|\r\nWellington\r\n|\r\nShrewsbury";
		assertEquals(s10x,s10);
		String s11 = rwc.listStationsInLine("Birmingham -- Worcester -- Hereford");
		String s11x = "Birmingham Moor Street\r\n|\r\nBirmingham Snow Hill\r\n|\r\nStourbridge Junction\r\n|\r\nHagley\r\n|\r\nKidderminster\r\n|\r\nHartlebury\r\n|\r\nDroitwich Spa -- Bromsgrove -- University -- Birmingham New Street\r\n|\r\nWorcester Shrub Hill\r\n|\r\nWorcester Foregate Street\r\n|\r\nMalvern Link\r\n|\r\nGreat Malvern\r\n|\r\nColwall\r\n|\r\nLedbury\r\n|\r\nHereford";
		assertEquals(s11x,s11);
	}

	@Test
	public void testGetPath() throws PreviousNotFoundException, InvalidFormatException, IOException {
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
				assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1].split(" ")[0]));
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
				assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1].split(" ")[0]));
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
			assertEquals((int) rwc.getMap().stations.get(route[i]).getConnections().get(route[i + 2]), (int) Integer.parseInt(route[i + 1].split(" ")[0]));
		}
	}

}
