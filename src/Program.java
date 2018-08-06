import java.io.FileNotFoundException;
import java.io.IOException;

import controller.RailwayController;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
import setup.RailwayCreator;
import view.TUI;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, InvalidFormatException, IOException, PreviousNotFoundException {
		RailwayCreator create = new RailwayCreator("");
		RailwayController controller = new RailwayController(create.processFile());
		new TUI(controller);
	}

}
