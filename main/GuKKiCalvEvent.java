package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Die Klasse GuKKiCalvEvent enthält alle Daten für eine VEVENT-Komponente im iCal Format
 *         
 * @author GuKKDevel
 *  
 * @formatter:off
 * 
 * 
 * 	RFC 5545 (september 2009) item 3.6.1; p. 52
 *  
 * 	Component Name: VEVENT
 * 
 * 	Purpose: Provide a grouping of component properties that describe an
 * 		event.
 * 
 * 	Description: A "VEVENT" calendar component is a grouping of
 * 		component properties, possibly including "VALARM" calendar
 * 		components, that represents a scheduled amount of time on a
 * 		calendar. For example, it can be an activity; such as a one-hour
 * 		long, department meeting from 8:00 AM to 9:00 AM, tomorrow.
 * 		Generally, an event will take up time on an individual calendar.
 * 		Hence, the event will appear as an opaque interval in a search for
 * 		busy time. Alternately, the event can have its Time Transparency
 * 		set to "TRANSPARENT" in order to prevent blocking of the event in
 * 		searches for busy time.
 * 
 * 		The "VEVENT" is also the calendar component used to specify an
 * 		anniversary or daily reminder within a calendar. These events
 * 		have a DATE value type for the "DTSTART" property instead of the
 * 		default value type of DATE-TIME. If such a "VEVENT" has a "DTEND"
 * 		property, it MUST be specified as a DATE value also. The
 * 		anniversary type of "VEVENT" can span more than one date (i.e.,
 * 		"DTEND" property value is set to a calendar date after the
 * 		"DTSTART" property value). If such a "VEVENT" has a "DURATION"
 * 		property, it MUST be specified as a "dur-day" or "dur-week" value.
 * 
 * 		The "DTSTART" property for a "VEVENT" specifies the inclusive
 * 		start of the event. For recurring events, it also specifies the
 * 		very first instance in the recurrence set. The "DTEND" property
 * 		for a "VEVENT" calendar component specifies the non-inclusive end
 * 		of the event. For cases where a "VEVENT" calendar component
 * 		specifies a "DTSTART" property with a DATE value type but no
 * 		"DTEND" nor "DURATION" property, the event’s duration is taken to
 * 		be one day. For cases where a "VEVENT" calendar component
 * 		specifies a "DTSTART" property with a DATE-TIME value type but no
 * 		"DTEND" property, the event ends on the same calendar date and
 * 		time of day specified by the "DTSTART" property.
 * 		
 * 		The "VEVENT" calendar component cannot be nested within another
 * 		calendar component. However, "VEVENT" calendar components can be
 * 		related to each other or to a "VTODO" or to a "VJOURNAL" calendar
 * 		component with the "RELATED-TO" property.
 * 
 * 	Format Definition: A "VEVENT" calendar component is defined by the
 * 		following notation:
 * 
 * 		eventc = "BEGIN" ":" "VEVENT" CRLF
 * 
 * 			eventprop *alarmc
 * 	
 *			"END" ":" "VEVENT" CRLF
 *
 *			eventprop = *(
 *	
 *		The following are REQUIRED, but MUST NOT occur more than once.
 *
 *				dtstamp / uid /
 *
 *		The following is REQUIRED if the component appears in an 
 *		iCalendar object that doesn’t specify the "METHOD" property
 *		otherwise, it is OPTIONAL
 *		in any case, it MUST NOT occur more than once.
 *
 *				dtstart /
 *
 *		The following are OPTIONAL, but MUST NOT occur more than once.
 *	
 *				class / created / description / geo /
 *				last-mod / location / organizer / priority /
 *				seq / status / summary / transp /
 *				url / recurid /
 *
 *		The following is OPTIONAL, but SHOULD NOT occur more than once.
 *
 *				rrule /
 *
 *		Either ’dtend’ or ’duration’ MAY appear in a ’eventprop’, 
 *		but ’dtend’ and ’duration’ MUST NOT occur in the same ’eventprop’.
 *
 *				dtend / duration /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				attach / attendee / categories / comment /
 *				contact / exdate / rstatus / related /
 *				resources / rdate / x-prop / iana-prop
 *			
 *			)
 *
 *	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-
 *	Modifications by RFC 7986 (October 2016) item 4.; p. 4
 *
 *			eventprop =/ *(
 *
 *	The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				color /
 *
 *	The following are OPTIONAL, and MAY occur more than once.
 *
 *				conference / image
 *
 *				)
 *
 * @formatter:on
 * 
 */
