package main;

import exceptions.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	// ArrayList<GuKKiCal> iCalendarSammlung = new ArrayList<GuKKiCal>();
	GuKKiCaliCalendar aktuellerKalender = null;
	/*
	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
	 */
	boolean iCalendarBearbeiten = false;
	String iCalendarDaten = "";
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

	/**
	 * Der Klasse GuKKiCalParser dient dazu, über die Methode kalenderEinlesen,
	 * aus der Datei, deren Name übergeben wird, einen Kalender aufzubauen und
	 * der Kalendersammlung hinzuzufügen.
	 */
	public GuKKiCalParser() throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	}

	/**
	 * Die Methode kalenderEinlesen liest den übergebenen Datenstrom zeilenweise
	 * ein und unterteilt ihn in mehrere Datenströme für die verschiedenen
	 * vCalendar-Komponenten
	 * 
	 * @param inPath           Pfad der Eingabedatei für den Kalender als String
	 * @param kalendersammlung Sammlung aller Kalender und deren Komponenten
	 * 
	 * @throws IOException
	 */

	protected void kalenderEinlesen(ArrayList<GuKKiCaliCalendar> iCalendarSammlung, String inPath) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		int kalenderNummer = 0;
		/*
		 * Datenstrom zur Verarbeitung der Kalenderdaten
		 */
		BufferedReader iCalDatenstrom = null; /* Datenstrom */
		String zeile; /* Datenzeile */

		try {
			iCalDatenstrom = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));

			while ((zeile = iCalDatenstrom.readLine()) != null) {
				switch (zeile) {
					case "BEGIN:VCALENDAR": {
						iCalendarBearbeiten = true;
						kalenderNummer = +1;
						iCalendarDaten = zeile + nz;
						break;
					}
					case "END:VCALENDAR": {
						iCalendarBearbeiten = false;
						iCalendarDaten += zeile + nz;
						kalenderAufbauen(iCalendarSammlung, iCalendarDaten, inPath, kalenderNummer);

						break;
					}
					case "BEGIN:VEVENT": {
						vEventBearbeiten = true;
						vEventDaten += zeile + nz;
						break;
					}
					case "END:VEVENT": {
						vEventBearbeiten = false;
						vEventDaten += zeile + nz;
						break;
					}
					case "BEGIN:VTODO": {
						vTodoBearbeiten = true;
						vTimezoneDaten += zeile + nz;
						break;
					}
					case "END:VTODO": {
						vTodoBearbeiten = false;
						vTimezoneDaten += zeile + nz;
						break;
					}
					case "BEGIN:VJOURNAL": {
						vJournalBearbeiten = true;
						vJournalDaten += zeile + nz;
						break;
					}
					case "END:VJOURNAL": {
						vJournalBearbeiten = false;
						vJournalDaten += zeile + nz;
						break;
					}
					case "BEGIN:VFREEBUSY": {
						vFreeBusyBearbeiten = true;
						vFreeBusyDaten += zeile + nz;
						break;
					}
					case "END:VFREEBUSY": {
						vFreeBusyBearbeiten = false;
						vFreeBusyDaten += zeile + nz;
						break;
					}
					case "BEGIN:VTIMEZONE": {
						vTimezoneBearbeiten = true;
						vTimezoneDaten += zeile + nz;
						break;
					}
					case "END:VTIMEZONE": {
						vTimezoneBearbeiten = false;
						vTimezoneDaten += zeile + nz;
						break;
					}
					default: {
						if (vEventBearbeiten) {
							if (vTodoBearbeiten | vJournalBearbeiten | vFreeBusyBearbeiten | vTimezoneBearbeiten
									| vAlarmBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vEvent");
							} else {
								vEventDaten += zeile + nz;
							}
						} else if (vTodoBearbeiten) {
							if (vJournalBearbeiten | vFreeBusyBearbeiten | vTimezoneBearbeiten | vAlarmBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vTodo");
							} else {
								vTimezoneDaten += zeile + nz;
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
						} else {
							iCalendarDaten += zeile + nz;
						}
					}
				}
			}
		} finally {
			if (iCalDatenstrom != null) {
				iCalDatenstrom.close();
			}
		}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // Ende kalenderEinlesen

	/**
	 * Die Methode kalenderAufbauen verwendet die getrennten Datenströme aus der
	 * methode kalenderEinlesen, um daraus einen Kalender mit all seinen
	 * Komponenten aufzubauen
	 * 
	 * @param kalendersammlung
	 * @param vCalendarDaten
	 * @param inPath
	 * @param kalenderNummer
	 * 
	 * @throws Exception
	 */

	private void kalenderAufbauen(ArrayList<GuKKiCaliCalendar> iCalendarSammlung, String iCalendarDaten, String inPath,
			Integer kalenderNummer) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}

		aktuellerKalender = new GuKKiCaliCalendar(iCalendarDaten, inPath, kalenderNummer);
		eventsAufbauen(aktuellerKalender, vEventDaten);
		todosAufbauen(aktuellerKalender, vTimezoneDaten);
		timezonesAufbauen(aktuellerKalender, vTimezoneDaten);
		iCalendarSammlung.add(aktuellerKalender);

		logger.log(logLevel, aktuellerKalender.toString("CETZ"));

		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // kalenderAufbauen

	/**
	 * Die Methode eventsAufbauen nimmt den Datenstrom für vEvents und legt für
	 * jedes im Datenstrom enthaltenen VEVENT im aktuellen Kalender ein neues
	 * Event an
	 * 
	 * @param kalendersammlung
	 * @param aktuellerKalender
	 * @param vEventDaten
	 * @throws Exception
	 */
	private void eventsAufbauen(GuKKiCaliCalendar iCalendar, String vEventDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		try {
//			System.out.println(vEventDaten);
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
						iCalendar.vEventNeu(vEventInformationen);
						break;
					}
					default: {
						vEventInformationen += zeile + nz;
					} // default
				} // switch
			} // while
		} finally {

		}
