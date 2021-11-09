package main;

import exceptions.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Die Klasse GuKKiCalvEvent enthält alle Daten für eine VEVENT-Komponente im iCal Format
 *         
 * @author GuKKDevel
 *  
 * @formatter:off
 * 
 * 
 *         Die Klasse GuKKiCalvCalendar enthält alle Daten für einen Kalender im
 *         iCal Format
 * 
 *         Definition nach RFC 5545
 *
 *         icalobject = "BEGIN" ":" "VCALENDAR" CRLF icalbody "END" ":"
 *         "VCALENDAR" CRLF
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
 * @formatter:on
 *	
 */
public class GuKKiCalvCalendar {
	/*
	 * Daten für die KalenderDatei
	 */
	String vCalendarKennung = null;
	String vCalendarPfad = null;
	String kalenderName = null;
	int kalenderNummer = 0;
	/*
	 * Daten für das VCALENDAR-Element
	 */
	String vCalendarPRODID = null;
	String vCalendarVERSION = null;
	String vCalendarCALSCALE = null;
	String vCalendarMETHOD = null;
	String vCalendarRestinformationen = null;
	/*
	 * Sammlungen der KalenderSubElemente
	 */
	GuKKiCal vCalendarSammlung = null;
	ArrayList<GuKKiCalvEvent> vEvent = new ArrayList<GuKKiCalvEvent>();
	ArrayList<GuKKiCalvTodo> vTodo = new ArrayList<GuKKiCalvTodo>();
	ArrayList<GuKKiCalvJournal> vJournal = new ArrayList<GuKKiCalvJournal>();
	ArrayList<GuKKiCalvFreeBusy> vFreeBusy = new ArrayList<GuKKiCalvFreeBusy>();
	ArrayList<GuKKiCalvTimezone> vTimezone = new ArrayList<GuKKiCalvTimezone>();
	ArrayList<GuKKiCalvAlarm> vAlarm = new ArrayList<GuKKiCalvAlarm>();
	/*
	 * Generelle Verarbeitungsvariablen
	 */
	String nz = "\n";
	String zeile = "";

	public GuKKiCalvCalendar(GuKKiCal vCalendarSammlung, String vCalendarDaten, String kalenderPfad, int kalenderNummer)
			throws Exception {
//		System.out.println("GuKKiCalvCalendar-Konstruktor: " + vCalendarPfad + "-----" + kalenderNummer);
		this.vCalendarPfad = kalenderPfad;
		this.kalenderName = bestimmenKalenderName(kalenderPfad);
		this.kalenderNummer = kalenderNummer;
		this.vCalendarSammlung = vCalendarSammlung;
		this.vCalendarKennung = this.kalenderName + String.format("%03d", this.kalenderNummer);

		try {
			// System.out.println(vCalendarDaten);
			BufferedReader vCalendarDatenstrom = new BufferedReader(new StringReader(vCalendarDaten));
			vCalendarRestinformationen = "";
			while ((zeile = vCalendarDatenstrom.readLine()) != null) {
				// System.out.println("Datenstrom: " + zeile);
				if (!zeile.equals("BEGIN:VCALENDAR") && !zeile.equals("END:VCALENDAR")) {
					if (zeile.length() >= 7 && zeile.substring(0, 7).equals("PRODID:"))
						vCalendarPRODID = zeile.substring(7);
					else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("VERSION:"))
						vCalendarVERSION = zeile.substring(8);
					else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("CALSCALE:"))
						vCalendarCALSCALE = zeile.substring(9);
					else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("METHOD:"))
						vCalendarMETHOD = zeile.substring(7);
					else
						vCalendarRestinformationen += zeile + "\n";
				}
			}
		} finally {
		}
		// vCalendarSammlung.addvCalendar(vCalendarSammlung, this); /* sollte im
		// übergeordneten Modul erfolgen */
//		System.out.println("GuKKiCalvCalendar-Konstruktor beendet");
	}

	// public GuKKiCalvCalendar(GuKKiCalvCalendar vKalender) {
	// }
	/**
	 * Modul erhält die Daten für ein einzelnes Event als Datenstrom (vEventDaten),
	 * erstellt dieses Element und fügt es in den Kalender ein. Zusätzlich wird das
	 * Event auch in die Kalendersammlung eingefügt.
	 * 
	 * @param vCalendarSammlung
	 * @param vEventDaten
	 * @return
	 * @throws Exception
	 */
	public boolean vEventNeu(GuKKiCal vCalendarSammlung, String vEventDaten) throws Exception {
//		System.out.println("GuKKiCalvCalendar.vEventNeu begonnen");
		GuKKiCalvEvent neuesEvent = null;
		neuesEvent = new GuKKiCalvEvent(vCalendarSammlung, vCalendarKennung, vEventDaten);
		this.vEvent.add(neuesEvent);
//		System.out.println("GuKKiCalvCalendar.vEventNeu beendet");
		return true;
	}
	/**
	 * Modul erhält die Daten für ein einzelnes Todo als Datenstrom (vTodoDaten),
	 * erstellt dieses Element und fügt es in den Kalender ein. Zusätzlich wird das
	 * Event auch in die Kalendersammlung eingefügt.
	 * 
	 * @param vCalendarSammlung
	 * @param vTodoDaten
	 * @return
	 * @throws Exception
	 */
	public boolean vTodoNeu(GuKKiCal vCalendarSammlung, String vTodoDaten) throws Exception {
		System.out.println("GuKKiCalvCalendar.vTodoNeu begonnen");
		GuKKiCalvTodo neuesTodo = null;
		neuesTodo = new GuKKiCalvTodo(vCalendarSammlung, vCalendarKennung, vTodoDaten);
		this.vTodo.add(neuesTodo);
		System.out.println("GuKKiCalvCalendar.vTodoNeu beendet");
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
		// System.out.println("GuKKiCalvCalendar.bestimmenKalenderName begonnen");
		/*
		 * Bestimmen, welches Betriebssystem gerade läuft, um den Verzeichnis-Trenner zu
		 * setzen
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
//		System.out.println("GuKKiCalvCalendar.bestimmenKalenderName beendet");
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
			ausgabeString += nz + "vCalendarInformationen:" + nz + vCalendarPfad + " --- " + kalenderName + " --- "
					+ String.format("%03d", kalenderNummer) + nz + "vCalendarDaten:" + nz;
			if (vCalendarPRODID != null)
				ausgabeString += "PRODID:" + vCalendarPRODID + nz;
			if (vCalendarVERSION != null)
				ausgabeString += "VERSION:" + vCalendarVERSION + nz;
			if (vCalendarCALSCALE != null)
				ausgabeString += "CALSCALE:" + vCalendarCALSCALE + nz;
			else
				ausgabeString += "CALSCALE:(GREGORIAN)" + nz;
			if (vCalendarMETHOD != null)
				ausgabeString += "METHOD:" + vCalendarMETHOD + nz;
			// if (vCalendarRestinformationen != null)
			// ausgabeString +=
			// nz+"vCalendarRestinformationen:"+nz+vCalendarRestinformationen;
		}
		if (ausgabeLevel.toUpperCase().indexOf("E") >= 0) {
			ausgabeString += nz;
			for (GuKKiCalvEvent pvEvent : vEvent) {
				ausgabeString += pvEvent.toString(ausgabeLevel) + nz;
			}
		}
		if (ausgabeLevel.toUpperCase().indexOf("T") >= 0) {
			ausgabeString += nz;
			for (GuKKiCalvTodo pvTodo : vTodo) {
				ausgabeString += pvTodo.toString(ausgabeLevel) + nz;
			}
		}
		return ausgabeString;
	}
}