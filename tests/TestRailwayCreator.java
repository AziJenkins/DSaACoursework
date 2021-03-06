import org.junit.Test;

import exceptions.BothStationsAlreadyPresentException;
import exceptions.InvalidFormatException;
import exceptions.InvalidSubLineException;
import exceptions.LineNotFoundException;
import setup.RailwayCreator;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;

public class TestRailwayCreator {

	RailwayCreator rwc;
	
	@Test
	public void test() throws LineNotFoundException, BothStationsAlreadyPresentException, InvalidFormatException, IOException, InvalidSubLineException {
		rwc = new RailwayCreator();
		boolean willContinue = true;
		while(willContinue) {
			willContinue = rwc.processInputLine();
		}
		rwc.close();
		PrintWriter pw = new PrintWriter("Output.txt", "UTF-8");
		pw.println(rwc.getMap().toString());
		pw.close();
	}
}
