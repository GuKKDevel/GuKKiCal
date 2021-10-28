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
 * 	RFC 5545 (september 2009) item 3.6.1; p. 96
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
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private GuKKiCalvCalendar kalender = null;
	private String kalenderKennung = "";
	/*
	 * Daten für das VEVENT-Element (alarmc)
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarm = new ArrayList<GuKKiCalvAlarm>();
	/*
	 * Daten für das VEVENT-Element (eventprop)
	 */
	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vEventDTSTAMP = null;
	private String vEventUID = null;
	/*
	 * The following is REQUIRED if the component appears in an iCalendar object
	 * that doesn’t specify the "METHOD" property; otherwise, it is OPTIONAL in
	 * any case, it MUST NOT occur more than once.
	 */
	private String vEventDTSTART = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private String vEventCLASS = null;
	private String vEventCREATED = null;
	private String vEventDESCRIPTION = null;
	private String vEventGEO = null;
	private String vEventLASTMODIFIED = null;
	private String vEventLOCATION = null;
	private String vEventORGANIZER = null;
	private String vEventPRIORITY = null;
	private String vEventSEQUENCE = null;
	private String vEventSUMMARY = null;
	private String vEventTRANSP = null;
	private String vEventURL = null;
	private String vEventRECURID = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private String vEventRRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’eventprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’eventprop’.
	 */
	private String vEventDTEND = null;
	private String vEventDURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<String> vEventATTACH = new ArrayList<String>();
	private ArrayList<String> vEventATTENDEE = new ArrayList<String>();
	private ArrayList<String> vEventCATEGORIES = new ArrayList<String>();
	private ArrayList<String> vEventCOMMENT = new ArrayList<String>();
	private ArrayList<String> vEventCONTACT = new ArrayList<String>();
	private ArrayList<String> vEventEXDATE = new ArrayList<String>();
	private ArrayList<String> vEventRSTATUS = new ArrayList<String>();
	private ArrayList<String> vEventRELATED = new ArrayList<String>();
	private ArrayList<String> vEventRESOURCES = new ArrayList<String>();
	private ArrayList<String> vEventRDATE = new ArrayList<String>();
	private ArrayList<String> vEventXMOZGENERATION = new ArrayList<String>();
	private ArrayList<String> vEventIANAPROP = new ArrayList<String>();

	private String vEventRestinformationen = "";

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";
	String folgezeile = "";
	boolean datenVorhanden;

	public GuKKiCalvEvent() {
		// TODO Automatisch generierter Konstruktorstub
	}

	/**
	 * Konstruktor zum Aufbereiten des VEVENT aus einem Eingabestring
	 * 
	 * @param kalendersammlung
	 * @param kalenderKennung
	 * @param vEventDaten
	 * @throws Exception
	 */
	public GuKKiCalvEvent(GuKKiCal kalendersammlung, String kalenderKennung, String vEventDaten) throws Exception {
//		System.out.println("GuKKiCalvEvent-Konstruktor begonnen: " + kalenderKennung);

		try {
//			System.out.println(vEventDaten);
			BufferedReader vEventDatenstrom = new BufferedReader(new StringReader(vEventDaten));
			vEventRestinformationen = "";
			zeile = vEventDatenstrom.readLine();
			if (zeile != null) {
				datenVorhanden = true;
			}
			while (datenVorhanden) {
				folgezeile = vEventDatenstrom.readLine();
				if (folgezeile == null) {
					verarbeitenZeile();
					datenVorhanden = false;
				} else {
					if (folgezeile.substring(0, 1).equals(" ")) {
						zeile = zeile.substring(0, zeile.length()) + folgezeile.substring(1);
					} else {
						verarbeitenZeile();
						zeile = folgezeile;
					}
				}
			} /* end while-Schleife */
		} finally

		{

		}
//		System.out.println("GuKKiCalvEvent-Konstruktor beendet: UID=" + this.vEventUID);
	}

	private void verarbeitenZeile() throws Exception {
		// TODO Auto-generated method stub
//		System.out.println("Zeile="+ zeile);
//
		if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
			if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				vEventDTSTAMP = eintragenDTSTAMP(zeile);
//				System.out.println("DTSTAMP=" + vEventDTSTAMP);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
				vEventUID = eintragenProperty(zeile, "UID");
//				System.out.println("UID=" + vEventUID);
			} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
				vEventDTSTART = eintragenProperty(zeile, "DTSTART");
//				System.out.println("DTSTART=" + vEventDTSTART);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
				vEventCLASS = eintragenProperty(zeile, "CLASS");
//				System.out.println("CLASS=" + vEventCLASS);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
				vEventCREATED = eintragenProperty(zeile, "CREATED");
//				System.out.println("CREATED=" + vEventCREATED);
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				vEventDESCRIPTION = eintragenProperty(zeile, "DESCRIPTION");
//				System.out.println("DESCRIPTION=" + vEventDESCRIPTION);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
				vEventGEO = eintragenProperty(zeile, "GEO");
