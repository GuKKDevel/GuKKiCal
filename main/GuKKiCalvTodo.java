package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Die Klasse GuKKiCalvTodo enthält alle Daten für eine VTODO-Komponente im iCal Format
 * 
 * @author GuKKDevel
 * 
 * @formatter:off
 * 
 *  
 *  RFC 5545 (september 2009) item 3.6.2; p. 55
 *  
 *  Component Name: VTODO
 *  
 *	Purpose: Provide a grouping of calendar properties that describe a
 *		to-do.
 *
 *	Description: A "VTODO" calendar component is a grouping of component
 *		properties and possibly "VALARM" calendar components that
 *		represent an action-item or assignment. For example, it can be
 *		used to represent an item of work assigned to an individual; such
 *		as "turn in travel expense today".
 *
 *		The "VTODO" calendar component cannot be nested within another
 *		calendar component. However, "VTODO" calendar components can be
 *		related to each other or to a "VEVENT" or to a "VJOURNAL" calendar
 *		component with the "RELATED-TO" property.
 *
 *		A "VTODO" calendar component without the "DTSTART" and "DUE" (or
 *		"DURATION") properties specifies a to-do that will be associated
 *		with each successive calendar date, until it is completed.
 *
 *	Format Definition: A "VTODO" calendar component is defined by the
 *		following notation:
 *
 *		todoc= "BEGIN" ":" "VTODO" CRLF
 *	
 *			todoprop *alarmc
 *
 *			"END" ":" "VTODO" CRLF
 *
 *
 *			todoprop = *(
 *
 *		The following are REQUIRED, but MUST NOT occur more than once.
 *	
 *				dtstamp / uid /
 *
 *		The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				class / completed / created / description /
 *				dtstart / geo / last-mod / location / organizer /
 *				percent / priority / recurid / seq / status /
 *				summary / url /
 *
 *		The following is OPTIONAL, but SHOULD NOT occur more than once.
 *
 *				rrule /
 *
 *		Either ’due’ or ’duration’ MAY appear in
 *		a ’todoprop’, but ’due’ and ’duration’
 *		MUST NOT occur in the same ’todoprop’.
 *		If ’duration’ appear in a ’todoprop’,
 *		then ’dtstart’ MUST also appear in
 *		the same ’todoprop’.
 *
 *				due / duration /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				attach / attendee / categories / comment / contact /
 *				exdate / rstatus / related / resources /
 *				rdate / x-prop / iana-prop
 *				
 *			)
 *
 * @formatter:on
 *
 */
public class GuKKiCalvTodo extends GuKKiCalvComponent {
	private GuKKiCal kalendersammlung;
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private GuKKiCalvCalendar kalender = null;
	private String vCalendarKennung = "";
	/*
	 * Daten für das VTODO-Element (alarmc)
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarm = new ArrayList<GuKKiCalvAlarm>();
	/*
	 * Daten für das VTODO-Element (todoc)
	 */
	private String vTodoKennung = "";

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vTodoDTSTAMP = null;
	private GuKKiCalProperty vTodoUID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty vTodoCLASS = null;
	private GuKKiCalProperty vTodoCOMPLETED = null;
	private GuKKiCalProperty vTodoCREATED = null;
	private GuKKiCalProperty vTodoDESCRIPTION = null;
	private GuKKiCalProperty vTodoDTSTART = null;
	private GuKKiCalProperty vTodoGEO = null;
	private GuKKiCalProperty vTodoLASTMODIFIED = null;
	private GuKKiCalProperty vTodoLOCATION = null;
	private GuKKiCalProperty vTodoORGANIZER = null;
	private GuKKiCalProperty vTodoPERCENT = null;
	private GuKKiCalProperty vTodoPRIORITY = null;
	private GuKKiCalProperty vTodoRECURID = null;
	private GuKKiCalProperty vTodoSEQUENCE = null;
	private GuKKiCalProperty vTodoSTATUS = null;
	private GuKKiCalProperty vTodoSUMMARY = null;
	private GuKKiCalProperty vTodoURL = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalProperty vTodoRRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’todoprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’todoprop’.
	 */
	private GuKKiCalProperty vTodoDUE = null;
	// TODO vTodoDUE
	private GuKKiCalProperty vTodoDURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<GuKKiCalProperty> vTodoATTACH = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoATTENDEE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoCATEGORIES = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoCOMMENT = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoCONTACT = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoEXDATE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoRSTATUS = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoRELATED = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoRESOURCES = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> vTodoRDATE = new ArrayList<GuKKiCalProperty>();
	private String vTodoXNAMEPROP = "";
	private String vTodoIANAPROP = "";