//		System.out.println("GuKKiCAlParser.eventsAufbauen beendet");

		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // eventsAufbauen

	/**
	 * Die Methode eventsAufbauen nimmt den Datenstrom für vTodos und legt für
	 * jedes im Datenstrom enthaltenen VTODO im aktuellen Kalender ein neues
	 * Todo an
	 * 
	 * @param kalendersammlung
	 * @param aktuellerKalender
	 * @param vTodoDaten
	 * @throws Exception
	 */
	private void todosAufbauen(GuKKiCaliCalendar iCalendar, String vTodoDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		try {
//			System.out.println(vTodoDaten);
			BufferedReader vTodoDatenstrom = new BufferedReader(new StringReader(vTodoDaten));
			String vTodoInformationen = "";
			String zeile;
			while ((zeile = vTodoDatenstrom.readLine()) != null) {
//				System.out.println("Datenstrom: " + zeile);
				switch (zeile) {
					case "BEGIN:VTODO": {
//					System.out.println("BEGIN:VTODO gefunden");
						vTodoInformationen = zeile + nz;
						break;
					}
					case "END:VTODO": {
//					System.out.println("END:VTODO gefunden");
						vTodoInformationen += zeile + nz;
						iCalendar.vTodoNeu(vTodoInformationen);
						break;
					}
					default: {
						vTodoInformationen += zeile + nz;
					} // default
				} // switch
			} // while
		} finally {

		}

		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // todosAufbauen

	/**
	 * Die Methode timezonesAufbauen nimmt den Datenstrom für vTodos und legt
	 * für jedes im Datenstrom enthaltenen VTIMEZONE im aktuellen Kalender ein
	 * neues Timezone-Element an
	 * 
	 * @param kalendersammlung
	 * @param aktuellerKalender
	 * @param vTimezoneDaten
	 * @throws Exception
	 */
	private void timezonesAufbauen(GuKKiCaliCalendar iCalendar, String vTimezoneDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		try {
			BufferedReader vTimezoneDatenstrom = new BufferedReader(new StringReader(vTimezoneDaten));
			String vTimezoneInformationen = "";
			String zeile;
			while ((zeile = vTimezoneDatenstrom.readLine()) != null) {
				switch (zeile) {
					case "BEGIN:VTIMEZONE": {
						vTimezoneInformationen = zeile + nz;
						break;
					}
					case "END:VTIMEZONE": {
						vTimezoneInformationen += zeile + nz;
						iCalendar.vTimezoneNeu(vTimezoneInformationen);
						break;
					}
					default: {
						vTimezoneInformationen += zeile + nz;
					} // default
				} // switch
			} // while
		} finally {

		}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // timezonesAufbauen
}
