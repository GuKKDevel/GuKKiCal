package main;

import exceptions.*;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * @author Karl-Heinz Gödderz (GuKKDevel)
 *
 */
public class GuKKiCalParser {

	GuKKiCal kalendersammlung = new GuKKiCal();
	GuKKiCalvCalendar aktuellerKalender = null;
	/*
	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
	 */
	boolean vCalendarBearbeiten = false;
	String vCalendarDaten = "";
	boolean vEventBearbeiten = false;
	String vEventDaten = "";
	boolean vTodoBearbeiten = false;
	String vTodoDaten = "";
	boolean vJournalBearbeiten = false;
	String vJournalDaten = "";
	boolean vFreeBusyBearbeiten = false;
	String vFreeBusyDaten = "";
	boolean vTimezoneBearbeiten = false;
	String vTimezoneDaten = "";
	boolean vAlarmBearbeiten = false;
	String vAlarmDaten = "";

	String nz = "\n";

	public GuKKiCalParser() throws Exception {
//		System.out.println("GuKKiCalParser Konstruktor begonnen");
		// TODO Automatisch generierter Konstruktorstub
//		System.out.println("GuKKiCalParser Konstruktor beendet");
	}

	public static void main(String[] args) throws Exception {
//		System.out.println("GuKKiCalParser.main <static> begonnen");
		try {
			GuKKiCalParser parser = new GuKKiCalParser();
			if (System.getProperty("os.name").equals("Linux")) {
				parser.kalenderEinlesen(
						"/home/programmieren/Git-Repositories/TestRepository/IgnoreForGit/iCalender/TestKalender.ics",
						parser.kalendersammlung);
			} else {
				parser.kalenderEinlesen(
						"C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics",
						parser.kalendersammlung);
			}
//			parser.kalenderEinlesen(
//					"/home/programmieren/Git-Repositories/TestRepository/IgnoreForGit/iCalender/TestKalender0.ics", kalendersammlung);

		} finally {

		}
//		System.out.println("GuKKiCalParser.main <static> beendet");
	}

	/**
	 * @param inPath Pfad der Eingabedatei für den Kalender als String
	 * 
	 * @throws IOException
	 */

	private void kalenderEinlesen(String inPath, GuKKiCal kalendersammlung) throws Exception {
//		System.out.println("GuKKiCalParser.kalenderEinlesen begonnen");
		int kalenderNummer = 0;
		/*
		 * Datenstrom zur Verarbeitung der Kalenderdaten
		 */
		BufferedReader iCalDatenstrom = null; /* Datenstrom */
		String zeile; /* Datenzeile */

		try {
			iCalDatenstrom = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));

