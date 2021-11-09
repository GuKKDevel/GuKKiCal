package main;

import java.util.ArrayList;

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
	private GuKKiCal kalendersammlung;
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private GuKKiCalvCalendar kalender = null;
	private String vCalendarKennung = "";
	/*
	 * Daten für das VFREEBUSY-Element (freebusyc)
	 */
	private String vFreeBusyKennung = "";

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vFreeBusyDTSTAMP = null;
	private GuKKiCalProperty vFreeBusyUID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once. 
	 * contact / dtstart / dtend / organizer / url /
	 * 
	 */
	private GuKKiCalProperty vFreeBusyCONTYCT = null;
	private GuKKiCalProperty vFreeBusyDTSTART = null;
	private GuKKiCalProperty vFreeBusyDTEND = null;
	private GuKKiCalProperty vFreeBusyORGANIZER = null;
	private GuKKiCalProperty vFreeBusyURL = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 * 			attendee / comment / freebusy / rstatus / 
	 *				x-prop /iana-prop
	 */
	private ArrayList<GuKKiCalProperty> vFreeBusyATTENDEE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vFreeBusyCOMMENT = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vFreeBusyFREEBUSY = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vFreeBusyRSTATUS = new ArrayList<GuKKiCalProperty>();
	
	String vFreeBusyRestinformationen = null;
	
	
	
	public GuKKiCalvFreeBusy(GuKKiCal kalendersammlung, String vCalendarKennung, String vFreeBusyDaten) throws Exception {
		System.out.println("GuKKiCalvFreeBusy-Konstruktor begonnen: " + vCalendarKennung);
		this.kalendersammlung = kalendersammlung;
		this.vCalendarKennung = vCalendarKennung;

		verarbeitenDatenstrom(vFreeBusyDaten);

		vFreeBusyKennung = this.toString();
	}

}
