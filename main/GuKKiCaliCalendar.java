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
	boolean vEventDatenSammeln = false;
	ArrayList<String> vEventDatenArray = new ArrayList<String>();
	boolean vTodoDatenSammeln = false;
	ArrayList<String> vTodoDatenArray = new ArrayList<String>();
	boolean vJournalDatenSammeln = false;
	ArrayList<String> vJournalDatenArray = new ArrayList<String>();
	boolean vFreeBusyDatenSammeln = false;
	ArrayList<String> vFreeBusyDatenArray = new ArrayList<String>();
	boolean vTimezoneDatenSammeln = false;
	ArrayList<String> vTimezoneDatenArray = new ArrayList<String>();

	public GuKKiCaliCalendar() {
		/*	@formatter:off */
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		this.kennung = GuKKiCalcKennung.CALENDAR;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
		/*	@formatter:on */
	}

	public GuKKiCaliCalendar(String iCalendarDatenParam, String kalenderNameParam, String kalenderPfadParam)
			throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		this.kennung = GuKKiCalcKennung.CALENDAR;

		einlesenAusDatenstrom(iCalendarDatenParam);

		this.iCalendarPfad = kalenderPfadParam;
		this.kalenderName = kalenderNameParam;
		this.schluessel = this.kalenderName;

		einlesenAusDatenstrom(iCalendarDaten);

// @formatter:off    	 
// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
 
// Subkomponente: vEvent GuKKiCalvEvent VEVENT
        if (vEventDatenArray.size() != 0) {
            vEventSammlungAnlegen();
        }
 
// Subkomponente: vTodo GuKKiCalvTodo VTODO
        if (vTodoDatenArray.size() != 0) {
            vTodoSammlungAnlegen();
        }
 
// Subkomponente: vJournal GuKKiCalvJournal VJOURNAL
        if (vJournalDatenArray.size() != 0) {
            vJournalSammlungAnlegen();
        }
 
// Subkomponente: vTimezone GuKKiCalvTimezone VTIMEZONE
        if (vTimezoneDatenArray.size() != 0) {
            vTimezoneSammlungAnlegen();
        }
 
