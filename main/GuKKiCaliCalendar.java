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
	String iCalendarName = null;
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
	ArrayList<GuKKiCalProperty> CATEGORIESSammlung = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> DESCRIPTIONSammlung = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> IMAGESammlung = new ArrayList<GuKKiCalProperty>();
	ArrayList<GuKKiCalProperty> NAMESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();
	private ArrayList<String> Restinformationen = new ArrayList<String>();
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
//	/*
//	 * Generelle Verarbeitungsvariablen
//	 */
//	String nz = "\n";
//	String zeile = "";
//	/*
//	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
//	 */
//	String vComponentDaten = "";
//	boolean iCalendarBearbeiten = false;
//	String iCalendarDaten = "";
//	boolean vEventDatenSammeln = false;
//	ArrayList<String> vEventDatenArray = new ArrayList<String>();
//	boolean vTodoDatenSammeln = false;
//	ArrayList<String> vTodoDatenArray = new ArrayList<String>();
//	boolean vJournalDatenSammeln = false;
//	ArrayList<String> vJournalDatenArray = new ArrayList<String>();
//	boolean vFreeBusyDatenSammeln = false;
//	ArrayList<String> vFreeBusyDatenArray = new ArrayList<String>();
//	boolean vTimezoneDatenSammeln = false;
//	ArrayList<String> vTimezoneDatenArray = new ArrayList<String>();

	public GuKKiCaliCalendar() {
		/*	@formatter:off */
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		this.kennung = GuKKiCalcKennung.CALENDAR;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
		/*	@formatter:on */
	}

	/**
	 * Mit dieser Methode wird die Zeilenweise Bearbeitung des Kalenders
	 * beendet.
	 * 
	 */
	protected void abschliessen(String iCalendarName, String iCalendarPfad) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.iCalendarPfad = this.SOURCE == null ? iCalendarPfad : this.SOURCE.getWert();
		this.iCalendarName = "";
		boolean langDE = false;
		boolean langEN = false;
		for (GuKKiCalProperty NAME : NAMESammlung) {
			if (NAME.getLANGUAGE().equals("de")) {
				this.iCalendarName = NAME.getWert();
				langDE = true;
				break;
			} else if (NAME.getLANGUAGE().equals("en")) {
				this.iCalendarName = NAME.getWert();
				langEN = true;
			}
		}
		if (this.iCalendarName.equals("")) {
			this.iCalendarName = iCalendarName;
		}
		this.schluessel = this.iCalendarName;
		status = GuKKiCalcStatus.GELESEN;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten)
	 * Zeilen untersucht und die jeweilige Eigenschaft wird abgespeichert
	 * Version V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22
	 */
	protected void neueZeile(String zeile) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (bearbeiteSubKomponente) {
			if (vEventBearbeiten) {
				if (zeile.equals("END:VEVENT")) {
					vEventNeu.abschliessen();
					vEventSammlung.add(vEventNeu);
					vEventBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vEventNeu.neueZeile(zeile);
				}
			} else if (vTodoBearbeiten) {
				if (zeile.equals("END:VTODO")) {
					vTodoNeu.abschliessen();
					vTodoSammlung.add(vTodoNeu);
					vTodoBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vTodoNeu.neueZeile(zeile);
				}
			} else if (vJournalBearbeiten) {
				if (zeile.equals("END:VJOURNAL")) {
					vJournalNeu.abschliessen();
					vJournalSammlung.add(vJournalNeu);
					vJournalBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vJournalNeu.neueZeile(zeile);
				}
			} else if (vTimezoneBearbeiten) {
				if (zeile.equals("END:VTIMEZONE")) {
					vTimezoneNeu.abschliessen();
					vTimezoneSammlung.add(vTimezoneNeu);
					vTimezoneBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vTimezoneNeu.neueZeile(zeile);
				}
			} else if (vFreeBusyBearbeiten) {
				if (zeile.equals("END:VFREEBUSY")) {
					vFreeBusyNeu.abschliessen();
					vFreeBusySammlung.add(vFreeBusyNeu);
					vFreeBusyBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vFreeBusyNeu.neueZeile(zeile);
				}
			}
		} else {
			if (zeile.equals("BEGIN:VEVENT")) {
				vEventNeu = new GuKKiCalvEvent();
				vEventBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.equals("BEGIN:VTODO")) {
				vTodoNeu = new GuKKiCalvTodo();
				vTodoBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.equals("BEGIN:VJOURNAL")) {
				vJournalNeu = new GuKKiCalvJournal();
				vJournalBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.equals("BEGIN:VTIMEZONE")) {
				vTimezoneNeu = new GuKKiCalvTimezone();
				vTimezoneBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.equals("BEGIN:VFREEBUSY")) {
				vFreeBusyNeu = new GuKKiCalvFreeBusy();
				vFreeBusyBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("CALSCALE")) {
				CALSCALE = new GuKKiCalProperty(zeile, "CALSCALE");
			} else if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
				COLOR = new GuKKiCalProperty(zeile, "COLOR");
			} else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				DESCRIPTIONSammlung.add(new GuKKiCalProperty(zeile, "DESCRIPTION"));
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
				IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
			} else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("METHOD")) {
				METHOD = new GuKKiCalProperty(zeile, "METHOD");
			} else if (zeile.length() > 4 && zeile.substring(0, 4).equals("NAME")) {
				NAMESammlung.add(new GuKKiCalProperty(zeile, "NAME"));
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("PRODID")) {
				PRODID = new GuKKiCalProperty(zeile, "PRODID");
			} else if (zeile.length() > 16 && zeile.substring(0, 16).equals("REFRESH-INTERVAL")) {
				REFRESH = new GuKKiCalProperty(zeile, "REFRESH-INTERVAL");
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("SOURCE")) {
				SOURCE = new GuKKiCalProperty(zeile, "SOURCE");
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
				UID = new GuKKiCalProperty(zeile, "UID");
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
				URL = new GuKKiCalProperty(zeile, "URL");
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("VERSION")) {
				VERSION = new GuKKiCalProperty(zeile, "VERSION");

				/* Abschluss und Fallbackparameter */

			} else if (zeile.length() > 2 && zeile.substring(0, 2).equals("X-")) {
				X_PROPSammlung.add(zeile);
			} else {
				Restinformationen.add(zeile);
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende neueZeile V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Diese Methode kopiert die iCalendar-Komponente GuKKiCaliCalendar und gibt
	 * diese Kopie zurück Version V 0.0.3 (RFC 5545, RFC 7968)
	 * 2021-12-22T15-12-22
	 */
	protected GuKKiCaliCalendar kopieren() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		GuKKiCaliCalendar temp = new GuKKiCaliCalendar();
		temp.kennung = this.kennung;
		temp.CALSCALE = this.CALSCALE == null ? null : this.CALSCALE.kopieren();
		for (GuKKiCalProperty CATEGORIES : CATEGORIESSammlung) {
			temp.CATEGORIESSammlung.add(CATEGORIES.kopieren());
		}
		temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
		for (GuKKiCalProperty DESCRIPTION : DESCRIPTIONSammlung) {
			temp.DESCRIPTIONSammlung.add(DESCRIPTION.kopieren());
		}
		for (GuKKiCalProperty IMAGE : IMAGESammlung) {
			temp.IMAGESammlung.add(IMAGE.kopieren());
		}
		temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
		temp.METHOD = this.METHOD == null ? null : this.METHOD.kopieren();
		for (GuKKiCalProperty NAME : NAMESammlung) {
			temp.NAMESammlung.add(NAME.kopieren());
		}
		temp.PRODID = this.PRODID == null ? null : this.PRODID.kopieren();
		temp.REFRESH = this.REFRESH == null ? null : this.REFRESH.kopieren();
		temp.SOURCE = this.SOURCE == null ? null : this.SOURCE.kopieren();
		temp.UID = this.UID == null ? null : this.UID.kopieren();
		temp.URL = this.URL == null ? null : this.URL.kopieren();
		temp.VERSION = this.VERSION == null ? null : this.VERSION.kopieren();
		for (GuKKiCalvEvent vEvent : this.vEventSammlung) {
			temp.vEventSammlung.add(vEvent.kopieren());
		}
		for (GuKKiCalvTodo vTodo : this.vTodoSammlung) {
			temp.vTodoSammlung.add(vTodo.kopieren());
		}
		for (GuKKiCalvJournal vJournal : this.vJournalSammlung) {
			temp.vJournalSammlung.add(vJournal.kopieren());
		}
		for (GuKKiCalvTimezone vTimezone : this.vTimezoneSammlung) {
			temp.vTimezoneSammlung.add(vTimezone.kopieren());
		}
		for (GuKKiCalvFreeBusy vFreeBusy : this.vFreeBusySammlung) {
			temp.vFreeBusySammlung.add(vFreeBusy.kopieren());
		}

		/* Abschluss und Fallbackparameter */

		for (String X_PROP : this.X_PROPSammlung) {
			temp.X_PROPSammlung.add(X_PROP);
		}
		for (String Restinformation : this.Restinformationen) {
			temp.Restinformationen.add(Restinformation);
		}
		temp.status = GuKKiCalcStatus.KOPIERT;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp;
	} // Ende kopieren V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Vergleichen aller Attribute der Komponente GuKKiCaliCalendar Version V
	 * 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22
	 *
	 * @return boolean
	 */
	protected boolean istGleich(Object dasAndere) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (!dasAndere.getClass().equals(this.getClass())) {
			return false;
		}
		GuKKiCaliCalendar temp = (GuKKiCaliCalendar) dasAndere;
		if (!((temp.CALSCALE == null && this.CALSCALE == null)
				|| (temp.CALSCALE != null && this.CALSCALE != null && temp.CALSCALE.istGleich(this.CALSCALE)))) {
			return false;
		}
		if (temp.CATEGORIESSammlung.size() != this.CATEGORIESSammlung.size()) {
			return false;
		}
		for (int i = 0; i < CATEGORIESSammlung.size(); i++) {
			if (!temp.CATEGORIESSammlung.get(i).istGleich(this.CATEGORIESSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.COLOR == null && this.COLOR == null)
				|| (temp.COLOR != null && this.COLOR != null && temp.COLOR.istGleich(this.COLOR)))) {
			return false;
		}
		if (temp.DESCRIPTIONSammlung.size() != this.DESCRIPTIONSammlung.size()) {
			return false;
		}
		for (int i = 0; i < DESCRIPTIONSammlung.size(); i++) {
			if (!temp.DESCRIPTIONSammlung.get(i).istGleich(this.DESCRIPTIONSammlung.get(i))) {
				return false;
			}
		}
		if (temp.IMAGESammlung.size() != this.IMAGESammlung.size()) {
			return false;
		}
		for (int i = 0; i < IMAGESammlung.size(); i++) {
			if (!temp.IMAGESammlung.get(i).istGleich(this.IMAGESammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
				|| (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
			return false;
		}
		if (!((temp.METHOD == null && this.METHOD == null)
				|| (temp.METHOD != null && this.METHOD != null && temp.METHOD.istGleich(this.METHOD)))) {
			return false;
		}
		if (temp.NAMESammlung.size() != this.NAMESammlung.size()) {
			return false;
		}
		for (int i = 0; i < NAMESammlung.size(); i++) {
			if (!temp.NAMESammlung.get(i).istGleich(this.NAMESammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.PRODID == null && this.PRODID == null)
				|| (temp.PRODID != null && this.PRODID != null && temp.PRODID.istGleich(this.PRODID)))) {
			return false;
		}
		if (!((temp.REFRESH == null && this.REFRESH == null)
				|| (temp.REFRESH != null && this.REFRESH != null && temp.REFRESH.istGleich(this.REFRESH)))) {
			return false;
		}
		if (!((temp.SOURCE == null && this.SOURCE == null)
				|| (temp.SOURCE != null && this.SOURCE != null && temp.SOURCE.istGleich(this.SOURCE)))) {
			return false;
		}
		if (!((temp.UID == null && this.UID == null)
				|| (temp.UID != null && this.UID != null && temp.UID.istGleich(this.UID)))) {
			return false;
		}
		if (!((temp.URL == null && this.URL == null)
				|| (temp.URL != null && this.URL != null && temp.URL.istGleich(this.URL)))) {
			return false;
		}
		if (!((temp.VERSION == null && this.VERSION == null)
				|| (temp.VERSION != null && this.VERSION != null && temp.VERSION.istGleich(this.VERSION)))) {
			return false;
		}
		if (temp.vEventSammlung.size() != this.vEventSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vEventSammlung.size(); i++) {
			if (!temp.vEventSammlung.get(i).istGleich(this.vEventSammlung.get(i))) {
				return false;
			}
		}
		if (temp.vTodoSammlung.size() != this.vTodoSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vTodoSammlung.size(); i++) {
			if (!temp.vTodoSammlung.get(i).istGleich(this.vTodoSammlung.get(i))) {
				return false;
			}
		}
		if (temp.vJournalSammlung.size() != this.vJournalSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vJournalSammlung.size(); i++) {
			if (!temp.vJournalSammlung.get(i).istGleich(this.vJournalSammlung.get(i))) {
				return false;
			}
		}
		if (temp.vTimezoneSammlung.size() != this.vTimezoneSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vTimezoneSammlung.size(); i++) {
			if (!temp.vTimezoneSammlung.get(i).istGleich(this.vTimezoneSammlung.get(i))) {
				return false;
			}
		}
		if (temp.vFreeBusySammlung.size() != this.vFreeBusySammlung.size()) {
			return false;
		}
		for (int i = 0; i < vFreeBusySammlung.size(); i++) {
			if (!temp.vFreeBusySammlung.get(i).istGleich(this.vFreeBusySammlung.get(i))) {
				return false;
			}
		}

		/* Abschluss und Fallbackparameter */

		if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
			return false;
		}
		for (int i = 0; i < X_PROPSammlung.size(); i++) {
			if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
				return false;
			}
		}
		if (temp.Restinformationen.size() != this.Restinformationen.size()) {
			return false;
		}
		for (int i = 0; i < Restinformationen.size(); i++) {
			if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {
				return false;
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return true;
	} // Ende istGleich V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Mit dieser Methode werden die einzelnen Eigenschaften als gültige
	 * Parameterkette ausgegeben Version V 0.0.3 (RFC 5545, RFC 7968)
	 * 2021-12-22T15-12-22
	 */
	protected String ausgeben() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VCALENDAR");
		componentDatenstrom += this.CALSCALE == null ? "" : ausgebenInDatenstrom(this.CALSCALE.ausgeben());
		for (GuKKiCalProperty CATEGORIES : CATEGORIESSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(CATEGORIES.ausgeben());
		}
		componentDatenstrom += this.COLOR == null ? "" : ausgebenInDatenstrom(this.COLOR.ausgeben());
		for (GuKKiCalProperty DESCRIPTION : DESCRIPTIONSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(DESCRIPTION.ausgeben());
		}
		for (GuKKiCalProperty IMAGE : IMAGESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(IMAGE.ausgeben());
		}
		componentDatenstrom += this.LAST_MOD == null ? "" : ausgebenInDatenstrom(this.LAST_MOD.ausgeben());
		componentDatenstrom += this.METHOD == null ? "" : ausgebenInDatenstrom(this.METHOD.ausgeben());
		for (GuKKiCalProperty NAME : NAMESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(NAME.ausgeben());
		}
		componentDatenstrom += this.PRODID == null ? "" : ausgebenInDatenstrom(this.PRODID.ausgeben());
		componentDatenstrom += this.REFRESH == null ? "" : ausgebenInDatenstrom(this.REFRESH.ausgeben());
		componentDatenstrom += this.SOURCE == null ? "" : ausgebenInDatenstrom(this.SOURCE.ausgeben());
		componentDatenstrom += this.UID == null ? "" : ausgebenInDatenstrom(this.UID.ausgeben());
		componentDatenstrom += this.URL == null ? "" : ausgebenInDatenstrom(this.URL.ausgeben());
		componentDatenstrom += this.VERSION == null ? "" : ausgebenInDatenstrom(this.VERSION.ausgeben());
		for (GuKKiCalvEvent vEvent : this.vEventSammlung) {
			componentDatenstrom += vEvent.ausgeben();
		}
		for (GuKKiCalvTodo vTodo : this.vTodoSammlung) {
			componentDatenstrom += vTodo.ausgeben();
		}
		for (GuKKiCalvJournal vJournal : this.vJournalSammlung) {
			componentDatenstrom += vJournal.ausgeben();
		}
		for (GuKKiCalvTimezone vTimezone : this.vTimezoneSammlung) {
			componentDatenstrom += vTimezone.ausgeben();
		}
		for (GuKKiCalvFreeBusy vFreeBusy : this.vFreeBusySammlung) {
			componentDatenstrom += vFreeBusy.ausgeben();
		}

		/* Abschluss und Fallbackparameter */

		for (String X_PROP : this.X_PROPSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(X_PROP);
		}
		for (String Restinformation : this.Restinformationen) {
			componentDatenstrom += ausgebenInDatenstrom(Restinformation);
		}
		componentDatenstrom += ausgebenInDatenstrom("END:VCALENDAR");
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return componentDatenstrom;
	} // Ende ausgeben V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

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
	@Override
	public String toString() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String ausgabeString = nz + "vCalendarInformationen:" + nz + iCalendarName + " --- " + iCalendarPfad + nz
				+ "vCalendarDaten:" + nz;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return ausgabeString;
	}

	public String toString(String ausgabeLevel) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
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
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return ausgabeString;
	}
}