public class GuKKiCalvEvent extends GuKKiCalComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty DTSTAMP = null;
	private GuKKiCalProperty UID = null;
	/*
	 * The following is REQUIRED if the component appears in an iCalendar object
	 * that doesn’t specify the "METHOD" property; otherwise, it is OPTIONAL in
	 * any case, it MUST NOT occur more than once.
	 */
	private GuKKiCalProperty DTSTART = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty CLASS = null;
	private GuKKiCalProperty COLOR = null;
	private GuKKiCalProperty CREATED = null;
	private GuKKiCalProperty DESCRIPTION = null;
	private GuKKiCalProperty GEO = null;
	private GuKKiCalProperty LAST_MOD = null;
	private GuKKiCalProperty LOCATION = null;
	private GuKKiCalProperty ORGANIZER = null;
	private GuKKiCalProperty PRIORITY = null;
	private GuKKiCalProperty SEQ = null;
	private GuKKiCalProperty STATUS = null;
	private GuKKiCalProperty SUMMARY = null;
	private GuKKiCalProperty TRANSP = null;
	private GuKKiCalProperty URL = null;
	private GuKKiCalProperty RECURID = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalProperty RRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’eventprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’eventprop’.
	 */
	private GuKKiCalProperty DTEND = null;
	private GuKKiCalProperty DURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<GuKKiCalProperty> ATTACHSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> ATTENDEESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CATEGORIESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> COMMENTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CONFERENCESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CONTACTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> EXDATESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> IMAGESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RSTATUSSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATEDSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATED_TOSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RESOURCESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RDATESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * X-Name Properties
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();

	private ArrayList<String> Restinformationen = new ArrayList<String>();
	/*
	 * Sammlungen der VEVENT-Komponenten
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarmSammlung = new ArrayList<GuKKiCalvAlarm>();

	ArrayList<String> vAlarmDatenArray = new ArrayList<String>();
	boolean vAlarmDatenSammeln = false;
	private GuKKiCalvAlarm vAlarmEvent = null;

	/*
	 * allgemeine Variablen
	 */
	public GuKKiCalvEvent() {
		this.kennung = GuKKiCalcKennung.EVENT;
	}

	/**
	 * Konstruktor zum Aufbereiten des VEVENT aus einem Eingabestring
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendarKennung
	 * @param vEventDaten
	 * @throws Exception
	 */

	public GuKKiCalvEvent(String vEventDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}

		einlesenAusDatenstrom(vEventDaten);

		this.kennung = GuKKiCalcKennung.EVENT;
		this.status = GuKKiCalcStatus.GELESEN;
		this.schluessel = this.toString();

// @formatter:off    	 
// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
 
