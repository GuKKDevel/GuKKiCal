package main;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * 
 * Die Klasse GuKKiCalvTimezone enthält alle Daten für eine VTIMEZONE-Komponente im iCal Format
 *         
 * @author GuKKDevel
 *  
 * @formatter:off
 * 
 * 	Time Zone Component
 * 
 * 	RFC 5545 (september 2009) item 3.6.5; p. 62
 * 
 * 	Component Name: VTIMEZONE
 * 
 * 	Purpose: Provide a grouping of component properties that defines a
 * 		time zone.
 * 
 *	Description: A time zone is unambiguously defined by the set of time
 *		measurement rules determined by the governing body for a given
 *		geographic area. These rules describe, at a minimum, the base
 *		offset from UTC for the time zone, often referred to as the
 *		Standard Time offset. Many locations adjust their Standard Time
 *		forward or backward by one hour, in order to accommodate seasonal
 *		changes in number of daylight hours, often referred to as Daylight
 *		Saving Time. Some locations adjust their time by a fraction of an
 *		hour. Standard Time is also known as Winter Time. Daylight
 *		Saving Time is also known as Advanced Time, Summer Time, or Legal
 *		Time in certain countries. The following table shows the changes
 *		in time zone rules in effect for New York City starting from 1967.
 *		Each line represents a description or rule for a particular
 *		observance.
 *
 *		Effective Observance Rule
 *		+-----------+--------------------------+--------+--------------+
 *		| Date		| (Date-Time)			   | Offset | Abbreviation |
 *		+-----------+--------------------------+--------+--------------+
 *		| 1967-1973 | last Sun in Apr, 02:00   | -0400  | EDT          |
 *		|           |                          |        |              |
 *		| 1967-2006 | last Sun in Oct, 02:00   | -0500  | EST          |
 *		|           |                          |        |              |
 *		| 1974-1974 | Jan 6, 02:00             | -0400  | EDT          |
 *		|           |                          |        |              |
 *		| 1975-1975 | Feb 23, 02:00            | -0400  | EDT          |
 *		|           |                          |        |              |
 *		| 1976-1986 | last Sun in Apr, 02:00   | -0400  | EDT		   |
 *		|           |                          |        |              |
 *		| 1987-2006 | first Sun in Apr, 02:00  | -0400  | EDT          |
 *		|           |                          |        |              |
 * 		| 2007-*    | second Sun in Mar, 02:00 | -0400  | EDT          |
 *		|           |                          |        |              |
 *   	| 2007-*    | first Sun in Nov, 02:00  | -0500  | EST          |  
 *		|           |                          |        |              |
 *		+-----------+--------------------------+--------+--------------+
 *
 *			Note: The specification of a global time zone registry is not
 *			addressed by this document and is left for future study.
 *			However, implementers may find the TZ database [TZDB] a useful
 *			reference. It is an informal, public-domain collection of time
 *			zone information, which is currently being maintained by
 *			volunteer Internet participants, and is used in several
 *			operating systems. This database contains current and
 *			historical time zone information for a wide variety of
 *			locations around the globe; it provides a time zone identifier
 *			for every unique time zone rule set in actual use since 1970,
 *			with historical data going back to the introduction of standard
 *			time.
 *
 *		Interoperability between two calendaring and scheduling
 *		applications, especially for recurring events, to-dos or journal
 *		entries, is dependent on the ability to capture and convey date
 *		and time information in an unambiguous format. The specification
 *		of current time zone information is integral to this behavior.
 *
 *		If present, the "VTIMEZONE" calendar component defines the set of
 *		Standard Time and Daylight Saving Time observances (or rules) for
 *		a particular time zone for a given interval of time. The
 *		"VTIMEZONE" calendar component cannot be nested within other
 *		calendar components. Multiple "VTIMEZONE" calendar components can
 *		exist in an iCalendar object. In this situation, each "VTIMEZONE"
 *		MUST represent a unique time zone definition. This is necessary
 *		for some classes of events, such as airline flights, that start in
 *		one time zone and end in another.
 *
 *		The "VTIMEZONE" calendar component MUST include the "TZID"
 *		property and at least one definition of a "STANDARD" or "DAYLIGHT"
 *		sub-component. The "STANDARD" or "DAYLIGHT" sub-component MUST
 *		include the "DTSTART", "TZOFFSETFROM", and "TZOFFSETTO"
 *		properties.
 *	
 *		An individual "VTIMEZONE" calendar component MUST be specified for
 *		each unique "TZID" parameter value specified in the iCalendar
 *		object. In addition, a "VTIMEZONE" calendar component, referred
 *		to by a recurring calendar component, MUST provide valid time zone
 *		information for all recurrence instances.
 *		
 *		Each "VTIMEZONE" calendar component consists of a collection of
 *		one or more sub-components that describe the rule for a particular
 *		observance (either a Standard Time or a Daylight Saving Time
 *		observance). The "STANDARD" sub-component consists of a
 *		collection of properties that describe Standard Time. The
 *		"DAYLIGHT" sub-component consists of a collection of properties
 *		that describe Daylight Saving Time. In general, this collection
 *		of properties consists of:
 *
 *			* the first onset DATE-TIME for the observance
 *			* the last onset DATE-TIME for the observance, if a last onset is known
 *			* the offset to be applied for the observance
 *			* a rule that describes the day and time when the observance takes effect
 *			* an optional name for the observance.
 *
 *		For a given time zone, there may be multiple unique definitions of
 *		the observances over a period of time. Each observance is
 *		described using either a "STANDARD" or "DAYLIGHT" sub-component.
 *		The collection of these sub-components is used to describe the
 *		time zone for a given period of time. The offset to apply at any
 *		given time is found by locating the observance that has the last
 *		onset date and time before the time in question, and using the
 *		offset value from that observance.
 *
 *		The top-level properties in a "VTIMEZONE" calendar component are:
 *
 *		The mandatory "TZID" property is a text value that uniquely
 *		identifies the "VTIMEZONE" calendar component within the scope of
 *		an iCalendar object.
 *
 *		The optional "LAST-MODIFIED" property is a UTC value that
 *		specifies the date and time that this time zone definition was
 *		last updated.
 *
 *		The optional "TZURL" property is a url value that points to a
 *		published "VTIMEZONE" definition. "TZURL" SHOULD refer to a
 *		resource that is accessible by anyone who might need to interpret
 *		the object. This SHOULD NOT normally be a "file" URL or other URL
 *		that is not widely accessible.
 *
 *		The collection of properties that are used to define the
 *		"STANDARD" and "DAYLIGHT" sub-components include:
 *		The mandatory "DTSTART" property gives the effective onset date
 *		and local time for the time zone sub-component definition.
 *		"DTSTART" in this usage MUST be specified as a date with a local
 *		time value.
 *
 *		The mandatory "TZOFFSETFROM" property gives the UTC offset that is
 *		in use when the onset of this time zone observance begins.
 *		"TZOFFSETFROM" is combined with "DTSTART" to define the effective
 *		onset for the time zone sub-component definition. For example,
 *		the following represents the time at which the observance of
 *		Standard Time took effect in Fall 1967 for New York City:
 *
 *			DTSTART:19671029T020000
 *			TZOFFSETFROM:-0400
 *
 *		The mandatory "TZOFFSETTO" property gives the UTC offset for the
 *		time zone sub-component (Standard Time or Daylight Saving Time)
 *		when this observance is in use.
 *
 *		The optional "TZNAME" property is the customary name for the time
 *		zone. This could be used for displaying dates.
 *		
 *		The onset DATE-TIME values for the observance defined by the time
 *		zone sub-component is defined by the "DTSTART", "RRULE", and
 *		"RDATE" properties.
 *
 *		The "RRULE" property defines the recurrence rule for the onset of
 *		the observance defined by this time zone sub-component. Some
 *		specific requirements for the usage of "RRULE" for this purpose
 *		include:
 *
 *			* If observance is known to have an effective end date, the
 *				"UNTIL" recurrence rule parameter MUST be used to specify the
 *				last valid onset of this observance (i.e., the UNTIL DATE-TIME
 *				will be equal to the last instance generated by the recurrence
 *				pattern). It MUST be specified in UTC time.
 *			* The "DTSTART" and the "TZOFFSETFROM" properties MUST be used
 *				when generating the onset DATE-TIME values (instances) from the
 *				"RRULE".
 *
 *		The "RDATE" property can also be used to define the onset of the
 *		observance by giving the individual onset date and times. "RDATE"
 *		in this usage MUST be specified as a date with local time value,
 *		relative to the UTC offset specified in the "TZOFFSETFROM"
 *		property.
 *
 *		The optional "COMMENT" property is also allowed for descriptive
 *		explanatory text.
 *		
 * 	Format Definition: A "VTIMEZONE" calendar component is defined by the following notation:
 * 
 * 		timezonec = "BEGIN" ":" "VTIMEZONE" CRLF
 * 
 * 			*(
 * 		
 * 		’tzid’ is REQUIRED, but MUST NOT occur more than once.
 * 
 * 			tzid /
 * 
 *  	’last-mod’ and ’tzurl’ are OPTIONAL, but MUST NOT occur more than once.
 *  
 *  		last-mod / tzurl /
 *  
 *  	One of ’standardc’ or ’daylightc’ MUST occur and each MAY occur more than once.
 *  
 *  		standardc / daylightc /
 *  
 *  	The following are OPTIONAL, and MAY occur more than once.
 *  
 *  		x-prop / iana-prop
 *  
 *  		)
 *  
 *  		"END" ":" "VTIMEZONE" CRLF
 *  
 *  		standardc = "BEGIN" ":" "STANDARD" CRLF
 *				tzprop
 *				"END" ":" "STANDARD" CRLF
 *
 *			daylightc = "BEGIN" ":" "DAYLIGHT" CRLF	
 *				tzprop
 *				"END" ":" "DAYLIGHT" CRLF
 *
 *			tzprop = *(
 *
 *		The following are REQUIRED, but MUST NOT occur more than once.
 *
 *				dtstart / tzoffsetto / tzoffsetfrom /
 *
 *		The following is OPTIONAL, but SHOULD NOT occur more than once.
 *
 *				rrule /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				comment / rdate / tzname / x-prop / iana-prop
 *
 *				)
 *
 * @formatter:on
 * 
 */

