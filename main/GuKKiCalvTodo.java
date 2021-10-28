package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Die Klasse GuKKiCalvEvent enthält alle Daten für eine VTODO-Komponente im iCal Format
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
 *
 */
public class GuKKiCalvTodo extends GuKKiCalvComponent {
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	private GuKKiCalvCalendar kalender = null;
	private String kalenderKennung = "";
	/*
	 * Daten für das VTODO-Element (alarmc)
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarm = new ArrayList<GuKKiCalvAlarm>();
	/*
	 * Daten für das VTODO-Element (todoprop)
	 */
	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private String vTodoDTSTAMP = null;
	private String vTodoUID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private String vTodoCLASS = null;
	private String vTodoCOMPLETED = null;
	private String vTodoCREATED = null;
	private String vTodoDESCRIPTION = null;
	private String vTodoDTSTART = null;
	private String vTodoGEO = null;
	private String vTodoLASTMODIFIED = null;
	private String vTodoLOCATION = null;
	private String vTodoORGANIZER = null;
	private String vTodoPERCENT = null;			
	private String vTodoPRIORITY = null;
	private String vTodoRECURID = null;
	private String vTodoSEQUENCE = null;
	private String vTodoSTATUS = null;	
	private String vTodoSUMMARY = null;
	private String vTodoURL = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private String vTodoRRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’todoprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’todoprop’.
	 */
	private String vTodoDUE = null;		
	// TODO vTodoDUE
	private String vTodoDURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<String> vTodoATTACH = new ArrayList<String>();
	private ArrayList<String> vTodoATTENDEE = new ArrayList<String>();
	private ArrayList<String> vTodoCATEGORIES = new ArrayList<String>();
	private ArrayList<String> vTodoCOMMENT = new ArrayList<String>();
	private ArrayList<String> vTodoCONTACT = new ArrayList<String>();
	private ArrayList<String> vTodoEXDATE = new ArrayList<String>();
	private ArrayList<String> vTodoRSTATUS = new ArrayList<String>();
	private ArrayList<String> vTodoRELATED = new ArrayList<String>();
	private ArrayList<String> vTodoRESOURCES = new ArrayList<String>();
	private ArrayList<String> vTodoRDATE = new ArrayList<String>();
	private ArrayList<String> vTodoXMOZGENERATION = new ArrayList<String>();
	private ArrayList<String> vTodoIANAPROP = new ArrayList<String>();
		
