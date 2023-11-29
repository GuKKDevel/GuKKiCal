package component;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import enumerations.*;
import exceptions.*;

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
public class GuKKiCalvFreeBusy extends GuKKiCalvComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * Daten für das VFREEBUSY-Element (freebusyc)
	 */
	private String Kennung = "";

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalcProperty DTSTAMP = null;
	private GuKKiCalcProperty UID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once. 
	 * contact / dtstart / dtend / organizer / url /
	 * 
	 */
	private GuKKiCalcProperty CONTACT = null;
	private GuKKiCalcProperty DTSTART = null;
	private GuKKiCalcProperty DTEND = null;
	private GuKKiCalcProperty ORGANIZER = null;
	private GuKKiCalcProperty URL = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 * 			attendee / comment / freebusy / rstatus / 
	 *				x-prop /iana-prop
	 */
	private List<GuKKiCalcProperty> ATTENDEESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> COMMENTSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> FREEBUSYSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RSTATUSSammlung = new LinkedList<GuKKiCalcProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private List<String> X_PROPSammlung = new LinkedList<String>();
	private List<String> Restinformationen = new LinkedList<String>();
	
	protected GuKKiCalvFreeBusy() {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		kennung = GuKKiCalcKennung.FREEBUSY;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	}
	
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected void neueZeile(String zeile) /* throws Exception */ {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
            ATTENDEESammlung.add(new GuKKiCalcProperty(zeile, "ATTENDEE"));
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
            COMMENTSammlung.add(new GuKKiCalcProperty(zeile, "COMMENT"));
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
            CONTACT = new GuKKiCalcProperty(zeile, "CONTACT");
        } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("DTEND")) {
            DTEND = new GuKKiCalcProperty(zeile, "DTEND");
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
            DTSTAMP = new GuKKiCalcProperty(zeile, "DTSTAMP");
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
            DTSTART = new GuKKiCalcProperty(zeile, "DTSTART");
        } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("FREEBUSY")) {
            FREEBUSYSammlung.add(new GuKKiCalcProperty(zeile, "FREEBUSY"));
        } else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
            ORGANIZER = new GuKKiCalcProperty(zeile, "ORGANIZER");
        } else if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
            RSTATUSSammlung.add(new GuKKiCalcProperty(zeile, "REQUEST-STATUS"));
        } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
            UID = new GuKKiCalcProperty(zeile, "UID");
        } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
            URL = new GuKKiCalcProperty(zeile, "URL");
 
