import org.junit.Test;

import exceptions.BothStationsAlreadyPresentException;
import exceptions.InvalidFormatException;
import exceptions.InvalidSubLineException;
import exceptions.LineNotFoundException;
import setup.RailwayCreator;
import java.io.EOFException;
import java.io.IOException;

public class TestRailwayCreator {

	RailwayCreator rwc;
	
	@Test
	public void test() throws LineNotFoundException, BothStationsAlreadyPresentException, InvalidFormatException, IOException, InvalidSubLineException {
		rwc = new RailwayCreator();
		boolean willContinue = true;
		while(willContinue) {
			willContinue = rwc.processInputLine();
		}
		System.out.println(rwc.getMap());
	}
}
