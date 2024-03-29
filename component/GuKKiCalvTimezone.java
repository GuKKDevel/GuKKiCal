package component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import enumerations.*;
import exceptions.*;

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
 *	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-
 *	Modifications by RFC 7808 (March 2016) item 7.; p. 40
 *
 *		7. New iCalendar Properties 
 *		7.1. Time Zone Upper Bound
 *		7.2. Time Zone Identifier Alias Property

 * @formatter:on
 * 
 */

public class GuKKiCalvTimezone extends GuKKiCalvComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * The following is REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalcProperty TZID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalcProperty LAST_MOD = null;
	private GuKKiCalcProperty TZURL = null;
	private GuKKiCalcProperty TZUNTIL = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private List<GuKKiCalcProperty> TZIDALIASOFSammlung = new LinkedList<GuKKiCalcProperty>();
	/*
	 * One of ’standardc’ or ’daylightc’ MUST occur and each MAY occur more than
	 * once.
	 */
	private List<GuKKiCalvDaylight> vDaylightSammlung = new LinkedList<GuKKiCalvDaylight>();
	private List<GuKKiCalvStandard> vStandardSammlung = new LinkedList<GuKKiCalvStandard>();
	private List<GuKKiCalvComponent> vComponentSammlung = new LinkedList<GuKKiCalvComponent>();
	/*
	 * X-Name Properties
	 */
	private List<String> X_PROPSammlung = new LinkedList<String>();
	private List<String> Restinformationen = new LinkedList<String>();
	/*
	 * Zwischenspeicher für den Datenstrom für vDaylight und vStandard nebst
	 * Verarbeitungsschalter
	 */
	List<String> vDaylightDatenArray = new LinkedList<String>();
	List<String> vStandardDatenArray = new LinkedList<String>();
	boolean vDaylightDatenSammeln = false;
	boolean vStandardDatenSammeln = false;

	/**
	 * Erstellen einer leeren VTimezone-Komponente
	 */
	protected GuKKiCalvTimezone() {
		/*	@formatter:off */
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		this.kennung = GuKKiCalcKennung.TIMEZONE;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
		/*	@formatter:on */
	}

	protected void abschliessen(String pNAME) throws GuKKiCalException {
		String tempTZID = this.TZID == null ? "" : this.TZID.getWert();
		this.schluessel = new GuKKiCalcSchluessel(this.kennung, pNAME, tempTZID);
		for (Iterator iterator = vComponentSammlung.iterator(); iterator.hasNext();) {
			GuKKiCalvComponent vComponent = (GuKKiCalvComponent) iterator.next();
			switch (vComponent.kennung) {
				case DAYLIGHT:
					GuKKiCalvDaylight vDaylight = (GuKKiCalvDaylight) vComponent;
					vDaylight.abschliessen(pNAME, tempTZID);
					break;
				case STANDARD:
					GuKKiCalvStandard vStandard = (GuKKiCalvStandard) vComponent;
					vStandard.abschliessen(pNAME, tempTZID);
					break;
			}
		}
		status = GuKKiCalcStatus.GELESEN;
	}

	/**
	 * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten)
	 * Zeilen untersucht und die jeweilige Eigenschaft wird abgespeichert
	 * Version V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22
	 */
	protected void neueZeile(String zeile) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (bearbeiteSubKomponente) {
			if (vDaylightBearbeiten) {
				if (zeile.equals("END:DAYLIGHT")) {
//					vDaylightNeu.abschliessen();
					vDaylightSammlung.add(vDaylightNeu);
					vComponentSammlung.add(vDaylightNeu);
					vDaylightBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vDaylightNeu.neueZeile(zeile);
				}
			} else if (vStandardBearbeiten) {
				if (zeile.equals("END:STANDARD")) {
//					vStandardNeu.abschliessen();
					vStandardSammlung.add(vStandardNeu);
					vComponentSammlung.add(vStandardNeu);
					vStandardBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vStandardNeu.neueZeile(zeile);
				}
			}
		} else {
			if (zeile.equals("BEGIN:DAYLIGHT")) {
				vDaylightNeu = new GuKKiCalvDaylight();
				vDaylightBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.equals("BEGIN:STANDARD")) {
				vStandardNeu = new GuKKiCalvStandard();
				vStandardBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				LAST_MOD = new GuKKiCalcProperty(zeile, "LAST-MODIFIED");
			} else if (zeile.length() > 4 && zeile.substring(0, 4).equals("TZID")) {
				TZID = new GuKKiCalcProperty(zeile, "TZID");
			} else if (zeile.length() > 13 && zeile.substring(0, 13).equals("TZID-ALIAS-OF")) {
				TZIDALIASOFSammlung.add(new GuKKiCalcProperty(zeile, "TZID-ALIAS-OF"));
			} else if (zeile.length() > 4 && zeile.substring(0, 4).equals("TZUNTIL")) {
				TZUNTIL = new GuKKiCalcProperty(zeile, "TZUNTIL");
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("TZURL")) {
				TZURL = new GuKKiCalcProperty(zeile, "TZURL");

				/* Abschluss und Fallbackparameter */

			} else if (zeile.length() > 2 && zeile.substring(0, 2).equals("X-")) {
				X_PROPSammlung.add(zeile);
			} else {
				Restinformationen.add(zeile);
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende neueZeile V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Diese Methode kopiert die iCalendar-Komponente GuKKiCalvTimezone und gibt
	 * diese Kopie zurück Version V 0.0.3 (RFC 5545, RFC 7968)
	 * 2021-12-22T15-12-22
	 */
	protected GuKKiCalvTimezone kopieren() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		GuKKiCalvTimezone temp = new GuKKiCalvTimezone();
		temp.kennung = this.kennung;
		temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
		temp.TZID = this.TZID == null ? null : this.TZID.kopieren();
		for (GuKKiCalcProperty TZIDALIASOF : TZIDALIASOFSammlung) {
			temp.TZIDALIASOFSammlung.add(TZIDALIASOF.kopieren());
		}
		temp.TZUNTIL = this.TZUNTIL == null ? null : this.TZUNTIL.kopieren();
		temp.TZURL = this.TZURL == null ? null : this.TZURL.kopieren();
		for (GuKKiCalvDaylight vDaylight : this.vDaylightSammlung) {
			temp.vDaylightSammlung.add(vDaylight.kopieren());
		}
		for (GuKKiCalvStandard vStandard : this.vStandardSammlung) {
			temp.vStandardSammlung.add(vStandard.kopieren());
		}

		/* Abschluss und Fallbackparameter */

		for (String X_PROP : this.X_PROPSammlung) {
			temp.X_PROPSammlung.add(X_PROP);
		}
		for (String Restinformation : this.Restinformationen) {
			temp.Restinformationen.add(Restinformation);
		}
		temp.status = GuKKiCalcStatus.KOPIERT;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp;
	} // Ende kopieren V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Vergleichen aller Attribute der Komponente GuKKiCalvTimezone Version V
	 * 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22
	 *
	 * @return boolean
	 */
	protected boolean istGleich(Object dasAndere) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (!dasAndere.getClass().equals(this.getClass())) {
			return false;
		}
		GuKKiCalvTimezone temp = (GuKKiCalvTimezone) dasAndere;
		if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
				|| (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
			return false;
		}
		if (!((temp.TZID == null && this.TZID == null)
				|| (temp.TZID != null && this.TZID != null && temp.TZID.istGleich(this.TZID)))) {
			return false;
		}
		if (temp.TZIDALIASOFSammlung.size() != this.TZIDALIASOFSammlung.size()) {
			return false;
		}
		for (int i = 0; i < TZIDALIASOFSammlung.size(); i++) {
			if (!temp.TZIDALIASOFSammlung.get(i).istGleich(this.TZIDALIASOFSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.TZUNTIL == null && this.TZUNTIL == null)
				|| (temp.TZUNTIL != null && this.TZUNTIL != null && temp.TZUNTIL.istGleich(this.TZUNTIL)))) {
			return false;
		}
		if (!((temp.TZURL == null && this.TZURL == null)
				|| (temp.TZURL != null && this.TZURL != null && temp.TZURL.istGleich(this.TZURL)))) {
			return false;
		}
		if (temp.vDaylightSammlung.size() != this.vDaylightSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vDaylightSammlung.size(); i++) {
			if (!temp.vDaylightSammlung.get(i).istGleich(this.vDaylightSammlung.get(i))) {
				return false;
			}
		}
		if (temp.vStandardSammlung.size() != this.vStandardSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vStandardSammlung.size(); i++) {
			if (!temp.vStandardSammlung.get(i).istGleich(this.vStandardSammlung.get(i))) {
				return false;
			}
		}

		/* Abschluss und Fallbackparameter */

		if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
			return false;
		}
		for (int i = 0; i < X_PROPSammlung.size(); i++) {
			if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
				return false;
			}
		}
		if (temp.Restinformationen.size() != this.Restinformationen.size()) {
			return false;
		}
		for (int i = 0; i < Restinformationen.size(); i++) {
			if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {
				return false;
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return true;
	} // Ende istGleich V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Mit dieser Methode werden die einzelnen Eigenschaften als gültige
	 * Parameterkette ausgegeben Version V 0.0.3 (RFC 5545, RFC 7968)
	 * 2021-12-22T15-12-22
	 */
	protected String ausgeben() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VTIMEZONE");
		componentDatenstrom += this.LAST_MOD == null ? "" : ausgebenInDatenstrom(this.LAST_MOD.ausgeben());
		componentDatenstrom += this.TZID == null ? "" : ausgebenInDatenstrom(this.TZID.ausgeben());
		for (GuKKiCalcProperty TZIDALIASOF : TZIDALIASOFSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(TZIDALIASOF.ausgeben());
		}
		componentDatenstrom += this.TZUNTIL == null ? "" : ausgebenInDatenstrom(this.TZUNTIL.ausgeben());
		componentDatenstrom += this.TZURL == null ? "" : ausgebenInDatenstrom(this.TZURL.ausgeben());
		for (GuKKiCalvDaylight vDaylight : this.vDaylightSammlung) {
			componentDatenstrom += vDaylight.ausgeben();
		}
		for (GuKKiCalvStandard vStandard : this.vStandardSammlung) {
			componentDatenstrom += vStandard.ausgeben();
		}

		/* Abschluss und Fallbackparameter */

		for (String X_PROP : this.X_PROPSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(X_PROP);
		}
		for (String Restinformation : this.Restinformationen) {
			componentDatenstrom += ausgebenInDatenstrom(Restinformation);
		}
		componentDatenstrom += ausgebenInDatenstrom("END:VTIMEZONE");
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return componentDatenstrom;
	} // Ende ausgeben V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Gibt Identifikationsdaten der VTIMEZONE-Komponente aus
	 */
	public String toString() {
		return this.schluessel.toString();
	}

	/**
	 * Gibt einige Daten der VTIMEZONE-Komponente aus
	 */
	public String toString(String ausgabeLevel) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String ausgabeString = "Timezone-Identifikation=" + this.toString()+nz;
		if (ausgabeLevel.toUpperCase().indexOf("D") >= 0) {
			for (GuKKiCalvDaylight vDaylight : vDaylightSammlung) {
				ausgabeString += vDaylight.toString(ausgabeLevel);
			}
		}
		if (ausgabeLevel.toUpperCase().indexOf("S") >= 0) {
			for (GuKKiCalvStandard vStandard : vStandardSammlung) {
				ausgabeString += vStandard.toString(ausgabeLevel);
			}
		}
		return ausgabeString;
	}

}