// Subkomponente: vAlarm GuKKiCalvAlarm VALARM
        if (vAlarmDatenArray.size() != 0) {
            vAlarmSammlungAnlegen();
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
 
// Subkomponente: vAlarm GuKKiCalvAlarm VALARM
    private void vAlarmSammlungAnlegen() throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
 
        String vAlarmDaten = "";
 
        for (String zeile : vAlarmDatenArray) {
            if (zeile.equals("BEGIN:VALARM")) {
                vAlarmDaten = zeile + nz;
            } else if (zeile.equals("END:VALARM")) {
                vAlarmDaten += zeile + nz;
                vAlarmSammlung.add(new GuKKiCalvAlarm(vAlarmDaten));
                vAlarmDaten = "";
            } else {
                vAlarmDaten += zeile + nz;
            }
        }
 
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
// Anfang der generierten Methoden für GuKKiCalvEvent 0.1 Wed Dec 08 23:39:38 CET 2021
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     */
    @Override
    protected void verarbeitenZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
 
// Subkomponente: vAlarm GuKKiCalvAlarm VALARM)
            if (zeile.equals("BEGIN:VALARM")) {
                vAlarmDatenSammeln = true;
                vAlarmDatenArray.add(zeile);
            } else if (zeile.equals("END:VALARM")) {
                vAlarmDatenSammeln = false;
                vAlarmDatenArray.add(zeile);
            } else if (vAlarmDatenSammeln) {
                vAlarmDatenArray.add(zeile);
 
// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
                ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
                ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
 
// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
                CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
 
// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("CLASS")) {
                CLASS = new GuKKiCalProperty(zeile, "CLASS");
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
                COLOR = new GuKKiCalProperty(zeile, "COLOR");
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
                COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
 
// Eigenschaft: CONFERENCE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("CONFERENCE")) {
                CONFERENCESammlung.add(new GuKKiCalProperty(zeile, "CONFERENCE"));
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
                CONTACTSammlung.add(new GuKKiCalProperty(zeile, "CONTACT"));
 
// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CREATED")) {
                CREATED = new GuKKiCalProperty(zeile, "CREATED");
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
                DESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
 
// Eigenschaft: DTEND GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("DTEND")) {
                DTEND = new GuKKiCalProperty(zeile, "DTEND");
 
// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
                DTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
                DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("DURATION")) {
                DURATION = new GuKKiCalProperty(zeile, "DURATION");
 
// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("EXDATE")) {
                EXDATESammlung.add(new GuKKiCalProperty(zeile, "EXDATE"));
 
// Eigenschaft: GEO GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("GEO")) {
                GEO = new GuKKiCalProperty(zeile, "GEO");
 
// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
                IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
 
// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
                LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
 
// Eigenschaft: LOCATION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("LOCATION")) {
                LOCATION = new GuKKiCalProperty(zeile, "LOCATION");
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
                ORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
 
// Eigenschaft: PRIORITY GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("PRIORITY")) {
                PRIORITY = new GuKKiCalProperty(zeile, "PRIORITY");
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
                RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
 
// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
                RECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
 
// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("RELATED-TO")) {
                RELATEDSammlung.add(new GuKKiCalProperty(zeile, "RELATED-TO"));
 
// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
                RSTATUSSammlung.add(new GuKKiCalProperty(zeile, "REQUEST-STATUS"));
 
// Eigenschaft: RESOURCES GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 9 && zeile.substring(0, 9).equals("RESOURCES")) {
                RESOURCESSammlung.add(new GuKKiCalProperty(zeile, "RESOURCES"));
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
                RRULE = new GuKKiCalProperty(zeile, "RRULE");
 
// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
                SEQ = new GuKKiCalProperty(zeile, "SEQUENCE");
 
// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("STATUS")) {
                STATUS = new GuKKiCalProperty(zeile, "STATUS");
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
                SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
 
// Eigenschaft: TRANSP GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("TRANSP")) {
                TRANSP = new GuKKiCalProperty(zeile, "TRANSP");
 
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
     * GuKKiCalvEvent und gibt diese Kopie zurück
     */
    protected GuKKiCalvEvent kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCalvEvent temp = new GuKKiCalvEvent();
 
        temp.kennung = this.kennung;
 
// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pATTACH : ATTACHSammlung) {
            temp.ATTACHSammlung.add(pATTACH.kopieren());
        }
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pATTENDEE : ATTENDEESammlung) {
            temp.ATTENDEESammlung.add(pATTENDEE.kopieren());
        }
 
// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCATEGORIES : CATEGORIESSammlung) {
            temp.CATEGORIESSammlung.add(pCATEGORIES.kopieren());
        }
 
// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
        temp.CLASS = this.CLASS == null ? null : this.CLASS.kopieren();
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
        temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCOMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(pCOMMENT.kopieren());
        }
 
// Eigenschaft: CONFERENCE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCONFERENCE : CONFERENCESammlung) {
            temp.CONFERENCESammlung.add(pCONFERENCE.kopieren());
        }
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCONTACT : CONTACTSammlung) {
            temp.CONTACTSammlung.add(pCONTACT.kopieren());
        }
 
// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
        temp.CREATED = this.CREATED == null ? null : this.CREATED.kopieren();
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
        temp.DESCRIPTION = this.DESCRIPTION == null ? null : this.DESCRIPTION.kopieren();
 
// Eigenschaft: DTEND GuKKiCalProperty auftreten 0:1
        temp.DTEND = this.DTEND == null ? null : this.DTEND.kopieren();
 
// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
        temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
        temp.DURATION = this.DURATION == null ? null : this.DURATION.kopieren();
 
// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pEXDATE : EXDATESammlung) {
            temp.EXDATESammlung.add(pEXDATE.kopieren());
        }
 
// Eigenschaft: GEO GuKKiCalProperty auftreten 0:1
        temp.GEO = this.GEO == null ? null : this.GEO.kopieren();
 
// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pIMAGE : IMAGESammlung) {
            temp.IMAGESammlung.add(pIMAGE.kopieren());
        }
 
// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
        temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
 
// Eigenschaft: LOCATION GuKKiCalProperty auftreten 0:1
        temp.LOCATION = this.LOCATION == null ? null : this.LOCATION.kopieren();
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
        temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
 