public class GuKKiCalvTimezone extends GuKKiCalComponent {
	/*
	 * The following is REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty TZID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalProperty LAST_MOD = null;
	private GuKKiCalProperty TZURL = null;
	/*
	 * One of ’standardc’ or ’daylightc’ MUST occur and each MAY occur more than
	 * once.
	 */
	private ArrayList<GuKKiCalcDaylight> cDaylightSammlung = new ArrayList<GuKKiCalcDaylight>();
	private ArrayList<GuKKiCalcStandard> cStandardSammlung = new ArrayList<GuKKiCalcStandard>();
	/*
	 * Sammlung für x-prop und iana-prop auf Ebene VTIMEZONE
	 */
	private String Restinformationen = "";
	/*
	 * Zwischenspeicher für den Datenstrom für cDaylight und cStandard nebst
	 * Verarbeitungsschalter
	 */
	ArrayList<String> cDaylightDatenArray = new ArrayList<String>();
	ArrayList<String> cStandardDatenArray = new ArrayList<String>();
	boolean cDaylightDatenSammeln = false;
	boolean cStandardDatenSammeln = false;

	/**
	 * Erstellen einer leeren VTimezone-Komponente
	 */
	protected GuKKiCalvTimezone() {
		/*	@formatter:off */
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		status = GuKKiCalcStatus.UNDEFINIERT;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
		/*	@formatter:on */
	}

