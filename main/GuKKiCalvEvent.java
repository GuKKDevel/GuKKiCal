package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
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
 * @formatter:on
 * 
 */
public class GuKKiCalvEvent extends GuKKiCalvComponent {
	private GuKKiCal kalendersammlung;
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private GuKKiCalvCalendar kalender = null;
	private String vCalendarKennung = "";
	/*
	 * Daten für das VEVENT-Element (alarmc)
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarm = new ArrayList<GuKKiCalvAlarm>();
	/*
	 * Daten für das VEVENT-Element (eventc)
	 */
	private String vEventKennung = "";
	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vEventDTSTAMP = null;
	private GuKKiCalProperty vEventUID = null;
	/*
	 * The following is REQUIRED if the component appears in an iCalendar object
	 * that doesn’t specify the "METHOD" property; otherwise, it is OPTIONAL in
	 * any case, it MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vEventDTSTART = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vEventCLASS = null;
	private GuKKiCalProperty vEventCREATED = null;
	private GuKKiCalProperty vEventDESCRIPTION = null;
	private GuKKiCalProperty vEventGEO = null;
	private GuKKiCalProperty vEventLASTMODIFIED = null;
	private GuKKiCalProperty vEventLOCATION = null;
	private GuKKiCalProperty vEventORGANIZER = null;
	private GuKKiCalProperty vEventPRIORITY = null;
	private GuKKiCalProperty vEventSEQUENCE = null;
	private GuKKiCalProperty vEventSUMMARY = null;
	private GuKKiCalProperty vEventTRANSP = null;
	private GuKKiCalProperty vEventURL = null;
	private GuKKiCalProperty vEventRECURID = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalProperty vEventRRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’eventprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’eventprop’.
	 */
	private GuKKiCalProperty vEventDTEND = null;
	private GuKKiCalProperty vEventDURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<GuKKiCalProperty> vEventATTACH = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventATTENDEE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventCATEGORIES = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventCOMMENT = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventCONTACT = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventEXDATE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventRSTATUS = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventRELATED = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventRESOURCES = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vEventRDATE = new ArrayList<GuKKiCalProperty>();
	private String vEventXNAMEPROP = "";
	private String vEventIANAPROP = "";

	private String vEventRestinformationen = "";

//	/*
//	 * allgemeine Variablen
//	 */
//	String nz = "\n";
//	String zeile = "";
//	String folgezeile = "";
//	boolean datenVorhanden;

	ArrayList<String> vAlarmDatenArray = new ArrayList<String>();
	boolean vAlarmDatenSammeln = false;
	private GuKKiCalvAlarm vAlarmEvent = null;

	/**
	 * Konstruktor zum Aufbereiten des VEVENT aus einem Eingabestring
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendarKennung
	 * @param vEventDaten
	 * @throws Exception
	 */

	public GuKKiCalvEvent(GuKKiCal kalendersammlung, String vCalendarKennung, String vEventDaten) throws Exception {
//		System.out.println("GuKKiCalvEvent-Konstruktor begonnen: " + vCalendarKennung);
		this.kalendersammlung = kalendersammlung;
		this.vCalendarKennung = vCalendarKennung;

		verarbeitenDatenstrom(vEventDaten);

		vEventKennung = this.toString();

		if (vAlarmDatenArray.size() != 0) {
			vAlarmNeu();
		}
//		for (GuKKiCalvAlarm alarm : vAlarm) {
//			System.out.println(alarm);
//		}

//		System.out.println("GuKKiCalvEvent-Konstruktor beendet: UID=" + this);
	}

	@Override
	protected void verarbeitenZeile(String zeile) throws Exception {

		if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
			if (zeile.equals("BEGIN:VALARM")) {
				vAlarmDatenSammeln = true;
				vAlarmDatenArray.add(zeile);
			} else if (zeile.equals("END:VALARM")) {
				vAlarmDatenArray.add(zeile);
				vAlarmDatenSammeln = false;
			} else if (vAlarmDatenSammeln) {
				vAlarmDatenArray.add(zeile);
			} else {
				if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
					vEventDTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
					vEventUID = new GuKKiCalProperty(zeile, "UID");
				} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
					vEventDTSTART = new GuKKiCalProperty(zeile, "DTSTART");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
					vEventCLASS = new GuKKiCalProperty(zeile, "CLASS");
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
					vEventCREATED = new GuKKiCalProperty(zeile, "CREATED");
				} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
					vEventDESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
					vEventGEO = new GuKKiCalProperty(zeile, "GEO");
				} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
					vEventLASTMODIFIED = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
					vEventLOCATION = new GuKKiCalProperty(zeile, "LOCATION");
				} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
					vEventORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
					vEventPRIORITY = new GuKKiCalProperty(zeile, "PRIORITY");
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
					vEventSEQUENCE = new GuKKiCalProperty(zeile, "SEQUENCE");
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
					vEventSUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("TRANSP")) {
					vEventTRANSP = new GuKKiCalProperty(zeile, "TRANSP");
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
					vEventURL = new GuKKiCalProperty(zeile, "URL");
				} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
					vEventRECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
					vEventRRULE = new GuKKiCalProperty(zeile, "RRULE");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("DTEND")) {
					vEventDTEND = new GuKKiCalProperty(zeile, "DTEND");
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
					vEventDURATION = new GuKKiCalProperty(zeile, "DURATION");
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
					vEventATTACH.add(new GuKKiCalProperty(zeile, "ATTACH"));
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
					vEventATTENDEE.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
				} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
					vEventCATEGORIES.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
					vEventCOMMENT.add(new GuKKiCalProperty(zeile, "COMMENT"));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
					vEventCONTACT.add(new GuKKiCalProperty(zeile, "CONTACT"));
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
					vEventEXDATE.add(new GuKKiCalProperty(zeile, "EXDATE"));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
					vEventRSTATUS.add(new GuKKiCalProperty(zeile, "RSTATUS"));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
					vEventRELATED.add(new GuKKiCalProperty(zeile, "RELATED"));
				} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
					vEventRESOURCES.add(new GuKKiCalProperty(zeile, "RESOURCES"));
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
					vEventRDATE.add(new GuKKiCalProperty(zeile, "RDATE"));
				} else {
					vEventRestinformationen += zeile + nz;
				}
			}
		}
	}

	private void vAlarmNeu() throws Exception {

		String vAlarmDaten = "";

		for (String zeile : vAlarmDatenArray) {
			if (zeile.equals("BEGIN:VALARM")) {
				vAlarmDaten = zeile + nz;
			} else if (zeile.equals("END:VALARM")) {
				vAlarmDaten += zeile + nz;
				vAlarm.add(new GuKKiCalvAlarm(kalendersammlung, vCalendarKennung, vEventKennung, vAlarmDaten));
			} else {
				vAlarmDaten += zeile + nz;
			}
		}
	}

	/**
	 * Gibt statt der Adresse die UID des vEvent zurück
	 */
	public String toString() {
		return this.vCalendarKennung + ",E," + vEventUID.getPropertyWert() + ","
				+ (vEventSEQUENCE == null ? "" : vEventSEQUENCE.getPropertyWert()) + ","
				+ (vEventRECURID == null ? "" : vEventRECURID.getPropertyWert()) + "";
	}

	/**
	 * Gibt sämtliche Daten des vEvent aus
	 */
	public String toString(String ausgabeLevel) {
		return "Event-Identifikation=" + this.toString() + "<-->"
				+ (vEventSUMMARY == null ? "" : vEventSUMMARY.getPropertyWert());
	}
}
