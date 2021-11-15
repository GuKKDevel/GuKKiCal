package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

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

public class GuKKiCalvAlarm extends GuKKiCalvComponent {
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private String vCalendarKennung = "";
	/*
	 * Rückverweis auf die enthaltende VEVENT bzw. VTODO CALENDAR-Componente
	 */
	private String vComponentKennung = "";

	private GuKKiCalProperty vAlarmACTION = null;
	private GuKKiCalProperty vAlarmTRIGGER = null;
	private GuKKiCalProperty vAlarmDURATION = null;
	private GuKKiCalProperty vAlarmREPEAT = null;
	private GuKKiCalProperty vAlarmDESCRIPTION = null;
	private GuKKiCalProperty vAlarmSUMMARY = null;
	private ArrayList<GuKKiCalProperty> vAlarmATTACH = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vAlarmATTENDEE = new ArrayList<GuKKiCalProperty>();

	private String vAlarmRestinformationen = "";

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";
	String folgezeile = "";
	boolean datenVorhanden;

	public GuKKiCalvAlarm(GuKKiCal kalendersammlung, String vCalendarKennung, String vComponentKennung,
			String vAlarmDaten) throws Exception {

//		System.out.println("GuKKiCalvAlarm begonnen für:" + vComponentKennung);

		this.vCalendarKennung = vCalendarKennung;
		this.vComponentKennung = vComponentKennung;
		verarbeitenDatenstrom(vAlarmDaten);

//		System.out.println("GuKKiCalvAlarm beendet für:" + vComponentKennung);
	}

	@Override
	protected void verarbeitenZeile(String zeile) throws Exception {
//		System.out.println("GuKKiCalvAlarm verarbeitenZeile begonnen");
//		System.out.println("Zeile=" + zeile);
		if (!zeile.equals("BEGIN:VALARM") & !zeile.equals("END:VALARM")) {
			if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ACTION")) {
				vAlarmACTION = new GuKKiCalProperty(zeile, "ACTION");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("TRIGGER")) {
				vAlarmTRIGGER = new GuKKiCalProperty(zeile, "TRIGGER");
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
				vAlarmDURATION = new GuKKiCalProperty(zeile, "DURATION");
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("REPEAT")) {
				vAlarmREPEAT = new GuKKiCalProperty(zeile, "REPEAT");
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				vAlarmDESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
				vAlarmATTACH.add(new GuKKiCalProperty(zeile, "ATTACH"));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				vAlarmATTENDEE.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
			} else {
				vAlarmRestinformationen += zeile + nz;
//					System.out.println("Restinformationen=" + vEventRestinformationen);
			}
		}
//		System.out.println("GuKKiCalvAlarm verarbeitenZeile beendet");
	}

	/**
	 * Gibt die UID des vAlarm aus
	 */
	public String toString() {
		return "Alarm=" + (vAlarmACTION == null ? "" : vAlarmACTION.getPropertyWert()) + ","
				+ (vAlarmTRIGGER == null ? "" : vAlarmTRIGGER.getPropertyWert());
	}
}
