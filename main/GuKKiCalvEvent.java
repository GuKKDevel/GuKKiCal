package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;

/* Spezifikation laut RF 5545


 ;

 ;
 ; The following are OPTIONAL,
 ; but MUST NOT occur more than once.
 ;
 class / created / description / geo /
 last-mod / location / organizer / priority /
 seq / status / summary / transp /
 url / recurid /
 ;
 ; The following is OPTIONAL,
 ; but SHOULD NOT occur more than once.
 ;
 rrule /
 ;
 ; Either ’dtend’ or ’duration’ MAY appear in
 ; a ’eventprop’, but ’dtend’ and ’duration’
 ; MUST NOT occur in the same ’eventprop’.
 ;
 dtend / duration /
 ;
 ; The following are OPTIONAL,
 ; and MAY occur more than once.
 ;
 attach / attendee / categories / comment /
 contact / exdate / rstatus / related /
 resources / rdate / x-prop / iana-prop
 ;
 )

 */
/**
 * 
 * @author gukkdevel <br>
 * <br>
 * 
 *         Die Klasse GuKKKalender enthält alle Daten für einen Kalender im
 *         iCal Format
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
 *
 */
public class GuKKiCalvEvent {
	/*
	 * Rückverweis auf das enthaltende VCALENDAR-Element
	 */
	GuKKiCalvCalendar kalender = null;
	String kalenderKennung = "";
	/*
	 * Daten für das VEVENT-Element
	 */
	String vEventDTSTAMP = null;
	String vEventUID = null;
	String vEventDTSTART = null;

	String vEventCREATED = null;
	String vEventLASTMODIFIED = null;
	String vEventSUMMARY = null;
	String vEventDTEND = null;
	String vEventDURATION = null;
	String vEventRestinformationen = "";

	/*
	 * allgemeine Variablen
	 */
	String nz = "\n";
	String zeile = "";

	public GuKKiCalvEvent(GuKKiCal kalendersammlung, String kalenderKennung,
			String vEventDaten) throws Exception {
		// TODO Automatisch generierter Konstruktorstub
		System.out.println("GuKKiCalvEvent-Konstruktor begonnen: "
				+ kalenderKennung);

		try {
			// System.out.println(vEventDaten);
			BufferedReader vEventDatenstrom = new BufferedReader(
					new StringReader(vEventDaten));
			vEventRestinformationen = "";
			while ((zeile = vEventDatenstrom.readLine()) != null) {
				// System.out.println("Datenstrom: " + zeile);
				if (!zeile.equals("BEGIN:VEVENT") & !zeile.equals("END:VEVENT")) {
					if (zeile.length() >= 8
							&& zeile.substring(0, 8).equals("DTSTAMP:")) {
						vEventDTSTAMP = zeile.substring(8);
						System.out.println("DTSTAMP=" + vEventDTSTAMP);
					} else if (zeile.length() >= 4
							&& zeile.substring(0, 4).equals("UID:")) {
						vEventUID = zeile.substring(4);
						System.out.println("UID=" + vEventUID);
					} else if (zeile.length() >= 8
							&& (zeile.substring(0, 8).equals("DTSTART:") || zeile
									.substring(0, 8).equals("DTSTART;"))) {
						vEventDTSTART = dtstartErmitteln(zeile.substring(7));
						System.out.println("DTSTART=" + vEventDTSTART);
					} else if (zeile.length() >= 8
							&& zeile.substring(0, 8).equals("CREATED:")) {
						vEventCREATED = zeile.substring(8);
						System.out.println("CREATED=" + vEventCREATED);
					} else if (zeile.length() >= 14
							&& zeile.substring(0, 14).equals("LAST-MODIFIED:")) {
						vEventLASTMODIFIED = zeile.substring(14);
						System.out
								.println("LASTMODIFIED=" + vEventLASTMODIFIED);
					} else {
						vEventRestinformationen += zeile + nz;
						System.out.println("Restinformationen="
								+ vEventRestinformationen);
					}

				}
			}
		} finally

		{

		}
		System.out.println("GuKKiCalvEvent-Konstruktor beendet: UID ="
				+ this.vEventUID);
	}

	/**
	 * Bestimmen des Inhalts der property dtstart
	 * 
	 * dtstart = "DTSTART" dtstparam ":" dtstval CRLF
	 * 
	 * dtstparam = *(
	 *
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 * (";" "VALUE" "=" ("DATE-TIME" / "DATE")) / (";" tzidparam) /
	 * 
	 * The following is OPTIONAL, and MAY occur more than once.
	 *
	 * (";" other-param) )
	 * 
	 * dtstval = date-time / date
	 * 
	 * Value MUST match value type
	 * 
	 * 
	 * @param substring
	 * @return
	 */
	private String dtstartErmitteln(String substring) {
		System.out.println("GuKKiCalvEvent.dtstartBestimmen begonnen");
		// TODO Auto-generated method stub
		System.out.println("GuKKiCalvEvent.dtstartBestimmen begonnen");
		return null;
	}

	public GuKKiCalvEvent() {
		// TODO Automatisch generierter Konstruktorstub
	}

	public String bestimmenUUID() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}
}
// dtstart /