	private String vTodoRestinformationen = "";

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";
	String folgezeile = "";
	boolean datenVorhanden;
	
	
	public GuKKiCalvTodo() {
		// TODO Automatisch generierter Konstruktorstub
	}
	/**
	 * Konstruktor zum Aufbereiten des VTODO aus einem Eingabestring
	 * 
	 * @param kalendersammlung
	 * @param kalenderKennung
	 * @param vTodoDaten
	 * @throws Exception
	 */
	public GuKKiCalvTodo(GuKKiCal kalendersammlung, String kalenderKennung, String vTodoDaten) throws Exception {
//		System.out.println("GuKKiCalvTodo-Konstruktor begonnen: " + kalenderKennung);

		try {
//			System.out.println(vTodoDaten);
			BufferedReader vTodoDatenstrom = new BufferedReader(new StringReader(vTodoDaten));
			vTodoRestinformationen = "";
			zeile = vTodoDatenstrom.readLine();
			if (zeile != null) {
				datenVorhanden = true;
			}
			while (datenVorhanden) {
				folgezeile = vTodoDatenstrom.readLine();
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
//		System.out.println("GuKKiCalvTodo-Konstruktor beendet: UID=" + this.vTodoUID);
	}
	private void verarbeitenZeile() {
		// TODO Auto-generated method stub
//		System.out.println("Zeile="+ zeile);
//
		if (!zeile.equals("BEGIN:VTODO") & !zeile.equals("END:VTODO")) {
			if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				vTodoDTSTAMP = eintragenProperty(zeile, "DTSTAMP");
//				System.out.println("DTSTAMP=" + vTodoDTSTAMP);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
				vTodoUID = eintragenProperty(zeile, "UID");
//				System.out.println("UID=" + vTodoUID);
			} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
				vTodoDTSTART = eintragenProperty(zeile, "DTSTART");
//				System.out.println("DTSTART=" + vTodoDTSTART);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
				vTodoCLASS = eintragenProperty(zeile, "CLASS");
//				System.out.println("CLASS=" + vTodoCLASS);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
				vTodoCREATED = eintragenProperty(zeile, "CREATED");
//				System.out.println("CREATED=" + vTodoCREATED);
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				vTodoDESCRIPTION = eintragenProperty(zeile, "DESCRIPTION");
//				System.out.println("DESCRIPTION=" + vTodoDESCRIPTION);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
				vTodoGEO = eintragenProperty(zeile, "GEO");
//				System.out.println("GEO=" + vTodoGEO);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				vTodoLASTMODIFIED = eintragenProperty(zeile, "LAST-MODIFIED");
//				System.out.println("LASTMODIFIED=" + vTodoLASTMODIFIED);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
				vTodoLOCATION = eintragenProperty(zeile, "LOCATION");
//				System.out.println("LOCATION=" + vTodoLOCATION);
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
				vTodoORGANIZER = eintragenProperty(zeile, "ORGANIZER");
//				System.out.println("ORGANIZER=" + vTodoORGANIZER);
			} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("PERCENT-COMPLETE")) {
				vTodoPERCENT = eintragenProperty(zeile, "PERCENT-COMPLETE");
//				System.out.println("ORGANIZER=" + vTodoORGANIZER);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
				vTodoPRIORITY = eintragenProperty(zeile, "PRIORITY");
//				System.out.println("PRIORITY=" + vTodoPRIORITY);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
				vTodoSEQUENCE = eintragenProperty(zeile, "SEQUENCE");
//				System.out.println("SEQUENCE=" + vTodoSEQUENCE);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("STATUS")) {
				vTodoSTATUS = eintragenProperty(zeile, "STATUS");
//				System.out.println("STATUS=" + vTodoSTATUS);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
				vTodoSUMMARY = eintragenProperty(zeile, "SUMMARY");
//				System.out.println("SUMMARY=" + vTodoSUMMARY);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
				vTodoURL = eintragenProperty(zeile, "URL");
//				System.out.println("URL=" + vTodoURL);
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
				vTodoRECURID = eintragenProperty(zeile, "RECURRENCE-ID");
//				System.out.println("RECURID=" + vTodoRECURID);
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
				vTodoRRULE = eintragenProperty(zeile, "RRULE");
//				System.out.println("RRULE=" + vTodoRRULE);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("DUE")) {
				vTodoDUE = eintragenProperty(zeile, "DUE");
//				System.out.println("DUE=" + vTodoDUE);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
				vTodoDURATION = eintragenProperty(zeile, "DURATION");
//				System.out.println("DURATION=" + vTodoDURATION);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
				vTodoATTACH.add(eintragenProperty(zeile, "ATTACH"));
//				System.out.println("ATTACH=" + vTodoATTACH.get(vTodoATTACH.size() -1));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				vTodoATTENDEE.add(eintragenProperty(zeile, "ATTENDEE"));
//				System.out.println("ATTENDEE=" + vTodoATTENDEE.get(vTodoATTENDEE.size() -1));
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				vTodoCATEGORIES.add(eintragenProperty(zeile, "CATEGORIES"));
//				System.out.println("CATEGORIES=" + vTodoCATEGORIES.get(vTodoCATEGORIES.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
				vTodoCOMMENT.add(eintragenProperty(zeile, "COMMENT"));
//				System.out.println("COMMENT=" + vTodoCOMMENT.get(vTodoCOMMENT.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
				vTodoCONTACT.add(eintragenProperty(zeile, "CONTACT"));
//				System.out.println("CONTACT=" + vTodoCONTACT.get(vTodoCONTACT.size() -1));
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
				vTodoEXDATE.add(eintragenProperty(zeile, "EXDATE"));
//				System.out.println("EXDATE=" + vTodoEXDATE.get(vTodoEXDATE.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
				vTodoRSTATUS.add(eintragenProperty(zeile, "RSTATUS"));
//				System.out.println("RSTATUS=" + vTodoRSTATUS.get(vTodoRSTATUS.size() -1));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
				vTodoRELATED.add(eintragenProperty(zeile, "RELATED"));
//				System.out.println("RELATED=" + vTodoRELATED.get(vTodoRELATED.size() -1));
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
				vTodoRESOURCES.add(eintragenProperty(zeile, "RESOURCES"));
//				System.out.println("RESOURCES=" + vTodoRESOURCES.get(vTodoRESOURCES.size() -1));
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
				vTodoRDATE.add(eintragenProperty(zeile, "RDATE"));
//				System.out.println("RDATE=" + vTodoRDATE.get(vTodoRDATE.size() -1));
			} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("X-MOZ-GENERATION")) {
				vTodoXMOZGENERATION.add(eintragenProperty(zeile, "X-MOZ-GENERATION"));
//				System.out.println("X-MOZ-GENERATION=" + vTodoXMOZGENERATION.get(vTodoXMOZGENERATION.size() -1));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("IANAPROP")) {
				vTodoIANAPROP.add(eintragenProperty(zeile, "IANAPROP"));
//				System.out.println("IANAPROP=" + vTodoAIANAPROP.get(vTodoIANAPROP.size() -1));
			} else {
				vTodoRestinformationen += zeile + nz;
				System.out.println("Restinformationen=" + vTodoRestinformationen);
			}
		}
	}
	/**
	 * Gibt statt der Adresse die UID des vTodo zurück
	 */
	public String toString() {
		return "vTodoUID=" + vTodoUID;
	}

	/**
	 * Gibt sämtliche Daten des vEvent aus
	 */
	public String toString(String ausgabeLevel) {
		return this.toString() + "<-->" + vTodoSUMMARY;
	}
}
