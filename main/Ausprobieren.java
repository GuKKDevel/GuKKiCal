package main;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class Ausprobieren {
	
//	GuKKiCal vCalendarSammlung = new GuKKiCal();
	
	public Ausprobieren() {
		// TODO Automatisch generierter Konstruktorstub
	}
	public static void main(String[] args) throws Exception {
		System.out.println("GuKKiCalParser.main <static> begonnen");
		GuKKiCal kalendersammlung = new GuKKiCal();
		try {
			GuKKiCalParser parser = new GuKKiCalParser();
			if (System.getProperty("os.name").equals("Linux")) {
				parser.kalenderEinlesen(											// Standard-KHG TestKalender Google temp
						"/home/programmieren/TestFiles/iCalender/temp.ics",
						kalendersammlung);
			} else {
				parser.kalenderEinlesen(
						"C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics",
						kalendersammlung);
			}
//			parser.kalenderEinlesen(
//					"/home/programmieren/Git-Repositories/TestRepository/IgnoreForGit/iCalender/TestKalender0.ics", vCalendarSammlung);

		} finally {

		}
		System.out.println("GuKKiCalParser.main <static> beendet");
	}
//	public static void main(String[] args) throws Exception {
//		// TODO Automatisch generierter Methodenstub
//		GuKKiCalProperty property = null;
////		property = new GuKKiCalProperty("CREATED:20151123T103732Z", "CREATED");
////		property = new GuKKiCalProperty("X-LIC-ERROR;X-LIC-ERRORTYPE=VALUE-PARSE-ERROR:No value for DESCRIPTION property. Removing entire property:", "X-LIC-ERROR");
//		property = new GuKKiCalProperty("SUMMARY;CN=\"TEST:Angiologie;Ulrich\"", "SUMMARY");}
	
//	static GuKKiCalProperty eintragen(String propertyZeile) throws Exception {
////		System.out.println("GuKKiCalvComponent.checkDTSTAMP begonnen");
////		System.out.println("Zeile="+zeichenkette);
//		
//		
////		System.out.println(property);
//		// System.out.println("Temp=" + zTemp);
////		System.out.println("GuKKiCalvComponent.checkDTSTAMP beendet");
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