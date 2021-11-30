package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.*;

public class Ausprobieren {

	public Ausprobieren() throws Exception {
		Logger logger = Logger.getLogger("GuKKiCal");
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
		
		ArrayList<GuKKiCaliCalendar> iCalendarSammlung = new ArrayList<GuKKiCaliCalendar>();

		try {
		
			GuKKiCalParser parser = new GuKKiCalParser();
			System.out.println(new Date());
			parser.kalenderEinlesen(iCalendarSammlung, "/home/programmieren/TestFiles/iCalender/TestKalender.ics");
//			System.out.println(new Date());
//			parser.kalenderEinlesen(iCalendarSammlung, "/home/programmieren/TestFiles/iCalender/Google.ics");
//			System.out.println(new Date());
//			parser.kalenderEinlesen(iCalendarSammlung, "/home/programmieren/TestFiles/iCalender/Standard-KHG.ics");
			System.out.println(new Date());

			// Standard-KHG TestKalender Google temp Abfallkalender

			if (System.getProperty("os.name").equals("Linux")) {
			} else {
				parser.kalenderEinlesen(iCalendarSammlung,
						"C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics");
			}
//			parser.kalenderEinlesen(
//					"/home/programmieren/Git-Repositories/TestRepository/IgnoreForGit/iCalender/TestKalender0.ics", vCalendarSammlung);
		} finally {

		}
		for (GuKKiCaliCalendar iCaliCalendar : iCalendarSammlung) {
			System.out.println(iCaliCalendar.toString("CZ"));
			System.out.println("\n\n" + new Date().toString() + "\n\n");
		}
		logger.finest("beendet");
		// TODO Automatisch generierter Konstruktorstub
	}

	public static void main(String[] args) throws Exception {
		Ausprobieren test = new Ausprobieren();
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

}

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