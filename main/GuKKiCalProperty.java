package main;

import java.util.ArrayList;

/**
 * Grundliegende Struktur für die Property-Parameter
 * 
 * @autor GuKKDevel
 * 
 * @formatter:off
 * 
 * 
 * 	RFC 5545 (september 2009) item 3.2; p. 13
 * 
 * 
 *  Property Parameters
 *  
 *  	A property can have attributes with which it is associated. These
 *  	"property parameters" contain meta-information about the property or
 *  	the property value. Property parameters are provided to specify such
 *  	information as the location of an alternate text representation for a
 *  	property value, the language of a text property value, the value type
 *  	of the property value, and other attributes.
 *  	
 *  	Property parameter values that contain the COLON, SEMICOLON, or COMMA
 *  	character separators MUST be specified as quoted-string text values.
 *  	Property parameter values MUST NOT contain the DQUOTE character. The
 *  	DQUOTE character is used as a delimiter for parameter values that
 *  	contain restricted characters or URI text. For example:
 *  
 *  		DESCRIPTION;ALTREP="cid:part1.0001@example.org":The Fall’98 Wild
 *  		  Wizards Conference - - Las Vegas\, NV\, USA
 *  
 *  	Property parameter values that are not in quoted-strings are case-
 *  	insensitive.
 *  
 *  	The general property parameters defined by this memo are defined by
 *  	the following notation:
 *  		
 *  		icalparameter =
 *				  altrepparam 			;Alternate text representation 
 *				/ cnparam				;Common name
 *				/ cutypeparam			;Calendar user type
 *				/ delfromparam			;Delegator
 *				/ deltoparam			;Delegatee
 *				/ dirparam				;Directory entry
 *				/ encodingparam			;Inline encoding
 *				/ fmttypeparam			;Format type
 *				/ fbtypeparam			;Free/busy time type
 *				/ languageparam			;Language for text
 *				/ memberparam			;Group or list membership
 *				/ partstatparam			;Participation status
 *				/ rangeparam			;Recurrence identifier range
 *				/ trigrelparam			;Alarm trigger relationship
 *				/ reltypeparam			;Relationship type
 *				/ roleparam				;Participation role
 *				/ rsvpparam				;RSVP expectation
 *				/ sentbyparam			;Sent by
 *				/ tzidparam				;Reference to time zone object
 *				/ valuetypeparam		;Property value data type
 *				/ other-param
 *
 *				other-param = (iana-param / x-param)
 *
 *		Some other IANA-registered iCalendar parameter.
 *		
 *				iana-param = iana-token "=" param-value *("," param-value)
 *
 *		A non-standard, experimental parameter.
 *
 *				x-param = x-name "=" param-value *("," param-value)
 *
 *		Applications MUST ignore x-param and iana-param values they don’t recognize.
 *
 * @formatter:on
 * 
 * 
 */
class GuKKiCalProperty {
	/*
	 * Notwendige Werte
	 */
	private String propertyLiteral = "";
	private String propertyWert = "";

	/**
	 *  Alternate Text Representation
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.1; p. 14 
	 * 
	 * @formatter:off
	 *  
	 *  Parameter Name: ALTREP
	 *  
	 *  Purpose: To specify an alternate text representation for the property value.
	 *
	 *  Description: This parameter specifies a URI that points to an
	 *  		alternate representation for a textual property value. A property
	 *  		specifying this parameter MUST also include a value that reflects
	 *  		the default representation of the text value. The URI parameter
	 *  		value MUST be specified in a quoted-string.
	 *  			Note: While there is no restriction imposed on the URI schemes
	 *  			allowed for this parameter, Content Identifier (CID) [RFC2392],
	 *  			HTTP [RFC2616], and HTTPS [RFC2818] are the URI schemes most
	 *  			commonly used by current implementations.
	 *  
	 *  Format Definition: This property parameter is defined by the following notation:
	 *  
	 *  	altrepparam = "ALTREP" "=" DQUOTE uri DQUOTE
	 *  
	 * @formatter:on
	 * 	 
	 */
	private String propertyALTREP = null;

	/**
	 * 	Common Name 
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.2; p. 15 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: CN
	 * 
	 * 	Purpose: To specify the common name to be associated with the
	 * 		calendar user specified by the property.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. The parameter specifies the common name
	 * 		to be associated with the calendar user specified by the property.
	 * 		The parameter value is text. The parameter value can be used for
	 * 		display text to be associated with the calendar address specified
	 * 		by the property.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		cnparam = "CN" "=" param-value
	 *  
	 * @formatter:on
	 * 	 
	 */
	private String propertyCN = null;