	/**
	 * Erstellen einer VTIMEZONE-Komponente aus dem Teil eines
	 * Kalenderdatenstroms
	 * 
	 * @param vTimezoneDaten
	 * 
	 * @throws Exception
	 */
	public GuKKiCalvTimezone(String vTimezoneDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		kennung = GuKKiCalcKennung.TIMEZONE;
		status = GuKKiCalcStatus.UNDEFINIERT;

		verarbeitenDatenstrom(vTimezoneDaten);

		if (cDaylightDatenArray.size() != 0) {
			cDaylightneu();
		}
		if (cStandardDatenArray.size() != 0) {
			cStandardneu();
		}
		status = GuKKiCalcStatus.GELESEN;

		if (!Restinformationen.equals("")) {
			logger.log(Level.INFO, this.toString() + " Restinformationen:\n" + "-->" + Restinformationen + "<--\n");
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	@Override
	protected void verarbeitenZeile(String zeile) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (!zeile.equals("BEGIN:VTIMEZONE") & !zeile.equals("END:VTIMEZONE")) {
			if (zeile.equals("BEGIN:DAYLIGHT")) {
				cDaylightDatenSammeln = true;
				cDaylightDatenArray.add(zeile);
			} else if (zeile.equals("END:DAYLIGHT")) {
				cDaylightDatenArray.add(zeile);
				cDaylightDatenSammeln = false;
			} else if (cDaylightDatenSammeln) {
				cDaylightDatenArray.add(zeile);
			} else if (zeile.equals("BEGIN:STANDARD")) {
				cStandardDatenSammeln = true;
				cStandardDatenArray.add(zeile);
			} else if (zeile.equals("END:STANDARD")) {
				cStandardDatenArray.add(zeile);
				cStandardDatenSammeln = false;
			} else if (cStandardDatenSammeln) {
				cStandardDatenArray.add(zeile);
			} else {
				if (zeile.length() >= 4 && zeile.substring(0, 4).equals("TZID")) {
					TZID = new GuKKiCalProperty(zeile, "TZID");
				} else if (zeile.length() >= 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
					LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("TZURL")) {
					TZURL = new GuKKiCalProperty(zeile, "TZURL");
				} else {
					Restinformationen += zeile + nz;
				}
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	private void cDaylightneu() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		String cDaylightDaten = "";

		for (String zeile : cDaylightDatenArray) {
			if (zeile.equals("BEGIN:DAYLIGHT")) {
				cDaylightDaten = zeile + nz;
			} else if (zeile.equals("END:DAYLIGHT")) {
				cDaylightDaten += zeile + nz;
				cDaylightSammlung.add(new GuKKiCalcDaylight(cDaylightDaten));
			} else {
				cDaylightDaten += zeile + nz;
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	private void cStandardneu() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		String cStandardDaten = "";

		for (String zeile : cStandardDatenArray) {
			if (zeile.equals("BEGIN:STANDARD")) {
				cStandardDaten = zeile + nz;
			} else if (zeile.equals("END:STANDARD")) {
				cStandardDaten += zeile + nz;
				cStandardSammlung.add(new GuKKiCalcStandard(cStandardDaten));
			} else {
				cStandardDaten += zeile + nz;
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	public GuKKiCalvTimezone kopieren() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		GuKKiCalvTimezone temp = new GuKKiCalvTimezone();

		temp.kennung = this.kennung;

		temp.TZID = TZID == null ? null : TZID.kopieren();
		temp.LAST_MOD = LAST_MOD == null ? null : LAST_MOD.kopieren();
		temp.TZURL = TZURL == null ? null : TZURL.kopieren();
		for (GuKKiCalcDaylight cDaylight : cDaylightSammlung) {
			temp.cDaylightSammlung.add(cDaylight.kopieren());
			
		}
		for (GuKKiCalcStandard cStandard : cStandardSammlung) {
			temp.cStandardSammlung.add(cStandard.kopieren());
		}
		temp.Restinformationen = this.Restinformationen;
		temp.status = GuKKiCalcStatus.KOPIERT;
		
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp;
	}

	/**
	 * Gibt Identifikationsdaten der VTIMEZONE-Komponente aus
	 */
	public String toString() {
		return (TZID == null ? "" : TZID.getWert());
	}

	/**
	 * Gibt einige Daten der VTIMEZONE-Komponente aus
	 */
	public String toString(String ausgabeLevel) {
		return "Timezone-Identifikation=" + this.toString();
	}

	class GuKKiCalcDaylight extends GuKKiCalComponent {
		/*
		 * The following are REQUIRED, but MUST NOT occur more than once.
		 */
		GuKKiCalProperty DTSTART = null;
		GuKKiCalProperty TZOFFSETTO = null;
		GuKKiCalProperty TZOFFSETFROM = null;
		/*
		 * The following is OPTIONAL, but SHOULD NOT occur more than once.
		 */
		GuKKiCalProperty RRULE = null;
		/*
		 * The following are OPTIONAL, and MAY occur more than once.
		 */
		private ArrayList<GuKKiCalProperty> COMMENT = new ArrayList<GuKKiCalProperty>();
		private ArrayList<GuKKiCalProperty> RDATE = new ArrayList<GuKKiCalProperty>();
		private ArrayList<GuKKiCalProperty> TZNAME = new ArrayList<GuKKiCalProperty>();
		/*
		 * Here are the x-prop and iana-prop are to be stored
		 */
		private String Restinformationen = "";

		private GuKKiCalcDaylight() {
			kennung = GuKKiCalcKennung.DAYLIGHT;
		}

		private GuKKiCalcDaylight(String cDaylightDaten) throws Exception {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}
			kennung = GuKKiCalcKennung.DAYLIGHT;
			
			verarbeitenDatenstrom(cDaylightDaten);

			status = GuKKiCalcStatus.GELESEN;
			if (!Restinformationen.equals("")) {
				logger.log(Level.INFO, this.toString() + " Restinformationen:\n" + "-->" + Restinformationen + "<--\n");
			}
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
		}

		@Override
		protected void verarbeitenZeile(String zeile) throws Exception {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}

			if (!zeile.equals("BEGIN:DAYLIGHT") & !zeile.equals("END:DAYLIGHT")) {
				if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTART")) {
					DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
				} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("TZOFFSETTO")) {
					TZOFFSETTO = new GuKKiCalProperty(zeile, "TZOFFSETTO");
				} else if (zeile.length() >= 12 && zeile.substring(0, 12).equals("TZOFFSETFROM")) {
					TZOFFSETFROM = new GuKKiCalProperty(zeile, "TZOFFSETFROM");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
					RRULE = new GuKKiCalProperty(zeile, "RRULE");
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
					COMMENT.add(new GuKKiCalProperty(zeile, "COMMENT"));
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
					RDATE.add(new GuKKiCalProperty(zeile, "RDATE"));
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("TZNAME")) {
					TZNAME.add(new GuKKiCalProperty(zeile, "TZNAME"));
				} else {
					Restinformationen += zeile + nz;
				}
			}
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
		}

		protected GuKKiCalcDaylight kopieren() {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}

			GuKKiCalcDaylight temp = new GuKKiCalcDaylight();
			temp.DTSTART = this.DTSTART.kopieren();
			temp.TZOFFSETTO = this.TZOFFSETTO.kopieren();
			temp.TZOFFSETFROM = this.TZOFFSETFROM.kopieren();
			temp.RRULE = this.RRULE.kopieren();
			for (GuKKiCalProperty property : this.COMMENT) {
				temp.COMMENT.add(property.kopieren());
			}
			for (GuKKiCalProperty property : this.RDATE) {
				temp.RDATE.add(property.kopieren());
			}
			for (GuKKiCalProperty property : this.TZNAME) {
				temp.TZNAME.add(property.kopieren());
			}
			temp.Restinformationen = this.Restinformationen;
			
			temp.status = GuKKiCalcStatus.KOPIERT;
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
			return temp;
		}
		@Override
		public boolean equals (Object dasAndere) {
			
			if (!dasAndere.getClass().equals(this.getClass())) 
			return false;
			
			return true;
		}
	}

	class GuKKiCalcStandard extends GuKKiCalComponent {
		/*
		 * The following are REQUIRED, but MUST NOT occur more than once.
		 */
		GuKKiCalProperty DTSTART = null;
		GuKKiCalProperty TZOFFSETTO = null;
		GuKKiCalProperty TZOFFSETFROM = null;
		/*
		 * The following is OPTIONAL, but SHOULD NOT occur more than once.
		 */
		GuKKiCalProperty RRULE = null;
		/*
		 * The following are OPTIONAL, and MAY occur more than once.
		 */
		private ArrayList<GuKKiCalProperty> COMMENT = new ArrayList<GuKKiCalProperty>();
		private ArrayList<GuKKiCalProperty> RDATE = new ArrayList<GuKKiCalProperty>();
		private ArrayList<GuKKiCalProperty> TZNAME = new ArrayList<GuKKiCalProperty>();
		/*
		 * Here are the x-prop and iana-prop are to be stored
		 */
		private String Restinformationen = "";

		private GuKKiCalcStandard() {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}
			kennung = GuKKiCalcKennung.STANDARD;
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
		}
		private GuKKiCalcStandard(String cStandardDaten) throws Exception {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}
			kennung = GuKKiCalcKennung.STANDARD;
			verarbeitenDatenstrom(cStandardDaten);

			if (!Restinformationen.equals("")) {
				logger.log(Level.INFO, this.toString() + " Restinformationen:\n" + "-->" + Restinformationen + "<--\n");
			}
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
		}

		@Override
		protected void verarbeitenZeile(String zeile) throws Exception {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}

			if (!zeile.equals("BEGIN:STANDARD") & !zeile.equals("END:STANDARD")) {
				if (zeile.length() >= 7 && zeile.substring(0, 7).equals("DTSTART")) {
					DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
				} else if (zeile.length() >= 10 && zeile.substring(0, 10).equals("TZOFFSETTO")) {
					TZOFFSETTO = new GuKKiCalProperty(zeile, "TZOFFSETTO");
				} else if (zeile.length() >= 12 && zeile.substring(0, 12).equals("TZOFFSETFROM")) {
					TZOFFSETFROM = new GuKKiCalProperty(zeile, "TZOFFSETFROM");
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RRULE")) {
					RRULE = new GuKKiCalProperty(zeile, "RRULE");
				} else if (zeile.length() >= 7 && zeile.substring(0, 7).equals("COMMENT")) {
					COMMENT.add(new GuKKiCalProperty(zeile, "COMMENT"));
				} else if (zeile.length() >= 5 && zeile.substring(0, 5).equals("RDATE")) {
					RDATE.add(new GuKKiCalProperty(zeile, "RDATE"));
				} else if (zeile.length() >= 6 && zeile.substring(0, 6).equals("TZNAME")) {
					TZNAME.add(new GuKKiCalProperty(zeile, "TZNAME"));
				} else {
					Restinformationen += zeile + nz;
				}
			}
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
		}
		protected GuKKiCalcStandard kopieren() {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}

			GuKKiCalcStandard temp = new GuKKiCalcStandard();
			temp.DTSTART = this.DTSTART.kopieren();
			temp.TZOFFSETTO = this.TZOFFSETTO.kopieren();
			temp.TZOFFSETFROM = this.TZOFFSETFROM.kopieren();
			temp.RRULE = this.RRULE.kopieren();
			for (GuKKiCalProperty property : this.COMMENT) {
				temp.COMMENT.add(property.kopieren());
			}
			for (GuKKiCalProperty property : this.RDATE) {
				temp.RDATE.add(property.kopieren());
			}
			for (GuKKiCalProperty property : this.TZNAME) {
				temp.TZNAME.add(property.kopieren());
			}
			temp.Restinformationen = this.Restinformationen;
			
			temp.status = GuKKiCalcStatus.KOPIERT;
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
			return temp;
		}
	}
}