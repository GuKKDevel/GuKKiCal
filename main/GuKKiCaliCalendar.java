package main;

import exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
	Level logLevel = Level.FINEST;

	/*
	 * Daten für die KalenderDatei
	 */
	String kalenderName = null;
	String iCalendarPfad = null;

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
	 * Aufbereitete Komponenten
	 */
	HashMap<String, GuKKiCalvTimezone> vTimezones = new HashMap<String, GuKKiCalvTimezone>();
	/*
	 * Generelle Verarbeitungsvariablen
	 */
	String nz = "\n";
	String zeile = "";
	/*
	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
	 */
	String vComponentDaten = "";
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

	public GuKKiCaliCalendar() {
		/*	@formatter:off */
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
		/*	@formatter:on */
	}

	public GuKKiCaliCalendar(String iCalendarDatenParam, String kalenderNameParam, String kalenderPfadParam)
			throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.kennung = GuKKiCalcKennung.CALENDAR;
		this.status = GuKKiCalcStatus.UNDEFINIERT;

		iCalendarDatenAufteilen(iCalendarDatenParam);

		this.iCalendarPfad = kalenderPfadParam;
		this.kalenderName = kalenderNameParam;
		this.schluessel = this.kalenderName;

		verarbeitenDatenstrom(iCalendarDaten);

		vComponentenAnlegen("VEVENT", vEventDaten);
		vComponentenAnlegen("VTODO", vTodoDaten);
		vComponentenAnlegen("VJOURNAL", vJournalDaten);
		vComponentenAnlegen("VTIMEZONE", vTimezoneDaten);
		vComponentenAnlegen("VFREEBUSY", vFreeBusyDaten);

	}

	/**
	 * Methode zur Trennung/Sammlung der einzelnen Kalenderkomponenten
	 * 
	 * @param iCalendarDaten
	 * @throws IOException
	 * @throws GuKKiCalFKalenderdaten
	 */
	private void iCalendarDatenAufteilen(String iCalendarDaten) throws IOException, GuKKiCalFKalenderdaten {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		try {
			/*
			 * Datenstrom zur Verarbeitung der Kalenderdaten
			 */

			BufferedReader iCalendarDatenstrom = new BufferedReader(new StringReader(iCalendarDaten));

			while ((zeile = iCalendarDatenstrom.readLine()) != null) {
				switch (zeile) {
					case "BEGIN:VCALENDAR": {
						iCalendarBearbeiten = true;
						this.iCalendarDaten = zeile + nz;
						break;
					}
					case "END:VCALENDAR": {
						iCalendarBearbeiten = false;
						this.iCalendarDaten += zeile + nz;
						break;
					}
					case "BEGIN:VEVENT": {
						vEventBearbeiten = true;
						this.vEventDaten += zeile + nz;
						break;
					}
					case "END:VEVENT": {
						vEventBearbeiten = false;
						this.vEventDaten += zeile + nz;
						break;
					}
					case "BEGIN:VTODO": {
						vTodoBearbeiten = true;
						this.vTodoDaten += zeile + nz;
						break;
					}
					case "END:VTODO": {
						vTodoBearbeiten = false;
						this.vTodoDaten += zeile + nz;
						break;
					}
					case "BEGIN:VJOURNAL": {
						vJournalBearbeiten = true;
						this.vJournalDaten += zeile + nz;
						break;
					}
					case "END:VJOURNAL": {
						vJournalBearbeiten = false;
						this.vJournalDaten += zeile + nz;
						break;
					}
					case "BEGIN:VTIMEZONE": {
						vTimezoneBearbeiten = true;
						this.vTimezoneDaten += zeile + nz;
						break;
					}
					case "END:VTIMEZONE": {
						vTimezoneBearbeiten = false;
						this.vTimezoneDaten += zeile + nz;
						break;
					}
					case "BEGIN:VFREEBUSY": {
						vFreeBusyBearbeiten = true;
						this.vFreeBusyDaten += zeile + nz;
						break;
					}
					case "END:VFREEBUSY": {
						vFreeBusyBearbeiten = false;
						this.vFreeBusyDaten += zeile + nz;
						break;
					}
					default: {
						if (vEventBearbeiten) {
							if (vTodoBearbeiten | vJournalBearbeiten | vTimezoneBearbeiten | vFreeBusyBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vEvent");
							} else {
								this.vEventDaten += zeile + nz;
							}
						} else if (vTodoBearbeiten) {
							if (vJournalBearbeiten | vTimezoneBearbeiten | vFreeBusyBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vTodo");
							} else {
								this.vTodoDaten += zeile + nz;
							}
						} else if (vJournalBearbeiten) {
							if (vTimezoneBearbeiten | vFreeBusyBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vJournal");
							} else {
								this.vJournalDaten += zeile + nz;
							}
						} else if (vTimezoneBearbeiten) {
							if (vFreeBusyBearbeiten) {
								throw new GuKKiCalFKalenderdaten("Fehler vTimezone");
							} else {
								this.vTimezoneDaten += zeile + nz;
							}
						} else if (vFreeBusyBearbeiten) {
							this.vFreeBusyDaten += zeile + nz;
						} else {
							this.iCalendarDaten += zeile + nz;
						}

					} // Ende switch
				} // Ende while
			} // Ende try
		} finally {
		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende iCalendarDatenAufteilen

	@Override
	protected void verarbeitenZeile(String zeile) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

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
			}
		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende verarbeitenZeile

	/**
	 * Modul zu Aufteilen des Datenstroms der gesammelten VEVENT-Einträge in
	 * einzelne VEVENT-Komponenten
	 */
	private void vComponentenAnlegen(String vComponentLiteral, String vComponentDatenParam) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (logger.isLoggable(Level.FINER)) {
			logger.log(Level.FINER, vComponentLiteral);
		}

		try {

			BufferedReader vComponentDatenStrom = new BufferedReader(new StringReader(vComponentDatenParam));

			while ((zeile = vComponentDatenStrom.readLine()) != null) {
				if (zeile.equals("BEGIN:" + vComponentLiteral)) {
					vComponentDaten = zeile + nz;
				} else if (!zeile.equals("END:" + vComponentLiteral)) {
					vComponentDaten += zeile + nz;
				} else {
					vComponentDaten += zeile + nz;
					switch (vComponentLiteral) {
						case "VEVENT": {
							vEventSammlung.add(new GuKKiCalvEvent(vComponentDaten));
							vComponentDaten = "";
							break;
						}
						case "VTODO": {
							vTodoSammlung.add(new GuKKiCalvTodo(vComponentDaten));
							vComponentDaten = "";
							break;
						}
						case "VJOURNAL": {
							vJournalSammlung.add(new GuKKiCalvJournal(vComponentDaten));
							vComponentDaten = "";
							break;
						}
						case "VTIMEZONE": {
							vTimezoneSammlung.add(new GuKKiCalvTimezone(vComponentDaten));
							vTimezonEintragen(vTimezoneSammlung.get(vTimezoneSammlung.size() - 1));
							vComponentDaten = "";
							break;
						}
						case "VFREEBUSY": {
							vFreeBusySammlung.add(new GuKKiCalvFreeBusy(vComponentDaten));
							vComponentDaten = "";
							break;
						}
						default: {

						}
					} // Ende switch
				} // Ende else
			} // Ende while
		} finally {
		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende vComponentenAnlegen

	/**
	 * Reduzieren der VTIMEZONE-Komponenten, dass jede Zeitzone mit ihren
	 * DAYLIGHT und STANDARD-Komponenten nur einmal erscheint
	 * 
	 * @param vTimezone
	 */
	private void vTimezonEintragen(GuKKiCalvTimezone vTimezone) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		
		GuKKiCalvTimezone temp = vTimezone.kopieren();

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * 
	 * @param ausgabeLevel
	 * @return
	 */
	public String toString() {
		String ausgabeString = nz + "vCalendarInformationen:" + nz + kalenderName + " --- " + iCalendarPfad + nz
				+ "vCalendarDaten:" + nz;
		return ausgabeString;
	}

	public String toString(String ausgabeLevel) {
		String ausgabeString = "";
		if (ausgabeLevel.toUpperCase().indexOf("C") >= 0) {
			ausgabeString = this.toString();
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
				ausgabeString += "Schlüssel: " + vTimezone.toString() + "\n";
			}
//			for (GuKKiCalvTimezone vTimezone : vTimezoneSammlung) {
//				ausgabeString += schluessel + ":" + vTimezone.toString(ausgabeLevel) + nz;
//			}
		}
		return ausgabeString;
	}
}