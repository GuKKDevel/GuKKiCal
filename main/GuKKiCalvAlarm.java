package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Die Klasse GuKKiCalvAlarm enthält alle Daten für eine VALARM-Komponente im iCal Format
 * 
 * @author GuKKDevel
 * 
 * @formatter:off
 * 
 *  
 *  RFC 5545 (september 2009) item 3.6.6; p. 71
 *  
 *  Component Name:	 VALARM
 *  
 *  Purpose: Provide a grouping of component properties that define an alarm.
 *  
 *  Description: A "VALARM" calendar component is a grouping of
 *  		component properties that is a reminder or alarm for an event or a
 *  		to-do. For example, it may be used to define a reminder for a
 *  		pending event or an overdue to-do.
 *  		
 *  		The "VALARM" calendar component MUST include the "ACTION" and
 *  		"TRIGGER" properties. The "ACTION" property further constrains
 *  		the "VALARM" calendar component in the following ways:
 *  		
 *  		When the action is "AUDIO", the alarm can also include one and
 *  		only one "ATTACH" property, which MUST point to a sound resource,
 *  		which is rendered when the alarm is triggered.
 *  		
 *  		When the action is "DISPLAY", the alarm MUST also include a
 *  		"DESCRIPTION" property, which contains the text to be displayed
 *  		when the alarm is triggered.
 *  		
 *  		When the action is "EMAIL", the alarm MUST include a "DESCRIPTION"
 *  		property, which contains the text to be used as the message body,
 *  		a "SUMMARY" property, which contains the text to be used as the
 *  		message subject, and one or more "ATTENDEE" properties, which
 *  		contain the email address of attendees to receive the message. It
 *  		can also include one or more "ATTACH" properties, which are
 *  		intended to be sent as message attachments. When the alarm is
 *  		triggered, the email message is sent.
 *  		
 *  		The "VALARM" calendar component MUST only appear within either a
 *  		"VEVENT" or "VTODO" calendar component. "VALARM" calendar
 *  		components cannot be nested. Multiple mutually independent
 *  		"VALARM" calendar components can be specified for a single
 *  		"VEVENT" or "VTODO" calendar component.
 *  		
 *  		The "TRIGGER" property specifies when the alarm will be triggered.
 *  		The "TRIGGER" property specifies a duration prior to the start of
 *  		an event or a to-do. The "TRIGGER" edge may be explicitly set to
 *  		be relative to the "START" or "END" of the event or to-do with the
 *  		"RELATED" parameter of the "TRIGGER" property. The "TRIGGER"
 *  		property value type can alternatively be set to an absolute
 *  		calendar date with UTC time.
 *  		
 *  		In an alarm set to trigger on the "START" of an event or to-do,
 *  		the "DTSTART" property MUST be present in the associated event or
 *  		to-do. In an alarm in a "VEVENT" calendar component set to
 *  		trigger on the "END" of the event, either the "DTEND" property
 *  		MUST be present, or the "DTSTART" and "DURATION" properties MUST
 *  		both be present. In an alarm in a "VTODO" calendar component set
 *  		to trigger on the "END" of the to-do, either the "DUE" property
 *  		MUST be present, or the "DTSTART" and "DURATION" properties MUST
 *  		both be present.
 *  		
 *  		The alarm can be defined such that it triggers repeatedly. A
 *  		definition of an alarm with a repeating trigger MUST include both
 *  		the "DURATION" and "REPEAT" properties. The "DURATION" property
 *  		specifies the delay period, after which the alarm will repeat.
 *  		The "REPEAT" property specifies the number of additional
 *  		repetitions that the alarm will be triggered. This repetition
 *  		count is in addition to the initial triggering of the alarm. Both
 *  		of these properties MUST be present in order to specify a
 *  		repeating alarm. If one of these two properties is absent, then
 *  		the alarm will not repeat beyond the initial trigger.
 *  		
 *  		The "ACTION" property is used within the "VALARM" calendar
 *  		component to specify the type of action invoked when the alarm is
 *  		triggered. The "VALARM" properties provide enough information for
 *  		a specific action to be invoked. It is typically the
 *  		responsibility of a "Calendar User Agent" (CUA) to deliver the
 *  		alarm in the specified fashion. An "ACTION" property value of
 *  		AUDIO specifies an alarm that causes a sound to be played to alert
 *  		the user; DISPLAY specifies an alarm that causes a text message to
 *  		be displayed to the user; and EMAIL specifies an alarm that causes
 *  		an electronic email message to be delivered to one or more email
 *  		addresses.
 *  
 *  		In an AUDIO alarm, if the optional "ATTACH" property is included,
 *  		it MUST specify an audio sound resource. The intention is that
 *  		the sound will be played as the alarm effect. If an "ATTACH"
 *  		property is specified that does not refer to a sound resource, or
 *  		if the specified sound resource cannot be rendered (because its
 *  		format is unsupported, or because it cannot be retrieved), then
 *  		the CUA or other entity responsible for playing the sound may
 *  		choose a fallback action, such as playing a built-in default
 *  		sound, or playing no sound at all.
 *  
 *  		In a DISPLAY alarm, the intended alarm effect is for the text
 *  		value of the "DESCRIPTION" property to be displayed to the user.
 *  		In an EMAIL alarm, the intended alarm effect is for an email
 *  		message to be composed and delivered to all the addresses
 *  		specified by the "ATTENDEE" properties in the "VALARM" calendar
 *  		component. The "DESCRIPTION" property of the "VALARM" calendar
 *  		component MUST be used as the body text of the message, and the
 *  		"SUMMARY" property MUST be used as the subject text. Any "ATTACH"
 *  		properties in the "VALARM" calendar component SHOULD be sent as
 *  		attachments to the message.
 *  
 *  			Note: Implementations should carefully consider whether they
 *  			accept alarm components from untrusted sources, e.g., when
 *  			importing calendar objects from external sources. One
 *  			reasonable policy is to always ignore alarm components that the
 *  			calendar user has not set herself, or at least ask for
 *  			confirmation in such a case.
 *  
 *	Format Definition: A "VALARM" calendar component is defined by the
 *		following notation:
 *
 *		alarmc= "BEGIN" ":" "VALARM" CRLF
 *			
 *			(audioprop / dispprop / emailprop)
 *
 *			"END" ":" "VALARM" CRLF
 *
 *			audioprop= *(
 *
 *		’action’ and ’trigger’ are both REQUIRED, but MUST NOT occur more than once.
 *
 *				action / trigger /
 *
 *		’duration’ and ’repeat’ are both OPTIONAL, and MUST NOT occur more than once each
 *		but if one occurs, so MUST the other.
 *
 *				duration / repeat /
 *
 *		The following is OPTIONAL, but MUST NOT occur more than once.
 *
 *				attach /
 *
 *		The following is OPTIONAL, and MAY occur more than once.
 *	
 *				x-prop / iana-prop
 *
 *			)
 *
 *			dispprop= *(
 *
 *		The following are REQUIRED, but MUST NOT occur more than once.
 *
 *				action / description / trigger /
 *
 *		’duration’ and ’repeat’ are both OPTIONAL, and MUST NOT occur more than once each;
 *		but if one occurs, so MUST the other.
 *	
 *				duration / repeat /
 *
 *		The following is OPTIONAL, and MAY occur more than once.
 *
 *				x-prop / iana-prop
 *
 *			)
 *
 *			emailprop = *(
 *
 *		The following are all REQUIRED, but MUST NOT occur more than once.
 *
 *				action / description / trigger / summary /
 *	
 *		The following is REQUIRED, and MAY occur more than once.
 *		
 *				attendee /
 *
 *		’duration’ and ’repeat’ are both OPTIONAL, and MUST NOT occur more than once each
 *		but if one occurs, so MUST the other.
 *
 *				duration / repeat /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				attach / x-prop / iana-prop
 *
 *			)
 *
 *	@formatter:on
 *    
 */

