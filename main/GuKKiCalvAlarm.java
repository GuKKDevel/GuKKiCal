package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
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
	private List<GuKKiCalProperty> ATTACHSammlung = new LinkedList<GuKKiCalProperty>();
	private List<GuKKiCalProperty> ATTENDEESammlung = new LinkedList<GuKKiCalProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private List<String> X_PROPSammlung = new LinkedList<String>();
	private List<String> Restinformationen = new LinkedList<String>();

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
//	public GuKKiCalvAlarm(String vAlarmDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//
//		einlesenAusDatenstrom(vAlarmDaten);
//		
//// @formatter:off    	 
//// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
// 
//        status = GuKKiCalcStatus.GELESEN;
// 
//        if (Restinformationen.size() > 0) {
//            for (String Restinformation : Restinformationen) {
//                logger.log(Level.INFO, "Restinformation:" + "-->" + Restinformation + "<--");
//            }
//        }
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "beendet");
//        }
//    }
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected void neueZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (zeile.length() > 6 && zeile.substring(0, 6).equals("ACTION")) {
            ACTION = new GuKKiCalProperty(zeile, "ACTION");
        } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
            ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
        } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
            ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
        } else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
            DESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
        } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("DURATION")) {
            DURATION = new GuKKiCalProperty(zeile, "DURATION");
        } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("REPEAT")) {
            REPEAT = new GuKKiCalProperty(zeile, "REPEAT");
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
            SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("TRIGGER")) {
            TRIGGER = new GuKKiCalProperty(zeile, "TRIGGER");
 
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
     * GuKKiCalvAlarm und gibt diese Kopie zurück
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected GuKKiCalvAlarm kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        GuKKiCalvAlarm temp = new GuKKiCalvAlarm();
        temp.kennung = this.kennung;
        temp.ACTION = this.ACTION == null ? null : this.ACTION.kopieren();
        for (GuKKiCalProperty ATTACH : ATTACHSammlung) {
            temp.ATTACHSammlung.add(ATTACH.kopieren());
        }
        for (GuKKiCalProperty ATTENDEE : ATTENDEESammlung) {
            temp.ATTENDEESammlung.add(ATTENDEE.kopieren());
        }
        temp.DESCRIPTION = this.DESCRIPTION == null ? null : this.DESCRIPTION.kopieren();
        temp.DURATION = this.DURATION == null ? null : this.DURATION.kopieren();
        temp.REPEAT = this.REPEAT == null ? null : this.REPEAT.kopieren();
        temp.SUMMARY = this.SUMMARY == null ? null : this.SUMMARY.kopieren();
        temp.TRIGGER = this.TRIGGER == null ? null : this.TRIGGER.kopieren();
 
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
     * Vergleichen aller Attribute der Komponente GuKKiCalvAlarm
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
        GuKKiCalvAlarm temp = (GuKKiCalvAlarm) dasAndere;
        if (!((temp.ACTION == null && this.ACTION == null)
                || (temp.ACTION != null && this.ACTION != null && temp.ACTION.istGleich(this.ACTION)))) {
            return false;
        }
        if (temp.ATTACHSammlung.size() != this.ATTACHSammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTACHSammlung.size(); i++) {
            if (!temp.ATTACHSammlung.get(i).istGleich(this.ATTACHSammlung.get(i))) {
                return false;
            }
        }
        if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
            return false;
        }
        for (int i = 0;i < ATTENDEESammlung.size(); i++) {
            if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.DESCRIPTION == null && this.DESCRIPTION == null)
                || (temp.DESCRIPTION != null && this.DESCRIPTION != null && temp.DESCRIPTION.istGleich(this.DESCRIPTION)))) {
            return false;
        }
        if (!((temp.DURATION == null && this.DURATION == null)
                || (temp.DURATION != null && this.DURATION != null && temp.DURATION.istGleich(this.DURATION)))) {
            return false;
        }
        if (!((temp.REPEAT == null && this.REPEAT == null)
                || (temp.REPEAT != null && this.REPEAT != null && temp.REPEAT.istGleich(this.REPEAT)))) {
            return false;
        }
        if (!((temp.SUMMARY == null && this.SUMMARY == null)
                || (temp.SUMMARY != null && this.SUMMARY != null && temp.SUMMARY.istGleich(this.SUMMARY)))) {
            return false;
        }
        if (!((temp.TRIGGER == null && this.TRIGGER == null)
                || (temp.TRIGGER != null && this.TRIGGER != null && temp.TRIGGER.istGleich(this.TRIGGER)))) {
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
        String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VALARM");
        componentDatenstrom +=  this.ACTION == null ? "" : ausgebenInDatenstrom(this.ACTION.ausgeben());
        for (GuKKiCalProperty ATTACH : ATTACHSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(ATTACH.ausgeben());
        }
        for (GuKKiCalProperty ATTENDEE : ATTENDEESammlung) {
            componentDatenstrom += ausgebenInDatenstrom(ATTENDEE.ausgeben());
        }
        componentDatenstrom +=  this.DESCRIPTION == null ? "" : ausgebenInDatenstrom(this.DESCRIPTION.ausgeben());
        componentDatenstrom +=  this.DURATION == null ? "" : ausgebenInDatenstrom(this.DURATION.ausgeben());
        componentDatenstrom +=  this.REPEAT == null ? "" : ausgebenInDatenstrom(this.REPEAT.ausgeben());
        componentDatenstrom +=  this.SUMMARY == null ? "" : ausgebenInDatenstrom(this.SUMMARY.ausgeben());
        componentDatenstrom +=  this.TRIGGER == null ? "" : ausgebenInDatenstrom(this.TRIGGER.ausgeben());
 
/* Abschluss und Fallbackparameter */
 
        for (String X_PROP : this.X_PROPSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(X_PROP);
        }
        for (String Restinformation : this.Restinformationen) {
            componentDatenstrom += ausgebenInDatenstrom(Restinformation);
        }
        componentDatenstrom += ausgebenInDatenstrom("END:VALARM");
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
        return componentDatenstrom;
    } // Ende ausgeben V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    protected void abschliessen(){status = GuKKiCalcStatus.GELESEN;}
 
	    
	/**
	 * Gibt die UID des vAlarm aus
	 */
	public String toString() {
		return "Alarm=" + (ACTION == null ? "" : ACTION.getWert()) + ","
				+ (TRIGGER == null ? "" : TRIGGER.getWert());
	}
}
