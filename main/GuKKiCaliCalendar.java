package main;

import exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Klasse GuKKiCalvEvent enthält alle Daten für eine VEVENT-Komponente im iCal Format
 *         
 * @author GuKKDevel
 *  
 * @formatter:off
 * 
 * 
 *         Die Klasse GuKKiCaliCalendar enthält alle Daten für einen Kalender im
 *         iCal Format
 * 
 *         Definition nach RFC 5545 und Ergänzung nach RFC 7986
 *
 *         icalobject = "BEGIN" ":" "VCALENDAR" CRLF 
 *         
 *         		icalbody 
 *         
 *         		"END" ":" "VCALENDAR" CRLF
 *
 *
 *         icalbody = calprops component
 * 
 *         calprops = *(
 * 
 *         The following are REQUIRED, but MUST NOT occur more than once.
 *
 *         prodid / version /
 *
 *         The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *         calscale / method /
 * 
 *         The following are OPTIONAL, and MAY occur more than once.
 * 
 *         x-prop / iana-prop
 *
 *	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-
 *	Modifications by RFC 7986 (October 2016) item 4.; p. 4
 *
 *			calprops =/ *(
 *
 *	The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				uid / last-mod / url /
 *				refresh / source / color
 *
 *	The following are OPTIONAL, and MAY occur more than once.
 *
 *				name / description / categories /
 *				image
 *
 *				)
 *
 * @formatter:on
 *	
 */
public class GuKKiCaliCalendar extends GuKKiCalComponent {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINER;

	/*
	 * Daten für die KalenderDatei
	 */
	String iCalendarPfad = null;
	String kalenderName = null;
	int kalenderNummer = 0;

	/* Daten für das VCALENDAR-Element */

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	GuKKiCalProperty PRODID = null;
	GuKKiCalProperty VERSION = null;
	GuKKiCalProperty UID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	GuKKiCalProperty CALSCALE = null;
	GuKKiCalProperty COLOR = null;
	GuKKiCalProperty LAST_MOD = null;
	GuKKiCalProperty METHOD = null;
	GuKKiCalProperty REFRESH = null;
	GuKKiCalProperty SOURCE = null;
	GuKKiCalProperty URL = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	ArrayList<GuKKiCalProperty> CATEGORIES = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> DESCRIPTION = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> IMAGE = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> NAME = new ArrayList<GuKKiCalProperty>();
	
	String Restinformationen = "";
	/*
	 * Sammlungen der Kalender-Komponenten
	 */
	ArrayList<GuKKiCalvEvent> vEventSammlung = new ArrayList<GuKKiCalvEvent>();
	ArrayList<GuKKiCalvTodo> vTodoSammlung = new ArrayList<GuKKiCalvTodo>();
	ArrayList<GuKKiCalvJournal> vJournalSammlung = new ArrayList<GuKKiCalvJournal>();
	ArrayList<GuKKiCalvFreeBusy> vFreeBusySammlung = new ArrayList<GuKKiCalvFreeBusy>();
	ArrayList<GuKKiCalvTimezone> vTimezoneSammlung = new ArrayList<GuKKiCalvTimezone>();
	/*
	 * Generelle Verarbeitungsvariablen
	 */
	String nz = "\n";
	String zeile = "";

	public GuKKiCaliCalendar() {

	}

	public GuKKiCaliCalendar(String iCalendarDaten, String kalenderPfad, int kalenderNummer) throws Exception {
//		System.out.println("GuKKiCaliCalendar-Konstruktor: " + vCalendarPfad + "-----" + kalenderNummer);
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.iCalendarPfad = kalenderPfad;
		this.kalenderName = bestimmenKalenderName(kalenderPfad);
		this.kalenderNummer = kalenderNummer;
		this.kennung = GuKKiCalcKennung.CALENDAR;
		this.status = GuKKiCalcStatus.UNDEFINIERT;
		this.schluessel = this.kalenderName + String.format("%03d", this.kalenderNummer);

		try {
			// System.out.println(vCalendarDaten);
			BufferedReader iCalendarDatenstrom = new BufferedReader(new StringReader(iCalendarDaten));
			Restinformationen = "";
			while ((zeile = iCalendarDatenstrom.readLine()) != null) {
				// System.out.println("Datenstrom: " + zeile);
				if (!zeile.equals("BEGIN:VCALENDAR") && !zeile.equals("END:VCALENDAR")) {
					if (zeile.length() >= 6 && zeile.substring(0, 6).equals("PRODID")) {
						PRODID = new GuKKiCalProperty(zeile, "PRODID");
					} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("VERSION")) {
						VERSION = new GuKKiCalProperty(zeile, "VERSION");
					} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("CALSCALE")) {
						CALSCALE = new GuKKiCalProperty(zeile, "CALSCALE");
					} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("METHOD")) {
						METHOD = new GuKKiCalProperty(zeile, "METHOD");
					} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
						UID = new GuKKiCalProperty(zeile, "UID");
					} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
						LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
					} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
						URL = new GuKKiCalProperty(zeile, "URL");
					} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("REFRESH-INTERVAL")) {
						REFRESH = new GuKKiCalProperty(zeile, "REFRESH-INTERVAL");
					} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("SOURCE")) {
						SOURCE = new GuKKiCalProperty(zeile, "SOURCE");
					} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("COLOR")) {
						COLOR = new GuKKiCalProperty(zeile, "COLOR");
					} else if (zeile.length() >= 4 && zeile.substring(0, 4).equals("NAME")) {
						NAME.add(new GuKKiCalProperty(zeile, "NAME"));
					} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
						DESCRIPTION.add(new GuKKiCalProperty(zeile, "DESCRIPTION"));
					} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
						CATEGORIES.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
					} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("IMAGE")) {
						IMAGE.add(new GuKKiCalProperty(zeile, "IMAGE"));
					} else {
						Restinformationen += zeile + nz;
					} /* * name / description / categories / image-**-/ */
				}
			}
		} finally {
		}
		// vCalendarSammlung.addvCalendar(vCalendarSammlung, this); /* sollte im
		// übergeordneten Modul erfolgen */
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende GuKKiCaliCalendar(String iCalendarDaten, String kalenderPfad, int
		// kalenderNummer)

	/**
	 * Modul erhält die Daten für ein einzelnes Event als Datenstrom
	 * (vEventDaten), erstellt dieses Element und fügt es in den Kalender ein.
	 * Zusätzlich wird das Event auch in die Kalendersammlung eingefügt.
	 * 
	 * @param vCalendarSammlung
	 * @param vEventDaten
	 * @return
	 * @throws Exception
	 */
	public boolean vEventNeu(String vEventDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.vEventSammlung.add(new GuKKiCalvEvent(vEventDaten));

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return true;
	}

	/**
	 * Modul erhält die Daten für ein einzelnes Todo als Datenstrom
	 * (vTodoDaten), erstellt dieses Element und fügt es in den Kalender ein.
	 * Zusätzlich wird das Event auch in die Kalendersammlung eingefügt.
	 * 
	 * @param vCalendarSammlung
	 * @param vTodoDaten
	 * @return
	 * @throws Exception
	 */
	public boolean vTodoNeu(String vTodoDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.vTodoSammlung.add(new GuKKiCalvTodo(vTodoDaten));

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return true;
	}

	/**
	 * Modul erhält die Daten für eine einzelne Timzone-Komponente als
	 * Datenstrom (vTimezoneDaten), erstellt dieses Element und fügt es in den
	 * Kalender ein. Zusätzlich wird das Event auch in die Kalendersammlung
	 * eingefügt.
	 * 
	 * @param vCalendarSammlung
	 * @param vTimezoneDaten
	 * @return
	 * @throws Exception
	 */
	public boolean vTimezoneNeu(String vTimezoneDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.vTimezoneSammlung.add(new GuKKiCalvTimezone(vTimezoneDaten));

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return true;
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
	 * 
	 * @param ausgabeLevel
	 * @return
	 */
	public String toString() {
		return super.toString();
	}

	public String toString(String ausgabeLevel) {
		String ausgabeString = "";
		if (ausgabeLevel.toUpperCase().indexOf("C") >= 0) {
			ausgabeString += nz + "vCalendarInformationen:" + nz + iCalendarPfad + " --- " + kalenderName + " --- "
					+ String.format("%03d", kalenderNummer) + nz + "vCalendarDaten:" + nz;
			if (PRODID != null)
				ausgabeString += "PRODID:" + PRODID.getWert() + nz;
			if (VERSION != null)
				ausgabeString += "VERSION:" + VERSION.getWert() + nz;
			if (CALSCALE != null)
				ausgabeString += "CALSCALE:" + CALSCALE.getWert() + nz;
			else
				ausgabeString += "CALSCALE:(GREGORIAN)" + nz;
			if (METHOD != null)
				ausgabeString += "METHOD:" + METHOD.getWert() + nz;
			// if (vCalendarRestinformationen != null)
			// ausgabeString +=
			// nz+"vCalendarRestinformationen:"+nz+vCalendarRestinformationen;
		}
		if (ausgabeLevel.toUpperCase().indexOf("E") >= 0) {
			ausgabeString += nz;
			for (GuKKiCalvEvent vEvent : vEventSammlung) {
				ausgabeString += schluessel + ":" + vEvent.toString(ausgabeLevel) + nz;
			}
		}
		if (ausgabeLevel.toUpperCase().indexOf("T") >= 0) {
			ausgabeString += nz;
			for (GuKKiCalvTodo vTodo : vTodoSammlung) {
				ausgabeString += schluessel + ":" + vTodo.toString(ausgabeLevel) + nz;
			}
		}
		if (ausgabeLevel.toUpperCase().indexOf("Z") >= 0) {
			ausgabeString += nz;
			for (GuKKiCalvTimezone vTimezone : vTimezoneSammlung) {
				ausgabeString += schluessel + ":" + vTimezone.toString(ausgabeLevel) + nz;
			}
		}
		return ausgabeString;
	}
}