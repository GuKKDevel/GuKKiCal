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

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";
	String folgezeile = "";
	boolean datenVorhanden;

	String vAlarmDaten = "";
	boolean vAlarmDatenSammeln = false;
	private GuKKiCalvAlarm vAlarmEvent = null;

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
		this.kalenderKennung = kalenderKennung;

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

//		System.out.println("Zeile=" + zeile);
		if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
			if (zeile.equals("BEGIN:VALARM")) {
				vAlarmDatenSammeln = true;
				vAlarmDaten = zeile + nz;
			} else if (zeile.equals("END:VALARM")) {
				vAlarmDaten += zeile + nz;
				vAlarmNeu();
				vAlarmDatenSammeln = false;
				vAlarmDaten = "";
			} else if (vAlarmDatenSammeln) {
				vAlarmDaten += zeile + nz;
			} else {
				if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
					vEventDTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
					if (vEventDTSTAMP.getPropertyUNKNOWN() != null)
						System.out.println(vEventDTSTAMP.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("DTSTAMP=" + vEventDTSTAMP);
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
					vEventUID = new GuKKiCalProperty(zeile, "UID");
					if (vEventUID.getPropertyUNKNOWN() != null)
						System.out.println(vEventUID.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("UID=" + vEventUID);
				} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
					vEventDTSTART = new GuKKiCalProperty(zeile, "DTSTART");
					if (vEventDTSTART.getPropertyUNKNOWN() != null)
						System.out.println(vEventDTSTART.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("DTSTART=" + vEventDTSTART);
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
					vEventCLASS = new GuKKiCalProperty(zeile, "CLASS");
					if (vEventCLASS.getPropertyUNKNOWN() != null)
						System.out.println(vEventCLASS.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("CLASS=" + vEventCLASS);
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
					vEventCREATED = new GuKKiCalProperty(zeile, "CREATED");
					if (vEventCREATED.getPropertyUNKNOWN() != null)
						System.out.println(vEventCREATED.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("CREATED=" + vEventCREATED);
				} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
					vEventDESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
					if (vEventDESCRIPTION.getPropertyUNKNOWN() != null)
						System.out.println(vEventDESCRIPTION.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("DESCRIPTION=" + vEventDESCRIPTION);
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
					vEventGEO = new GuKKiCalProperty(zeile, "GEO");
					if (vEventGEO.getPropertyUNKNOWN() != null)
						System.out.println(vEventGEO.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("GEO=" + vEventGEO);
				} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
					vEventLASTMODIFIED = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
					if (vEventLASTMODIFIED.getPropertyUNKNOWN() != null)
						System.out.println(vEventLASTMODIFIED.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("LASTMODIFIED=" + vEventLASTMODIFIED);
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
					vEventLOCATION = new GuKKiCalProperty(zeile, "LOCATION");
					if (vEventLOCATION.getPropertyUNKNOWN() != null)
						System.out.println(vEventLOCATION.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("LOCATION=" + vEventLOCATION);
				} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
					vEventORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
					if (vEventORGANIZER.getPropertyUNKNOWN() != null)
						System.out.println(vEventORGANIZER.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("ORGANIZER=" + vEventORGANIZER);
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
					vEventPRIORITY = new GuKKiCalProperty(zeile, "PRIORITY");
					if (vEventPRIORITY.getPropertyUNKNOWN() != null)
						System.out.println(vEventPRIORITY.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("PRIORITY=" + vEventPRIORITY);
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
					vEventSEQUENCE = new GuKKiCalProperty(zeile, "SEQUENCE");
					if (vEventSEQUENCE.getPropertyUNKNOWN() != null)
						System.out.println(vEventSEQUENCE.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("SEQUENCE=" + vEventSEQUENCE);
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
					vEventSUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
					if (vEventSUMMARY.getPropertyUNKNOWN() != null)
						System.out.println(vEventSUMMARY.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("SUMMARY=" + vEventSUMMARY);
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("TRANSP")) {
					vEventTRANSP = new GuKKiCalProperty(zeile, "TRANSP");
					if (vEventTRANSP.getPropertyUNKNOWN() != null)
						System.out.println(vEventTRANSP.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("TRANSP=" + vEventTRANSP);
				} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
					vEventURL = new GuKKiCalProperty(zeile, "URL");
					if (vEventURL.getPropertyUNKNOWN() != null)
						System.out.println(vEventURL.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("URL=" + vEventURL);
				} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
					vEventRECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
					if (vEventRECURID.getPropertyUNKNOWN() != null)
						System.out.println(vEventRECURID.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RECURID=" + vEventRECURID);
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
					vEventRRULE = new GuKKiCalProperty(zeile, "RRULE");
					if (vEventRRULE.getPropertyUNKNOWN() != null)
						System.out.println(vEventRRULE.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RRULE=" + vEventRRULE);
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("DTEND")) {
					vEventDTEND = new GuKKiCalProperty(zeile, "DTEND");
					if (vEventDTEND.getPropertyUNKNOWN() != null)
						System.out.println(vEventDTEND.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("DTEND=" + vEventDTEND);
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
					vEventDURATION = new GuKKiCalProperty(zeile, "DURATION");
					if (vEventDURATION.getPropertyUNKNOWN() != null)
						System.out.println(vEventDURATION.getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("DURATION=" + vEventDURATION);
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
					vEventATTACH.add(new GuKKiCalProperty(zeile, "ATTACH"));
					if (vEventATTACH.get(vEventATTACH.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventATTACH.get(vEventATTACH.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("ATTACH=" + vEventATTACH.get(vEventATTACH.size() -1));
				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
					vEventATTENDEE.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
					if (vEventATTENDEE.get(vEventATTENDEE.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventATTENDEE.get(vEventATTENDEE.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile+nz+this);
//				System.out.println("ATTENDEE=" + vEventATTENDEE.get(vEventATTENDEE.size() -1));
				} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
					vEventCATEGORIES.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
					if (vEventCATEGORIES.get(vEventCATEGORIES.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(vEventCATEGORIES.get(vEventCATEGORIES.size() - 1).getPropertyUNKNOWN()
								+ "<-->" + zeile);
//				System.out.println("CATEGORIES=" + vEventCATEGORIES.get(vEventCATEGORIES.size() -1));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
					vEventCOMMENT.add(new GuKKiCalProperty(zeile, "COMMENT"));
					if (vEventCOMMENT.get(vEventCOMMENT.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventCOMMENT.get(vEventCOMMENT.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("COMMENT=" + vEventCOMMENT.get(vEventCOMMENT.size() -1));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
					vEventCONTACT.add(new GuKKiCalProperty(zeile, "CONTACT"));
					if (vEventCONTACT.get(vEventCONTACT.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventCONTACT.get(vEventCONTACT.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("CONTACT=" + vEventCONTACT.get(vEventCONTACT.size() -1));
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
					vEventEXDATE.add(new GuKKiCalProperty(zeile, "EXDATE"));
					if (vEventEXDATE.get(vEventEXDATE.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventEXDATE.get(vEventEXDATE.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("EXDATE=" + vEventEXDATE.get(vEventEXDATE.size() -1));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
					vEventRSTATUS.add(new GuKKiCalProperty(zeile, "RSTATUS"));
					if (vEventRSTATUS.get(vEventRSTATUS.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventRSTATUS.get(vEventRSTATUS.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RSTATUS=" + vEventRSTATUS.get(vEventRSTATUS.size() -1));
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
					vEventRELATED.add(new GuKKiCalProperty(zeile, "RELATED"));
					if (vEventRELATED.get(vEventRELATED.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventRELATED.get(vEventRELATED.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RELATED=" + vEventRELATED.get(vEventRELATED.size() -1));
				} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
					vEventRESOURCES.add(new GuKKiCalProperty(zeile, "RESOURCES"));
					if (vEventRESOURCES.get(vEventRESOURCES.size() - 1).getPropertyUNKNOWN() != null)
						System.out.println(
								vEventRESOURCES.get(vEventRESOURCES.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RESOURCES=" + vEventRESOURCES.get(vEventRESOURCES.size() -1));
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
					vEventRDATE.add(new GuKKiCalProperty(zeile, "RDATE"));
					if (vEventRDATE.get(vEventRDATE.size() - 1).getPropertyUNKNOWN() != null)
						System.out
								.println(vEventRDATE.get(vEventRDATE.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
//				System.out.println("RDATE=" + vEventRDATE.get(vEventRDATE.size() -1));
//				} else if (zeile.length() >= 2 && zeile.substring(0, 2).equals("X-")) {
//					vEventXNAMEPROP += zeile + nz;
//					
////				System.out.println("vEventXNAMEPROP=" + vEventXNAMEPROP);
//				} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("IANAPROP")) {
//					vEventIANAPROP.add(new GuKKiCalProperty(zeile, "IANAPROP"));
//					if (vEventIANAPROP.get(vEventIANAPROP.size() - 1).getPropertyUNKNOWN() != null)
//						System.out.println(
//								vEventIANAPROP.get(vEventIANAPROP.size() - 1).getPropertyUNKNOWN() + "<-->" + zeile);
////				System.out.println("IANAPROP=" + vEventAIANAPROP.get(vEventIANAPROP.size() -1));
				} else {
					vEventRestinformationen += zeile + nz;
					System.out.println("Restinformationen=" + vEventRestinformationen);
				}
			}
		}
	}

	private void vAlarmNeu() {
		// TODO Automatisch generierter Methodenstub
		System.out.println("VALARM-einrichten:" + nz + vAlarmDaten);
	}

	/**
	 * Gibt statt der Adresse die UID des vEvent zurück
	 */
	public String toString() {
//		System.out.println(vEventSEQUENCE);
		return this.kalenderKennung + "," + vEventUID.getPropertyWert() + ","
				+ (vEventSEQUENCE == null ? "" : vEventSEQUENCE.getPropertyWert()) + ","
				+ (vEventRECURID == null ? "" : vEventRECURID.getPropertyWert()) + "";
	}

	/**
	 * Gibt sämtliche Daten des vEvent aus
	 */
	public String toString(String ausgabeLevel) {
		return "vEventUID=" + this.toString() + "<-->" + (vEventSUMMARY == null ? "" : vEventSUMMARY.getPropertyWert());
	}
}