	/**
	 * 	Calendar User Type
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.3; p. 16 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: CUTYPE
	 * 
	 * 	Purpose: To identify the type of calendar user specified by the property.
	 *
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. The parameter identifies the type of
	 * 		calendar user specified by the property. If not specified on a
	 * 		property that allows this parameter, the default is INDIVIDUAL.
	 * 		Applications MUST treat x-name and iana-token values they don’t
	 * 		recognize the same way as they would the UNKNOWN value.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		cutypeparam = "CUTYPE" "=" 
	 * 			("INDIVIDUAL" 	;An individual
	 * 			/ "GROUP"		;A group of individuals
	 * 			/ "RESOURCE"	;A physical resource
	 * 			/ "ROOM"		;A room resource
	 * 			/ "UNKNOWN"		;Otherwise not known
	 * 			/ x-name		;Experimental type
	 * 			/ iana-token)	;Other IANA-registered type
	 * 
	 * 		Default is INDIVIDUAL
	 * 	
	 * @formatter:on
	 * 	 
	 */
	private String propertyCUTYPE = null;

	/**
	 * 	Delegators
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.4; p. 17 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: DELEGATED-FROM
	 * 
	 * 	Purpose: To specify the calendar users that have delegated their
	 * 		participation to the calendar user specified by the property.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. This parameter specifies those calendar
	 * 		users that have delegated their participation in a group-scheduled
	 * 		event or to-do to the calendar user specified by the property.
	 * 		The individual calendar address parameter values MUST each be
	 * 		specified in a quoted-string.
	 * 		
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		delfromparam = "DELEGATED-FROM" "=" DQUOTE cal-address DQUOTE 
	 * 			
	 * 			*("," DQUOTE cal-address DQUOTE)
	 *  
	 * @formatter:on
	 * 	 
	 */
	private String propertyDELFROM = null;

	/**
	 * 	Delegatees
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.5; p. 17 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: DELEGATED-TO
	 * 
	 * 	Purpose: To specify the calendar users to whom the calendar user
	 * 		specified by the property has delegated participation.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 *		CAL-ADDRESS value type. This parameter specifies those calendar
	 *		users whom have been delegated participation in a group-scheduled
	 *		event or to-do by the calendar user specified by the property.
	 *		The individual calendar address parameter values MUST each be
	 *		specified in a quoted-string.
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		deltoparam = "DELEGATED-TO" "=" DQUOTE cal-address DQUOTE
	 *			*("," DQUOTE cal-address DQUOTE)
	 *  
	 * @formatter:on
	 * 	 
	 */
	private String propertyDELTO = null;

	/**
	 *  Directory Entry Reference
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.6; p. 18 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: DIR
	 * 
	 * 	Purpose: To specify reference to a directory entry associated with
	 * 		the calendar user specified by the property.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. The parameter specifies a reference to
	 * 		the directory entry associated with the calendar user specified by
	 * 		the property. The parameter value is a URI. The URI parameter
	 * 		value MUST be specified in a quoted-string.
	 * 			Note: While there is no restriction imposed on the URI schemes
	 * 			allowed for this parameter, CID [RFC2392], DATA [RFC2397], FILE
	 * 			[RFC1738], FTP [RFC1738], HTTP [RFC2616], HTTPS [RFC2818], LDAP
	 * 			[RFC4516], and MID [RFC2392] are the URI schemes most commonly
	 * 			used by current implementations.
	 *  
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		dirparam = "DIR" "=" DQUOTE uri DQUOTE
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyDIR = null;

	/**
	 *  Inline Encoding
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.7; p. 18 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: ENCODING
	 * 
	 *	Purpose: To specify an alternate inline encoding for the property value.
	 *
	 *	Description: This property parameter identifies the inline encoding
	 *		used in a property value. The default encoding is "8BIT",
	 *		corresponding to a property value consisting of text. The
	 *		"BASE64" encoding type corresponds to a property value encoded
	 *		using the "BASE64" encoding defined in [RFC2045].
	 *
	 *		If the value type parameter is ";VALUE=BINARY", then the inline
	 *		encoding parameter MUST be specified with the value
	 *		";ENCODING=BASE64".
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		encodingparam = "ENCODING" "="( 
	 *						  "8BIT"	 	; "8bit" text encoding is defined in [RFC2045]
	 *						/ "BASE64"		; "BASE64" binary encoding format is defined in [RFC4648]
	 *			)
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyENCODING = null;

	/**
	 * 	Free/Busy Time Type
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.9; p. 20 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: FBTYPE
	 * 
	 * 	Purpose: To specify the free or busy time type.
	 * 
	 * 	Description: This parameter specifies the free or busy time type.
	 * 		The value FREE indicates that the time interval is free for
	 * 		scheduling. The value BUSY indicates that the time interval is
	 * 		busy because one or more events have been scheduled for that
	 * 		interval. The value BUSY-UNAVAILABLE indicates that the time
	 * 		interval is busy and that the interval can not be scheduled. The
	 * 		value BUSY-TENTATIVE indicates that the time interval is busy
	 * 		because one or more events have been tentatively scheduled for
	 * 		that interval. If not specified on a property that allows this
	 * 		parameter, the default is BUSY. Applications MUST treat x-name
	 * 		and iana-token values they don’t recognize the same way as they
	 * 		would the BUSY value.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		fbtypeparam = "FBTYPE" "=" ("FREE" / "BUSY" 
	 * 					    /  "BUSY-UNAVAILABLE" / "BUSY-TENTATIVE"
	 * 			
	 * 		Some experimental iCalendar free/busy type.
	 * 					    / x-name
	 * 		 	
	 * 		Some other IANA-registered iCalendar free/busy type.
	 * 						/ iana-token
	 * 		
	 *		 				)
	 * 
	 * @formatter:on
	 * 	 
	 */

