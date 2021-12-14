package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
	 * Die Klasse GuKKiCalvFreeBusy enthält alle Daten für eine VFREEBUSY-Komponente im iCal Format
	 *         
	 * @author GuKKDevel
	 *  
	 * @formatter:off
	 * 
	 * 	Free/Busy Component
	 * 
	 * 	RFC 5545 (september 2009) item 3.6.4; p. 60
	 *  
	 * 	Component Name: VFREEBUSY
	 * 
	 * 	Purpose: Provide a grouping of component properties that describe
	 * 		either a request for free/busy time, describe a response to a
	 * 		request for free/busy time, or describe a published set of busy time.
	 * 
	 *	Description: A "VFREEBUSY" calendar component is a grouping of
	 *		component properties that represents either a request for free or
	 *		busy time information, a reply to a request for free or busy time
	 *		information, or a published set of busy time information.
	 *
	 *		When used to request free/busy time information, the "ATTENDEE"
	 *		property specifies the calendar users whose free/busy time is
	 *		being requested; the "ORGANIZER" property specifies the calendar
	 *		user who is requesting the free/busy time; the "DTSTART" and
	 *		"DTEND" properties specify the window of time for which the free/
	 *		busy time is being requested; the "UID" and "DTSTAMP" properties
	 *		are specified to assist in proper sequencing of multiple free/busy
	 *		time requests.
	 *
	 *		When used to reply to a request for free/busy time, the "ATTENDEE"
	 *		property specifies the calendar user responding to the free/busy
	 *		time request; the "ORGANIZER" property specifies the calendar user
	 *		that originally requested the free/busy time; the "FREEBUSY"
	 *		property specifies the free/busy time information (if it exists);
	 *		and the "UID" and "DTSTAMP" properties are specified to assist in
	 *		proper sequencing of multiple free/busy time replies.
	 *
	 *		When used to publish busy time, the "ORGANIZER" property specifies
	 *		the calendar user associated with the published busy time; the
	 *		"DTSTART" and "DTEND" properties specify an inclusive time window
	 *		that surrounds the busy time information; the "FREEBUSY" property
	 *		specifies the published busy time information; and the "DTSTAMP"
	 *		property specifies the DATE-TIME that iCalendar object was
	 *		created.
	 *
	 *		The "VFREEBUSY" calendar component cannot be nested within another
	 *		calendar component. Multiple "VFREEBUSY" calendar components can
	 *		be specified within an iCalendar object. This permits the
	 *		grouping of free/busy information into logical collections, such
	 *		as monthly groups of busy time information.
	 *
	 *		The "VFREEBUSY" calendar component is intended for use in
	 *		iCalendar object methods involving requests for free time,
	 *		requests for busy time, requests for both free and busy, and the
	 *		associated replies.
	 *
	 *		Free/Busy information is represented with the "FREEBUSY" property.
	 *		This property provides a terse representation of time periods.
	 *		One or more "FREEBUSY" properties can be specified in the
	 *		"VFREEBUSY" calendar component.
	 *
	 *		When present in a "VFREEBUSY" calendar component, the "DTSTART"
	 *		and "DTEND" properties SHOULD be specified prior to any "FREEBUSY"
	 *		properties.
	 *
	 *		The recurrence properties ("RRULE", "RDATE", "EXDATE") are not
	 *		permitted within a "VFREEBUSY" calendar component. Any recurring
	 *		events are resolved into their individual busy time periods using
	 *		the "FREEBUSY" property.
	 *
	 * 	Format Definition: A "VFREEBUSY" calendar component is defined by the following notation:
	 * 
	 * 		freebusyc = "BEGIN" ":" "VFREEBUSY" CRLF
	 *		
	 *			fbprop
	 *
	 *			"END" ":" "VFREEBUSY" CRLF
	 *
	 *			fbprop = *(
	 *
	 *		The following are REQUIRED, but MUST NOT occur more than once.
	 *
	 *				dtstamp / uid /
	 *	
	 *		The following are OPTIONAL, but MUST NOT occur more than once.
	 *
	 *				contact / dtstart / dtend /
	 *				organizer / url /
	 *		
	 *		The following are OPTIONAL, and MAY occur more than once.
	 *
	 *				attendee / comment / freebusy / rstatus / 
	 *				x-prop /iana-prop
	 *
	 *				)
	 *
	 *
	 * @formatter:on
	 * 
	 */
public class GuKKiCalvFreeBusy extends GuKKiCalComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * Daten für das VFREEBUSY-Element (freebusyc)
	 */
	private String Kennung = "";

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty DTSTAMP = null;
	private GuKKiCalProperty UID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once. 
	 * contact / dtstart / dtend / organizer / url /
	 * 
	 */
	private GuKKiCalProperty CONTACT = null;
	private GuKKiCalProperty DTSTART = null;
	private GuKKiCalProperty DTEND = null;
	private GuKKiCalProperty ORGANIZER = null;
	private GuKKiCalProperty URL = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 * 			attendee / comment / freebusy / rstatus / 
	 *				x-prop /iana-prop
	 */
	private ArrayList<GuKKiCalProperty> ATTENDEESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> COMMENTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> FREEBUSYSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RSTATUSSammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();
	private ArrayList<String> Restinformationen = new ArrayList<String>();
	
	private GuKKiCalvFreeBusy() {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		kennung = GuKKiCalcKennung.FREEBUSY;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	}
	public GuKKiCalvFreeBusy(String vFreeBusyDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}

		kennung = GuKKiCalcKennung.FREEBUSY;

		einlesenAusDatenstrom(vFreeBusyDaten);