			while ((zeile = iCalDatenstrom.readLine()) != null) {
//				System.out.println(":"+zeile+":");
				switch (zeile) {
				case "BEGIN:VCALENDAR": {
					vCalendarBearbeiten = true;
//					System.out.println("BEGIN:VCALENDAR gefunden");
					kalenderNummer = +1;
					vCalendarDaten = zeile + nz;
					break;
				}
				case "END:VCALENDAR": {
					vCalendarBearbeiten = false;
//					System.out.println("END:VCALENDAR gefunden");
					vCalendarDaten += zeile + nz;
					kalenderAufbauen(kalendersammlung, vCalendarDaten, inPath, kalenderNummer);

					break;
				}
				case "BEGIN:VEVENT": {
					vEventBearbeiten = true;
//					System.out.println("BEGIN:VEVENT gefunden");
					vEventDaten += zeile + nz;
					break;
				}
				case "END:VEVENT": {
					vEventBearbeiten = false;
//					System.out.println("END:VEVENT gefunden");
					vEventDaten += zeile + nz;
					break;
				}
				case "BEGIN:VTODO": {
					vTodoBearbeiten = true;
//					System.out.println("BEGIN:VTODO gefunden");
					vTodoDaten += zeile + nz;
					break;
				}
				case "END:VTODO": {
					vTodoBearbeiten = false;
//					System.out.println("END:VTODO gefunden");
					vTodoDaten += zeile + nz;
					break;
				}
				case "BEGIN:VJOURNAL": {
					vJournalBearbeiten = true;
//					System.out.println("BEGIN:VJOURNAL gefunden");
					vJournalDaten += zeile + nz;
					break;
				}
				case "END:VJOURNAL": {
					vJournalBearbeiten = false;
//					System.out.println("END:VJOURNAL gefunden");
					vJournalDaten += zeile + nz;
					break;
				}
				case "BEGIN:VFREEBUSY": {
					vFreeBusyBearbeiten = true;
//					System.out.println("BEGIN:VFREEBUSY gefunden");
					vFreeBusyDaten += zeile + nz;
					break;
				}
				case "END:VFREEBUSY": {
					vFreeBusyBearbeiten = false;
//					System.out.println("END:VFREEBUSY gefunden");
					vFreeBusyDaten += zeile + nz;
					break;
				}
				case "BEGIN:VTIMEZONE": {
					vTimezoneBearbeiten = true;
//					System.out.println("BEGIN:VTIMEZONE gefunden");
					vTimezoneDaten += zeile + nz;
					break;
				}
				case "END:VTIMEZONE": {
					vTimezoneBearbeiten = false;
//					System.out.println("END:VTIMEZONE gefunden");
					vTimezoneDaten += zeile + nz;
					break;
				}
				case "BEGIN:VALARM": {
					vAlarmBearbeiten = true;
//					System.out.println("BEGIN:VALARM gefunden");
					vAlarmDaten += zeile + nz;
					break;
				}
				case "END:VALARM": {
					vAlarmBearbeiten = false;
//					System.out.println("END:VALARM gefunden");
					vAlarmDaten += zeile + nz;
					break;
				}
				default: {
					if (vEventBearbeiten) {
						if (vTodoBearbeiten | vJournalBearbeiten | vFreeBusyBearbeiten | vTimezoneBearbeiten
								| vAlarmBearbeiten) {
							throw new GuKKiCalFKalenderdaten("Fehler vEvent");
						} else {
							vEventDaten += zeile + nz;
//							if (!zeile.substring(0, 1).equals(" "))
//								vEventDaten += nz;
						}
					} else if (vTodoBearbeiten) {
						if (vJournalBearbeiten | vFreeBusyBearbeiten | vTimezoneBearbeiten | vAlarmBearbeiten) {
							throw new GuKKiCalFKalenderdaten("Fehler vTodo");
						} else {
							vTodoDaten += zeile + nz;
						}
					} else if (vJournalBearbeiten) {
						if (vFreeBusyBearbeiten | vTimezoneBearbeiten | vAlarmBearbeiten) {
							throw new GuKKiCalFKalenderdaten("Fehler vJournal");
						} else {
							vJournalDaten += zeile + nz;
						}
					} else if (vFreeBusyBearbeiten) {
						if (vTimezoneBearbeiten | vAlarmBearbeiten) {
							throw new GuKKiCalFKalenderdaten("Fehler vFreeBusy");
						} else {
							vFreeBusyDaten += zeile + nz;
						}
					} else if (vTimezoneBearbeiten) {
						if (vAlarmBearbeiten) {
							throw new GuKKiCalFKalenderdaten("Fehler vTimezone");
						} else {
							vTimezoneDaten += zeile + nz;
						}
					} else if (vAlarmBearbeiten) {
						vAlarmDaten += zeile + nz;
					} else {
						vCalendarDaten += zeile + nz;
					}
//					System.out.println("vC="+vCalendarBearbeiten+ "<-->"+vCalendarDaten+"<-----"+nz);
//					System.out.println("vE="+vEventBearbeiten+"<-->"+vEventDaten+"<-----"+nz);
//					System.out.println(vTodoDaten);
//					System.out.println(vJournalDaten);
//					System.out.println(vFreeBusyDaten);
//					System.out.println(vTimezoneDaten);
//					System.out.println(vAlarmDaten);
				}
				}
//				System.out.println("#---------#---------#---------#");
//				System.out.println(vCalendarDaten);
//				System.out.println(vEventDaten);
//				System.out.println(vTodoDaten);
//				System.out.println(vJournalDaten);
//				System.out.println(vFreeBusyDaten);
//				System.out.println(vTimezoneDaten);
//				System.out.println(vAlarmDaten);

			}
		} finally {
			if (iCalDatenstrom != null) {
				iCalDatenstrom.close();
			}
		}
//		System.out.println(aktuellerKalender.toString("C"));
//		System.out.println("GuKKiCalParser.kalenderEinlesen beendet");
//		return true;

	} // kalenderEinlesen

	private void kalenderAufbauen(GuKKiCal kalendersammlung, String vCalendarDaten, String inPath,
			Integer kalenderNummer) throws Exception {
//		System.out.println("GuKKiCAlParser.kalenderAufbauen begonnen");

		aktuellerKalender = new GuKKiCalvCalendar(kalendersammlung, vCalendarDaten, inPath, kalenderNummer);
		kalendersammlung.addvCalendar(kalendersammlung, aktuellerKalender);
		eventsAufbauen(kalendersammlung, aktuellerKalender, vEventDaten);

		System.out.println(aktuellerKalender.toString("CE"));

//		System.out.println("GuKKiCAlParser.kalenderAufbauen beendet");

	} // kalenderAufbauen

	private void eventsAufbauen(GuKKiCal kalendersammlung, GuKKiCalvCalendar aktuellerKalender, String vEventDaten)
			throws Exception {
		System.out.println("GuKKiCAlParser.eventsAufbauen begonnen");
		try {
//			System.out.println(vCalendarDaten);
			BufferedReader vEventDatenstrom = new BufferedReader(new StringReader(vEventDaten));
			String vEventInformationen = "";
			String zeile;
			while ((zeile = vEventDatenstrom.readLine()) != null) {
//				System.out.println("Datenstrom: " + zeile);
				switch (zeile) {
				case "BEGIN:VEVENT": {
//					System.out.println("BEGIN:VEVENT gefunden");
					vEventInformationen = zeile + nz;
					break;
				}
				case "END:VEVENT": {
//					System.out.println("END:VEVENT gefunden");
					vEventInformationen += zeile + nz;
					aktuellerKalender.vEventNeu(kalendersammlung, vEventInformationen);
					break;
				}
				default: {
					vEventInformationen += zeile + nz;
				} // default
				} // switch
			} // while
		} finally {

		}
//		aktuellerKalender.vEventNeu(kalendersammlung, vEventDaten);

		System.out.println("GuKKiCAlParser.eventsAufbauen beendet");
	} // eventsAufbauen
}
