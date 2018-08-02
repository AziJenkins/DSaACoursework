import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import controller.RailwayController;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
import model.Line;
import model.Map;
import model.Station;
import setup.RailwayCreator;

public class TestRailwayController {

	private RailwayController rwc;

	public void setUp() throws PreviousNotFoundException, InvalidFormatException, IOException {
		RailwayCreator creator = new RailwayCreator();
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
		for (String s: t0) {
			assertTrue(t0ex.contains(s));
		}
		assertEquals(t0ex.size(), t0.length);
		String[] t1 = rwc.listAllTermini("Cross City Line").split("\n");
		HashSet<String> t1ex = new HashSet<String>();
		t1ex.add("Lichfield Trent Valley");
		t1ex.add("Redditch");
		for (String s: t1) {
			assertTrue(t1ex.contains(s));
		}
		assertEquals(t1ex.size(), t1.length);
		String[] t2 = rwc.listAllTermini("Birmingham -- Rugby -- Northampton -- London").split("\n");
		HashSet<String> t2ex =  new HashSet<String>();
		t2ex.add("Birmingham New Street");
		t2ex.add("London Euston");
		for (String s: t2) {
			assertTrue(t2ex.contains(s));
		}
		assertEquals(t2ex.size(), t2.length);
		String[] t3 = rwc.listAllTermini("Nuneaton -- Coventry").split("\n");
		HashSet<String> t3ex =  new HashSet<String>();
		t3ex.add("Nuneaton");
		t3ex.add("Coventry");
		for (String s: t3) {
			assertTrue(t3ex.contains(s));
		}
		assertEquals(t3ex.size(), t3.length);
		String[] t4 = rwc.listAllTermini("Watford -- St Albans Abbey").split("\n");
		HashSet<String> t4ex =  new HashSet<String>();
		t4ex.add("Watford Junction");
		t4ex.add("St Albans Abbey");
		for (String s: t4) {
			assertTrue(t4ex.contains(s));
		}
		assertEquals(t4ex.size(), t4.length);
		String[] t5 = rwc.listAllTermini("Bletchley -- Bedford").split("\n");
		HashSet<String> t5ex =  new HashSet<String>();
		t5ex.add("Bletchley");
		t5ex.add("Bedford");
		for (String s: t5) {
			assertTrue(t5ex.contains(s));
		}
		assertEquals(t5ex.size(), t5.length);
		String[] t6 = rwc.listAllTermini("Crewe -- Stoke -- Stafford -- London").split("\n");
		HashSet<String> t6ex =  new HashSet<String>();
		t6ex.add("Crewe");
		t6ex.add("London Euston");
		for (String s: t6) {
			assertTrue(t6ex.contains(s));
		}
		assertEquals(t6ex.size(), t6.length);
		String[] t7 = rwc.listAllTermini("Worcester -- Birmingham").split("\n");
		HashSet<String> t7ex =  new HashSet<String>();
		t7ex.add("Worcester Foregate Street");
		t7ex.add("Dorridge");
		t7ex.add("Stratford-upon-Avon");
		for (String s: t7) {
			assertTrue(t7ex.contains(s));
		}
		assertEquals(t7ex.size(), t7.length);
		String[] t8 = rwc.listAllTermini("Smethwick Galton Bridge Connections").split("\n");
		HashSet<String> t8ex =  new HashSet<String>();
		t8ex.add("Liverpool Lime Street");
		t8ex.add("Birmingham International");
		for (String s: t8) {
			assertTrue(t8ex.contains(s));
		}
		assertEquals(t8ex.size(), t8.length);
		String[] t9 = rwc.listAllTermini("Birmingham -- Stratford-upon-Avon").split("\n");
		HashSet<String> t9ex =  new HashSet<String>();
		t9ex.add("Worcester Foregate Street");
		t9ex.add("Stratford-upon-Avon");
		for (String s: t9) {
			assertTrue(t9ex.contains(s));
		}
		assertEquals(t9ex.size(), t9.length);
		String[] t10 = rwc.listAllTermini("Birmingham -- Wolverhampton -- Telford -- Shrewsbury").split("\n");
		HashSet<String> t10ex =  new HashSet<String>();
		t10ex.add("Birmingham International");
		t10ex.add("Shrewsbury");
		for (String s: t10) {
			assertTrue(t10ex.contains(s));
		}
		assertEquals(t10ex.size(), t10.length);
		String[] t11 = rwc.listAllTermini("Birmingham -- Worcester -- Hereford").split("\n");
		HashSet<String> t11ex =  new HashSet<String>();
		t11ex.add("Birmingham Moor Street");
		t11ex.add("Birmingham New Street");
		t11ex.add("Hereford");
		for (String s: t11) {
			assertTrue(t11ex.contains(s));
		}
		assertEquals(t11ex.size(), t11.length);
	}

}
