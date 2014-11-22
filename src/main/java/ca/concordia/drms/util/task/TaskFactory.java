package ca.concordia.drms.util.task;

import java.io.IOException;
import java.util.Arrays;

import ca.concordia.drms.orb.LibraryServer;
import ca.concordia.drms.util.*;
import ca.concordia.drms.util.parser.ConsoleParser;

public class TaskFactory {
	public static Task create(LibraryServer libraryServer, String institution, String argument) throws IOException {
		String command = ConsoleParser.parseCommand(argument);
		Task task = null;
		switch (Arrays.asList(Configuration.ALLOWED_COMMANDS).indexOf(command)) {
		case Configuration.BONJOUR:
			task = new BonjourTask(argument);
			break;
		case Configuration.OVERDUE:
			task = new OverdueTask(libraryServer, institution, argument);
			break;
		case Configuration.ACCOUNT:
			task = new AccountTask(libraryServer, institution, argument);
			break;
		case Configuration.RESERVATION:
			task = new ReservationTask(libraryServer, institution, argument);
			break;
		case Configuration.INTERLIB:
			task = new ReservationTask(libraryServer, institution, argument, true);
			break;
		case Configuration.EXIT:
			task = new ExitTask(libraryServer, institution, argument);
			break;
		}
		return task;
	}
}