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
	String inPfad = "/home/programmieren/TestFiles/iCalender/";
	String ausPfad = "/home/programmieren/TestFiles/iCalender/a";
//	String[] kalender = {"TestKalender","Standard-KHG", "Google", "temp", "Abfallkalender" };
	String[] kalender = {"TestKalender"};

	public Ausprobieren() throws Exception {
		loggerhandling();
		try {
			System.out.println(new Date());
			for (String pkalender : kalender) {
				parser.kalenderEinlesen(iCalendarSammlung, bestimmenDateistamm("Testdaten/iCalender/" + pkalender + ".ics"));
				System.out.println(new Date());
				File iCalFile = new File(bestimmenDateistamm("Testdaten/iCalender/" + pkalender + "a.ics"));
				iCalWriter = new PrintWriter(iCalFile, "UTF-8");
				iCalWriter.println(iCalendarSammlung.get(iCalendarSammlung.size() - 1).ausgeben());
				iCalWriter.flush();
			}
//			parser.kalenderEinlesen(iCalendarSammlung, "/home/programmieren/TestFiles/iCalender/Google.ics");
//			System.out.println(new Date());
//			parser.kalenderEinlesen(iCalendarSammlung, "/home/programmieren/TestFiles/iCalender/Standard-KHG.ics");
//			System.out.println(new Date());

			// Standard-KHG TestKalender Google temp Abfallkalender

//			if (System.getProperty("os.name").equals("Linux")) {
//				parser.kalenderEinlesen(iCalendarSammlung,
//						"/home/programmieren/Git-Repositories/TestRepository/IgnoreForGit/iCalender/TestKalender0.ics");
//			} else {
//				parser.kalenderEinlesen(iCalendarSammlung,
//						"C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics");
//			}
		
		} finally {

		}
//		for (GuKKiCaliCalendar iCaliCalendar : iCalendarSammlung) {
//			System.out.println(iCaliCalendar.toString("CAETJZF"));
////			System.out.println(iCaliCalendar.toString());
//			System.out.println("\n\n" + new Date().toString() + "\n\n");
//		}
		logger.finest("beendet");
		// TODO Automatisch generierter Konstruktorstub
	}
//	public Ausprobieren() throws Exception {
//		System.out.println(DateFormat.getDateInstance().format(new Date()));
//	}

	public static void main(String[] args) throws Exception {
		/* Klasse DateFormat für Datumsformatierung */
		/* Klasse Calendar für Datumshandling */

		Ausprobieren test = new Ausprobieren();
	}

	private void loggerhandling() {
		logger.setLevel(Level.INFO);
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
//	public static void main(String[] args) throws Exception {
//		// TODO Automatisch generierter Methodenstub
//		GuKKiCalProperty property = null;
////		property = new GuKKiCalProperty("CREATED:20151123T103732Z", "CREATED");
////		property = new GuKKiCalProperty("X-LIC-ERROR;X-LIC-ERRORTYPE=VALUE-PARSE-ERROR:No value for DESCRIPTION property. Removing entire property:", "X-LIC-ERROR");
//		property = new GuKKiCalProperty("SUMMARY;CN=\"TEST:Angiologie;Ulrich\"", "SUMMARY");}

//	static GuKKiCalProperty eintragen(String propertyZeile) throws Exception {
////		System.out.println("GuKKiCalComponent.checkDTSTAMP begonnen");
////		System.out.println("Zeile="+zeichenkette);
//		
//		
////		System.out.println(property);
//		// System.out.println("Temp=" + zTemp);
////		System.out.println("GuKKiCalComponent.checkDTSTAMP beendet");
//		return property;
//	}


/* Bestimmen des Betriebssysteme */
//		String OS = System.getProperty("os.name");
//		System.out.println(OS);
//
/* eindeutige UID generieren lassen (für neue Events) */
//		String uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);

/* Erkennen der korrekten Dateienstruktur auf Windows-Laptop */

//		BufferedReader temp = null;
//		String inPath = "C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics";
//		try {
//			temp = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));
//			String zeile = temp.readLine();
//			System.out.println(zeile);
//		}
//		finally {
//			
//		}
	/**
	 * @param string
	 * @return
	 */
	private String bestimmenDateistamm(String pfad) {

		if (osIstLinux()) {
			return "/home/programmieren/" + pfad.replace("\\", "/");
		} else {
			return System.getProperty("user.home") + "\\Desktop\\Workspace\\" + pfad.replace("/", "\\");
		}
	}

	/**
	 * Feststellen ob unter Linux oder Windows gearbeitet wird
	 */
	private boolean osIstLinux() {
		return System.getProperty("os.name").equals("Linux");
	}

}