	private String vTodoRestinformationen = "";

	/*
	 * allgemeine Variablen
	 */
//	String nz = "\n";
//	String zeile = "";
//	String folgezeile = "";
//	boolean datenVorhanden;
	
	ArrayList<String> vAlarmDatenArray = new ArrayList<String>();
	boolean vAlarmDatenSammeln = false;
	private GuKKiCalvAlarm vAlarmEvent = null;

	public GuKKiCalvTodo() {
		// TODO Automatisch generierter Konstruktorstub
	}

	/**
	 * Konstruktor zum Aufbereiten des VTODO aus einem Eingabestring
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendarKennung
	 * @param vTodoDaten
	 * @throws Exception
	 */
	public GuKKiCalvTodo(GuKKiCal kalendersammlung, String kalenderKennung, String vTodoDaten) throws Exception {
//		System.out.println("GuKKiCalvTodo-Konstruktor begonnen: " + vCalendarKennung);

		this.vCalendarKennung = kalenderKennung;

		verarbeitenDatenstrom(vTodoDaten);
		vTodoKennung = this.toString();

		if (vAlarmDatenArray.size() != 0) {
			vAlarmNeu();
		}
		for (GuKKiCalvAlarm alarm : vAlarm) {
			System.out.println(alarm);
		}
//		System.out.println("GuKKiCalvTodo-Konstruktor beendet: UID=" + this.vTodoUID);
	}