	private String propertyFBTYPE = null;

	/**
	 * 	Format Type
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.8; p. 19 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: FMTTYPE
	 * 
	 *	Purpose: To specify the content type of a referenced object.
	 *
	 *	Description: This parameter can be specified on properties that are
	 *		used to reference an object. The parameter specifies the media
	 *		type [RFC4288] of the referenced object. For example, on the
	 *		"ATTACH" property, an FTP type URI value does not, by itself,
	 *		necessarily convey the type of content associated with the
	 *		resource. The parameter value MUST be the text for either an
	 *		IANA-registered media type or a non-standard media type.
	 *	
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		fmttypeparam = "FMTTYPE" "=" type-name "/" subtype-name
	 *
	 *		Where "type-name" and "subtype-name" are defined in Section 4.2 of [RFC4288].
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyFMTTYPE = null;

	/**
	 * 	Language
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.10; p. 21 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: LANGUAGE
	 * 
	 * 	Purpose: To specify the language for text values in a property or
	 * 		property parameter.
	 * 
	 * 	Description: This parameter identifies the language of the text in
	 * 		the property value and of all property parameter values of the
	 * 		property. The value of the "LANGUAGE" property parameter is that
	 * 		defined in [RFC5646].
	 * 
	 * 		For transport in a MIME entity, the Content-Language header field
	 * 		can be used to set the default language for the entire body part.
	 * 		Otherwise, no default language is assumed.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		languageparam = "LANGUAGE" "=" language
	 * 
	 * 			language = Language-Tag		;as defined in [RFC5646].
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyLANGUAGE = null;

	/**
	 * 	Group or List Membership
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.11; p. 21 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: MEMBER
	 * 
	 * 	Purpose: To specify the group or list membership of the calendar
	 * 		user specified by the property.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		memberparam = "MEMBER" "=" DQUOTE cal-address DQUOTE
	 *			*("," DQUOTE cal-address DQUOTE)
	 *
	 *	Description: This parameter can be specified on properties with a
	 *		CAL-ADDRESS value type. The parameter identifies the groups or
	 *		list membership for the calendar user specified by the property.
	 *		The parameter value is either a single calendar address in a
	 *		quoted-string or a COMMA-separated list of calendar addresses,
	 *		each in a quoted-string. The individual calendar address
	 *		parameter values MUST each be specified in a quoted-string.
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyMEMBER = null;

	/**
	 * 	Participation Status
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.12; p. 22 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: PARTSTAT
	 * 
	 * 	Purpose: To specify the participation status for the calendar user
	 * 		specified by the property.
	 *
	 *	Description: This parameter can be specified on properties with a
	 *		CAL-ADDRESS value type. The parameter identifies the
	 *		participation status for the calendar user specified by the
	 *		property value. The parameter values differ depending on whether
	 *		they are associated with a group-scheduled "VEVENT", "VTODO", or
	 *		"VJOURNAL". The values MUST match one of the values allowed for
	 *		the given calendar component. If not specified on a property that
	 *		allows this parameter, the default value is NEEDS-ACTION.
	 *		Applications MUST treat x-name and iana-token values they don’t
	 *		recognize the same way as they would the NEEDS-ACTION value.
	 * 	
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		partstatparam= "PARTSTAT" "="(partstat-event
	 * 									/ partstat-todo
	 * 									/ partstat-jour)
	 * 
	 *		These are the participation statuses for a "VEVENT".
	 *		Default is NEEDS-ACTION.
	 * 			
	 * 			partstat-event = ("NEEDS-ACTION"		;Event needs action
	 * 							/ "ACCEPTED"			;Event accepted
	 *							/ "DECLINED"			;Event declined
	 *							/ "TENTATIVE"			;Event tentatively accepted
	 *							/ "DELEGATED"			;Event delegated
	 *							/ x-name				;Experimental status
	 *							/ iana-token)			;Other IANA-registered status
	 *
	 *		These are the participation statuses for a "VTODO".
	 *		Default is NEEDS-ACTION.
	 *
	 *			partstat-todo = ("NEEDS-ACTION"			;To-do needs action		
	 *							/ "ACCEPTED"			;To-do accepted
	 *							/ "DECLINED"			;To-do declined
	 *							/ "TENTATIVE"			;To-do tentatively accepted
	 *							/ "DELEGATED"			;To-do delegated
	 *							/ "COMPLETED"			;To-do completed
	 *													;COMPLETED property has
	 *													;DATE-TIME completed
	 *							/ "IN-PROCESS"			;To-do in process of 
	 *													;being completed
	 *							/ x-name				;Experimental status
	 *							/ iana-token)			;Other IANA-registered status
	 *
	 *		These are the participation statuses for a "VJOURNAL".
	 *		Default is NEEDS-ACTION.
	 *
	 *			partstat-jour = ("NEEDS-ACTION"			;Journal needs action
	 *							/ "ACCEPTED"			;Journal accepted
	 *							/ "DECLINED"			;Journal declined
	 *							/ x-name				;Experimental status
	 *							/ iana-token)			;Other IANA-registered status
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyPARTSTAT = null;

	/**
	 *	Recurrence Identifier Range
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.13; p. 23
	 * 
	 * @formatter:off
	 *
	 *	Parameter Name: RANGE
	 *
	 *	Purpose: To specify the effective range of recurrence instances from
	 *		the instance specified by the recurrence identifier specified by
	 *		the property.
	 *
	 *	Description: This parameter can be specified on a property that
	 *		specifies a recurrence identifier. The parameter specifies the
	 *		effective range of recurrence instances that is specified by the
	 *		property. The effective range is from the recurrence identifier
	 *		specified by the property. If this parameter is not specified on
	 *		an allowed property, then the default range is the single instance
	 *		specified by the recurrence identifier value of the property. The
	 *		parameter value can only be "THISANDFUTURE" to indicate a range
	 *		defined by the recurrence identifier and all subsequent instances.
	 *		The value "THISANDPRIOR" is deprecated by this revision of
	 *		iCalendar and MUST NOT be generated by applications.
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *		
	 *		rangeparam = "RANGE" "=" "THISANDFUTURE"
	 *
	 *		To specify the instance specified by the recurrence identifier
	 *		and all subsequent recurrence instances.
	 *
	 * @formatter:on
	 * 	 
	 */
	private String propertyRANGE = null;