//				System.out.println("GEO=" + vEventGEO);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				vEventLASTMODIFIED = eintragenProperty(zeile, "LAST-MODIFIED");
//				System.out.println("LASTMODIFIED=" + vEventLASTMODIFIED);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
				vEventLOCATION = eintragenProperty(zeile, "LOCATION");
//				System.out.println("LOCATION=" + vEventLOCATION);
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
				vEventORGANIZER = eintragenProperty(zeile, "ORGANIZER");
//				System.out.println("ORGANIZER=" + vEventORGANIZER);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
				vEventPRIORITY = eintragenProperty(zeile, "PRIORITY");
//				System.out.println("PRIORITY=" + vEventPRIORITY);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
				vEventSEQUENCE = eintragenProperty(zeile, "SEQUENCE");
//				System.out.println("SEQUENCE=" + vEventSEQUENCE);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
				vEventSUMMARY = eintragenProperty(zeile, "SUMMARY");
//				System.out.println("SUMMARY=" + vEventSUMMARY);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("TRANSP")) {
				vEventTRANSP = eintragenProperty(zeile, "TRANSP");
//				System.out.println("TRANSP=" + vEventTRANSP);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
				vEventURL = eintragenProperty(zeile, "URL");
//				System.out.println("URL=" + vEventURL);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
				vEventRECURID = eintragenProperty(zeile, "RECURRENCE-ID");
//				System.out.println("RECURID=" + vEventRECURID);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
				vEventRRULE = eintragenProperty(zeile, "RRULE");
//				System.out.println("RRULE=" + vEventRRULE);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("DTEND")) {
				vEventDTEND = eintragenProperty(zeile, "DTEND");
//				System.out.println("DTEND=" + vEventDTEND);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
				vEventDURATION = eintragenProperty(zeile, "DURATION");
//				System.out.println("DURATION=" + vEventDURATION);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
				vEventATTACH.add(eintragenProperty(zeile, "ATTACH"));
//				System.out.println("ATTACH=" + vEventATTACH.get(vEventATTACH.size() -1));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				vEventATTENDEE.add(eintragenProperty(zeile, "ATTENDEE"));
//				System.out.println("ATTENDEE=" + vEventATTENDEE.get(vEventATTENDEE.size() -1));
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				vEventCATEGORIES.add(eintragenProperty(zeile, "CATEGORIES"));
//				System.out.println("CATEGORIES=" + vEventCATEGORIES.get(vEventCATEGORIES.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
				vEventCOMMENT.add(eintragenProperty(zeile, "COMMENT"));
//				System.out.println("COMMENT=" + vEventCOMMENT.get(vEventCOMMENT.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
				vEventCONTACT.add(eintragenProperty(zeile, "CONTACT"));
//				System.out.println("CONTACT=" + vEventCONTACT.get(vEventCONTACT.size() -1));
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
				vEventEXDATE.add(eintragenProperty(zeile, "EXDATE"));
//				System.out.println("EXDATE=" + vEventEXDATE.get(vEventEXDATE.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
				vEventRSTATUS.add(eintragenProperty(zeile, "RSTATUS"));
//				System.out.println("RSTATUS=" + vEventRSTATUS.get(vEventRSTATUS.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
				vEventRELATED.add(eintragenProperty(zeile, "RELATED"));
//				System.out.println("RELATED=" + vEventRELATED.get(vEventRELATED.size() -1));
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
				vEventRESOURCES.add(eintragenProperty(zeile, "RESOURCES"));
//				System.out.println("RESOURCES=" + vEventRESOURCES.get(vEventRESOURCES.size() -1));
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
				vEventRDATE.add(eintragenProperty(zeile, "RDATE"));
//				System.out.println("RDATE=" + vEventRDATE.get(vEventRDATE.size() -1));
			} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("X-MOZ-GENERATION")) {
				vEventXMOZGENERATION.add(eintragenProperty(zeile, "X-MOZ-GENERATION"));
//				System.out.println("X-MOZ-GENERATION=" + vEventXMOZGENERATION.get(vEventXMOZGENERATION.size() -1));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("IANAPROP")) {
				vEventIANAPROP.add(eintragenProperty(zeile, "IANAPROP"));
//				System.out.println("IANAPROP=" + vEventAIANAPROP.get(vEventIANAPROP.size() -1));
			} else {
				vEventRestinformationen += zeile + nz;
				System.out.println("Restinformationen=" + vEventRestinformationen);
			}
		}
	}

	/**
	 * Gibt statt der Adresse die UID des vEvent zurück
	 */
	public String toString() {
		return "vEventUID=" + vEventUID;
	}

	/**
	 * Gibt sämtliche Daten des vEvent aus
	 */
	public String toString(String ausgabeLevel) {
		return this.toString() + "<-->" + vEventSUMMARY;
	}
}