// Subkomponente: vFreeBusy GuKKiCalvFreeBusy VFREEBUSY
        if (vFreeBusyDatenArray.size() != 0) {
            vFreeBusySammlungAnlegen();
        }
 
        status = GuKKiCalcStatus.GELESEN;
 
        if (Restinformationen.size() > 0) {
            for (String Restinformation : Restinformationen) {
                logger.log(Level.INFO, "Restinformation:" + "-->" + Restinformation + "<--");
            }
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Generieren der Methoden für den Aufbau der Komponentensammlungen
 
// Subkomponente: vEvent GuKKiCalvEvent VEVENT
    private void vEventSammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vEventDaten = "";
 
        for (String zeile : vEventDatenArray) {
            if (zeile.equals("BEGIN:VEVENT")) {
                vEventDaten = zeile + nz;
            } else if (zeile.equals("END:VEVENT")) {
                vEventDaten += zeile + nz;
                vEventSammlung.add(new GuKKiCalvEvent(vEventDaten));
                vEventDaten = "";
            } else {
                vEventDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Subkomponente: vTodo GuKKiCalvTodo VTODO
    private void vTodoSammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vTodoDaten = "";
 
        for (String zeile : vTodoDatenArray) {
            if (zeile.equals("BEGIN:VTODO")) {
                vTodoDaten = zeile + nz;
            } else if (zeile.equals("END:VTODO")) {
                vTodoDaten += zeile + nz;
                vTodoSammlung.add(new GuKKiCalvTodo(vTodoDaten));
                vTodoDaten = "";
            } else {
                vTodoDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Subkomponente: vJournal GuKKiCalvJournal VJOURNAL
    private void vJournalSammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vJournalDaten = "";
 
        for (String zeile : vJournalDatenArray) {
            if (zeile.equals("BEGIN:VJOURNAL")) {
                vJournalDaten = zeile + nz;
            } else if (zeile.equals("END:VJOURNAL")) {
                vJournalDaten += zeile + nz;
                vJournalSammlung.add(new GuKKiCalvJournal(vJournalDaten));
                vJournalDaten = "";
            } else {
                vJournalDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Subkomponente: vTimezone GuKKiCalvTimezone VTIMEZONE
    private void vTimezoneSammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vTimezoneDaten = "";
 
        for (String zeile : vTimezoneDatenArray) {
            if (zeile.equals("BEGIN:VTIMEZONE")) {
                vTimezoneDaten = zeile + nz;
            } else if (zeile.equals("END:VTIMEZONE")) {
                vTimezoneDaten += zeile + nz;
                vTimezoneSammlung.add(new GuKKiCalvTimezone(vTimezoneDaten));
                vTimezoneDaten = "";
            } else {
                vTimezoneDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Subkomponente: vFreeBusy GuKKiCalvFreeBusy VFREEBUSY
    private void vFreeBusySammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vFreeBusyDaten = "";
 
        for (String zeile : vFreeBusyDatenArray) {
            if (zeile.equals("BEGIN:VFREEBUSY")) {
                vFreeBusyDaten = zeile + nz;
            } else if (zeile.equals("END:VFREEBUSY")) {
                vFreeBusyDaten += zeile + nz;
                vFreeBusySammlung.add(new GuKKiCalvFreeBusy(vFreeBusyDaten));
                vFreeBusyDaten = "";
            } else {
                vFreeBusyDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
// Anfang der generierten Methoden für GuKKiCaliCalendar 0.1 Wed Dec 08 23:39:38 CET 2021
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     */
    @Override
    protected void verarbeitenZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (!zeile.equals("BEGIN:VCALENDAR") & !zeile.equals("END:VCALENDAR")) {
 
// Subkomponente: vEvent GuKKiCalvEvent VEVENT)
            if (zeile.equals("BEGIN:VEVENT")) {
                vEventDatenSammeln = true;
                vEventDatenArray.add(zeile);
            } else if (zeile.equals("END:VEVENT")) {
                vEventDatenSammeln = false;
                vEventDatenArray.add(zeile);
            } else if (vEventDatenSammeln) {
                vEventDatenArray.add(zeile);
 
// Subkomponente: vTodo GuKKiCalvTodo VTODO)
            } else if (zeile.equals("BEGIN:VTODO")) {
                vTodoDatenSammeln = true;
                vTodoDatenArray.add(zeile);
            } else if (zeile.equals("END:VTODO")) {
                vTodoDatenSammeln = false;
                vTodoDatenArray.add(zeile);
            } else if (vTodoDatenSammeln) {
                vTodoDatenArray.add(zeile);
 
// Subkomponente: vJournal GuKKiCalvJournal VJOURNAL)
            } else if (zeile.equals("BEGIN:VJOURNAL")) {
                vJournalDatenSammeln = true;
                vJournalDatenArray.add(zeile);
            } else if (zeile.equals("END:VJOURNAL")) {
                vJournalDatenSammeln = false;
                vJournalDatenArray.add(zeile);
            } else if (vJournalDatenSammeln) {
                vJournalDatenArray.add(zeile);
 
// Subkomponente: vTimezone GuKKiCalvTimezone VTIMEZONE)
            } else if (zeile.equals("BEGIN:VTIMEZONE")) {
                vTimezoneDatenSammeln = true;
                vTimezoneDatenArray.add(zeile);
            } else if (zeile.equals("END:VTIMEZONE")) {
                vTimezoneDatenSammeln = false;
                vTimezoneDatenArray.add(zeile);
            } else if (vTimezoneDatenSammeln) {
                vTimezoneDatenArray.add(zeile);
 
// Subkomponente: vFreeBusy GuKKiCalvFreeBusy VFREEBUSY)
            } else if (zeile.equals("BEGIN:VFREEBUSY")) {
                vFreeBusyDatenSammeln = true;
                vFreeBusyDatenArray.add(zeile);
            } else if (zeile.equals("END:VFREEBUSY")) {
                vFreeBusyDatenSammeln = false;
                vFreeBusyDatenArray.add(zeile);
            } else if (vFreeBusyDatenSammeln) {
                vFreeBusyDatenArray.add(zeile);
 
// Eigenschaft: CALSCALE GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("CALSCALE")) {
                CALSCALE = new GuKKiCalProperty(zeile, "CALSCALE");
 
// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
                CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
                COLOR = new GuKKiCalProperty(zeile, "COLOR");
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
                DESCRIPTIONSammlung.add(new GuKKiCalProperty(zeile, "DESCRIPTION"));
 
// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
                IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
 
// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
                LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
 
// Eigenschaft: METHOD GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("METHOD")) {
                METHOD = new GuKKiCalProperty(zeile, "METHOD");
 
// Eigenschaft: NAME GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 4 && zeile.substring(0, 4).equals("NAME")) {
                NAMESammlung.add(new GuKKiCalProperty(zeile, "NAME"));
 
// Eigenschaft: PRODID GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("PRODID")) {
                PRODID = new GuKKiCalProperty(zeile, "PRODID");
 
// Eigenschaft: REFRESH GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 16 && zeile.substring(0, 16).equals("REFRESH-INTERVAL")) {
                REFRESH = new GuKKiCalProperty(zeile, "REFRESH-INTERVAL");
 
// Eigenschaft: SOURCE GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("SOURCE")) {
                SOURCE = new GuKKiCalProperty(zeile, "SOURCE");
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
                UID = new GuKKiCalProperty(zeile, "UID");
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
                URL = new GuKKiCalProperty(zeile, "URL");
 
// Eigenschaft: VERSION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("VERSION")) {
                VERSION = new GuKKiCalProperty(zeile, "VERSION");
 
// Eigenschaft: X_PROP String auftreten 0:n
            } else  if (zeile.length() > 2 && zeile.substring(0, 2).equals("X-")) {
                X_PROPSammlung.add(zeile);
 
// Abschluss und Fallbackparameter
 
            } else {
                Restinformationen.add(zeile);
            }
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    } // Ende verarbeitenZeile
 
    /**
     * Diese Methode kopiert die iCalendar-Komponente
     * GuKKiCaliCalendar und gibt diese Kopie zurück
     */
    protected GuKKiCaliCalendar kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCaliCalendar temp = new GuKKiCaliCalendar();
 
        temp.kennung = this.kennung;
 
// Eigenschaft: CALSCALE GuKKiCalProperty auftreten 0:1
        temp.CALSCALE = this.CALSCALE == null ? null : this.CALSCALE.kopieren();
 
// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCATEGORIES : CATEGORIESSammlung) {
            temp.CATEGORIESSammlung.add(pCATEGORIES.kopieren());
        }
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
        temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pDESCRIPTION : DESCRIPTIONSammlung) {
            temp.DESCRIPTIONSammlung.add(pDESCRIPTION.kopieren());
        }
 
// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pIMAGE : IMAGESammlung) {
            temp.IMAGESammlung.add(pIMAGE.kopieren());
        }
 
// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
        temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
 
// Eigenschaft: METHOD GuKKiCalProperty auftreten 0:1
        temp.METHOD = this.METHOD == null ? null : this.METHOD.kopieren();
 
// Eigenschaft: NAME GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pNAME : NAMESammlung) {
            temp.NAMESammlung.add(pNAME.kopieren());
        }
 
// Eigenschaft: PRODID GuKKiCalProperty auftreten 0:1
        temp.PRODID = this.PRODID == null ? null : this.PRODID.kopieren();
 
// Eigenschaft: REFRESH GuKKiCalProperty auftreten 0:1
        temp.REFRESH = this.REFRESH == null ? null : this.REFRESH.kopieren();
 
// Eigenschaft: SOURCE GuKKiCalProperty auftreten 0:1
        temp.SOURCE = this.SOURCE == null ? null : this.SOURCE.kopieren();
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
        temp.UID = this.UID == null ? null : this.UID.kopieren();
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
        temp.URL = this.URL == null ? null : this.URL.kopieren();
 
// Eigenschaft: VERSION GuKKiCalProperty auftreten 0:1
        temp.VERSION = this.VERSION == null ? null : this.VERSION.kopieren();
 
// Eigenschaft: X_PROP String auftreten 0:n
        for (String pX_PROP : X_PROPSammlung) {
            temp.X_PROPSammlung.add(pX_PROP);
        }
 
// Subkomponente: vEvent GuKKiCalvEvent auftreten 0:n
        for (GuKKiCalvEvent vEvent : this.vEventSammlung) {
            temp.vEventSammlung.add(vEvent.kopieren());
        }
 
// Subkomponente: vTodo GuKKiCalvTodo auftreten 0:n
        for (GuKKiCalvTodo vTodo : this.vTodoSammlung) {
            temp.vTodoSammlung.add(vTodo.kopieren());
        }
 
// Subkomponente: vJournal GuKKiCalvJournal auftreten 0:n
        for (GuKKiCalvJournal vJournal : this.vJournalSammlung) {
            temp.vJournalSammlung.add(vJournal.kopieren());
        }
 
// Subkomponente: vTimezone GuKKiCalvTimezone auftreten 0:n
        for (GuKKiCalvTimezone vTimezone : this.vTimezoneSammlung) {
            temp.vTimezoneSammlung.add(vTimezone.kopieren());
        }
 
// Subkomponente: vFreeBusy GuKKiCalvFreeBusy auftreten 0:n
        for (GuKKiCalvFreeBusy vFreeBusy : this.vFreeBusySammlung) {
            temp.vFreeBusySammlung.add(vFreeBusy.kopieren());
        }
 
// Abschluss und Fallbackparameter
        for (String Restinformation : this.Restinformationen) {
            temp.Restinformationen.add(Restinformation);
        }
 
        temp.status = GuKKiCalcStatus.KOPIERT;
 
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
 
        return temp;
    } // Ende kopieren
 
    /**
     * Vergleichen aller Attribute der Komponente GuKKiCaliCalendar
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
 
        GuKKiCaliCalendar temp = (GuKKiCaliCalendar) dasAndere;
 
// Eigenschaft: CALSCALE GuKKiCalProperty auftreten 0:1
        if (!((temp.CALSCALE == null && this.CALSCALE == null)
                || (temp.CALSCALE != null && this.CALSCALE != null && temp.CALSCALE.istGleich(this.CALSCALE)))) {
            return false;
        }
 
// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
        if (temp.CATEGORIESSammlung.size() != this.CATEGORIESSammlung.size()) {
            return false;
        }
        for (int i = 0;i < CATEGORIESSammlung.size(); i++) {
            if (!temp.CATEGORIESSammlung.get(i).istGleich(this.CATEGORIESSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
        if (!((temp.COLOR == null && this.COLOR == null)
                || (temp.COLOR != null && this.COLOR != null && temp.COLOR.istGleich(this.COLOR)))) {
            return false;
        }
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
        if (temp.DESCRIPTIONSammlung.size() != this.DESCRIPTIONSammlung.size()) {
            return false;
        }
        for (int i = 0;i < DESCRIPTIONSammlung.size(); i++) {
            if (!temp.DESCRIPTIONSammlung.get(i).istGleich(this.DESCRIPTIONSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
        if (temp.IMAGESammlung.size() != this.IMAGESammlung.size()) {
            return false;
        }
        for (int i = 0;i < IMAGESammlung.size(); i++) {
            if (!temp.IMAGESammlung.get(i).istGleich(this.IMAGESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
        if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
                || (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
            return false;
        }
 
// Eigenschaft: METHOD GuKKiCalProperty auftreten 0:1
        if (!((temp.METHOD == null && this.METHOD == null)
                || (temp.METHOD != null && this.METHOD != null && temp.METHOD.istGleich(this.METHOD)))) {
            return false;
        }
 
// Eigenschaft: NAME GuKKiCalProperty auftreten 0:n
        if (temp.NAMESammlung.size() != this.NAMESammlung.size()) {
            return false;
        }
        for (int i = 0;i < NAMESammlung.size(); i++) {
            if (!temp.NAMESammlung.get(i).istGleich(this.NAMESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: PRODID GuKKiCalProperty auftreten 0:1
        if (!((temp.PRODID == null && this.PRODID == null)
                || (temp.PRODID != null && this.PRODID != null && temp.PRODID.istGleich(this.PRODID)))) {
            return false;
        }
 
// Eigenschaft: REFRESH GuKKiCalProperty auftreten 0:1
        if (!((temp.REFRESH == null && this.REFRESH == null)
                || (temp.REFRESH != null && this.REFRESH != null && temp.REFRESH.istGleich(this.REFRESH)))) {
            return false;
        }
 
// Eigenschaft: SOURCE GuKKiCalProperty auftreten 0:1
        if (!((temp.SOURCE == null && this.SOURCE == null)
                || (temp.SOURCE != null && this.SOURCE != null && temp.SOURCE.istGleich(this.SOURCE)))) {
            return false;
        }
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
        if (!((temp.UID == null && this.UID == null)
                || (temp.UID != null && this.UID != null && temp.UID.istGleich(this.UID)))) {
            return false;
        }
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
        if (!((temp.URL == null && this.URL == null)
                || (temp.URL != null && this.URL != null && temp.URL.istGleich(this.URL)))) {
            return false;
        }
 
// Eigenschaft: VERSION GuKKiCalProperty auftreten 0:1
        if (!((temp.VERSION == null && this.VERSION == null)
                || (temp.VERSION != null && this.VERSION != null && temp.VERSION.istGleich(this.VERSION)))) {
            return false;
        }
 
// Eigenschaft: X_PROP String auftreten 0:n
        if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
            return false;
        }
        for (int i = 0;i < X_PROPSammlung.size(); i++) {
            if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
               return false;
            }
        }
 
// Subkomponente: vEvent GuKKiCalvEvent auftreten 0:n
        if (temp.vEventSammlung.size() != this.vEventSammlung.size()) {
            return false;
        }
        for (int i = 0; i < vEventSammlung.size(); i++) {
            if (!temp.vEventSammlung.get(i).istGleich(this.vEventSammlung.get(i))) {
                return false;
            }
        }
 
// Subkomponente: vTodo GuKKiCalvTodo auftreten 0:n
        if (temp.vTodoSammlung.size() != this.vTodoSammlung.size()) {
            return false;
        }
        for (int i = 0; i < vTodoSammlung.size(); i++) {
            if (!temp.vTodoSammlung.get(i).istGleich(this.vTodoSammlung.get(i))) {
                return false;
            }
        }
 
// Subkomponente: vJournal GuKKiCalvJournal auftreten 0:n
        if (temp.vJournalSammlung.size() != this.vJournalSammlung.size()) {
            return false;
        }
        for (int i = 0; i < vJournalSammlung.size(); i++) {
            if (!temp.vJournalSammlung.get(i).istGleich(this.vJournalSammlung.get(i))) {
                return false;
            }
        }
 
// Subkomponente: vTimezone GuKKiCalvTimezone auftreten 0:n
        if (temp.vTimezoneSammlung.size() != this.vTimezoneSammlung.size()) {
            return false;
        }
        for (int i = 0; i < vTimezoneSammlung.size(); i++) {
            if (!temp.vTimezoneSammlung.get(i).istGleich(this.vTimezoneSammlung.get(i))) {
                return false;
            }
        }
 
// Subkomponente: vFreeBusy GuKKiCalvFreeBusy auftreten 0:n
        if (temp.vFreeBusySammlung.size() != this.vFreeBusySammlung.size()) {
            return false;
        }
        for (int i = 0; i < vFreeBusySammlung.size(); i++) {
            if (!temp.vFreeBusySammlung.get(i).istGleich(this.vFreeBusySammlung.get(i))) {
                return false;
            }
        }
 
// Abschluss und Fallbackparameter
        if (temp.Restinformationen.size() != this.Restinformationen.size()) {
            return false;
        }
        for (int i = 0; i < Restinformationen.size(); i++) {
            if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {
                return false; 
            }
        }
 
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
 
        return true;
    } // Ende istGleich
 
// Ende der generierten Methoden für GuKKiCaliCalendar
// @formatter:on    	 
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