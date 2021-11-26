package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * *	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-
 *	Modifications by RFC 7986 (October 2016) item 4.; p. 4
 *
 *			todoprop =/ *(
 *
 *		The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				color /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				conference / image
 *
 *				)
 *
 * @formatter:on
 *
 */
public class GuKKiCalvTodo extends GuKKiCalComponent {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * Daten für das VTODO-Element (todoc)
	 */
	private String vTodoKennung = "";
	private GuKKiCalcStatus vTodoStatus = GuKKiCalcStatus.UNDEFINIERT;

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty DTSTAMP = null;
	private GuKKiCalProperty UID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty CLASS = null;
	private GuKKiCalProperty COLOR = null;
	private GuKKiCalProperty COMPLETED = null;
	private GuKKiCalProperty CREATED = null;
	private GuKKiCalProperty DESCRIPTION = null;
	private GuKKiCalProperty DTSTART = null;
	private GuKKiCalProperty GEO = null;
	private GuKKiCalProperty LAST_MOD = null;
	private GuKKiCalProperty LOCATION = null;
	private GuKKiCalProperty ORGANIZER = null;
	private GuKKiCalProperty PERCENT = null;
	private GuKKiCalProperty PRIORITY = null;
	private GuKKiCalProperty RECURID = null;
	private GuKKiCalProperty SEQUENCE = null;
	private GuKKiCalProperty STATUS = null;
	private GuKKiCalProperty SUMMARY = null;
	private GuKKiCalProperty URL = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalProperty RRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’todoprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’todoprop’.
	 */
	private GuKKiCalProperty DUE = null;
	// TODO vTodoDUE
	private GuKKiCalProperty DURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<GuKKiCalProperty> ATTACHSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> ATTENDEESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CATEGORIESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CONFERENCESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> COMMENTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CONTACTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> EXDATESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> IMAGESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RSTATUSSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATED_TOSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATEDSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RESOURCESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RDATESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * X-Name Properties
	 */
	private ArrayList<GuKKiCalProperty> X_MICROSOFT_CDO_OWNERAPPTID = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_GENERATION = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_LASTACK = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_RECEIVED_DTSTAMP = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_RECEIVED_SEQUENCE = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_SEND_INVITATIONS_UNDISCLOSED = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> X_MOZ_SEND_INVITATIONS = new ArrayList<GuKKiCalProperty>();

