package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
import java.util.ArrayList;

/**
 * 
 * @author gukkdevel <br>
 *         <br>
 * 
 *         Die Klasse GuKKvEvent enthält alle Daten für ein Event im iCal Format
 * 
 *         Definition nach RFC 5545
 * 
 *         eventc = "BEGIN" ":" "VEVENT" CRLF eventprop *alarmc "END" ":"
 *         "VEVENT" CRLF
 *
 *         eventprop = *(
 *
 *         The following are REQUIRED, but MUST NOT occur more than once.
 *
 *         dtstamp / uid /
 *
 *         The following is REQUIRED if the component appears in an iCalendar
 *         object that doesn’t specify the "METHOD" property; otherwise, it is
 *         OPTIONAL; in any case, it MUST NOT occur more than once.
 *
 *         dtstart /
 *
 *         The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *         class / created / description / geo / last-mod / location / organizer
 *         / priority / seq / status / summary / transp / url / recurid /
 * 
 *         The following is OPTIONAL, but SHOULD NOT occur more than once.
 * 
 *         rrule /
 * 
 *         Either ’dtend’ or ’duration’ MAY appear in a ’eventprop’, but ’dtend’
 *         and ’duration’ MUST NOT occur in the same ’eventprop’.
 * 
 *         dtend / duration /
 * 
 *         The following are OPTIONAL, and MAY occur more than once.
 * 
 *         attach / attendee / categories / comment / contact / exdate / rstatus
 *         / related / resources / rdate / x-prop / iana-prop
 * 
 *         )
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
	private String vEventDTSTAMP = null;
	private String vEventUID = null;
	/*
	 * The following is REQUIRED if the component appears in an iCalendar object
	 * that doesn’t specify the "METHOD" property; otherwise, it is OPTIONAL in any
	 * case, it MUST NOT occur more than once.
	 */
	private String vEventDTSTART = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private String vEventCLASS = null;
	private String vEventCREATED = null;
	private String vEventDESCRIPTION = null;
	private String vEventGEO = null;
	private String vEventLASTMOD = null;
	private String vEventLOCATION = null;
	private String vEventORGANIZER = null;
	private String vEventPRIORITY = null;
	private String vEventSEQ = null;
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
	 * The following are OPTIONAL, and MAY occur more than once. attach / attendee /
	 * categories / comment / contact / exdate / rstatus / related / resources /
	 * rdate / x-prop / iana-prop
	 *
	 *
	 */

	String vEventRestinformationen = "";

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

	public GuKKiCalvEvent(GuKKiCal kalendersammlung, String kalenderKennung, String vEventDaten) throws Exception {
		// TODO Automatisch generierter Konstruktorstub
		System.out.println("GuKKiCalvEvent-Konstruktor begonnen: " + kalenderKennung);

		try {
			// System.out.println(vEventDaten);
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
		System.out.println("GuKKiCalvEvent-Konstruktor beendet: UID=" + this.vEventUID);
	}

	private void verarbeitenZeile() {
		// TODO Auto-generated method stub
//		System.out.println("Zeile="+ zeile);
//
//		private String vEventGEO = null;
//		private String vEventLASTMOD = null;
//		private String vEventLOCATION = null;
		if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
			if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				vEventDTSTAMP = checkDTSTAMP(zeile);
				System.out.println("DTSTAMP=" + vEventDTSTAMP);
			} else if (zeile.length() >= 3 && zeile.substring(0, 3).equals("UID")) {
				vEventUID = checkUID(zeile.substring(3));
				System.out.println("UID=" + vEventUID);
			} else if (zeile.length() >= 7 && (zeile.substring(0, 7).equals("DTSTART"))) {
				vEventDTSTART = checkDTSTART(zeile.substring(7));
//				System.out.println("DTSTART=" + vEventDTSTART);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("CLASS:")) {
				vEventCLASS = checkCLASS(zeile.substring(6));
//				System.out.println("CLASS=" + vEventCLASS);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("CREATED:")) {
				vEventCREATED = checkCREATED(zeile.substring(8));
//				System.out.println("CREATED=" + vEventCREATED);
			} else if (zeile.length() >= 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				vEventDESCRIPTION = checkDESCRIPTION(zeile.substring(11));
//				System.out.println("DESCRIPTION=" + vEventDESCRIPTION);
			} else if (zeile.length() >= 4 && zeile.substring(0, 4).equals("GEO:")) {
				vEventGEO = zeile.substring(4);
//				System.out.println("GEO=" + vEventGEO);
			} else if (zeile.length() >= 14 && zeile.substring(0, 14).equals("LAST-MODIFIED:")) {
				vEventLASTMOD = zeile.substring(14);
//				System.out.println("LASTMODIFIED=" + vEventLASTMOD);
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("LOCATION:")) {
				vEventLOCATION = zeile.substring(9);
//				System.out.println("LOCATION=" + vEventLOCATION);
			} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("ORGANIZER:")) {
				vEventORGANIZER = zeile.substring(10);
//				System.out.println("ORGANIZER=" + vEventORGANIZER);
			} else if (zeile.length() >= 9 && zeile.substring(0, 9).equals("PRIORITY:")) {
				vEventPRIORITY = zeile.substring(9);
//				System.out.println("PRIORITY=" + vEventPRIORITY);
			} else if (zeile.length() >= 4 && zeile.substring(0, 4).equals("SEQ:")) {
				vEventSEQ = zeile.substring(4);
//				System.out.println("SEQ=" + vEventSEQ);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("SUMMARY:")) {
				vEventSUMMARY = zeile.substring(8);
//				System.out.println("URL=" + vEventURL);
			} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("TRANSP:")) {
				vEventTRANSP = zeile.substring(7);
//				System.out.println("TRANSP=" + vEventTRANSP);
			} else if (zeile.length() >= 4 && zeile.substring(0, 4).equals("URL:")) {
				vEventURL = zeile.substring(4);
//				System.out.println("URL=" + vEventURL);
			} else if (zeile.length() >= 8 && zeile.substring(0, 8).equals("RECURID:")) {
				vEventRECURID = zeile.substring(8);
//				System.out.println("RECURID=" + vEventRECURID);
			} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("RRULE:")) {
				vEventRRULE = zeile.substring(6);
//				System.out.println("URL=" + vEventURL);
			} else {
				vEventRestinformationen += zeile + nz;
				System.out.println("Restinformationen=" + vEventRestinformationen);
			}
		}
	}

	private String bestimmenUUID() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
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
