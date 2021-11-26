package main;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuKKiCalComponent {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * Standardvariablen für alle Komponenten
	 */
	GuKKiCalcKennung kennung = GuKKiCalcKennung.UNDEFINIERT;
	GuKKiCalcStatus status = GuKKiCalcStatus.UNDEFINIERT;
	String schluessel = "";

	/*
	 * Standard-Variablen (Konstanten)
	 */
	String nz = "\n";

	/**
	 * Konstruktor
	 */
	public GuKKiCalComponent() {
		logger.log(logLevel, "begonnen\n");
		logger.log(logLevel, "beendet\n");
		// TODO Automatisch generierter Konstruktorstub
	}

	/**
	 * 
	 * Generelle Bearbeitungsfunktionen
	 * 
	 */
	protected void verarbeitenDatenstrom(String vComponentDaten) throws Exception {
		logger.log(logLevel, "begonnen\n");

		String zeile = "";
		String folgezeile = "";
		boolean datenVorhanden = true;

		try {
//			System.out.println(vComponentDaten);
			BufferedReader vComponentDatenstrom = new BufferedReader(new StringReader(vComponentDaten));
			zeile = vComponentDatenstrom.readLine();
//			System.out.println("-->" + zeile + "<--");
			if (zeile == null) {
				datenVorhanden = false;
			}
			while (datenVorhanden) {
				folgezeile = vComponentDatenstrom.readLine();
//				System.out.println("-->" + folgezeile + "<--");

				if (folgezeile == null) {
					verarbeitenZeile(zeile);
					datenVorhanden = false;
				} else {
					if (folgezeile.length() > 0) {
						if (folgezeile.substring(0, 1).equals(" ")) {
							zeile = zeile.substring(0, zeile.length()) + folgezeile.substring(1);
						} else {
							verarbeitenZeile(zeile);
							zeile = folgezeile;
						}
					}
				}
			} /* end while-Schleife */
		} finally {
		}

		logger.log(logLevel, "beendet\n");
	}

	protected void verarbeitenZeile(String zeile) throws Exception {
		System.out.println("GuKKiCalComponent verarbeitenZeile-->" + zeile + "<--");
	}

	String eintragenProperty(String zeichenkette, String kennung) {
		return zeichenkette.substring(kennung.length());
	}

	String auslesenProperty(String zeichenkette, String kennung) {
		return kennung + zeichenkette;
	}

	/**
	 * Bearbeitungsroutinen für die property XXX
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.1; p. 80
	 *
	 *
	 *
	 *
	 * @formatter:on
	 * 
	 */
	String checkXXX(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property Attach
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.1; p. 80
	 *
	 * 	Property Name: ATTACH
	 * 
	 * 	Purpose: This property provides the capability to associate a
	 * 		document object with a calendar component.
	 * 
	 * 	Value Type: The default value type for this property is URI. The
	 * 		value type can also be set to BINARY to indicate inline binary
	 * 		encoded content information.
	 * 
	 * 	Property Parameters: IANA, non-standard, inline encoding, and value
	 * 		data type property parameters can be specified on this property.
	 * 		The format type parameter can be specified on this property and is
	 * 		RECOMMENDED for inline binary encoded content information.
	 * 
	 * 	Conformance: This property can be specified multiple times in a
	 * 		"VEVENT", "VTODO", "VJOURNAL", or "VALARM" calendar component with
	 * 		the exception of AUDIO alarm that only allows this property to
	 * 		occur once.
	 * 
	 * 	Description: This property is used in "VEVENT", "VTODO", and
	 * 		"VJOURNAL" calendar components to associate a resource (e.g.,
	 * 		document) with the calendar component. This property is used in
	 * 		"VALARM" calendar components to specify an audio sound resource or
	 * 		an email message attachment. This property can be specified as a
	 * 		URI pointing to a resource or as inline binary encoded content.
	 * 		When this property is specified as inline binary encoded content,
	 * 		calendar applications MAY attempt to guess the media type of the
	 * 		resource via inspection of its content if and only if the media
	 * 		type of the resource is not given by the "FMTTYPE" parameter. If
	 * 		the media type remains unknown, calendar applications SHOULD treat
	 * 		it as type "application/octet-stream".
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		attach = "ATTACH" attachparam ( ":" uri ) / 
	 * 			(
	 * 			";" "ENCODING" "=" "BASE64"
	 * 			";" "VALUE" "=" "BINARY"
	 * 			":" binary
	 * 			) CRLF
	 * 
	 * 			attachparam = *(
	 * 
	 *  	The following is OPTIONAL for a URI value, RECOMMENDED for a BINARY value, 
	 *  	and MUST NOT occur more than once.
	 *  
	 *  			(";" fmttypeparam) /
	 *  
	 *  	The following is OPTIONAL, and MAY occur more than once.
	 *		
	 *				(";" other-param)
	 *
	 *				)
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkATTACH(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property Attendee
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.4.1.; p. 107
	 *
	 *	Property Name: ATTENDEE
	 *
	 *	Purpose: This property defines an "Attendee" within a calendar component.
	 *
	 *	Value Type: CAL-ADDRESS
	 *
	 *	Property Parameters: IANA, non-standard, language, calendar user
	 *		type, group or list membership, participation role, participation
	 *		status, RSVP expectation, delegatee, delegator, sent by, common
	 *		name, or directory entry reference property parameters can be
	 *		specified on this property.
	 *
	 *	Conformance: This property MUST be specified in an iCalendar object
	 *		that specifies a group-scheduled calendar entity. This property
	 *		MUST NOT be specified in an iCalendar object when publishing the
	 *		calendar information (e.g., NOT in an iCalendar object that
	 *		specifies the publication of a calendar user’s busy time, event,
	 *		to-do, or journal). This property is not specified in an
	 *		iCalendar object that specifies only a time zone definition or
	 *		that defines calendar components that are not group-scheduled
	 *		components, but are components only on a single user’s calendar.
	 *	
	 *	Description: This property MUST only be specified within calendar
	 *		components to specify participants, non-participants, and the
	 *		chair of a group-scheduled calendar entity. The property is
	 *		specified within an "EMAIL" category of the "VALARM" calendar
	 *		component to specify an email address that is to receive the email
	 *		type of iCalendar alarm.
	 *		The property parameter "CN" is for the common or displayable name
	 *		associated with the calendar address; "ROLE", for the intended
	 *		role that the attendee will have in the calendar component;
	 *		"PARTSTAT", for the status of the attendee’s participation;
	 *		"RSVP", for indicating whether the favor of a reply is requested;
	 *		"CUTYPE", to indicate the type of calendar user; 
	 *		"MEMBER", to indicate the groups that the attendee belongs to; 
	 *		"DELEGATED-TO",to indicate the calendar users that the original request was
	 *			delegated to; and 
	 *		"DELEGATED-FROM", to indicate whom the request was delegated from; 
	 *		"SENT-BY", to indicate whom is acting on behalf of the "ATTENDEE"; and 
	 *		"DIR", to indicate the URI that points to the directory information 
	 *			corresponding to the attendee.
	 *		These property parameters can be specified on an "ATTENDEE"
	 *		property in either a "VEVENT", "VTODO", or "VJOURNAL" calendar
	 *		component. They MUST NOT be specified in an "ATTENDEE" property
	 *		in a "VFREEBUSY" or "VALARM" calendar component. 
	 *		If the "LANGUAGE" property parameter is specified, the identified
	 *		language applies to the "CN" parameter.
	 *
	 *		A recipient delegated a request MUST inherit the "RSVP" and "ROLE"
	 *		values from the attendee that delegated the request to them.
	 *
	 *		Multiple attendees can be specified by including multiple 
	 *		"ATTENDEE" properties within the calendar component.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		attendee = "ATTENDEE" attparam ":" cal-address CRLF
	 *
	 *			attparam = *(
	 *
	 *		The following are OPTIONAL, but MUST NOT occur more than once.
	 *				(";" cutypeparam) / 
	 *				(";" memberparam) /
	 *				(";" roleparam) / 
	 *				(";" partstatparam) /
	 *				(";" rsvpparam) / 
	 *				(";" deltoparam) /
	 *				(";" delfromparam) / 
	 *				(";" sentbyparam) /
	 *				(";" cnparam) / 
	 *				(";" dirparam) /
	 *				(";" languageparam) /
	 *
	 *		The following is OPTIONAL, and MAY occur more than once.
	 *
	 *				*(";" other-param)
	 *				
	 *				)
	 *
	 * @formatter:on
	 * 
	 */
	String checkATTENDEE(GuKKiCalProperty property) {
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "begonnen");
			}
			String kennung = property.getLiteral();
			String zTemp = "";
			if (logger.isLoggable(logLevel)) {
				logger.log(logLevel, "beendet");
			}
			return zTemp;
		}

	/**
	 * Bearbeitungsroutinen für die property Calendar Scale
	 * 
	 * RFC 5545 (september 2009) item 3.7.1.; p. 76 
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: CALSCALE
	 * 
	 * 	Purpose: This property defines the calendar scale used for the
	 * 		calendar information specified in the iCalendar object.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar
	 * 		object. The default value is "GREGORIAN".
	 * 
	 * 	Description: This memo is based on the Gregorian calendar scale.
	 * 		The Gregorian calendar scale is assumed if this property is not
	 * 		specified in the iCalendar object. It is expected that other
	 * 		calendar scales will be defined in other specifications or by
	 * 		future versions of this memo.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		calscale = "CALSCALE" calparam ":" calvalue CRLF
	 * 
	 * 			calparam = *(";" other-param)
	 * 
	 * 			calvalue = "GREGORIAN"
	 * 
	 * @formatter:on
	 * 
	 */
	String checkCALSCALE(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property CATEGORIES
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.2; p. 80
	 *
	 * 	Property Name: CATEGORIES
	 * 
	 * 	Purpose: This property defines the categories for a calendar component.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA, non-standard, and language property
	 * 	parameters can be specified on this property.
	 * 
	 * 	Conformance: The property can be specified within "VEVENT", "VTODO",
	 * 		or "VJOURNAL" calendar components.
	 * 
	 * 	Description: This property is used to specify categories or subtypes
	 * 		of the calendar component. The categories are useful in searching
	 * 		for a calendar component of a particular type and category.
	 * 		Within the "VEVENT", "VTODO", or "VJOURNAL" calendar components,
	 * 		more than one category can be specified as a COMMA-separated list
	 * 		of categories.
	 * 	
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		categories = "CATEGORIES" catparam ":" text *("," text) CRLF
	 * 
	 * 			catparam = *(
	 * 
	 * 	The following is OPTIONAL, but MUST NOT occur more than once.
	 * 
	 * 				(";" languageparam ) /
	 * 
	 * 	The following is OPTIONAL, and MAY occur more than once.
	 * 				
	 * 				(";" other-param)
	 *
	 * 			)
	 * 
	 *	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
	 * 
	 * RFC 7986 (October 2016) item 5.6.; p. 8 
	 * 
	 *  CATEGORIES
	 *  
	 * 	This specification modifies the definition of the "CATEGORIES"
	 * 		property to allow it to be defined in an iCalendar object. The
	 * 		following additions are made to the definition of this property,
	 * 		originally specified in Section 3.8.1.2 of [RFC5545].
	 * 
	 * 	Purpose: This property defines the categories for an entire calendar.
	 * 
	 * 	Conformance: This property can be specified multiple times in an iCalendar object.
	 * 
	 * 	Description: When multiple properties are present, the set of
	 * 		categories that apply to the iCalendar object are the union of all
	 * 		the categories listed in each property value.
	 *
	 * @formatter:on
	 * 
	 */
	String checkCATEGORIES(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property CLASS
	 * 
	 * @formatter:off
	 * 
	 *	RFC 5545 (september 2009) item 3.8.1.3; p. 82
	 * 
	 *  Property Name: CLASS
	 * 
	 *  Purpose: This property defines the access classification for a calendar component.
	 * 
	 *  Value Type: TEXT
	 * 
	 *  Property Parameters: IANA and non-standard property parameters can be 
	 * 		specified on this property.
	 * 
	 *  Conformance: The property can be specified once in a "VEVENT", "VTODO", 
	 * 		or "VJOURNAL" calendar components.
	 * 
	 *  Description: An access classification is only one component of the 
	 * 		general security system within a calendar application. It
	 * 		provides a method of capturing the scope of the access the
	 * 		calendar owner intends for information within an individual
	 * 		calendar entry. The access classification of an individual
	 * 		iCalendar component is useful when measured along with the other
	 * 		security components of a calendar system (e.g., calendar user
	 * 		authentication, authorization, access rights, access role, etc.).
	 * 		Hence, the semantics of the individual access classifications
	 * 		cannot be completely defined by this memo alone. Additionally,
	 * 		due to the "blind" nature of most exchange processes using this 
	 * 		memo, these access classifications cannot serve as an enforcement
	 * 		statement for a system receiving an iCalendar object. Rather,
	 * 		they provide a method for capturing the intention of the calendar
	 * 		owner for the access to the calendar component. If not specified
	 * 		in a component that allows this property, the default value is
	 * 		PUBLIC. Applications MUST treat x-name and iana-token values they
	 * 		don’t recognize the same way as they would the PRIVATE value.
	 * 
	 *  Format Definition: This property is defined by the following notation:
	 * 
	 * 		class = "CLASS" classparam ":" classvalue CRLF
	 * 		
	 * 			classparam = *(";" other-param)
	 * 
	 * 			classvalue = "PUBLIC" / "PRIVATE" / "CONFIDENTIAL" / iana-token
	 * 					/ x-name
	 * 
	 * 		Default is PUBLIC
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkCLASS(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property COLOR
	 * 
	 * RFC 7986 (October 2016) item 5.9.; p. 10 
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: COLOR
	 * 	
	 * 	Purpose: This property specifies a color used for displaying the
	 * 		calendar, event, todo, or journal data.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 	
	 * 	Conformance: This property can be specified once in an iCalendar
	 * 		object or in "VEVENT", "VTODO", or "VJOURNAL" calendar components.
	 * 
	 * 	Description: This property specifies a color that clients MAY use
	 * 		when presenting the relevant data to a user. Typically, this
	 * 		would appear as the "background" color of events or tasks. The
	 * 		value is a case-insensitive color name taken from the CSS3 set of
	 * 		names, defined in Section 4.3 of [W3C.REC-css3-color-20110607].
	 * 		
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		color = "COLOR" colorparam ":" text CRLF
	 * 
	 * 		colorparam = *(";" other-param)
	 * 
	 * 		text = Value is CSS3 color name
	 * 
	 * @formatter:on
	 * 
	 */
	String checkCOLOR(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property CREATED
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.2.1; p. 94
	 * 
	 * 	Property Name: COMPLETED
	 * 
	 * 	Purpose: This property defines the date and time that a to-do was
	 *		actually completed.
	 *
	 *	Value Type: DATE-TIME
	 *
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: The property can be specified in a "VTODO" calendar
	 *		component. The value MUST be specified as a date with UTC time.
	 *
	 *	Description: This property defines the date and time that a to-do
	 *		was actually completed.
	 *	
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		completed = "COMPLETED" compparam ":" date-time CRLF
	 *
	 *			compparam = *(";" other-param)
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkCOMPLETED(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property CREATED
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.7.3; p. 138
	 * 
	 *	Property Name: CREATED
	 *
	 *	Purpose: This property specifies the date and time that the calendar
	 *		information was created by the calendar user agent in the calendar
	 *		store.
	 *
	 *	Note: This is analogous to the creation date and time for a
	 *		file in the file system.
	 *
	 *	Value Type: DATE-TIME
	 *
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: The property can be specified once in "VEVENT",
	 *		"VTODO", or "VJOURNAL" calendar components. The value MUST be
	 *		specified as a date with UTC time.
	 *	
	 *	Description: This property specifies the date and time that the
	 *		calendar information was created by the calendar user agent in the
	 *		calendar store.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		created = "CREATED" creaparam ":" date-time CRLF
	 *
	 *			creaparam = *(";" other-param)
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkCREATED(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DESCRIPTION
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.5; p. 84
	 * 
	 *	Property Name: DESCRIPTION
	 *
	 *	Purpose: This property provides a more complete description of the
	 *		calendar component than that provided by the "SUMMARY" property.
	 *
	 *	Value Type: TEXT
	 *
	 *	Property Parameters: IANA, non-standard, alternate text
	 *		representation, and language property parameters can be specified
	 *		on this property.
	 *
	 *	Conformance: The property can be specified in the "VEVENT", "VTODO",
	 *		"VJOURNAL", or "VALARM" calendar components. The property can be
	 *		specified multiple times only within a "VJOURNAL" calendar
	 *		component.
	 *
	 *	Description: This property is used in the "VEVENT" and "VTODO" to
	 *		capture lengthy textual descriptions associated with the activity.
	 *		This property is used in the "VJOURNAL" calendar component to
	 *		capture one or more textual journal entries.
	 *		This property is used in the "VALARM" calendar component to
	 *		capture the display text for a DISPLAY category of alarm, and to
	 *		capture the body text for an EMAIL category of alarm.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		description = "DESCRIPTION" descparam ":" text CRLF
	 *		
	 *			descparam = *(
	 *
	 *		The following are OPTIONAL, but MUST NOT occur more than once.
	 *
	 *				(";" altrepparam) / (";" languageparam) /
	 *
	 *		The following is OPTIONAL, and MAY occur more than once.
	 *
	 *				(";" other-param)
	 *
	 *				)
	 *
	 *	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -		
	 *
	 * RFC 7986 (October 2016) item 5.2.; p. 6 
	 * 
	 * 	DESCRIPTION 
	 * 
	 * 	This specification modifies the definition of the "DESCRIPTION"
	 * 		property to allow it to be defined in an iCalendar object. The
	 * 		following additions are made to the definition of this property,
	 * 		originally specified in Section 3.8.1.5 of [RFC5545].
	 * 
	 * 	Purpose: This property specifies the description of the calendar.
	 * 
	 * 	Conformance: This property can be specified multiple times in an
	 * 		iCalendar object. However, each property MUST represent the
	 * 		description of the calendar in a different language.
	 * 	
	 * 	Description: This property is used to specify a lengthy textual
	 * 		description of the iCalendar object that can be used by calendar
	 * 		user agents when describing the nature of the calendar data to a
	 * 		user. Whilst a calendar only has a single description, multiple
	 * 		language variants can be specified by including this property
	 * 		multiple times with different "LANGUAGE" parameter values on each.
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkDESCRIPTION(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DTEND
	 *
	 * @formatter:off
	 * 
	 *  RFC 5545 (september 2009) item 3.8.2.2; p. 95
	 *  
	 *	Property Name: DTEND
	 *
	 *	Purpose: This property specifies the date and time that a calendar
	 *		component ends.
	 *
	 *	Value Type: The default value type is DATE-TIME.
	 *		 The value type can be set to a DATE value type.
	 *
	 *	Property Parameters: IANA, non-standard, value data type, and time
	 *		zone identifier property parameters can be specified on this
	 *		property.
	 *
	 *	Conformance: This property can be specified in "VEVENT" or
	 *		"VFREEBUSY" calendar components.
	 *
	 *	Description: Within the "VEVENT" calendar component, this property
	 *		defines the date and time by which the event ends. The value type
	 *		of this property MUST be the same as the "DTSTART" property, and
	 *		its value MUST be later in time than the value of the "DTSTART"
	 *		property. Furthermore, this property MUST be specified as a date
	 *		with local time if and only if the "DTSTART" property is also
	 *		specified as a date with local time.
	 *		Within the "VFREEBUSY" calendar component, this property defines
	 *		the end date and time for the free or busy time information. The
	 *		time MUST be specified in the UTC time format. The value MUST be
	 *		later in time than the value of the "DTSTART" property.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		dtend = "DTEND" dtendparam ":" dtendval CRLF
	 *
	 *			dtendparam = *(
	 *
	 *		The following are OPTIONAL, but MUST NOT occur more than once.
	 *
	 *				(";" "VALUE" "=" ("DATE-TIME" / "DATE")) /
	 *				(";" tzidparam) /
	 *
	 *		The following is OPTIONAL, and MAY occur more than once.
	 *
	 *				(";" other-param)
	 *				)
	 *
	 *			dtendval = date-time / date
	 *
	 *		Value MUST match value type
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkDTEND(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DTSTAMP
	 *
	 * @formatter:off
	 * 
	 *	RFC 5545 (september 2009) item 3.8.7.2; p. 137
	 *
	 *	Property Name: DTSTAMP
	 *
	 *	Purpose: In the case of an iCalendar object that specifies a
	 *		"METHOD" property, this property specifies the date and time that
	 *		the instance of the iCalendar object was created. In the case of
	 *		an iCalendar object that doesn’t specify a "METHOD" property, this
	 *		property specifies the date and time that the information
	 *		associated with the calendar component was last revised in the
	 *		calendar store.
	 *
	 *	Value Type: DATE-TIME
	 *
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: This property MUST be included in the "VEVENT",
	 *		"VTODO", "VJOURNAL", or "VFREEBUSY" calendar components.
	 *
	 *	Description: The value MUST be specified in the UTC time format.
	 *		This property is also useful to protocols such as [2447bis] that
	 *		have inherent latency issues with the delivery of content. This
	 *		property will assist in the proper sequencing of messages
	 *		containing iCalendar objects.
	 *		In the case of an iCalendar object that specifies a "METHOD"
	 *		property, this property differs from the "CREATED" and "LAST-
	 *		MODIFIED" properties. These two properties are used to specify
	 *		when the particular calendar data in the calendar store was
	 *		created and last modified. This is different than when the
	 *		iCalendar object representation of the calendar service
	 *		information was created or last modified.
	 *		In the case of an iCalendar object that doesn’t specify a "METHOD"
	 *		property, this property is equivalent to the "LAST-MODIFIED" property.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		dtstamp = "DTSTAMP" stmparam ":" date-time CRLF
	 *
	 *			stmparam = *(";" other-param)
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkDTSTAMP(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DTSTART
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.2.4; p. 97
	 * 
	 * 	Property Name: DTSTART
	 * 
	 * 	Purpose: This property specifies when the calendar component begins.
	 * 		
	 * 	Value Type: The default value type is DATE-TIME. The time value
	 * 		MUST be one of the forms defined for the DATE-TIME value type.
	 * 		The value type can be set to a DATE value type.
	 * 	
	 * 	Property Parameters: IANA, non-standard, value data type, and time
	 * 		zone identifier property parameters can be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in the "VEVENT",
	 * 		"VTODO", or "VFREEBUSY" calendar components as well as in the
	 * 		"STANDARD" and "DAYLIGHT" sub-components. This property is
	 * 		REQUIRED in all types of recurring calendar components that
	 * 		specify the "RRULE" property. This property is also REQUIRED in
	 * 		"VEVENT" calendar components contained in iCalendar objects that
	 * 		don’t specify the "METHOD" property.
	 * 
	 * 	Description: Within the "VEVENT" calendar component, this property
	 * 		defines the start date and time for the event.
	 * 		Within the "VFREEBUSY" calendar component, this property defines
	 * 		the start date and time for the free or busy time information.
	 * 		The time MUST be specified in UTC time.
	 * 		Within the "STANDARD" and "DAYLIGHT" sub-components, this property
	 * 		defines the effective start date and time for a time zone
	 * 		specification. This property is REQUIRED within each "STANDARD"
	 * 		and "DAYLIGHT" sub-components included in "VTIMEZONE" calendar
	 * 		components and MUST be specified as a date with local time without
	 * 		the "TZID" property parameter.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		dtstart = "DTSTART" dtstparam ":" dtstval CRLF
	 * 
	 * 			dtstparam = *(
	 * 
	 * 		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 				
	 * 				(";" "VALUE" "=" ("DATE-TIME" / "DATE")) /
	 * 				(";" tzidparam) /
	 * 
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 				(";" other-param)
	 * 				)
	 * 
	 * 			dtstval = date-time / date
	 * 
	 * 		Value MUST match value type
	 *  
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkDTSTART(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DUE
	 * 
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.2.3; p. 96
	 * 
	 * 	Property Name: DUE
	 * 
	 * 	Purpose: This property defines the date and time that a to-do is
	 * 		expected to be completed.
	 * 
	 * 	Value Type: The default value type is DATE-TIME.
	 * 		The value type can be set to a DATE value type.
	 * 
	 * 	Property Parameters: IANA, non-standard, value data type, and time
	 * 		zone identifier property parameters can be specified on this property.
	 * 
	 * 	Conformance: The property can be specified once in a "VTODO" calendar component.
	 * 
	 * 	Description: This property defines the date and time before which a
	 * 		to-do is expected to be completed. For cases where this property
	 * 		is specified in a "VTODO" calendar component that also specifies a
	 * 		"DTSTART" property, the value type of this property MUST be the
	 * 		same as the "DTSTART" property, and the value of this property
	 * 		MUST be later in time than the value of the "DTSTART" property.
	 * 		Furthermore, this property MUST be specified as a date with local
	 * 		time if and only if the "DTSTART" property is also specified as a
	 * 		date with local time.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		due = "DUE" dueparam ":" dueval CRLF
	 * 
	 * 			dueparam = *(
	 * 
	 * 		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 				(";" "VALUE" "=" ("DATE-TIME" / "DATE")) /
	 * 				(";" tzidparam) /
	 * 
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 				(";" other-param)
	 * 
	 * 			)
	 * 
	 * 			dueval = date-time / date 
	 * 
	 * 		Value MUST match value type

	 *  
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkDUE(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property DURATION
	 *
	 * @formatter:off
	 * 
	 *  RFC 5545 (september 2009) item 3.8.2.5; p. 99
	 *  
	 *	
	 *	Property Name: DURATION
	 *
	 *	Purpose: This property specifies a positive duration of time.
	 *
	 *	Value Type: DURATION
	 *
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: This property can be specified in "VEVENT", "VTODO", or
	 *		"VALARM" calendar components.
	 *
	 *	Description: In a "VEVENT" calendar component the property may be
	 *		used to specify a duration of the event, instead of an explicit
	 *		end DATE-TIME. In a "VTODO" calendar component the property may
	 *		be used to specify a duration for the to-do, instead of an
	 *		explicit due DATE-TIME. In a "VALARM" calendar component the
	 *		property may be used to specify the delay period prior to
	 *		repeating an alarm. When the "DURATION" property relates to a
	 *		"DTSTART" property that is specified as a DATE value, then the
	 *		"DURATION" property MUST be specified as a "dur-day" or "dur-week"
	 *		value.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		duration = "DURATION" durparam ":" dur-value CRLF
	 *
	 *		consisting of a positive duration of time.
	 *	
	 *			durparam = *(";" other-param)
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkDURATION(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property GEO
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.6; p. 85
	 * 
	 * 	Property Name: GEO
	 * 
	 * 	Purpose: This property specifies information related to the global
	 * 		position for the activity specified by a calendar component.
	 * 
	 * 	Value Type: FLOAT. The value MUST be two SEMICOLON-separated FLOAT values.
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified in "VEVENT" or "VTODO"
	 * 		calendar components.
	 * 
	 * 	Description: This property value specifies latitude and longitude,
	 * 		in that order (i.e., "LAT LON" ordering). The longitude
	 * 		represents the location east or west of the prime meridian as a
	 * 		positive or negative real number, respectively. The longitude and
	 * 		latitude values MAY be specified up to six decimal places, which
	 * 		will allow for accuracy to within one meter of geographical
	 * 		position. Receiving applications MUST accept values of this
	 * 		precision and MAY truncate values of greater precision.
	 * 		Values for latitude and longitude shall be expressed as decimal
	 * 		fractions of degrees. Whole degrees of latitude shall be
	 * 		represented by a two-digit decimal number ranging from 0 through
	 * 		90. Whole degrees of longitude shall be represented by a decimal
	 * 		number ranging from 0 through 180. When a decimal fraction of a
	 * 		degree is specified, it shall be separated from the whole number
	 * 		of degrees by a decimal point.
	 * 		Latitudes north of the equator shall be specified by a plus sign
	 * 		(+), or by the absence of a minus sign (-), preceding the digits
	 * 		designating degrees. Latitudes south of the Equator shall be
	 * 		designated by a minus sign (-) preceding the digits designating
	 * 		degrees. A point on the Equator shall be assigned to the Northern
	 * 		Hemisphere.
	 * 		Longitudes east of the prime meridian shall be specified by a plus
	 * 		sign (+), or by the absence of a minus sign (-), preceding the
	 * 		digits designating degrees. Longitudes west of the meridian shall
	 * 		be designated by minus sign (-) preceding the digits designating
	 * 		degrees. A point on the prime meridian shall be assigned to the
	 * 		Eastern Hemisphere. A point on the 180th meridian shall be
	 * 		assigned to the Western Hemisphere. One exception to this last
	 * 		convention is permitted. For the special condition of describing
	 * 		a band of latitude around the earth, the East Bounding Coordinate
	 * 		data element shall be assigned the value +180 (180) degrees.
	 * 		Any spatial address with a latitude of +90 (90) or -90 degrees
	 * 		will specify the position at the North or South Pole,
	 * 		respectively. The component for longitude may have any legal
	 * 		value.
	 * 		With the exception of the special condition described above, this
	 * 		form is specified in [ANSI INCITS 61-1986].
	 * 		The simple formula for converting degrees-minutes-seconds into
	 * 		decimal degrees is:
	 * 		
	 * 		decimal = degrees + minutes/60 + seconds/3600.
	 * 	
	 * Format Definition: This property is defined by the following notation:
	 * 
	 * 		geo = "GEO" geoparam ":" geovalue CRLF
	 * 
	 * 			geoparam = *(";" other-param)
	 * 
	 * 			geovalue = float ";" float
	 * 		
	 * 		Latitude and Longitude components
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkGEO(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property IMAGE
	 * 
	 * @formatter:off
	 * 
	 * RFC 7986 (October 2016) item 5.10.; p. 11 
	 * 
	 * 	Property Name: IMAGE
	 * 
	 * 	Purpose: This property specifies an image associated with the
	 * 		calendar or a calendar component.
	 * 
	 * 	Value Type: URI or BINARY -- no default. 
	 * 		The value MUST be data with a media type of "image" or refer to such data.
	 * 
	 * 	Property Parameters: IANA, non-standard, display, inline encoding,
	 * 		and value data type property parameters can be specified on this
	 * 		property. The format type parameter can be specified on this
	 * 		property and is RECOMMENDED for inline binary-encoded content
	 * 		information.
	 * 		
	 * 	Conformance: This property can be specified multiple times in an
	 * 		iCalendar object or in "VEVENT", "VTODO", or "VJOURNAL" calendar components.
	 * 
	 * 	Description: This property specifies an image for an iCalendar
	 * 		object or a calendar component via a URI or directly with inline
	 * 		data that can be used by calendar user agents when presenting the
	 * 		calendar data to a user. Multiple properties MAY be used to
	 * 		specify alternative sets of images with, for example, varying
	 * 		media subtypes, resolutions, or sizes. When multiple properties
	 * 		are present, calendar user agents SHOULD display only one of them,
	 * 		picking one that provides the most appropriate image quality, or
	 * 		display none. The "DISPLAY" parameter is used to indicate the
	 * 		intended display mode for the image. The "ALTREP" parameter,
	 * 		defined in [RFC5545], can be used to provide a "clickable" image
	 * 		where the URI in the parameter value can be "launched" by a click
	 * 		on the image in the calendar user agent.
	 * 		
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		image = "IMAGE" imageparam (
	 * 		
	 * 			(";" "VALUE" "=" "URI" ":" uri) /
	 * 			(";" "ENCODING" "=" "BASE64" ";" "VALUE" "=" "BINARY" ":" binary)
	 * 		
	 * 			) CRLF
	 * 
	 * 		imageparam = *(
	 * 
	 * 	The following is OPTIONAL for a URI value, RECOMMENDED for a BINARY value,
	 * 		and MUST NOT occur more than once.
	 * 
	 * 			(";" fmttypeparam) /
	 * 
	 * 	The following are OPTIONAL, and MUST NOT occur more than once.;
	 * 
	 * 			(";" altrepparam) / (";" displayparam) /
	 * 
	 * 	The following is OPTIONAL,and MAY occur more than once.
	 * 
	 * 			(";" other-param)
	 * 
	 * 			)
	 * 
	 * @formatter:on
	 * 
	 */
	String checkIMAGE(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property LAST-MOD
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.7.3p. 138
	 * 
	 * 	Property Name: LAST-MODIFIED
	 * 
	 * 	Purpose: This property specifies the date and time that the
	 * 		information associated with the calendar component was last
	 * 		revised in the calendar store.
	 * 
	 * 	Note: This is analogous to the modification date and time for a
	 * 		file in the file system.
	 * 
	 * 	Value Type: DATE-TIME
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified in the "VEVENT",
	 *		"VTODO", "VJOURNAL", or "VTIMEZONE" calendar components.
	 *
	 *	Description: The property value MUST be specified in the UTC time
	 *		format.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		last-mod = "LAST-MODIFIED" lstparam ":" date-time CRLF
	 *
	 *			lstparam = *(";" other-param)
	 *
	 *	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 *
	 * RFC 7986 (October 2016) item 5.4.; p. 8 
	 * 
	 * LAST-MODIFIED Property
	 * 
	 * 	This specification modifies the definition of the "LAST-MODIFIED"
	 *		property to allow it to be defined in an iCalendar object. The
	 *		following additions are made to the definition of this property,
	 *		originally specified in Section 3.8.7.3 of [RFC5545].
	 *		
	 *	Purpose: This property specifies the date and time that the
	 *		information associated with the calendar was last revised.
	 *
	 *	Conformance: This property can be specified once in an iCalendar object.
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkLAST_MOD(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property LOCATION
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.7; p. 87
	 * 
	 * 	Property Name:  LOCATION
	 * 
	 * 	Purpose: This property defines the intended venue for the activity
	 * 		defined by a calendar component.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA, non-standard, alternate text
	 * 		representation, and language property parameters can be specified
	 * 		on this property.
	 * 
	 * 	Conformance: This property can be specified in "VEVENT" or "VTODO"
	 * 		calendar component.
	 * 
	 * 	Description: Specific venues such as conference or meeting rooms may
	 * 		be explicitly specified using this property. An alternate
	 * 		representation may be specified that is a URI that points to
	 * 		directory information with more structured specification of the
	 * 		location. For example, the alternate representation may specify
	 * 		either an LDAP URL [RFC4516] pointing to an LDAP server entry or a
	 * 		CID URL [RFC2392] pointing to a MIME body part containing a
	 * 		Virtual-Information Card (vCard) [RFC2426] for the location.
	 *
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		location = "LOCATION" locparam ":" text CRLF
	 * 
	 * 			locparam= *(
	 * 
	 * 		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 * 				(";" altrepparam) / (";" languageparam) /
	 * 
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 				(";" other-param)
	 * 
	 * 			)
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkLOCATION(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property Method
	 * 
	 * RFC 5545 (september 2009) item 3.7.2.; p. 77 
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: METHOD
	 * 
	 * 	Purpose: This property defines the iCalendar object method
	 * 		associated with the calendar object.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar object.
	 * 
	 * 	Description: When used in a MIME message entity, the value of this
	 * 		property MUST be the same as the Content-Type "method" parameter
	 * 		value. If either the "METHOD" property or the Content-Type
	 * 		"method" parameter is specified, then the other MUST also be
	 * 		specified.
	 * 		No methods are defined by this specification. This is the subject
	 * 		of other specifications, such as the iCalendar Transport-
	 * 		independent Interoperability Protocol (iTIP) defined by [2446bis].
	 * 		If this property is not present in the iCalendar object, then a
	 * 		scheduling transaction MUST NOT be assumed. In such cases, the
	 * 		iCalendar object is merely being used to transport a snapshot of
	 * 		some calendar information; without the intention of conveying a
	 * 		scheduling semantic.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		method = "METHOD" metparam ":" metvalue CRLF
	 * 
	 * 		metparam = *(";" other-param)
	 * 
	 * 		metvalue = iana-token
	 * 
	 * @formatter:on
	 * 
	 */
	String checkMETHOD(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property NAME
	 * 
	 * @formatter:off
	 * 
	 * RFC 7986 (October 2016) item 5.1.; p. 5 
	 * 
	 * 	Property Name: NAME
	 * 
	 * 	Purpose: This property specifies the name of the calendar.
	 * 
	 * 	Value Type: TEXT
	 * 
	 *	Property Parameters: IANA, non-standard, alternate text
	 *		representation, and language property parameters can be specified
	 *		on this property.
	 *
	 *	Conformance: This property can be specified multiple times in an
	 *		iCalendar object. However, each property MUST represent the name
	 *		of the calendar in a different language.
	 *	
	 *	Description: This property is used to specify a name of the
	 *		iCalendar object that can be used by calendar user agents when
	 *		presenting the calendar data to a user. Whilst a calendar only
	 *		has a single name, multiple language variants can be specified by
	 *		including this property multiple times with different "LANGUAGE"
	 *		parameter values on each.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		name = "NAME" nameparam ":" text CRLF
	 *
	 *		nameparam = *(
	 *	
	 *	The following are OPTIONAL, but MUST NOT occur more than once.
	 *
	 *			(";" altrepparam) / (";" languageparam) /
	 *
	 *	The following is OPTIONAL, and MAY occur more than once.
	 *
	 *			(";" other-param)
	 *
	 *			)
	 * 
	 * @formatter:on
	 * 
	 */
	String checkNAME(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property ORGANIZER
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.4.3; p. 111
	 * 
	 * 	Property Name: ORGANIZER
	 * 
	 * 	Purpose: This property defines the organizer for a calendar
	 * 		component.
	 * 
	 * 	Value Type: CAL-ADDRESS
	 * 
	 * 	Property Parameters: IANA, non-standard, language, common name,
	 * 		directory entry reference, and sent-by property parameters can be
	 * 		specified on this property.
	 * 
	 * Conformance: This property MUST be specified in an iCalendar object
	 * 		that specifies a group-scheduled calendar entity. This property
	 * 		MUST be specified in an iCalendar object that specifies the
	 * 		publication of a calendar user’s busy time. This property MUST
	 * 		NOT be specified in an iCalendar object that specifies only a time
	 * 		zone definition or that defines calendar components that are not
	 * 		group-scheduled components, but are components only on a single
	 * 		user’s calendar.
	 * 
	 * 	Description: This property is specified within the "VEVENT",
	 * 		"VTODO", and "VJOURNAL" calendar components to specify the
	 * 		organizer of a group-scheduled calendar entity. The property is
	 * 		specified within the "VFREEBUSY" calendar component to specify the
	 * 		calendar user requesting the free or busy time. When publishing a
	 * 		"VFREEBUSY" calendar component, the property is used to specify
	 * 		the calendar that the published busy time came from.
	 * 		The property has the property parameters "CN", for specifying the
	 * 		common or display name associated with the "Organizer", "DIR", for
	 * 		specifying a pointer to the directory information associated with
	 * 		the "Organizer", "SENT-BY", for specifying another calendar user
	 * 		that is acting on behalf of the "Organizer". The non-standard
	 * 		parameters may also be specified on this property. If the
	 * 		"LANGUAGE" property parameter is specified, the identified
	 * 		language applies to the "CN" parameter value.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		organizer = "ORGANIZER" orgparam ":" cal-address CRLF
	 * 
	 * 			orgparam= *(
	 * 
	 * 		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 * 				(";" cnparam) / (";" dirparam) / (";" sentbyparam) /
	 * 				(";" languageparam) /
	 * 
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 				(";" other-param)
	 * 
	 * 			)
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkORGANIZER(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property PERCENT
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.8; p. 88.
	 * 
	 * 	Property Name: PERCENT-COMPLETE
	 * 
	 * 	Purpose: This property is used by an assignee or delegatee of a
	 * 		to-do to convey the percent completion of a to-do to the
	 * 		"Organizer".
	 * 
	 * 	Value Type: INTEGER
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in a "VTODO"
	 * 		calendar component.
	 * 
	 * 	Description: The property value is a positive integer between 0 and
	 * 		100. A value of "0" indicates the to-do has not yet been started.
	 * 		A value of "100" indicates that the to-do has been completed.
	 * 		Integer values in between indicate the percent partially complete.
	 * 		When a to-do is assigned to multiple individuals, the property
	 * 		value indicates the percent complete for that portion of the to-do
	 * 		assigned to the assignee or delegatee. For example, if a to-do is
	 * 		assigned to both individuals "A" and "B". A reply from "A" with a
	 * 		percent complete of "70" indicates that "A" has completed 70% of
	 * 		the to-do assigned to them. A reply from "B" with a percent
	 * 		complete of "50" indicates "B" has completed 50% of the to-do
	 * 		assigned to them.
	 * 
	 * Format Definition: This property is defined by the following notation:
	 * 
	 * 		percent = "PERCENT-COMPLETE" pctparam ":" integer CRLF
	 * 
	 * 			pctparam = *(";" other-param)
	 *  
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkPERCENT(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property PRIORITY
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.9; p. 89
	 * 
	 * 	Property Name: PRIORITY
	 * 
	 * 	Purpose: This property defines the relative priority for a calendar
	 * 		component.
	 * 
	 * 	Value Type: INTEGER
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified in "VEVENT" and "VTODO"
	 * 		calendar components.
	 * 
	 * 	Description: This priority is specified as an integer in the range 0
	 * 		to 9. A value of 0 specifies an undefined priority. A value of 1
	 * 		is the highest priority. A value of 2 is the second highest
	 * 		priority. Subsequent numbers specify a decreasing ordinal
	 * 		priority. A value of 9 is the lowest priority.
	 * 		A CUA with a three-level priority scheme of "HIGH", "MEDIUM", and
	 * 		"LOW" is mapped into this property such that a property value in
	 * 		the range of 1 to 4 specifies "HIGH" priority. A value of 5 is
	 * 		the normal or "MEDIUM" priority. A value in the range of 6 to 9
	 * 		is "LOW" priority.
	 * 		A CUA with a priority schema of "A1", "A2", "A3", "B1", "B2", ...,
	 * 		"C3" is mapped into this property such that a property value of 1
	 * 		specifies "A1", a property value of 2 specifies "A2", a property
	 * 		value of 3 specifies "A3", and so forth up to a property value of
	 * 		9 specifies "C3".
	 * 		Other integer values are reserved for future use.
	 * 		Within a "VEVENT" calendar component, this property specifies a
	 * 		priority for the event. This property may be useful when more
	 * 		than one event is scheduled for a given time period.
	 * 		Within a "VTODO" calendar component, this property specified a
	 * 		priority for the to-do. This property is useful in prioritizing
	 * 		multiple action items for a given time period.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 *		priority = "PRIORITY" prioparam ":" priovalue CRLF
	 *
	 *		Default is zero (i.e., undefined).
	 *
	 *			prioparam = *(";" other-param)
	 *
	 *			priovalue = integer
	 *
	 * 		Must be in the range [0..9]
	 * 		All other values are reserved for future use.
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkPRIORITY(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkPRIORITY begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 8;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkPRIORITY beendet");
		return zTemp;
	}

	/**
	 * 	Bearbeitungsroutinen für die property PRODID
	 * 
	 * 	RFC 5545 (september 2009) item 3.7.3.; p. 78 
	 * 
	 * @formatter:off
	 * 
	 *	Property Name: PRODID
	 *
	 *	Purpose: This property specifies the identifier for the product that
	 *		created the iCalendar object.
	 *
	 *	Value Type: TEXT
	 *
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: The property MUST be specified once in an iCalendar object.
	 *
	 *	Description: The vendor of the implementation SHOULD assure that
	 *		this is a globally unique identifier; using some technique such as
	 *		an FPI value, as defined in [ISO.9070.1991].
	 *		This property SHOULD NOT be used to alter the interpretation of an
	 *		iCalendar object beyond the semantics specified in this memo. For
	 *		example, it is not to be used to further the understanding of non-
	 *		standard properties.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		prodid = "PRODID" pidparam ":" pidvalue CRLF
	 *
	 *			pidparam = *(";" other-param)
	 *
	 *			pidvalue = text
	 *		
	 *		Any text that describes the product and version
	 *
	 * @formatter:on
	 * 	 
	 */
	String checkPRODID(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property RECURID
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.4.4; p. 112
	 * 
	 * 	Property Name: RECURRENCE-ID
	 * 
	 * 	Purpose: This property is used in conjunction with the "UID" and
	 * 		"SEQUENCE" properties to identify a specific instance of a
	 * 		recurring "VEVENT", "VTODO", or "VJOURNAL" calendar component.
	 * 		The property value is the original value of the "DTSTART" property
	 * 		of the recurrence instance.
	 * 
	 * 	Value Type: The default value type is DATE-TIME. The value type can
	 * 		be set to a DATE value type. This property MUST have the same
	 * 		value type as the "DTSTART" property contained within the
	 * 		recurring component. Furthermore, this property MUST be specified
	 * 		as a date with local time if and only if the "DTSTART" property
	 * 		contained within the recurring component is specified as a date
	 * 		with local time.
	 * 
	 * 	Property Parameters: IANA, non-standard, value data type, time zone
	 * 		identifier, and recurrence identifier range parameters can be
	 * 		specified on this property.
	 * 
	 * 	Conformance: This property can be specified in an iCalendar object
	 * 		containing a recurring calendar component.
	 * 
	 * 	Description: The full range of calendar components specified by a
	 * 		recurrence set is referenced by referring to just the "UID"
	 * 		property value corresponding to the calendar component. The
	 * 		"RECURRENCE-ID" property allows the reference to an individual
	 * 		instance within the recurrence set.
	 * 		If the value of the "DTSTART" property is a DATE type value, then
	 * 		the value MUST be the calendar date for the recurrence instance.
	 * 		The DATE-TIME value is set to the time when the original
	 * 		recurrence instance would occur; meaning that if the intent is to
	 * 		change a Friday meeting to Thursday, the DATE-TIME is still set to
	 * 		the original Friday meeting.
	 * 		The "RECURRENCE-ID" property is used in conjunction with the "UID"
	 * 		and "SEQUENCE" properties to identify a particular instance of a
	 * 		recurring event, to-do, or journal. For a given pair of "UID" and
	 * 		"SEQUENCE" property values, the "RECURRENCE-ID" value for a
	 * 		recurrence instance is fixed.
	 * 		The "RANGE" parameter is used to specify the effective range of
	 * 		recurrence instances from the instance specified by the
	 * 		"RECURRENCE-ID" property value. The value for the range parameter
	 * 		can only be "THISANDFUTURE" to indicate a range defined by the
	 * 		given recurrence instance and all subsequent instances.
	 * 		Subsequent instances are determined by their "RECURRENCE-ID" value
	 * 		and not their current scheduled start time. Subsequent instances
	 * 		defined in separate components are not impacted by the given
	 * 		recurrence instance. When the given recurrence instance is
	 * 		rescheduled, all subsequent instances are also rescheduled by the
	 * 		same time difference. For instance, if the given recurrence
	 * 		instance is rescheduled to start 2 hours later, then all
	 * 		subsequent instances are also rescheduled 2 hours later.
	 * 		Similarly, if the duration of the given recurrence instance is
	 * 		modified, then all subsequence instances are also modified to have
	 * 		this same duration.
	 * 
	 * 	Note: The "RANGE" parameter may not be appropriate to
	 * 		reschedule specific subsequent instances of complex recurring
	 * 		calendar component. Assuming an unbounded recurring calendar
	 * 		component scheduled to occur on Mondays and Wednesdays, the
	 * 		"RANGE" parameter could not be used to reschedule only the
	 * 		future Monday instances to occur on Tuesday instead. In such
	 * 		cases, the calendar application could simply truncate the
	 * 		unbounded recurring calendar component (i.e., with the "COUNT"
	 * 		or "UNTIL" rule parts), and create two new unbounded recurring
	 * 		calendar components for the future instances.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 *		recurid = "RECURRENCE-ID" ridparam ":" ridval CRLF
	 * 
	 *			ridparam = *(
	 *            
	 *		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 *            	(";" "VALUE" "=" ("DATE-TIME" / "DATE")) / 
	 *            	(";" tzidparam) / (";" rangeparam) /
	 * 
	 *		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 *            	(";" other-param)
	 * 
	 *			)
	 *
	 *		ridval = date-time / date
	 *
	 *		Value MUST match value type
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkRECURID(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkRECURID begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 13;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkRECURID beendet");
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property RRULE
	 *
	 * @formatter:off
	 * 
	 * RFC 5545 (september 2009) item 3.8.5.3; p. 122
	 * 
	 * 	Property Name: RRULE
	 * 
	 * 	Purpose: This property defines a rule or repeating pattern for
	 * 		recurring events, to-dos, journal entries, or time zone definitions.
	 * 
	 * 	Value Type: RECUR
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified in recurring "VEVENT",
	 * 		"VTODO", and "VJOURNAL" calendar components as well as in the
	 * 		"STANDARD" and "DAYLIGHT" sub-components of the "VTIMEZONE"
	 * 		calendar component, but it SHOULD NOT be specified more than once.
	 * 		The recurrence set generated with multiple "RRULE" properties is
	 * 		undefined.
	 * 
	 * 	Description: The recurrence rule, if specified, is used in computing
	 * 		the recurrence set. The recurrence set is the complete set of
	 * 		recurrence instances for a calendar component. The recurrence set
	 * 		is generated by considering the initial "DTSTART" property along
	 * 		with the "RRULE", "RDATE", and "EXDATE" properties contained
	 * 		within the recurring component. The "DTSTART" property defines
	 * 		the first instance in the recurrence set. The "DTSTART" property
	 * 		value SHOULD be synchronized with the recurrence rule, if
	 * 		specified. The recurrence set generated with a "DTSTART" property
	 * 		value not synchronized with the recurrence rule is undefined. The
	 * 		final recurrence set is generated by gathering all of the start
	 * 		DATE-TIME values generated by any of the specified "RRULE" and
	 * 		"RDATE" properties, and then excluding any start DATE-TIME values
	 * 		specified by "EXDATE" properties. This implies that start DATE-TIME 
	 * 		values specified by "EXDATE" properties take precedence over 
	 * 		those specified by inclusion properties (i.e., "RDATE" and 
	 * 		"RRULE"). Where duplicate instances are generated by the "RRULE"
	 * 		and "RDATE" properties, only one recurrence is considered.
	 * 		Duplicate instances are ignored.
	 * 		The "DTSTART" property specified within the iCalendar object
	 * 		defines the first instance of the recurrence. In most cases, a
	 * 		"DTSTART" property of DATE-TIME value type used with a recurrence
	 * 		rule, should be specified as a date with local time and time zone
	 * 		reference to make sure all the recurrence instances start at the
	 * 		same local time regardless of time zone changes.
	 * 		If the duration of the recurring component is specified with the
	 * 		"DTEND" or "DUE" property, then the same exact duration will apply
	 * 		to all the members of the generated recurrence set. Else, if the
	 * 		duration of the recurring component is specified with the
	 * 		"DURATION" property, then the same nominal duration will apply to
	 * 		all the members of the generated recurrence set and the exact
	 * 		duration of each recurrence instance will depend on its specific
	 * 		start time. For example, recurrence instances of a nominal
	 * 		duration of one day will have an exact duration of more or less
	 * 		than 24 hours on a day where a time zone shift occurs. The
	 * 		duration of a specific recurrence may be modified in an exception
	 * 		component or simply by using an "RDATE" property of PERIOD value
	 * 		type.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		rrule = "RRULE" rrulparam ":" recur CRLF
	 * 
	 * 			rrulparam = *(";" other-param)
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkRRULE(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkRRULE begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 5;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkRRULE beendet");
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property REFRESH-INTERVAL
	 * 
	 * RFC 7986 (October 2016) item 5.7.; p. 9 
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: REFRESH-INTERVAL
	 * 
	 * 	Purpose: This property specifies a suggested minimum interval for
	 * 		polling for changes of the calendar data from the original source
	 * 		of that data.
	 * 
	 * 	Value Type: DURATION -- no default
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar object.
	 * 
	 * 	Description: This property specifies a positive duration that gives
	 * 		a suggested minimum polling interval for checking for updates to
	 * 		the calendar data. The value of this property SHOULD be used by
	 * 		calendar user agents to limit the polling interval for calendar
	 * 		data updates to the minimum interval specified.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		refresh = "REFRESH-INTERVAL" refreshparam ":" dur-value CRLF
	 * 		
	 * 			refreshparam = *(
	 * 		
	 * 		The following is REQUIRED, but MUST NOT occur more than once.
	 * 
	 * 				(";" "VALUE" "=" "DURATION") /
	 * 		
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 				(";" other-param)
	 * 				)
	 * 
	 * 		dur-value = a positive duration of time.
	 *
	 * @formatter:on
	 * 
	 */
	String checkREFRESH(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property SEQUENCE
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.7.4; p. 138
	 * 
	 * Property Name: SEQUENCE
	 * 
	 * Purpose: This property defines the revision sequence number of the
	 * 		calendar component within a sequence of revisions.
	 * 
	 * 	Value Type: INTEGER
	 * 
	 *	Property Parameters: IANA and non-standard property parameters can
	 *		be specified on this property.
	 *
	 *	Conformance: The property can be specified in "VEVENT", "VTODO", or
	 *		"VJOURNAL" calendar component.
	 *
	 *	Description: When a calendar component is created, its sequence
	 *		number is 0. It is monotonically incremented by the "Organizer’s"
	 *		CUA each time the "Organizer" makes a significant revision to the
	 *		calendar component.
	 *		The "Organizer" includes this property in an iCalendar object that
	 *		it sends to an "Attendee" to specify the current version of the
	 *		calendar component.
	 *		The "Attendee" includes this property in an iCalendar object that
	 *		it sends to the "Organizer" to specify the version of the calendar
	 *		component to which the "Attendee" is referring.
	 *		A change to the sequence number is not the mechanism that an
	 *		"Organizer" uses to request a response from the "Attendees". The
	 *		"RSVP" parameter on the "ATTENDEE" property is used by the
	 *		"Organizer" to indicate that a response from the "Attendees" is
	 *		requested.
	 *		Recurrence instances of a recurring component MAY have different
	 *		sequence numbers.
	 *
	 *	Format Definition: This property is defined by the following notation:
	 *
	 *		seq = "SEQUENCE" seqparam ":" integer CRLF
	 *
	 *		Default is "0"
	 *
	 *			seqparam = *(";" other-param)
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkSEQUENCE(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkSEQUENCE begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 8;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkSEQUENCE beendet");
		return zTemp;
	}
	/**
	 * Bearbeitungsroutinen für die property SOURCE
	 * 
	 * RFC 7986 (October 2016) item 5.8.; p. 9
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: SOURCE
	 * 
	 * 	Purpose: This property identifies a URI where calendar data can be
	 * 		refreshed from.
	 * 
	 * 	Value Type: URI -- no default
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar object.
	 * 
	 * 	Description: This property identifies a location where a client can
	 * 		retrieve updated data for the calendar. Clients SHOULD honor any
	 * 		specified "REFRESH-INTERVAL" value when periodically retrieving
	 * 		data. Note that this property differs from the "URL" property in
	 * 		that "URL" is meant to provide an alternative representation of
	 * 		the calendar data rather than the original location of the data.
	 * 		
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		source = "SOURCE" sourceparam ":" uri CRLF
	 * 
	 * 		sourceparam = *(
	 * 
	 * 	The following is REQUIRED, but MUST NOT occur more than once.
	 * 
	 * 			(";" "VALUE" "=" "URI") /
	 * 		
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 
	 * 			(";" other-param)
	 * 			)
	 * 
	 * @formatter:on
	 * 
	 */
	String checkSOURCE(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property STATUS
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.11; p. 92
	 * 
	 * 	Property Name: STATUS
	 * 
	 * 	Purpose: This property defines the overall status or confirmation
	 * 		for the calendar component.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in "VEVENT",
	 * 		"VTODO", or "VJOURNAL" calendar components.
	 * 
	 * 	Description: In a group-scheduled calendar component, the property
	 * 		is used by the "Organizer" to provide a confirmation of the event
	 * 		to the "Attendees". For example in a "VEVENT" calendar component,
	 * 		the "Organizer" can indicate that a meeting is tentative,
	 * 		confirmed, or cancelled. In a "VTODO" calendar component, the
	 * 		"Organizer" can indicate that an action item needs action, is
	 * 		completed, is in process or being worked on, or has been
	 * 		cancelled. In a "VJOURNAL" calendar component, the "Organizer"
	 * 		can indicate that a journal entry is draft, final, or has been
	 * 		cancelled or removed.
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		status = "STATUS" statparam ":" statvalue CRLF
	 * 
	 * 			statparam = *(";" other-param)
	 * 
	 * 			statvalue = (statvalue-event / statvalue-todo / statvalue-jour)
	 * 
	 * 		Status values for a "VEVENT"
	 * 			statvalue-event = "TENTATIVE" 	;Indicates event is tentative.
	 * 							/ "CONFIRMED" 	;Indicates event is definite.
	 * 							/ "CANCELLED" 	;Indicates event was cancelled.
	 *
	 * 		Status values for "VTODO"
	 * 			statvalue-todo = "NEEDS-ACTION" ;Indicates to-do needs action.
	 * 							/ "COMPLETED"   ;Indicates to-do is completed.
	 * 							/ "IN-PROCESS"  ;Indicates to-do in process of.
	 * 							/ "CANCELLED"   ;Indicates to-do was cancelled.
	 * 
	 * 		Status values for "VJOURNAL"
	 * 			statvalue-jour = "DRAFT"	 	;Indicates journal is draft.
	 * 							/ "FINAL"	 	;Indicates journal is final.
	 * 							/ "CANCELLED"	;Indicates journal is removed.
	 *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkSTATUS(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkSTATUS begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 8;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkSTATUS beendet");
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property SUMMARY
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.1.12; p. 93
	 * 
	 * 	Property Name: SUMMARY
	 * 
	 * 	Purpose: This property defines a short summary or subject for the
	 * 		calendar component.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA, non-standard, alternate text
	 * 		representation, and language property parameters can be specified
	 * 		on this property.
	 * 
	 * 	Conformance: The property can be specified in "VEVENT", "VTODO",
	 * 		"VJOURNAL", or "VALARM" calendar components.
	 * 
	 * 	Description: This property is used in the "VEVENT", "VTODO", and
	 * 		"VJOURNAL" calendar components to capture a short, one-line
	 * 		summary about the activity or journal entry.
	 * 		This property is used in the "VALARM" calendar component to
	 * 		capture the subject of an EMAIL category of alarm.
	 * 	
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		summary = "SUMMARY" summparam ":" text CRLF
	 * 
	 * 			summparam = *(
	 * 
	 * 		The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 * 				(";" altrepparam) / (";" languageparam) /
	 * 
	 * 		The following is OPTIONAL, and MAY occur more than once.
	 * 	
	 * 				(";" other-param)
	 * 
	 *			)
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkSUMMARY(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkSUMMARY begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 7;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkSUMMARY beendet");
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property TRANSP
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.2.7; p. 101
	 * 
	 * 	Property Name: TRANSP
	 * 
	 * 	Purpose: This property defines whether or not an event is
	 * 		transparent to busy time searches.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: This property can be specified once in a "VEVENT"
	 * 		calendar component.
	 * 
	 * 	Description: Time Transparency is the characteristic of an event
	 * 		that determines whether it appears to consume time on a calendar.
	 * 		Events that consume actual time for the individual or resource
	 * 		associated with the calendar SHOULD be recorded as OPAQUE,
	 * 		allowing them to be detected by free/busy time searches. Other
	 * 		events, which do not take up the individual’s (or resource’s) time
	 * 		SHOULD be recorded as TRANSPARENT, making them invisible to free/
	 * 		busy time searches.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		transp = "TRANSP" transparam ":" transvalue CRLF
	 * 
	 * 			transparam = *(";" other-param)
	 * 
	 * 			transvalue = "OPAQUE"
	 * 		
	 * 		Blocks or opaque on busy time searches.
	 * 				
	 * 				/ "TRANSPARENT"
	 * 
	 * 		Transparent on busy time searches.
	 * 
	 * 		Default value is OPAQUE
	 * 
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */

	String checkTRANSP(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkTRANSP begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 6;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkTRANSP beendet");
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property UID
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.4.7; p. 117
	 * 
	 * 	Property Name: UID
	 * 
	 * 	Purpose: This property defines the persistent, globally unique
	 * 		identifier for the calendar component.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 
	 * 	Conformance: The property MUST be specified in the "VEVENT",
	 * 		"VTODO", "VJOURNAL", or "VFREEBUSY" calendar components.
	 * 		Description: The "UID" itself MUST be a globally unique identifier.
	 * 		The generator of the identifier MUST guarantee that the identifier
	 * 		is unique. There are several algorithms that can be used to
	 * 		accomplish this. A good method to assure uniqueness is to put the
	 * 		domain name or a domain literal IP address of the host on which
	 * 		the identifier was created on the right-hand side of an "@", and
	 * 		on the left-hand side, put a combination of the current calendar
	 * 		date and time of day (i.e., formatted in as a DATE-TIME value)
	 * 		along with some other currently unique (perhaps sequential)
	 * 		identifier available on the system (for example, a process id
	 * 		number). Using a DATE-TIME value on the left-hand side and a
	 * 		domain name or domain literal on the right-hand side makes it
	 * 		possible to guarantee uniqueness since no two hosts should be
	 * 		using the same domain name or IP address at the same time. Though
	 * 		other algorithms will work, it is RECOMMENDED that the right-hand
	 * 		side contain some domain identifier (either of the host itself or
	 * 		otherwise) such that the generator of the message identifier can
	 * 		guarantee the uniqueness of the left-hand side within the scope of
	 * 		that domain.
	 * 		This is the method for correlating scheduling messages with the
	 * 		referenced "VEVENT", "VTODO", or "VJOURNAL" calendar component.
	 * 		The full range of calendar components specified by a recurrence
	 * 		set is referenced by referring to just the "UID" property value
	 * 		corresponding to the calendar component. The "RECURRENCE-ID"
	 * 		property allows the reference to an individual instance within the
	 * 		recurrence set.
	 * 		This property is an important method for group-scheduling
	 * 		applications to match requests with later replies, modifications,
	 * 		or deletion requests. Calendaring and scheduling applications
	 * 		MUST generate this property in "VEVENT", "VTODO", and "VJOURNAL"
	 * 		calendar components to assure interoperability with other group-
	 * 		scheduling applications. This identifier is created by the
	 * 		calendar system that generates an iCalendar object.
	 * 		Implementations MUST be able to receive and persist values of at
	 * 		least 255 octets for this property, but they MUST NOT truncate
	 * 		values in the middle of a UTF-8 multi-octet sequence.
	 * 	
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		uid = "UID" uidparam ":" text CRLF
	 * 
	 * 			uidparam = *(";" other-param)
	 * 
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	 *  
	 * 	RFC 7986 (october 2016) item 5.3.; p. 7 
	 * 
	 *  UID Property
	 * 
	 * 	This specification modifies the definition of the "UID" property to
	 * 		allow it to be defined in an iCalendar object. The following
	 * 		additions are made to the definition of this property, originally
	 * 		specified in Section 3.8.4.7 of [RFC5545].
	 * 
	 * 	Purpose: This property specifies the persistent, globally unique
	 * 		identifier for the iCalendar object. This can be used, for
	 * 		example, to identify duplicate calendar streams that a client may
	 * 		have been given access to. It can be used in conjunction with the
	 * 		"LAST-MODIFIED" property also specified on the "VCALENDAR" object
	 * 		to identify the most recent version of a calendar.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar object.
	 * 
	 * 	Description: The description of the "UID" property in [RFC5545] contains some
	 * 		recommendations on how the value can be constructed. In particular,
	 * 		it suggests use of host names, IP addresses, and domain names to
	 * 		construct the value. However, this is no longer considered good
	 * 		practice, particularly from a security and privacy standpoint, since
	 * 		use of such values can leak key information about a calendar user or
	 * 		their client and network environment. This specification updates
	 * 		[RFC5545] by stating that "UID" values MUST NOT include any data that
	 * 		might identify a user, host, domain, or any other security- or
	 * 		privacy-sensitive information. It is RECOMMENDED that calendar user
	 * 		agents now generate "UID" values that are hex-encoded random
	 * 		Universally Unique Identifier (UUID) values as defined in
	 * 		Sections 4.4 and 4.5 of [RFC4122].
	 * 		
	 * 	The following is an example of such a property value:
	 * 		UID:5FC53010-1267-4F8E-BC28-1D7AE55A7C99
	 * 
	 * 	Additionally, if calendar user agents choose to use other forms of
	 * 		opaque identifiers for the "UID" value, they MUST have a length less
	 * 		than 255 octets and MUST conform to the "iana-token" ABNF syntax
	 * 		defined in Section 3.1 of [RFC5545].
     *
	 * @formatter:on
	 * 
	 * @param zeichenkette - String
	 * @param aktion - int
	 * 
	 * @return 
	 * 
	 * 	aktion 0
	 * 		Teil der übergebenen Zeichenkette nach dem Schlüsselwort
	 * 
	 */
	String checkUID(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}

	private String holenNeueUID() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String uniqueID = UUID.randomUUID().toString();
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return uniqueID;
	}

	/**
	 * Bearbeitungsroutinen für die property URL
	 *
	 * @formatter:off
	 * 
	 * 	RFC 5545 (september 2009) item 3.8.4.6; p. 116
	 * 
	 * 	Property Name: URL
	 * 
	 * 	Purpose: This property defines a Uniform Resource Locator (URL)
	 * 		associated with the iCalendar object.
	 * 
	 * 	Value Type: URI
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 	
	 * 	Conformance: This property can be specified once in the "VEVENT",
	 * 		"VTODO", "VJOURNAL", or "VFREEBUSY" calendar components.
	 * 
	 * 	Description: This property may be used in a calendar component to
	 * 		convey a location where a more dynamic rendition of the calendar
	 * 		information associated with the calendar component can be found.
	 * 		This memo does not attempt to standardize the form of the URI, nor
	 * 		the format of the resource pointed to by the property value. If
	 * 		the URL property and Content-Location MIME header are both
	 * 		specified, they MUST point to the same resource.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		url = "URL" urlparam ":" uri CRLF
	 * 
	 *			urlparam = *(";" other-param)
	 *
	 *	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
	 * 
	 * RFC 7986 (October 2016) item 5.5.; p. 8  
	 * 
	 * URL Property
	 *
	 * 	This specification modifies the definition of the "URL" property to
	 * 		allow it to be defined in an iCalendar object. The following
	 * 		additions are made to the definition of this property, originally
	 * 		specified in Section 3.8.4.6 of [RFC5545].
	 * 
	 * 	Purpose: This property may be used to convey a location where a more
	 * 		dynamic rendition of the calendar information can be found.
	 * 
	 * 	Conformance: This property can be specified once in an iCalendar object.
	 * 
	 * @formatter:on
	 * 
	 */
	String checkURL(String zeichenkette, int aktion) {
//		System.out.println("GuKKiCalComponent.checkURL begonnen");
//		System.out.println("Zeile="+zeichenkette);
		int laengeLiteral = 3;
		String zTemp = "";
		if (aktion == 0) {
			zTemp = zeichenkette.substring(laengeLiteral);
		}
//		System.out.println("Temp=" + zTemp);
//		System.out.println("GuKKiCalComponent.checkURL beendet");
		return zTemp;
	}

	/**
	 * Bearbeitungsroutinen für die property Version
	 * 
	 * RFC 5545 (september 2009) item 3.7.4.; p. 79 
	 * 
	 * @formatter:off
	 * 
	 * 	Property Name: VERSION
	 * 
	 * 	Purpose: This property specifies the identifier corresponding to the
	 * 		highest version number or the minimum and maximum range of the
	 * 		iCalendar specification that is required in order to interpret the
	 * 		iCalendar object.
	 * 
	 * 	Value Type: TEXT
	 * 
	 * 	Property Parameters: IANA and non-standard property parameters can
	 * 		be specified on this property.
	 * 	
	 * 	Conformance: This property MUST be specified once in an iCalendar object.
	 * 
	 * 	Description: A value of "2.0" corresponds to this memo.
	 * 
	 * 	Format Definition: This property is defined by the following notation:
	 * 
	 * 		version = "VERSION" verparam ":" vervalue CRLF
	 * 
	 * 			verparam = *(";" other-param)
	 * 
	 * 			vervalue = "2.0" ;This memo
	 * 				/ maxver
	 * 				/ (minver ";" maxver)
	 * 				
	 * 			minver = <A IANA-registered iCalendar version identifier>
	 * 				;Minimum iCalendar version needed to parse the iCalendar object.
	 * 
	 * 			maxver = <A IANA-registered iCalendar version identifier>
	 * 				;Maximum iCalendar version needed to parse the iCalendar object.
	 * 
	 * @formatter:on
	 * 
	 */
	String checkVERSION(GuKKiCalProperty property) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String kennung = property.getLiteral();
		String zTemp = "";
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return zTemp;
	}
}