	/**
	 * 	Alarm Trigger Relationship
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.14; p. 24 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: RELATED
	 * 
	 * 	Purpose: To specify the relationship of the alarm trigger with
	 * 		respect to the start or end of the calendar component.
	 * 
	 * 	Description: This parameter can be specified on properties that
	 * 		specify an alarm trigger with a "DURATION" value type. The
	 * 		parameter specifies whether the alarm will trigger relative to the
	 * 		start or end of the calendar component. The parameter value START
	 * 		will set the alarm to trigger off the start of the calendar
	 * 		component; the parameter value END will set the alarm to trigger
	 * 		off the end of the calendar component. If the parameter is not
	 * 		specified on an allowable property, then the default is START.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 	
	 * 		trigrelparam = "RELATED" "="("START"		;Trigger off of start
	 * 									/ "END")		;Trigger off of end
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyRELATED = null;

	/**
	 * 	Relationship Type
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.15; p. 25 
	 * 
	 * @formatter:off
	 *
	 *	Parameter Name: RELTYPE
	 *
	 *	Purpose: To specify the type of hierarchical relationship associated
	 *		with the calendar component specified by the property.
	 *
	 *	Description: This parameter can be specified on a property that
	 *		references another related calendar. The parameter specifies the
	 *		hierarchical relationship type of the calendar component
	 *		referenced by the property. The parameter value can be 
	 *		PARENT, to indicate that the referenced calendar component is a 
	 *			superior of	calendar component; 
	 *		CHILD to indicate that the referenced calendar component is a 
	 *			subordinate of the calendar component; or 
	 *		SIBLING to indicate that the referenced calendar component is a peer of
	 *			the calendar component. 
	 *		If this parameter is not specified on an allowable property, 
	 *		the default relationship type is PARENT.
	 *		Applications MUST treat x-name and iana-token values they don’t
	 *		recognize the same way as they would the PARENT value.
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		reltypeparam = "RELTYPE" "=" ("PARENT"		;Parent relationship - Default
	 *									/ "CHILD"		;Child relationship
	 *									/ "SIBLING"		;Sibling relationship
	 *
	 *									/ x-name		;A non-standard, experimental
	 *													 relationship type
	 *
	 *									/ iana-token)	;Some other IANA-registered
	 *													;iCalendar relationship type
	 *	
	 * @formatter:on
	 * 	 
	 */
	private String propertyRELTYPE = null;