// @formatter:off    	 
// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
 
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
// Anfang der generierten Methoden für GuKKiCalvFreeBusy 0.1 Wed Dec 08 23:39:38 CET 2021
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     */
    @Override
    protected void verarbeitenZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (!zeile.equals("BEGIN:VFREEBUSY") & !zeile.equals("END:VFREEBUSY")) {
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
             if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
                ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
                COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
                CONTACT = new GuKKiCalProperty(zeile, "CONTACT");
 
// Eigenschaft: DTEND GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("DTEND")) {
                DTEND = new GuKKiCalProperty(zeile, "DTEND");
 
// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
                DTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
                DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
 
// Eigenschaft: FREEBUSY GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 8 && zeile.substring(0, 8).equals("FREEBUSY")) {
                FREEBUSYSammlung.add(new GuKKiCalProperty(zeile, "FREEBUSY"));
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
                ORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
 
// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
                RSTATUSSammlung.add(new GuKKiCalProperty(zeile, "REQUEST-STATUS"));
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
                UID = new GuKKiCalProperty(zeile, "UID");
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
                URL = new GuKKiCalProperty(zeile, "URL");
 
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
     * GuKKiCalvFreeBusy und gibt diese Kopie zurück
     */
    protected GuKKiCalvFreeBusy kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCalvFreeBusy temp = new GuKKiCalvFreeBusy();
 
        temp.kennung = this.kennung;
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pATTENDEE : ATTENDEESammlung) {
            temp.ATTENDEESammlung.add(pATTENDEE.kopieren());
        }
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCOMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(pCOMMENT.kopieren());
        }
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:1
        temp.CONTACT = this.CONTACT == null ? null : this.CONTACT.kopieren();
 
// Eigenschaft: DTEND GuKKiCalProperty auftreten 0:1
        temp.DTEND = this.DTEND == null ? null : this.DTEND.kopieren();
 
// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
        temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
 
// Eigenschaft: FREEBUSY GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pFREEBUSY : FREEBUSYSammlung) {
            temp.FREEBUSYSammlung.add(pFREEBUSY.kopieren());
        }
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
        temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
 
// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRSTATUS : RSTATUSSammlung) {
            temp.RSTATUSSammlung.add(pRSTATUS.kopieren());
        }
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
        temp.UID = this.UID == null ? null : this.UID.kopieren();
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
        temp.URL = this.URL == null ? null : this.URL.kopieren();
 
// Eigenschaft: X_PROP String auftreten 0:n
        for (String pX_PROP : X_PROPSammlung) {
            temp.X_PROPSammlung.add(pX_PROP);
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
     * Vergleichen aller Attribute der Komponente GuKKiCalvFreeBusy
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
 
        GuKKiCalvFreeBusy temp = (GuKKiCalvFreeBusy) dasAndere;
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
        if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTENDEESammlung.size(); i++) {
            if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
        if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
            return false;
        }
        for (int i = 0;i < COMMENTSammlung.size(); i++) {
            if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:1
        if (!((temp.CONTACT == null && this.CONTACT == null)
                || (temp.CONTACT != null && this.CONTACT != null && temp.CONTACT.istGleich(this.CONTACT)))) {
            return false;
        }
 
// Eigenschaft: DTEND GuKKiCalProperty auftreten 0:1
        if (!((temp.DTEND == null && this.DTEND == null)
                || (temp.DTEND != null && this.DTEND != null && temp.DTEND.istGleich(this.DTEND)))) {
            return false;
        }
 
// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
        if (!((temp.DTSTAMP == null && this.DTSTAMP == null)
                || (temp.DTSTAMP != null && this.DTSTAMP != null && temp.DTSTAMP.istGleich(this.DTSTAMP)))) {
            return false;
        }
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
        if (!((temp.DTSTART == null && this.DTSTART == null)
                || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
            return false;
        }
 
// Eigenschaft: FREEBUSY GuKKiCalProperty auftreten 0:n
        if (temp.FREEBUSYSammlung.size() != this.FREEBUSYSammlung.size()) {
            return false;
        }
        for (int i = 0;i < FREEBUSYSammlung.size(); i++) {
            if (!temp.FREEBUSYSammlung.get(i).istGleich(this.FREEBUSYSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
        if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
                || (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
            return false;
        }
 
// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
        if (temp.RSTATUSSammlung.size() != this.RSTATUSSammlung.size()) {
            return false;
        }
        for (int i = 0;i < RSTATUSSammlung.size(); i++) {
            if (!temp.RSTATUSSammlung.get(i).istGleich(this.RSTATUSSammlung.get(i))) {
                return false;
            }
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
 
// Eigenschaft: X_PROP String auftreten 0:n
        if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
            return false;
        }
        for (int i = 0;i < X_PROPSammlung.size(); i++) {
            if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
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
 
// Ende der generierten Methoden für GuKKiCalvFreeBusy
// @formatter:on    	 	 
}