// Eigenschaft: PRIORITY GuKKiCalProperty auftreten 0:1
        temp.PRIORITY = this.PRIORITY == null ? null : this.PRIORITY.kopieren();
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRDATE : RDATESammlung) {
            temp.RDATESammlung.add(pRDATE.kopieren());
        }
 
// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
        temp.RECURID = this.RECURID == null ? null : this.RECURID.kopieren();
 
// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRELATED : RELATEDSammlung) {
            temp.RELATEDSammlung.add(pRELATED.kopieren());
        }
 
// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRSTATUS : RSTATUSSammlung) {
            temp.RSTATUSSammlung.add(pRSTATUS.kopieren());
        }
 
// Eigenschaft: RESOURCES GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRESOURCES : RESOURCESSammlung) {
            temp.RESOURCESSammlung.add(pRESOURCES.kopieren());
        }
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
        temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
 
// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
        temp.SEQ = this.SEQ == null ? null : this.SEQ.kopieren();
 
// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
        temp.STATUS = this.STATUS == null ? null : this.STATUS.kopieren();
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
        temp.SUMMARY = this.SUMMARY == null ? null : this.SUMMARY.kopieren();
 
// Eigenschaft: TRANSP GuKKiCalProperty auftreten 0:1
        temp.TRANSP = this.TRANSP == null ? null : this.TRANSP.kopieren();
 
// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
        temp.UID = this.UID == null ? null : this.UID.kopieren();
 
// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
        temp.URL = this.URL == null ? null : this.URL.kopieren();
 
// Eigenschaft: X_PROP String auftreten 0:n
        for (String pX_PROP : X_PROPSammlung) {
            temp.X_PROPSammlung.add(pX_PROP);
        }
 