	/**
	 *  Participation Role
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.16; p. 25 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: ROLE
	 * 
	 * 	Purpose: To specify the participation role for the calendar user
	 * 		specified by the property.
	 *
	 *	Description: This parameter can be specified on properties with a
	 *		CAL-ADDRESS value type. The parameter specifies the participation
	 *		role for the calendar user specified by the property in the group
	 *		schedule calendar component. If not specified on a property that
	 *		allows this parameter, the default value is REQ-PARTICIPANT.
	 *		Applications MUST treat x-name and iana-token values they don’t
	 *		recognize the same way as they would the REQ-PARTICIPANT value.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		roleparam = "ROLE" "=" ("CHAIR"				;Indicates chair of the calendar entity
	 * 							/ "REQ-PARTICIPANT"		;Indicates a participant whose participation
	 * 													;is required
	 * 							/ "OPT-PARTICIPANT"		;Indicates a participant whose participation 
	 * 													;is optional
	 * 							/ "NON-PARTICIPANT"		;Indicates a participant who is copied for 
	 * 													;information purposes only
	 *								
	 *							/ x-name				;Experimental role
	 *
	 *							/ iana-token)			;Other IANA role
	 *
	 *		Default is REQ-PARTICIPANT
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyROLE = null;

	/**
	 * 	RSVP Expectation
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.17; p. 26 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: RSVP
	 * 
	 * 	Purpose: To specify whether there is an expectation of a favor of a
	 * 		reply from the calendar user specified by the property value.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. The parameter identifies the expectation
	 * 		of a reply from the calendar user specified by the property value.
	 * 		This parameter is used by the "Organizer" to request a
	 * 		participation status reply from an "Attendee" of a group-scheduled
	 * 		event or to-do. If not specified on a property that allows this
	 * 		parameter, the default value is FALSE.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		rsvpparam = "RSVP" "=" ("TRUE" / "FALSE")
	 * 		
	 * 		Default is FALSE
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyRSVP = null;

	/**
	 * 	Sent By
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.18; p. 27 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: SENT-BY
	 * 
	 * 	Purpose: To specify the calendar user that is acting on behalf of
	 * 		the calendar user specified by the property.
	 * 
	 * 	Description: This parameter can be specified on properties with a
	 * 		CAL-ADDRESS value type. The parameter specifies the calendar user
	 * 		that is acting on behalf of the calendar user specified by the
	 * 		property. The parameter value MUST be a mailto URI as defined in
	 * 		[RFC2368]. The individual calendar address parameter values MUST
	 * 		each be specified in a quoted-string.
	 *  
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		sentbyparam = "SENT-BY" "=" DQUOTE cal-address DQUOTE
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertySENTBY = null;

	/**
	 * 	Time Zone Identifier
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.19; p. 27
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: TZID
	 * 
	 * 	Purpose: To specify the identifier for the time zone definition for
	 * 		a time component in the property value.
	 * 
	 * 	Description: This parameter MUST be specified on the "DTSTART",
	 * 		"DTEND", "DUE", "EXDATE", and "RDATE" properties when either a
	 * 		DATE-TIME or TIME value type is specified and when the value is
	 * 		neither a UTC or a "floating" time. Refer to the DATE-TIME or
	 * 		TIME value type definition for a description of UTC and "floating
	 * 		time" formats. This property parameter specifies a text value
	 * 		that uniquely identifies the "VTIMEZONE" calendar component to be
	 * 		used when evaluating the time portion of the property. The value
	 * 		of the "TZID" property parameter will be equal to the value of the
	 * 		"TZID" property for the matching time zone definition. An
	 * 		individual "VTIMEZONE" calendar component MUST be specified for
	 * 		each unique "TZID" parameter value specified in the iCalendar
	 * 		object.
	 * 
	 * 		The parameter MUST be specified on properties with a DATE-TIME
	 * 		value if the DATE-TIME is not either a UTC or a "floating" time.
	 * 		Failure to include and follow VTIMEZONE definitions in iCalendar
	 * 		objects may lead to inconsistent understanding of the local time
	 * 		at any given location.
	 * 
	 * 		The presence of the SOLIDUS character as a prefix, indicates that
	 * 		this "TZID" represents a unique ID in a globally defined time zone
	 * 		registry (when such registry is defined).
	 * 			
	 * 			Note: This document does not define a naming convention for
	 * 			time zone identifiers. Implementers may want to use the naming
	 * 			conventions defined in existing time zone specifications such
	 * 			as the public-domain TZ database [TZDB]. The specification of
	 * 			globally unique time zone identifiers is not addressed by this
	 * 			document and is left for future study.
	 * 		
	 * 		The following are examples of this property parameter:
	 * 			
	 * 			DTSTART;TZID=America/New_York:19980119T020000
	 * 			DTEND;TZID=America/New_York:19980119T030000
	 * 
	 * 		The "TZID" property parameter MUST NOT be applied to DATE
	 * 		properties and DATE-TIME or TIME properties whose time values are
	 * 		specified in UTC.
	 * 		
	 * 		The use of local time in a DATE-TIME or TIME value without the
	 * 		"TZID" property parameter is to be interpreted as floating time,
	 * 		regardless of the existence of "VTIMEZONE" calendar components in
	 * 		the iCalendar object.
	 * 		
	 * 		For more information, see the sections on the value types DATE-
	 * 		TIME and TIME.
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		tzidparam = "TZID" "=" [tzidprefix] paramtext
	 * 		
	 * 			tzidprefix = "/"
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyTZID = null;

	/**
	 *	Value Data Types
	 * 
	 * 	RFC 5545 (september 2009) item 3.2.20; p. 29 
	 * 
	 * @formatter:off
	 *
	 *	Parameter Name: VALUE
	 *
	 *	Purpose: To explicitly specify the value type format for a property value.
	 *
	 *	Description: This parameter specifies the value type and format of
	 *		the property value. The property values MUST be of a single value
	 *		type. For example, a "RDATE" property cannot have a combination
	 *		of DATE-TIME and TIME value types.
	 *		
	 *		If the property’s value is the default value type, then this
	 *		parameter need not be specified. However, if the property’s
	 *		default value type is overridden by some other allowable value
	 *		type, then this parameter MUST be specified.
	 *		
	 *		Applications MUST preserve the value data for x-name and iana-
	 *		token values that they don’t recognize without attempting to
	 *		interpret or parse the value data.
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		valuetypeparam = "VALUE" "=" valuetype
	 *
	 *		valuetype = ("BINARY"
	 *					/ "BOOLEAN"
	 *					/ "CAL-ADDRESS"
	 *					/ "DATE"
	 *					/ "DATE-TIME"
	 *					/ "DURATION"
	 *					/ "FLOAT"
	 *					/ "INTEGER"
	 *					/ "PERIOD"
	 *					/ "RECUR"
	 *					/ "TEXT"
	 *					/ "TIME"
	 *					/ "URI"
	 *					/ "UTC-OFFSET"
	 *					/ x-name			;Some experimental iCalendar value type.
	 *					/ iana-token)		;Some other IANA-registered iCalendar value type.
	 * 
	 * @formatter:on
	 * 	 
	 */
	private String propertyVALUETYPE = null;

