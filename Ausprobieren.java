
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import component.GuKKiCalvCalendar;
import main.GuKKiCalParser;

public class Ausprobieren {
	Logger logger = Logger.getLogger("GuKKiCal");

	ArrayList<GuKKiCalvCalendar> vCalendarSammlung = new ArrayList<GuKKiCalvCalendar>();
	GuKKiCalParser parser = new GuKKiCalParser();

//	BufferedWriter iCalWriter = null;
	PrintWriter iCalWriter = null;
	String inPfad = machineHandling("testfiles/iCalender/");
	String ausPfad = machineHandling("testfiles/iCalender/a");
	String[] kalender = { "TestKalender", "Standard-KHG", "Google", "temp", "Abfallkalender" };
//	String[] kalender = { "Abfallkalender" };

	boolean ausgeben = false;

	public Ausprobieren() throws Exception {
		loggerhandling();
		try {
			System.out.println(new Date() + " Startzeit");
			for (String pkalender : kalender) {
				parser.kalenderEinlesen(vCalendarSammlung, inPfad + pkalender + ".ics");
				System.out.println(new Date() + " " + pkalender);
				if (ausgeben) {
					String ausgebenauf = ausPfad + pkalender + ".ics";
					File iCalFile = new File(ausgebenauf);
					iCalWriter = new PrintWriter(iCalFile, "UTF-8");
					iCalWriter.println(vCalendarSammlung.get(vCalendarSammlung.size() - 1).ausgeben());
					iCalWriter.flush();
				}
//				System.out.println(iCalendarSammlung.get(iCalendarSammlung.size() - 1).toString("CETJAZDSF"));
			}
		} finally {

		}
		logger.finest("beendet");
		// TODO Automatisch generierter Konstruktorstub
	}

	public static void main(String[] args) throws Exception {
		/* Klasse DateFormat für Datumsformatierung */
		/* Klasse Calendar für Datumshandling */

		Ausprobieren test = new Ausprobieren();
	}

	private void loggerhandling() {
		logger.setLevel(Level.FINE);
//		Handler handler = new FileHandler("/home/programmieren/TestFiles/iCalender/temp.log");
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		handler.setFormatter(new Formatter() {
			@Override
			public String format(LogRecord record) {
				return record.getSourceClassName() + "." + record.getSourceMethodName() + ": " + record.getMessage()
						+ "\n";
			}

		});
		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
		logger.finest("begonnen");
	}

	private String machineHandling(String verzeichnis) {
		if (System.getProperty("os.name").equals("Linux")) {
			return "/home/programmieren/" + verzeichnis;
		} else {
			return System.getProperty("user.home") + "/Workspace/" + verzeichnis;
		}
	}
}