	@Override protected void verarbeitenZeile(String zeile) {
		// TODO Auto-generated method stub
//		System.out.println("Zeile="+ zeile);
//
		if (!zeile.equals("BEGIN:VTODO") & !zeile.equals("END:VTODO")) {
			if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				vTodoDTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
//				System.out.println("DTSTAMP=" + vTodoDTSTAMP);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
				vTodoUID = new GuKKiCalProperty(zeile, "UID");
//				System.out.println("UID=" + vTodoUID);
			} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
				vTodoDTSTART = new GuKKiCalProperty(zeile, "DTSTART");
//				System.out.println("DTSTART=" + vTodoDTSTART);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
				vTodoCLASS = new GuKKiCalProperty(zeile, "CLASS");
//				System.out.println("CLASS=" + vTodoCLASS);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
				vTodoCREATED = new GuKKiCalProperty(zeile, "CREATED");
//				System.out.println("CREATED=" + vTodoCREATED);
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				vTodoDESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
//				System.out.println("DESCRIPTION=" + vTodoDESCRIPTION);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
				vTodoGEO = new GuKKiCalProperty(zeile, "GEO");
//				System.out.println("GEO=" + vTodoGEO);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				vTodoLASTMODIFIED = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
//				System.out.println("LASTMODIFIED=" + vTodoLASTMODIFIED);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
				vTodoLOCATION = new GuKKiCalProperty(zeile, "LOCATION");
//				System.out.println("LOCATION=" + vTodoLOCATION);
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
				vTodoORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
//				System.out.println("ORGANIZER=" + vTodoORGANIZER);
			} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("PERCENT-COMPLETE")) {
				vTodoPERCENT = new GuKKiCalProperty(zeile, "PERCENT-COMPLETE");
//				System.out.println("ORGANIZER=" + vTodoORGANIZER);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
				vTodoPRIORITY = new GuKKiCalProperty(zeile, "PRIORITY");
//				System.out.println("PRIORITY=" + vTodoPRIORITY);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
				vTodoSEQUENCE = new GuKKiCalProperty(zeile, "SEQUENCE");
//				System.out.println("SEQUENCE=" + vTodoSEQUENCE);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("STATUS")) {
				vTodoSTATUS = new GuKKiCalProperty(zeile, "STATUS");
//				System.out.println("STATUS=" + vTodoSTATUS);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
				vTodoSUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
//				System.out.println("SUMMARY=" + vTodoSUMMARY);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
				vTodoURL = new GuKKiCalProperty(zeile, "URL");
//				System.out.println("URL=" + vTodoURL);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
				vTodoRECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
//				System.out.println("RECURID=" + vTodoRECURID);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
				vTodoRRULE = new GuKKiCalProperty(zeile, "RRULE");
//				System.out.println("RRULE=" + vTodoRRULE);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("DUE")) {
				vTodoDUE = new GuKKiCalProperty(zeile, "DUE");
//				System.out.println("DUE=" + vTodoDUE);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
				vTodoDURATION = new GuKKiCalProperty(zeile, "DURATION");
//				System.out.println("DURATION=" + vTodoDURATION);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
				vTodoATTACH.add(new GuKKiCalProperty(zeile, "ATTACH"));
//				System.out.println("ATTACH=" + vTodoATTACH.get(vTodoATTACH.size() -1));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				vTodoATTENDEE.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
//				System.out.println("ATTENDEE=" + vTodoATTENDEE.get(vTodoATTENDEE.size() -1));
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				vTodoCATEGORIES.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
//				System.out.println("CATEGORIES=" + vTodoCATEGORIES.get(vTodoCATEGORIES.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
				vTodoCOMMENT.add(new GuKKiCalProperty(zeile, "COMMENT"));
//				System.out.println("COMMENT=" + vTodoCOMMENT.get(vTodoCOMMENT.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
				vTodoCONTACT.add(new GuKKiCalProperty(zeile, "CONTACT"));
//				System.out.println("CONTACT=" + vTodoCONTACT.get(vTodoCONTACT.size() -1));
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
				vTodoEXDATE.add(new GuKKiCalProperty(zeile, "EXDATE"));
//				System.out.println("EXDATE=" + vTodoEXDATE.get(vTodoEXDATE.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
				vTodoRSTATUS.add(new GuKKiCalProperty(zeile, "RSTATUS"));
//				System.out.println("RSTATUS=" + vTodoRSTATUS.get(vTodoRSTATUS.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
				vTodoRELATED.add(new GuKKiCalProperty(zeile, "RELATED"));
//				System.out.println("RELATED=" + vTodoRELATED.get(vTodoRELATED.size() -1));
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
				vTodoRESOURCES.add(new GuKKiCalProperty(zeile, "RESOURCES"));
//				System.out.println("RESOURCES=" + vTodoRESOURCES.get(vTodoRESOURCES.size() -1));
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
				vTodoRDATE.add(new GuKKiCalProperty(zeile, "RDATE"));
//				System.out.println("RDATE=" + vTodoRDATE.get(vTodoRDATE.size() -1));
			} else {
				vTodoRestinformationen += zeile + nz;
				System.out.println("Restinformationen=" + vTodoRestinformationen);
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
				vAlarm.add(new GuKKiCalvAlarm(kalendersammlung, vCalendarKennung, vTodoKennung, vAlarmDaten));
			} else {
				vAlarmDaten += zeile + nz;
			}
		}
	}
	/**
	 * Gibt statt der Adresse die UID des vTodo zurück
	 */
	public String toString() {
		return vCalendarKennung + ",T," + (vTodoUID == null ? "" : vTodoUID.getPropertyWert()) + ","
				+ (vTodoSEQUENCE == null ? "" : vTodoSEQUENCE.getPropertyWert()) + ","
				+ (vTodoRECURID == null ? "" : vTodoRECURID.getPropertyWert());
	}

	/**
	 * Gibt sämtliche Daten des vTodo aus
	 */
	public String toString(String ausgabeLevel) {
		return "ToDo-Identifikation=" + this.toString() + "<-->" + (vTodoSUMMARY == null ? "" : vTodoSUMMARY.getPropertyWert());
	}
}