	private String propertyUNKNOWN = null;

	/**
	 * Konstruktor
	 * 
	 */
	int anzahl = 0;

	protected GuKKiCalProperty(String kennung) {
//		System.out.println("GuKKiCalProperty Konstruktor für " + kennung + " begonnen");
		this.propertyLiteral = kennung;
//		System.out.println("GuKKiCalProperty Konstruktor für " + kennung + " beendet");
	}

	public GuKKiCalProperty(String propertyDaten, String kennung) {
//		System.out.println("GuKKiCalProperty Konstruktor für " + kennung + " begonnen -->" + propertyZeile);
		this.propertyLiteral = kennung;
		if (propertyDaten.substring(kennung.length(), kennung.length() + 1).equals(":")) {
			this.propertyWert = propertyDaten.substring(kennung.length() + 1);
		} else {
			parameterAnalysieren(propertyDaten.substring(kennung.length()));
		}
//		if (propertyUNKNOWN != null) {
//			System.out.println(propertyUNKNOWN);
//		}
//		System.out.println(this);
//		System.out.println("GuKKiCalProperty Konstruktor für " + kennung + " beendet");
	}

	void parameterAnalysieren(String propertyDaten) {
//		System.out.println("GuKKiCalProperty parameterAnalysieren begonnen");
//		System.out.println("Parameterkette=" + propertyDaten);

		boolean backslash = false;
		boolean literal = false;
//		boolean hochkomma = false;
//		boolean doppelpunkt = false;
//		boolean semikolon = false;
		String trenner = "";
		String zeichen = "";
		String parameter = "";

		for (int i = 0; i < propertyDaten.length(); i++) {
//			System.out.println("i=" + i + " Zeichen=" + propertyDaten.substring(i, i + 1));
			zeichen = propertyDaten.substring(i, i + 1);

			switch (zeichen) {

				case ("\\"): {
					if (backslash) {
						backslash = false;
					} else {
						backslash = true;
					}
					parameter += zeichen;
					break;
				}
				case ("\""): {
					if (!backslash) {
						if (!literal) {
							literal = true;
						} else {
							literal = false;
						}
					} else {
						backslash = false;
					}
					parameter += zeichen;
					break;
				}
				case (":"): {
					if (!backslash && !literal && !trenner.equals(":")) {
						if (parameter.length() > 0) {
							parameterBestimmen(parameter, ":");
						}
						trenner = zeichen;
						parameter = "";
					} else {
						parameter += zeichen;
						backslash = false;
					}
					break;
				}
				case (";"): {
					if (!backslash && !literal && !trenner.equals(":")) {
						if (parameter.length() > 0) {
							parameterBestimmen(parameter, ";");
						}
						trenner = zeichen;
						parameter = "";
					} else {
						parameter += zeichen;
						backslash = false;
					}
					break;
				}
				default: {
					parameter += zeichen;
					backslash = false;
				}
			} // Ende switch

//			System.out.println("---->" + parameter);

		} // Ende for-Schleife

		if (parameter.length() > 0) {
			this.propertyWert = parameter;
		}

//		System.out.println("GuKKiCalProperty parameterAnalysieren beendet");
	}

