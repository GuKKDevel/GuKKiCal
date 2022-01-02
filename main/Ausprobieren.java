package main;

import java.io.PrintWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class Ausprobieren {
	Logger logger = Logger.getLogger("GuKKiCal");

	ArrayList<GuKKiCaliCalendar> iCalendarSammlung = new ArrayList<GuKKiCaliCalendar>();
	GuKKiCalParser parser = new GuKKiCalParser();

//	BufferedWriter iCalWriter = null;
	PrintWriter iCalWriter = null;
	String inPfad = machineHandling("TestFiles/iCalender/");
	String ausPfad = machineHandling("TestFiles/iCalender/a");
//	String[] kalender = {"TestKalender","Standard-KHG", "Google", "temp", "Abfallkalender" };
	String[] kalender = { "aTestKalender" };

	public Ausprobieren() throws Exception {
		loggerhandling();
		try {
			System.out.println(new Date());
			for (String pkalender : kalender) {
				parser.kalenderEinlesen(iCalendarSammlung, inPfad + kalender[0] + ".ics");
				System.out.println(new Date());
				String ausgebenauf = ausPfad + pkalender + ".ics";
				File iCalFile = new File(ausgebenauf);
				iCalWriter = new PrintWriter(iCalFile, "UTF-8");
				iCalWriter.println(iCalendarSammlung.get(iCalendarSammlung.size() - 1).ausgeben());
				iCalWriter.flush();
				System.out.println(iCalendarSammlung.get(iCalendarSammlung.size() - 1).toString("CAETJZF"));
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
		handler.setLevel(Level.FINEST);
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
			return "P:/" + verzeichnis;
		}
	}
}