/* Abschluss und Fallbackparameter */
 
        } else if (zeile.length() > 2 && zeile.substring(0,2).equals("X-")) {
            X_PROPSammlung.add(zeile);
        } else {
            Restinformationen.add(zeile);
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    } // Ende neueZeile V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
    /**
     * Diese Methode kopiert die iCalendar-Komponente
     * GuKKiCalvFreeBusy und gibt diese Kopie zurück
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected GuKKiCalvFreeBusy kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        GuKKiCalvFreeBusy temp = new GuKKiCalvFreeBusy();
        temp.kennung = this.kennung;
        for (GuKKiCalcProperty ATTENDEE : ATTENDEESammlung) {
            temp.ATTENDEESammlung.add(ATTENDEE.kopieren());
        }
        for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(COMMENT.kopieren());
        }
        temp.CONTACT = this.CONTACT == null ? null : this.CONTACT.kopieren();
        temp.DTEND = this.DTEND == null ? null : this.DTEND.kopieren();
        temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
        for (GuKKiCalcProperty FREEBUSY : FREEBUSYSammlung) {
            temp.FREEBUSYSammlung.add(FREEBUSY.kopieren());
        }
        temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
        for (GuKKiCalcProperty RSTATUS : RSTATUSSammlung) {
            temp.RSTATUSSammlung.add(RSTATUS.kopieren());
        }
        temp.UID = this.UID == null ? null : this.UID.kopieren();
        temp.URL = this.URL == null ? null : this.URL.kopieren();
 
/* Abschluss und Fallbackparameter */
 
        for (String X_PROP : this.X_PROPSammlung) {
            temp.X_PROPSammlung.add(X_PROP);
        }
        for (String Restinformation : this.Restinformationen) {
            temp.Restinformationen.add(Restinformation);
        }
        temp.status = GuKKiCalcStatus.KOPIERT;
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
        return temp;
    } // Ende kopieren V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    /**
     * Vergleichen aller Attribute der Komponente GuKKiCalvFreeBusy
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
        GuKKiCalvFreeBusy temp = (GuKKiCalvFreeBusy) dasAndere;
        if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTENDEESammlung.size(); i++) {
            if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
                return false;
            }
        }
        if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
            return false;
        }
        for (int i = 0;i < COMMENTSammlung.size(); i++) {
            if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.CONTACT == null && this.CONTACT == null)
                || (temp.CONTACT != null && this.CONTACT != null && temp.CONTACT.istGleich(this.CONTACT)))) {
            return false;
        }
        if (!((temp.DTEND == null && this.DTEND == null)
                || (temp.DTEND != null && this.DTEND != null && temp.DTEND.istGleich(this.DTEND)))) {
            return false;
        }
        if (!((temp.DTSTAMP == null && this.DTSTAMP == null)
                || (temp.DTSTAMP != null && this.DTSTAMP != null && temp.DTSTAMP.istGleich(this.DTSTAMP)))) {
            return false;
        }
        if (!((temp.DTSTART == null && this.DTSTART == null)
                || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
            return false;
        }
        if (temp.FREEBUSYSammlung.size() != this.FREEBUSYSammlung.size()) {
            return false;
        }
        for (int i = 0;i < FREEBUSYSammlung.size(); i++) {
            if (!temp.FREEBUSYSammlung.get(i).istGleich(this.FREEBUSYSammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
                || (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
            return false;
        }
        if (temp.RSTATUSSammlung.size() != this.RSTATUSSammlung.size()) {
            return false;
        }
        for (int i = 0;i < RSTATUSSammlung.size(); i++) {
            if (!temp.RSTATUSSammlung.get(i).istGleich(this.RSTATUSSammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.UID == null && this.UID == null)
                || (temp.UID != null && this.UID != null && temp.UID.istGleich(this.UID)))) {
            return false;
        }
        if (!((temp.URL == null && this.URL == null)
                || (temp.URL != null && this.URL != null && temp.URL.istGleich(this.URL)))) {
            return false;
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
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
        return true;
    } // Ende istGleich V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
    /**
     * Mit dieser Methode werden die einzelnen Eigenschaften als gültige Parameterkette ausgegeben
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected String ausgeben() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VFREEBUSY");
        for (GuKKiCalcProperty ATTENDEE : ATTENDEESammlung) {
            componentDatenstrom += ausgebenInDatenstrom(ATTENDEE.ausgeben());
        }
        for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(COMMENT.ausgeben());
        }
        componentDatenstrom +=  this.CONTACT == null ? "" : ausgebenInDatenstrom(this.CONTACT.ausgeben());
        componentDatenstrom +=  this.DTEND == null ? "" : ausgebenInDatenstrom(this.DTEND.ausgeben());
        componentDatenstrom +=  this.DTSTAMP == null ? "" : ausgebenInDatenstrom(this.DTSTAMP.ausgeben());
        componentDatenstrom +=  this.DTSTART == null ? "" : ausgebenInDatenstrom(this.DTSTART.ausgeben());
        for (GuKKiCalcProperty FREEBUSY : FREEBUSYSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(FREEBUSY.ausgeben());
        }
        componentDatenstrom +=  this.ORGANIZER == null ? "" : ausgebenInDatenstrom(this.ORGANIZER.ausgeben());
        for (GuKKiCalcProperty RSTATUS : RSTATUSSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(RSTATUS.ausgeben());
        }
        componentDatenstrom +=  this.UID == null ? "" : ausgebenInDatenstrom(this.UID.ausgeben());
        componentDatenstrom +=  this.URL == null ? "" : ausgebenInDatenstrom(this.URL.ausgeben());
 
/* Abschluss und Fallbackparameter */
 
        for (String X_PROP : this.X_PROPSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(X_PROP);
        }
        for (String Restinformation : this.Restinformationen) {
            componentDatenstrom += ausgebenInDatenstrom(Restinformation);
        }
        componentDatenstrom += ausgebenInDatenstrom("END:VFREEBUSY");
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
        return componentDatenstrom;
    } // Ende ausgeben V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    protected void abschliessen(){status = GuKKiCalcStatus.GELESEN;}	 	 
}