	private String Restinformationen = "";
	/*
	 * Sammlungen der VTODO-Komponenten
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarmSammlung = new ArrayList<GuKKiCalvAlarm>();

	ArrayList<String> vAlarmDatenArray = new ArrayList<String>();
	boolean vAlarmDatenSammeln = false;
	private GuKKiCalvAlarm vAlarmEvent = null;

	/*
	 * allgemeine Variablen
	 */
/**
 * Pseudokonstruktor
 */
	public GuKKiCalvTodo() {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // Ende GuKKiCalvTodo() 

	/**
	 * Konstruktor zum Aufbereiten des VTODO aus einem Eingabestring
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendarKennung
	 * @param vTodoDaten
	 * @throws Exception
	 */
	public GuKKiCalvTodo(String vTodoDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
	
		verarbeitenDatenstrom(vTodoDaten);

		vTodoKennung = this.toString();

		if (vAlarmDatenArray.size() != 0) {
			vAlarmSammlungAufbauen();
		}
		if (!Restinformationen.equals("")) {
			logger.log(Level.INFO, this.toString() + " Restinformationen:\n" + "-->" + Restinformationen + "<--\n");
		}

		vTodoStatus = GuKKiCalcStatus.GELESEN;

		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // Ende GuKKiCalvTodo(String vTodoDaten)

	@Override
	protected void verarbeitenZeile(String zeile) {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		if (!zeile.equals("BEGIN:VTODO") & !zeile.equals("END:VTODO")) {
			if (zeile.length() >= 6 && zeile.substring(0, 6).equals("ATTACH")) {
				ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("CLASS")) {
				CLASS = new GuKKiCalProperty(zeile, "CLASS");
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("COLOR")) {
				COLOR = new GuKKiCalProperty(zeile, "COLOR");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
				COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("COMPLETED")) {
				COMPLETED = new GuKKiCalProperty(zeile, "COMPLETED");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONERENCE")) {
				CONFERENCESammlung.add(new GuKKiCalProperty(zeile, "CONERENCE"));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CONTACT")) {
				CONTACTSammlung.add(new GuKKiCalProperty(zeile, "CONTACT"));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("CREATED")) {
				CREATED = new GuKKiCalProperty(zeile, "CREATED");
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				DESCRIPTION = new GuKKiCalProperty(zeile, "DESCRIPTION");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				DTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
			} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
				DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("DUE")) {
				DUE = new GuKKiCalProperty(zeile, "DUE");
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("DURATION")) {
				DURATION = new GuKKiCalProperty(zeile, "DURATION");
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("EXDATE")) {
				EXDATESammlung.add(new GuKKiCalProperty(zeile, "EXDATE"));
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("GEO")) {
				GEO = new GuKKiCalProperty(zeile, "GEO");
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("IMAGE")) {
				IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("LOCATION")) {
				LOCATION = new GuKKiCalProperty(zeile, "LOCATION");
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
				ORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
			} else if (zeile.length() >= 16 && zeile.substring(0, 16).equals("PERCENT-COMPLETE")) {
				PERCENT = new GuKKiCalProperty(zeile, "PERCENT-COMPLETE");
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("PRIORITY")) {
				PRIORITY = new GuKKiCalProperty(zeile, "PRIORITY");
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
				RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
			} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
				RECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("RELATED-TO")) {
				RELATED_TOSammlung.add(new GuKKiCalProperty(zeile, "RELATED-TO"));
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RELATED")) {
				RELATEDSammlung.add(new GuKKiCalProperty(zeile, "RELATED"));
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("RESOURCES")) {
				RESOURCESSammlung.add(new GuKKiCalProperty(zeile, "RESOURCES"));
			} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
				RRULE = new GuKKiCalProperty(zeile, "RRULE");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("RSTATUS")) {
				RSTATUSSammlung.add(new GuKKiCalProperty(zeile, "RSTATUS"));
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
				SEQUENCE = new GuKKiCalProperty(zeile, "SEQUENCE");
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("STATUS")) {
				STATUS = new GuKKiCalProperty(zeile, "STATUS");
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("SUMMARY")) {
				SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
				UID = new GuKKiCalProperty(zeile, "UID");
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("URL")) {
				URL = new GuKKiCalProperty(zeile, "URL");
				/*
				 * X-Name Properties
				 */
			} else if (zeile.length() >= ("X-MICROSOFT-CDO-OWNERAPPTID").length() && zeile
					.substring(0, ("X-MICROSOFT-CDO-OWNERAPPTID").length()).equals("X-MICROSOFT-CDO-OWNERAPPTID")) {
				X_MICROSOFT_CDO_OWNERAPPTID.add(new GuKKiCalProperty(zeile, "X-MICROSOFT-CDO-OWNERAPPTID"));
			} else if (zeile.length() >= ("X-MOZ-GENERATION").length()
					&& zeile.substring(0, ("X-MOZ-GENERATION").length()).equals("X-MOZ-GENERATION")) {
				X_MOZ_GENERATION.add(new GuKKiCalProperty(zeile, "X-MOZ-GENERATION"));
			} else if (zeile.length() >= ("X-MOZ-LASTACK").length()
					&& zeile.substring(0, ("X-MOZ-LASTACK").length()).equals("X-MOZ-LASTACK")) {
				X_MOZ_LASTACK.add(new GuKKiCalProperty(zeile, "X-MOZ-LASTACK"));
			} else if (zeile.length() >= ("X-MOZ-RECEIVED-DTSTAMP").length()
					&& zeile.substring(0, ("X-MOZ-RECEIVED-DTSTAMP").length()).equals("X-MOZ-RECEIVED-DTSTAMP")) {
				X_MOZ_RECEIVED_DTSTAMP.add(new GuKKiCalProperty(zeile, "X-MOZ-RECEIVED-DTSTAMP"));
			} else if (zeile.length() >= ("X-MOZ-RECEIVED-SEQUENCE").length()
					&& zeile.substring(0, ("X-MOZ-RECEIVED-SEQUENCE").length()).equals("X-MOZ-RECEIVED-SEQUENCE")) {
				X_MOZ_RECEIVED_SEQUENCE.add(new GuKKiCalProperty(zeile, "X-MOZ-RECEIVED-SEQUENCE"));
			} else if (zeile.length() >= ("X-MOZ-SEND-INVITATIONS-UNDISCLOSED").length()
					&& zeile.substring(0, ("X-MOZ-SEND-INVITATIONS-UNDISCLOSED").length()).equals("X-MOZ-SEND-INVITATIONS-UNDISCLOSED")) {
				X_MOZ_SEND_INVITATIONS_UNDISCLOSED.add(new GuKKiCalProperty(zeile, "X-MOZ-SEND-INVITATIONS-UNDISCLOSED"));
			} else if (zeile.length() >= ("X-MOZ-SEND-INVITATIONS").length()
					&& zeile.substring(0, ("X-MOZ-SEND-INVITATIONS").length()).equals("X-MOZ-SEND-INVITATIONS")) {
				X_MOZ_SEND_INVITATIONS.add(new GuKKiCalProperty(zeile, "X-MOZ-SEND-INVITATIONS"));
			} else {
				Restinformationen += zeile + nz;
			}
		}
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} //Ende verarbeitenZeile(String zeile)

	private void vAlarmSammlungAufbauen() throws Exception {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}

		String vAlarmDaten = "";

		for (String zeile : vAlarmDatenArray) {
			if (zeile.equals("BEGIN:VALARM")) {
				vAlarmDaten = zeile + nz;
			} else if (zeile.equals("END:VALARM")) {
				vAlarmDaten += zeile + nz;
				vAlarmSammlung.add(new GuKKiCalvAlarm(vAlarmDaten));
			} else {
				vAlarmDaten += zeile + nz;
			}
		}

		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	} // Ende vAlarmSammlungAufbauen()

	/**
	 * Gibt statt der Adresse die UID des vTodo zurück
	 */
	public String toString() {
		return "T," + (UID == null ? "" : UID.getWert()) + "," + (SEQUENCE == null ? "" : SEQUENCE.getWert()) + ","
				+ (RECURID == null ? "" : RECURID.getWert());
	}

	/**
	 * Gibt sämtliche Daten des vTodo aus
	 */
	public String toString(String ausgabeLevel) {
		return "ToDo-Identifikation=" + this.toString() + "<-->" + (SUMMARY == null ? "" : SUMMARY.getWert());
	}
}