	void parameterBestimmen(String parameter, String trenner) {
//		System.out.println("GuKKiCalProperty parameterBestimmen begonnen");
//		System.out.println("Parameter=" + parameter + " <--> " + trenner);
		if (parameter.length() > 7 && parameter.substring(0, 7).equals("ALTREP=")) {
			this.propertyALTREP = parameter.substring(7);
		} else if (parameter.length() > 3 && parameter.substring(0, 3).equals("CN=")) {
			this.propertyCN = parameter.substring(3);
		} else if (parameter.length() > 7 && parameter.substring(0, 7).equals("CUTYPE=")) {
			this.propertyCUTYPE = parameter.substring(7);
		} else if (parameter.length() > 15 && parameter.substring(0, 15).equals("DELEGATED-FROM=")) {
			this.propertyDELFROM = parameter.substring(15);
		} else if (parameter.length() > 13 && parameter.substring(0, 13).equals("DELEGATED-TO=")) {
			this.propertyDELTO = parameter.substring(13);
		} else if (parameter.length() > 4 && parameter.substring(0, 4).equals("DIR=")) {
			this.propertyDIR = parameter.substring(4);
		} else if (parameter.length() > 9 && parameter.substring(0, 9).equals("ENCODING=")) {
			this.propertyENCODING = parameter.substring(9);
		} else if (parameter.length() > 7 && parameter.substring(0, 7).equals("FBTYPE=")) {
			this.propertyFBTYPE = parameter.substring(7);
		} else if (parameter.length() > 8 && parameter.substring(0, 8).equals("FMTTYPE=")) {
			this.propertyFMTTYPE = parameter.substring(8);
		} else if (parameter.length() > 9 && parameter.substring(0, 9).equals("LANGUAGE=")) {
			this.propertyLANGUAGE = parameter.substring(9);
		} else if (parameter.length() > 7 && parameter.substring(0, 7).equals("MEMBER=")) {
			this.propertyMEMBER = parameter.substring(7);
		} else if (parameter.length() > 9 && parameter.substring(0, 9).equals("PARTSTAT=")) {
			this.propertyPARTSTAT = parameter.substring(9);
		} else if (parameter.length() > 6 && parameter.substring(0, 6).equals("RANGE=")) {
			this.propertyRANGE = parameter.substring(6);
		} else if (parameter.length() > 8 && parameter.substring(0, 8).equals("RELATED=")) {
			this.propertyRELATED = parameter.substring(8);
		} else if (parameter.length() > 8 && parameter.substring(0, 8).equals("RELTYPE=")) {
			this.propertyRELTYPE = parameter.substring(8);
		} else if (parameter.length() > 5 && parameter.substring(0, 5).equals("ROLE=")) {
			this.propertyROLE = parameter.substring(5);
		} else if (parameter.length() > 5 && parameter.substring(0, 5).equals("RSVP=")) {
			this.propertyRSVP = parameter.substring(5);
		} else if (parameter.length() > 8 && parameter.substring(0, 8).equals("SENT-BY=")) {
			this.propertySENTBY = parameter.substring(8);
		} else if (parameter.length() > 5 && parameter.substring(0, 5).equals("TZID=")) {
			this.propertyTZID = parameter.substring(5);
		} else if (parameter.length() > 6 && parameter.substring(0, 6).equals("VALUE=")) {
			this.propertyVALUETYPE = parameter.substring(6);
		} else {
//			System.out.println(trenner + "unbekannter Parameter=" + parameter);
			if (propertyUNKNOWN == null) {
				this.propertyUNKNOWN = parameter + trenner;
			} else {
				this.propertyUNKNOWN += parameter + trenner;
			}
//			System.out.println(propertyUNKNOWN);
		}

//		System.out.println("GuKKiCalProperty parameterBestimmen beendet");
	}

