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

	ArrayList<GuKKiCaliCalendar> iCalendarSammlung = new ArrayList<GuKKiCaliCalendar>();
	GuKKiCaliCalendar iCalendarNeu = null;
	/*
	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
	 */
	String kalenderPfad = "";
	String kalenderName = "";
	int kalenderNummer = 0;

	/**
	 * Der Klasse GuKKiCalParser dient dazu, über die Methode kalenderEinlesen,
	 * aus der Datei, deren Name übergeben wird, einen Kalender aufzubauen und
	 * der Kalendersammlung hinzuzufügen.
	 */
	public GuKKiCalParser() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
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
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.iCalendarSammlung = iCalendarSammlung;
		this.kalenderPfad = inPath;
		this.kalenderName = bestimmenKalenderName(inPath);
		this.kalenderNummer = 0;
		/*
		 * Datenstrom zur Verarbeitung der Kalenderdaten
		 */
		BufferedReader iCalendarDatenstrom = null;
		String zeile = "";
		String folgezeile = "";
		boolean datenVorhanden = true;

		try {
			iCalendarDatenstrom = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));
			zeile = iCalendarDatenstrom.readLine();
			if (zeile == null) {
				datenVorhanden = false;
			}
			while (datenVorhanden) {
				folgezeile = iCalendarDatenstrom.readLine();

				if (folgezeile == null) {
					neueZeile(zeile);
					datenVorhanden = false;
				} else {
					if (folgezeile.length() > 0) {
						if (folgezeile.substring(0, 1).equals(" ")) {
							zeile = zeile.substring(0, zeile.length()) + folgezeile.substring(1);
						} else {
							neueZeile(zeile);
							zeile = folgezeile;
						}
					}
				}
			} /* end while-Schleife */
		} finally {

			if (iCalendarDatenstrom != null) {
				iCalendarDatenstrom.close();
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende kalenderEinlesen

	void neueZeile(String zeile) throws Exception{
		if (zeile.equals("BEGIN:VCALENDAR")) {
			kalenderNummer++;
			iCalendarNeu = new GuKKiCaliCalendar();
		}
		else if (zeile.equals("END:VCALENDAR")) {
			iCalendarNeu.abschliessen(kalenderName+String.format("%03d", kalenderNummer), kalenderPfad);
			iCalendarSammlung.add(iCalendarNeu);
		}
		else {
			iCalendarNeu.neueZeile(zeile);
		}
	}

	/**
	 * Bestimmt aus der Pfadangabe für den Kalender den Kalendernamen aus dem
	 * Dateinamen
	 * 
	 * @param vCalendarPfad
	 * @return
	 */
	private String bestimmenKalenderName(String kalenderPfad) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		// System.out.println("GuKKiCaliCalendar.bestimmenKalenderName
		// begonnen");
		/*
		 * Bestimmen, welches Betriebssystem gerade läuft, um den
		 * Verzeichnis-Trenner zu setzen
		 */
		String vzt = "\\";
		if (System.getProperty("os.name").equals("Linux"))
			vzt = "/";

		/*
		 * Analysieren des Pfades um den Kalendernamen zu bestimmen
		 */
		String stringNeu = kalenderPfad;
		int startIndex = stringNeu.length();
		int endeIndex = stringNeu.length();
		while (startIndex > 0 && stringNeu.substring(startIndex, endeIndex).indexOf(vzt) < 0) {
//			System.out.println("StartIndex=" + startIndex + " Substring="
//					+ stringNeu.substring(startIndex, endeIndex));
			startIndex -= 1;
		}

//		System.out.println("StartIndex=" + startIndex + " EndeIndex="
//				+ endeIndex + "Kalenderkennung="
//				+ stringNeu.substring(startIndex + 1, endeIndex - 4));
//		System.out.println("GuKKiCaliCalendar.bestimmenKalenderName beendet");
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return stringNeu.substring(startIndex + 1, endeIndex - 4);
	}

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

//	private void kalenderAufbauen(ArrayList<GuKKiCaliCalendar> iCalendarSammlung, String inPath, String kalenderName)
//			throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//
//		iCalendarNeu = new GuKKiCaliCalendar(iCalendarDaten, inPath, kalenderName);
////		eventsAufbauen(iCalendarNeu, vEventDaten);
////		vEventDaten = "";
////		todosAufbauen(iCalendarNeu, vTodoDaten);
////		vTodoDaten = "";
////		journalsAufbauen(iCalendarNeu, vJournalDaten);
////		vJournalDaten = "";
////		timezonesAufbauen(iCalendarNeu, vTimezoneDaten);
////		vTimezoneDaten = "";
////		freebusysAufbauen(iCalendarNeu, vFreeBusyDaten);
////		vFreeBusyDaten = "";
//		iCalendarSammlung.add(iCalendarNeu);
//		iCalendarDaten = "";
//
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // kalenderAufbauen
//
//	/**
//	 * Die Methode eventsAufbauen nimmt den Datenstrom für vEvents und legt für
//	 * jedes im Datenstrom enthaltenen VEVENT im aktuellen Kalender ein neues
//	 * Event an
//	 * 
//	 * @param kalendersammlung
//	 * @param iCalendarNeu
//	 * @param vEventDaten
//	 * @throws Exception
//	 */
//	private void eventsAufbauen(GuKKiCaliCalendar iCalendar, String vEventDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		try {
//			BufferedReader vEventDatenstrom = new BufferedReader(new StringReader(vEventDaten));
//			String vEventInformationen = "";
//			String zeile;
//			while ((zeile = vEventDatenstrom.readLine()) != null) {
//				switch (zeile) {
//					case "BEGIN:VEVENT": {
//						vEventInformationen = zeile + nz;
//						break;
//					}
//					case "END:VEVENT": {
//						vEventInformationen += zeile + nz;
//						iCalendar.vEventNeu(vEventInformationen);
//						break;
//					}
//					default: {
//						vEventInformationen += zeile + nz;
//					} // default
//				} // switch
//			} // while
//		} finally {
//
//		} // Ende try-finally
//
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // Ende eventsAufbauen(GuKKiCaliCalendar, String)
//
//	/**
//	 * Die Methode eventsAufbauen nimmt den Datenstrom für vTodos und legt für
//	 * jedes im Datenstrom enthaltenen VTODO im aktuellen Kalender ein neues
//	 * Todo an
//	 * 
//	 * @param kalendersammlung
//	 * @param iCalendarNeu
//	 * @param vTodoDaten
//	 * @throws Exception
//	 */
//	private void todosAufbauen(GuKKiCaliCalendar iCalendar, String vTodoDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		try {
//			BufferedReader vTodoDatenstrom = new BufferedReader(new StringReader(vTodoDaten));
//			String vTodoInformationen = "";
//			String zeile;
//			while ((zeile = vTodoDatenstrom.readLine()) != null) {
//				switch (zeile) {
//					case "BEGIN:VTODO": {
//						vTodoInformationen = zeile + nz;
//						break;
//					}
//					case "END:VTODO": {
//						vTodoInformationen += zeile + nz;
//						iCalendar.vTodoNeu(vTodoInformationen);
//						break;
//					}
//					default: {
//						vTodoInformationen += zeile + nz;
//					} // default
//				} // switch
//			} // while
//		} finally {
//
//		} // Ende try-finally
//
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // Ende todosAufbauen(GuKKiCaliCalendar, String)
//
//	/**
//	 * Die Methode journalsAufbauen nimmt den Datenstrom für vJournals und legt
//	 * für jedes im Datenstrom enthaltenen VJOURNAL im aktuellen Kalender ein
//	 * neues Journal an
//	 * 
//	 * @param iCalendarNeu
//	 * @param vJournalDaten
//	 * @throws Exception
//	 */
//	private void journalsAufbauen(GuKKiCaliCalendar iCalendarNeu, String vJournalDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		// TODO Automatisch generierter Methodenstub
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // Ende journalsAufbauen(GuKKiCaliCalendar, String)
//
//	/**
//	 * Die Methode timezonesAufbauen nimmt den Datenstrom für vTimezones und
//	 * legt für jedes im Datenstrom enthaltenen VTIMEZONE im aktuellen Kalender
//	 * ein neues Timezone-Element an
//	 * 
//	 * @param iCalendarNeu
//	 * @param vTimezoneDaten
//	 * @throws Exception
//	 */
//	private void timezonesAufbauen(GuKKiCaliCalendar iCalendar, String vTimezoneDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		try {
//			BufferedReader vTimezoneDatenstrom = new BufferedReader(new StringReader(vTimezoneDaten));
//			String vTimezoneInformationen = "";
//			String zeile;
//			while ((zeile = vTimezoneDatenstrom.readLine()) != null) {
//				switch (zeile) {
//					case "BEGIN:VTIMEZONE": {
//						vTimezoneInformationen = zeile + nz;
//						break;
//					}
//					case "END:VTIMEZONE": {
//						vTimezoneInformationen += zeile + nz;
//						iCalendar.vTimezoneNeu(vTimezoneInformationen);
//						break;
//					}
//					default: {
//						vTimezoneInformationen += zeile + nz;
//					} // default
//				} // switch
//			} // while
//		} finally {
//
//		} // Ende try-finally
//
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // Ende timezonesAufbauen(GuKKiCaliCalendar, String)
//
//	/**
//	 * Die Methode freebusysAufbauen nimmt den Datenstrom für vFreeBusy und legt
//	 * für jedes im Datenstrom enthaltenen VFREEBUSY im aktuellen Kalender ein
//	 * neues FreeBusy-Element an
//	 * 
//	 * @param iCalendarNeu
//	 * @param vFreeBusyDaten
//	 * @throws Exception
//	 */
//	private void freebusysAufbauen(GuKKiCaliCalendar iCalendarNeu, String vFreeBusyDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		// TODO Automatisch generierter Methodenstub
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "beendet");
//		}
//	} // Ende journalsAufbauen(GuKKiCaliCalendar, String)
}