// Subkomponente: vAlarm GuKKiCalvAlarm auftreten 0:n
        for (GuKKiCalvAlarm vAlarm : this.vAlarmSammlung) {
            temp.vAlarmSammlung.add(vAlarm.kopieren());
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
     * Vergleichen aller Attribute der Komponente GuKKiCalvEvent
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
 
        GuKKiCalvEvent temp = (GuKKiCalvEvent) dasAndere;
 
// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
        if (temp.ATTACHSammlung.size() != this.ATTACHSammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTACHSammlung.size(); i++) {
            if (!temp.ATTACHSammlung.get(i).istGleich(this.ATTACHSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
        if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTENDEESammlung.size(); i++) {
            if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
                return false;
            }
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
 
// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
        if (!((temp.CLASS == null && this.CLASS == null)
                || (temp.CLASS != null && this.CLASS != null && temp.CLASS.istGleich(this.CLASS)))) {
            return false;
        }
 
// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
        if (!((temp.COLOR == null && this.COLOR == null)
                || (temp.COLOR != null && this.COLOR != null && temp.COLOR.istGleich(this.COLOR)))) {
            return false;
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
 
// Eigenschaft: CONFERENCE GuKKiCalProperty auftreten 0:n
        if (temp.CONFERENCESammlung.size() != this.CONFERENCESammlung.size()) {
            return false;
        }
        for (int i = 0;i < CONFERENCESammlung.size(); i++) {
            if (!temp.CONFERENCESammlung.get(i).istGleich(this.CONFERENCESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
        if (temp.CONTACTSammlung.size() != this.CONTACTSammlung.size()) {
            return false;
        }
        for (int i = 0;i < CONTACTSammlung.size(); i++) {
            if (!temp.CONTACTSammlung.get(i).istGleich(this.CONTACTSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
        if (!((temp.CREATED == null && this.CREATED == null)
                || (temp.CREATED != null && this.CREATED != null && temp.CREATED.istGleich(this.CREATED)))) {
            return false;
        }
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
        if (!((temp.DESCRIPTION == null && this.DESCRIPTION == null)
                || (temp.DESCRIPTION != null && this.DESCRIPTION != null && temp.DESCRIPTION.istGleich(this.DESCRIPTION)))) {
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
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
        if (!((temp.DURATION == null && this.DURATION == null)
                || (temp.DURATION != null && this.DURATION != null && temp.DURATION.istGleich(this.DURATION)))) {
            return false;
        }
 
// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
        if (temp.EXDATESammlung.size() != this.EXDATESammlung.size()) {
            return false;
        }
        for (int i = 0;i < EXDATESammlung.size(); i++) {
            if (!temp.EXDATESammlung.get(i).istGleich(this.EXDATESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: GEO GuKKiCalProperty auftreten 0:1
        if (!((temp.GEO == null && this.GEO == null)
                || (temp.GEO != null && this.GEO != null && temp.GEO.istGleich(this.GEO)))) {
            return false;
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
 
// Eigenschaft: LOCATION GuKKiCalProperty auftreten 0:1
        if (!((temp.LOCATION == null && this.LOCATION == null)
                || (temp.LOCATION != null && this.LOCATION != null && temp.LOCATION.istGleich(this.LOCATION)))) {
            return false;
        }
 
// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
        if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
                || (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
            return false;
        }
 
// Eigenschaft: PRIORITY GuKKiCalProperty auftreten 0:1
        if (!((temp.PRIORITY == null && this.PRIORITY == null)
                || (temp.PRIORITY != null && this.PRIORITY != null && temp.PRIORITY.istGleich(this.PRIORITY)))) {
            return false;
        }
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
        if (temp.RDATESammlung.size() != this.RDATESammlung.size()) {
            return false;
        }
        for (int i = 0;i < RDATESammlung.size(); i++) {
            if (!temp.RDATESammlung.get(i).istGleich(this.RDATESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
        if (!((temp.RECURID == null && this.RECURID == null)
                || (temp.RECURID != null && this.RECURID != null && temp.RECURID.istGleich(this.RECURID)))) {
            return false;
        }
 
// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
        if (temp.RELATEDSammlung.size() != this.RELATEDSammlung.size()) {
            return false;
        }
        for (int i = 0;i < RELATEDSammlung.size(); i++) {
            if (!temp.RELATEDSammlung.get(i).istGleich(this.RELATEDSammlung.get(i))) {
                return false;
            }
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
 
// Eigenschaft: RESOURCES GuKKiCalProperty auftreten 0:n
        if (temp.RESOURCESSammlung.size() != this.RESOURCESSammlung.size()) {
            return false;
        }
        for (int i = 0;i < RESOURCESSammlung.size(); i++) {
            if (!temp.RESOURCESSammlung.get(i).istGleich(this.RESOURCESSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
        if (!((temp.RRULE == null && this.RRULE == null)
                || (temp.RRULE != null && this.RRULE != null && temp.RRULE.istGleich(this.RRULE)))) {
            return false;
        }
 
// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
        if (!((temp.SEQ == null && this.SEQ == null)
                || (temp.SEQ != null && this.SEQ != null && temp.SEQ.istGleich(this.SEQ)))) {
            return false;
        }
 
// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
        if (!((temp.STATUS == null && this.STATUS == null)
                || (temp.STATUS != null && this.STATUS != null && temp.STATUS.istGleich(this.STATUS)))) {
            return false;
        }
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
        if (!((temp.SUMMARY == null && this.SUMMARY == null)
                || (temp.SUMMARY != null && this.SUMMARY != null && temp.SUMMARY.istGleich(this.SUMMARY)))) {
            return false;
        }
 
// Eigenschaft: TRANSP GuKKiCalProperty auftreten 0:1
        if (!((temp.TRANSP == null && this.TRANSP == null)
                || (temp.TRANSP != null && this.TRANSP != null && temp.TRANSP.istGleich(this.TRANSP)))) {
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
 
// Eigenschaft: X_PROP String auftreten 0:n
        if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
            return false;
        }
        for (int i = 0;i < X_PROPSammlung.size(); i++) {
            if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
               return false;
            }
        }
 
// Subkomponente: vAlarm GuKKiCalvAlarm auftreten 0:n
        if (temp.vAlarmSammlung.size() != this.vAlarmSammlung.size()) {
            return false;
        }
        for (int i = 0; i < vAlarmSammlung.size(); i++) {
            if (!temp.vAlarmSammlung.get(i).istGleich(this.vAlarmSammlung.get(i))) {
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
 
// Ende der generierten Methoden für GuKKiCalvEvent
// @formatter:on    	   	 
	
	/**
	 * Gibt statt der Adresse die UID des vEvent zurück
	 */
	public String toString() {
		return "E," + UID.getWert() + "," + (SEQ == null ? "" : SEQ.getWert()) + ","
				+ (RECURID == null ? "" : RECURID.getWert()) + "";
	} // Ende toString()

	/**
	 * Gibt sämtliche Daten des vEvent aus
	 */
	public String toString(String ausgabeLevel) {
		return "Event-Identifikation=" + this.toString() + "<-->" + (SUMMARY == null ? "" : SUMMARY.getWert());
	} // Ende toString(String)
} // Ende GuKKiCalvEvent-Klasse
