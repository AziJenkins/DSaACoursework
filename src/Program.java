import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import controller.RailwayController;
import exceptions.InvalidFormatException;
import exceptions.PreviousNotFoundException;
import setup.RailwayCreator;
import view.TUI;

public class Program {

	public static void main(String[] args) throws FileNotFoundException, InvalidFormatException, IOException, PreviousNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please choose an option:\r\n1: WestMidlandsRailway.csv is in the same folder as me\r\n2: I would like to specify a path to a csv file\r\n");
		boolean cont = true;
		String response;
		String file = null;
		while (cont) {
			response = in.nextLine();
			switch (response) {
			case "1":
				file = "";
				cont = false;
				break;
			case "2":
				System.out.println("Enter the path:\r\n");
				file = in.nextLine();
				cont = false;
				break;
			default : 
				System.out.println("Please choose an option (1 or 2)");
			}
		}
		RailwayCreator create = new RailwayCreator(file);
		RailwayController controller = new RailwayController(create.processFile());
		new TUI(controller);
	}

}