public class GuKKiCalvAlarm extends GuKKiCalComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * ’action’ and ’trigger’ are both REQUIRED, but MUST NOT occur more than
	 * once.
	 */
	private GuKKiCalProperty ACTION = null;
	private GuKKiCalProperty TRIGGER = null;
	/*
	 * ’duration’ and ’repeat’ are both OPTIONAL, and MUST NOT occur more than
	 * once each but if one occurs, so MUST the other.
	 */
	private GuKKiCalProperty DURATION = null;
	private GuKKiCalProperty REPEAT = null;
	/*
	 * The following is OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty DESCRIPTION = null;
	private GuKKiCalProperty SUMMARY = null;
	private ArrayList<GuKKiCalProperty> ATTACHSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> ATTENDEESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();
	private ArrayList<String> Restinformationen = new ArrayList<String>();

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";
	String folgezeile = "";
	boolean datenVorhanden;
/**
 * leerer Konstruktor
 */
	public GuKKiCalvAlarm () {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

	}
/**
 * 
 * @param vAlarmDaten
 * @throws Exception
 */
	public GuKKiCalvAlarm(String vAlarmDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		einlesenAusDatenstrom(vAlarmDaten);
		
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
// Anfang der generierten Methoden für GuKKiCalvAlarm 0.1 Wed Dec 08 23:39:38 CET 2021
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     */
    @Override
    protected void verarbeitenZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (!zeile.equals("BEGIN:VALARM") & !zeile.equals("END:VALARM")) {
 
// Eigenschaft: ACTION GuKKiCalProperty auftreten 0:1
            if (zeile.length() > 6 && zeile.substring(0, 6).equals("ACTION")) {
                ACTION = new GuKKiCalProperty(zeile, "ACTION");
 
// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
                ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
                ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
                DESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("DURATION")) {
                DURATION = new GuKKiCalProperty(zeile, "DURATION");
 
// Eigenschaft: REPEAT GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("REPEAT")) {
                REPEAT = new GuKKiCalProperty(zeile, "REPEAT");
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
                SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
 
// Eigenschaft: TRIGGER GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("TRIGGER")) {
                TRIGGER = new GuKKiCalProperty(zeile, "TRIGGER");
 
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
     * GuKKiCalvAlarm und gibt diese Kopie zurück
     */
    protected GuKKiCalvAlarm kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCalvAlarm temp = new GuKKiCalvAlarm();
 
        temp.kennung = this.kennung;
 
// Eigenschaft: ACTION GuKKiCalProperty auftreten 0:1
        temp.ACTION = this.ACTION == null ? null : this.ACTION.kopieren();
 
// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pATTACH : ATTACHSammlung) {
            temp.ATTACHSammlung.add(pATTACH.kopieren());
        }
 
// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pATTENDEE : ATTENDEESammlung) {
            temp.ATTENDEESammlung.add(pATTENDEE.kopieren());
        }
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
        temp.DESCRIPTION = this.DESCRIPTION == null ? null : this.DESCRIPTION.kopieren();
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
        temp.DURATION = this.DURATION == null ? null : this.DURATION.kopieren();
 
// Eigenschaft: REPEAT GuKKiCalProperty auftreten 0:1
        temp.REPEAT = this.REPEAT == null ? null : this.REPEAT.kopieren();
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
        temp.SUMMARY = this.SUMMARY == null ? null : this.SUMMARY.kopieren();
 
// Eigenschaft: TRIGGER GuKKiCalProperty auftreten 0:1
        temp.TRIGGER = this.TRIGGER == null ? null : this.TRIGGER.kopieren();
 
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
     * Vergleichen aller Attribute der Komponente GuKKiCalvAlarm
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
 
        GuKKiCalvAlarm temp = (GuKKiCalvAlarm) dasAndere;
 
// Eigenschaft: ACTION GuKKiCalProperty auftreten 0:1
        if (!((temp.ACTION == null && this.ACTION == null)
                || (temp.ACTION != null && this.ACTION != null && temp.ACTION.istGleich(this.ACTION)))) {
            return false;
        }
 
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
 
// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:1
        if (!((temp.DESCRIPTION == null && this.DESCRIPTION == null)
                || (temp.DESCRIPTION != null && this.DESCRIPTION != null && temp.DESCRIPTION.istGleich(this.DESCRIPTION)))) {
            return false;
        }
 
// Eigenschaft: DURATION GuKKiCalProperty auftreten 0:1
        if (!((temp.DURATION == null && this.DURATION == null)
                || (temp.DURATION != null && this.DURATION != null && temp.DURATION.istGleich(this.DURATION)))) {
            return false;
        }
 
// Eigenschaft: REPEAT GuKKiCalProperty auftreten 0:1
        if (!((temp.REPEAT == null && this.REPEAT == null)
                || (temp.REPEAT != null && this.REPEAT != null && temp.REPEAT.istGleich(this.REPEAT)))) {
            return false;
        }
 
// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
        if (!((temp.SUMMARY == null && this.SUMMARY == null)
                || (temp.SUMMARY != null && this.SUMMARY != null && temp.SUMMARY.istGleich(this.SUMMARY)))) {
            return false;
        }
 
// Eigenschaft: TRIGGER GuKKiCalProperty auftreten 0:1
        if (!((temp.TRIGGER == null && this.TRIGGER == null)
                || (temp.TRIGGER != null && this.TRIGGER != null && temp.TRIGGER.istGleich(this.TRIGGER)))) {
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
 
// Ende der generierten Methoden für GuKKiCalvAlarm
// @formatter:on    	 	
	    
	/**
	 * Gibt die UID des vAlarm aus
	 */
	public String toString() {
		return "Alarm=" + (ACTION == null ? "" : ACTION.getWert()) + ","
				+ (TRIGGER == null ? "" : TRIGGER.getWert());
	}
}