	String getPropertyWert() {
		return propertyWert;
	}

	void setPropertyWert(String propertyWert) {
		this.propertyWert = propertyWert;
	}

	String getPropertyLiteral() {
		return propertyLiteral;
	}

	void setPropertyLiteral(String propertyLiteral) {
		this.propertyLiteral = propertyLiteral;
	}

	String getPropertyUNKNOWN() {
		return propertyUNKNOWN;
	}

	void setPropertyUNKNOWN(String propertyUNKNOWN) {
		this.propertyUNKNOWN = propertyUNKNOWN;
	}

	public String toString() {
		String nz = "\n";
		String ausgabe = "Literal=" + propertyLiteral + nz;
		if (propertyALTREP != null)
			ausgabe += "altrepparm=" + propertyALTREP + nz;
		if (propertyCN != null)
			ausgabe += "cnparam=" + propertyCN + nz;
		if (propertyCUTYPE != null)
			ausgabe += "cutypeparam=" + propertyCUTYPE + nz;
		if (propertyDELFROM != null)
			ausgabe += "delfromparam=" + propertyDELFROM + nz;
		if (propertyDELTO != null)
			ausgabe += "deltoparam=" + propertyDELTO + nz;
		if (propertyDIR != null)
			ausgabe += "dirparam=" + propertyDIR + nz;
		if (propertyENCODING != null)
			ausgabe += "encodingparam=" + propertyENCODING + nz;
		if (propertyFBTYPE != null)
			ausgabe += "fbtypeparam=" + propertyFBTYPE + nz;
		if (propertyFMTTYPE != null)
			ausgabe += "fmttypeparam=" + propertyFMTTYPE + nz;
		if (propertyLANGUAGE != null)
			ausgabe += "languageparam=" + propertyLANGUAGE + nz;
		if (propertyMEMBER != null)
			ausgabe += "memberparam=" + propertyMEMBER + nz;
		if (propertyPARTSTAT != null)
			ausgabe += "partstatparam=" + propertyPARTSTAT + nz;
		if (propertyRANGE != null)
			ausgabe += "rangeparamm=" + propertyRANGE + nz;
		if (propertyRELATED != null)
			ausgabe += "trigrelparam=" + propertyRELATED + nz;
		if (propertyRELTYPE != null)
			ausgabe += "reltypeparam=" + propertyRELTYPE + nz;
		if (propertyROLE != null)
			ausgabe += "roleparam=" + propertyROLE + nz;
		if (propertyRSVP != null)
			ausgabe += "rsvpparam=" + propertyRSVP + nz;
		if (propertySENTBY != null)
			ausgabe += "sentbyparam=" + propertySENTBY + nz;
		if (propertyTZID != null)
			ausgabe += "tzidparam=" + propertyTZID + nz;
		if (propertyVALUETYPE != null)
			ausgabe += "valuetypeparam=" + propertyVALUETYPE + nz;
		if (propertyUNKNOWN != null)
			ausgabe += "other-param=" + propertyUNKNOWN + nz;
		ausgabe += "Wert=" + propertyWert + nz;

		return ausgabe.substring(0, ausgabe.length() - 1);
	}
}
