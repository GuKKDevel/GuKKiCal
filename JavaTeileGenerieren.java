import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class JavaTeileGenerieren {

	/* @formatter:off*/
//
//			Components Registry
//			 
//			RFC 5545
//		
//			+------------+---------+-------------------------+-------------------+
//			| Component  | Status  | Reference       	     | Java-Klasse		 |	
//			+------------+---------+-------------------------+-------------------+
//			|VCALENDAR 	 | Current | RFC 5545, Section 3.4   | GuKKiCalvCalendar |
//			+------------+---------+-------------------------+-------------------+
//			|VEVENT    	 | Current | RFC 5545, Section 3.6.1 | GuKKiCalvEvent    |
//			+------------+---------+-------------------------+-------------------+
//			|VTODO		 | Current | RFC 5545, Section 3.6.2 | GuKKiCalvTodo     |
//			+------------+---------+-------------------------+-------------------+
//			|VJOURNAL 	 | Current | RFC 5545, Section 3.6.3 | GuKKiCalvJournal  |
//			+------------+---------+-------------------------+-------------------+
//			|VALARM		 | Current | RFC 5545, Section 3.6.6 | GuKKiCalvAlarm    |
//			+------------+---------+-------------------------+-------------------+
//			|VTIMEZONE   | Current | RFC 5545, Section 3.6.5 | GuKKiCalvTimezone |
//			+------------+---------+-------------------------+-------------------+
//			|DAYLIGHT    | Current | RFC 5545, Section 3.6.5 | GuKKiCalvDaylight |
//			+------------+---------+-------------------------+-------------------+
//			|STANDARD    | Current | RFC 5545, Section 3.6.5 | GuKKiCalvStandard |
//			+------------+---------+-------------------------+-------------------+
//			|VFREEBUSY   | Current | RFC 5545, Section 3.6.4 | GuKKiCalvFreeBusy |
//			+------------+---------+-------------------------+-------------------+

	public String [] [] component = {					// Liste der einzelnen Komponenten
			{"0", "GuKKiCalvCalendar", "VCALENDAR","iCalendar","4"},
			{"1", "GuKKiCalvEvent", "VEVENT","vEvent","4"},
			{"2", "GuKKiCalvTodo", "VTODO","vTodo","4"},
			{"3", "GuKKiCalvJournal", "VJOURNAL","vJournal","4"},
			{"4", "GuKKiCalvAlarm", "VALARM", "VALARM","4"},
			{"5", "GuKKiCalvTimezone", "VTIMEZONE","vTimezone","4"},
			{"6", "GuKKiCalvDaylight","DAYLIGHT","cDaylight","4"},
			{"7", "GuKKiCalvStandard","STANDARD","cStandard","4"},
			{"8", "GuKKiCalvFreeBusy","VFREEBUSY","vFreeBusy","4"}
	};
	
	public String [] [] [] subcomponent = {				// Liste der zugehörigen Sub-Komponenten
			{{"vEvent","GuKKiCalvEvent","VEVENT"},{"vTodo","GuKKiCalvTodo", "VTODO"},{"vJournal","GuKKiCalvJournal", "VJOURNAL"},
				{"vTimezone","GuKKiCalvTimezone", "VTIMEZONE"},{"vFreeBusy","GuKKiCalvFreeBusy","VFREEBUSY"}},
			{{"vAlarm","GuKKiCalvAlarm", "VALARM"}},
			{{"vAlarm","GuKKiCalvAlarm", "VALARM"}},
			{{"vAlarm","GuKKiCalvAlarm", "VALARM"}},
			{},
			{{"cDaylight","GuKKiCalvDaylight","DAYLIGHT"},{"cStandard","GuKKiCalvStandard","STANDARD"}},
			{},
			{},
			{}
	};
	
	/* @formatter:on */

	/* @formatter:off*/
	
//			Properties Registry
//		
//			RFC 5545, RFC 7986
//		
//			+-------------------+---------------+-------------------------------+-------------------+
//			| Property		   	| Status		| Reference						| 					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| ACTION			| Current		| RFC 5545, Section 3.8.6.1 	| ACTION			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| ATTACH			| Current		| RFC 5545, Section 3.8.1.1  	| ATTACH			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| ATTENDEE			| Current		| RFC 5545, Section 3.8.4.1 	| ATTENDEE			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CALSCALE			| Current		| RFC 5545, Section 3.7.1		| CALSCALE			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CATEGORIES		| Current		| RFC 5545, Section 3.8.1.2  	| CATEGORIES		|
//			| 					| 				| RFC 7986, Section 5.6			|					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CLASS				| Current		| RFC 5545, Section 3.8.1.3  	| CLASS				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| COLOR 			| Current 		| RFC 7986, Section 5.9			| COLOR				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| COMMENT			| Current		| RFC 5545, Section 3.8.1.4  	| COMMENT			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| COMPLETED			| Current		| RFC 5545, Section 3.8.2.1 	| COMPLETED			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CONFERENCE 		| Current 		| RFC 7986, Section 5.11		| CONFERENCE		|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CONTACT			| Current		| RFC 5545, Section 3.8.4.2 	| CONTACT			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| CREATED			| Current		| RFC 5545, Section 3.8.7.1 	| CREATED			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DESCRIPTION		| Current		| RFC 5545, Section 3.8.1.5  	| DESCRIPTION		|
//			| 					|				| RFC 7986, Section 5.2			|					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DTEND				| Current		| RFC 5545, Section 3.8.2.2 	| DTEND				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DTSTAMP			| Current		| RFC 5545, Section 3.8.7.2 	| DTSTAMP			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DTSTART			| Current		| RFC 5545, Section 3.8.2.4 	| DTSTART			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DUE				| Current		| RFC 5545, Section 3.8.2.3 	| DUE				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| DURATION			| Current		| RFC 5545, Section 3.8.2.5 	| DURATION			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| EXDATE			| Current		| RFC 5545, Section 3.8.5.1 	| EXDATE			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| EXRULE			| Deprecated	| [RFC2445], Section 4.8.5.2 	| EXRULE			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| FREEBUSY			| Current		| RFC 5545, Section 3.8.2.6		| FREEBUSY			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| GEO				| Current		| RFC 5545, Section 3.8.1.6  	| GEO				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| IMAGE 			| Current 		| RFC 7986, Section 5.10		| IMAGE				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| LAST-MODIFIED		| Current		| RFC 5545, Section 3.8.7.3 	| LAST_MOD			|
//			|					|				| RFC 7986, Section 5.4			|					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| LOCATION			| Current		| RFC 5545, Section 3.8.1.7  	| LOCATION			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| METHOD			| Current		| RFC 5545, Section 3.7.2	 	| METHOD 			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| NAME 				| Current 		| RFC 7986, Section 5.1			| NAME				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| ORGANIZER			| Current		| RFC 5545, Section 3.8.4.3 	| ORGANIZER			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| PERCENT-COMPLETE 	| Current		| RFC 5545, Section 3.8.1.8  	| PERCENT			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| PRIORITY			| Current		| RFC 5545, Section 3.8.1.9  	| PRIORITY			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| PRODID			| Current		| RFC 5545, Section 3.7.3	 	| PRODID			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| RDATE				| Current		| RFC 5545, Section 3.8.5.2 	| RDATE				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| RECURRENCE-ID		| Current		| RFC 5545, Section 3.8.4.4 	| RECURID			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| REFRESH-INTERVAL 	| Current 		| RFC 7986, Section 5.7			| REFRESH			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| RELATED-TO		| Current		| RFC 5545, Section 3.8.4.5 	| RELATED			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| REPEAT			| Current		| RFC 5545, Section 3.8.6.2 	| REPEAT			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| REQUEST-STATUS	| Current		| RFC 5545, Section 3.8.8.3 	| RSTATUS			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| RESOURCES			| Current		| RFC 5545, Section 3.8.1.10 	| RESOURCES			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| RRULE				| Current		| RFC 5545, Section 3.8.5.3 	| RRULE				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| SEQUENCE			| Current		| RFC 5545, Section 3.8.7.4 	| SEQ				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| SOURCE 			| Current 		| RFC 7986, Section 5.8			| SOURCE			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| STATUS			| Current		| RFC 5545, Section 3.8.1.11 	| STATUS			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| SUMMARY			| Current		| RFC 5545, Section 3.8.1.12 	| SUMMARY			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TRANSP			| Current		| RFC 5545, Section 3.8.2.7 	| TRANSP			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TRIGGER			| Current		| RFC 5545, Section 3.8.6.3 	| TRIGGER			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TZID				| Current		| RFC 5545, Section 3.8.3.1 	| TZID				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TZNAME			| Current		| RFC 5545, Section 3.8.3.2 	| TZNAME			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TZOFFSETFROM		| Current		| RFC 5545, Section 3.8.3.3 	| TZOFFSETFROM		|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TZOFFSETTO		| Current		| RFC 5545, Section 3.8.3.4 	| TZOFFSETTO		|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| TZURL				| Current		| RFC 5545, Section 3.8.3.5 	| TZURL				|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| UID				| Current		| RFC 5545, Section 3.8.4.7 	| UID				|
//			| 					| Current 		| RFC 7986, Section 5.3			|					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| URL				| Current		| RFC 5545, Section 3.8.4.6 	| URL				|
//			|					|				| RFC 7986, Section 5.5			|					|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| VERSION			| Current		| RFC 5545, Section 3.7.4	 	| VERSION			|
//			+-------------------+---------------+-------------------------------+-------------------+
//			| X-    			| Current		| RFC 5545, Section 3.8.8.2. 	| X_PROP			|
//			+-------------------+---------------+-------------------------------+-------------------+
	
//			Crosstable for Components and Properties
//		
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| Property		   	| VCALENDAR	| VEVENT	| VTODO		| VJOURNAL	| VALARM	| VTIMEZONE	| DAYLIGHT	| STANDARD	| VFREEBUSY	|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| ACTION			| 			|			|			|			|	1:1		|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| ATTACH			| 			|	0:n		|	0:n		|	0:n		|	a0:1	|			|			|			|			|
//			| 					| 			|			|			|			|  	e0:n	|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| ATTENDEE			|  			|	0:n		|	0:n		|	0:n		|	e1:n	|			|			|			|	0:n		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CALSCALE			|  	0:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CATEGORIES		|  			|	0:n		|	0:n		|	0:n		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CLASS				|  			|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| COLOR 			|  	0:1		|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| COMMENT			| 			|	0:n		|	0:n		|	0:n		|			|			|			|	0:n		|	0:n		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| COMPLETED			|  			|			|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CONFERENCE 		|  			|	0:n		|	0:n		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CONTACT			|  			|	0:n		|	0:n		|	0:n		|			|			|			|			|	0:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| CREATED			|  			|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DESCRIPTION		|  			|	0:1		|	0:1		|	0:n		|	d1:1	|			|			|			|			|
//			| 					| 			|			|			|			|  	e1:1	|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DTEND				|  			|	0:1		|			|			|			|			|			|			|	0:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DTSTAMP			|  			|	1:1		|	1:1		|	1:1		|			|			|			|			|	1:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DTSTART			|  			|	0:1		|	0:1		|	0:1		|			|			|	1:1		|	1:1		|	0:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DUE				|  			|			|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| DURATION			|  			|	0:1		|	0:1		|			|	0:1*	|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| EXDATE			|  			|	0:n		|	0:n		|	0:n		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| EXRULE			|  			|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| FREEBUSY			|  			|			|			|			|			|			|			|			|	0:n		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| GEO				|  			|	0:1		|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| IMAGE 			|  			|	0:n		|	0:n		|	0:n		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| LAST-MODIFIED		|  	0:1		|	0:1		|	0:1		|	0:1		|			|	0:1		|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| LOCATION			|   		|	0:1		|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| METHOD			|   0:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| NAME 				|   		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| ORGANIZER			|   		|	0:1		|	0:1		|	0:1		|			|			|			|			|	0:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| PERCENT-COMPLETE 	|   		|			|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| PRIORITY			|   		|	0:1		|	0:1		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| PRODID			|   1:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| RDATE				|   		|	0:n		|	0:n		|	0:n		|			|			|	0:n		|	0:n		|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| RECURRENCE-ID		|   		|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| REFRESH-INTERVAL 	|   0:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| RELATED-TO		|   		|	0:n		|	0:n		|	0:n		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| REPEAT			|   		|			|			|			|	0:1*	|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| REQUEST-STATUS	|   		|	0:n		|	0:n		|	0:n		|			|			|			|			|	0:n		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| RESOURCES			|  	 		|	0:n		|	0:n		|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| RRULE				|   		|	0:1		|	0:1		|	0:1		|			|			|	0:1		|	0:1		|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| SEQUENCE			|   		|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| SOURCE 			|   0:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| STATUS			|   		|	0:1		|	0:1		|	0:1		|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| SUMMARY			|  			|	0:1		|	0:1		|	0:1		|	e1:1	|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TRANSP			|   		|	0:1		|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TRIGGER			|   		|			|			|			|	1:1		|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TZID				|   		|			|			|			|			|	1:1		|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TZNAME			|   		|			|			|			|			|			|	0:n		|	0:n		|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TZOFFSETFROM		|   		|			|			|			|			|			|	1:1		|	1:1		|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TZOFFSETTO		|   		|			|			|			|			|			|	1:1		|	1:1		|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| TZURL				|   		|			|			|			|			|	0:1		|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| UID				|   0:1		|	1:1		|	1:1		|	1:1		|			|			|			|			|	1:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| URL				|   0:1		|	0:1		|	0:1		|	0:1		|			|			|			|			|	0:1		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| VERSION			|   1:1		|			|			|			|			|			|			|			|			|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+
//			| X-    			|   0:n		|	0:n		|	0:n		|	0:n		|	0:n		|	0:n		|	0:n		|	0:n		|	0:n		|
//			+-------------------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+-----------+


	public String [] [] property = {					// Liste der einzelnen Properties/Komponente

// Bedeutung der einzelnen Kennziffern:
//		0 String mehrfach (Restinformationen ),
//		1 Object einfach, 
//		2 Object mehrfach,
//		8 String mehrfach,
//      9 String einfach
        /*	  C	  E   T   J   A   Z   D   S   F */
			{"-","-","-","-","1","-","-","-","-","ACTION","ACTION"},
			{"-","2","2","2","2","-","-","-","-","ATTACH","ATTACH"},
			{"-","2","2","2","2","-","-","-","2","ATTENDEE","ATTENDEE"},
			{"1","-","-","-","-","-","-","-","-","CALSCALE","CALSCALE"},
			{"2","2","2","2","-","-","-","-","-","CATEGORIES","CATEGORIES"},
			{"-","1","1","1","-","-","-","-","-","CLASS","CLASS"},
			{"1","1","1","1","-","-","-","-","-","COLOR","COLOR"},
			{"-","2","2","2","-","-","2","2","2","COMMENT","COMMENT"},
			{"-","-","1","-","-","-","-","-","-","COMPLETED","COMPLETED"},
			{"-","2","2","-","-","-","-","-","-","CONFERENCE","CONFERENCE"},
			{"-","2","2","2","-","-","-","-","1","CONTACT","CONTACT"},
			{"-","1","1","1","-","-","-","-","-","CREATED","CREATED"},
			{"2","1","1","2","1","-","-","-","-","DESCRIPTION","DESCRIPTION"},
			{"-","1","-","-","-","-","-","-","1","DTEND","DTEND"},
			{"-","1","1","1","-","-","-","-","1","DTSTAMP","DTSTAMP"},
			{"-","1","1","1","-","-","1","1","1","DTSTART","DTSTART"},
			{"-","-","1","-","-","-","-","-","-","DUE","DUE"},
			{"-","1","1","-","1","-","-","-","-","DURATION","DURATION"},
			{"-","2","2","2","-","-","-","-","-","EXDATE","EXDATE"},
			{"-","-","-","-","-","-","-","-","-","EXRULE","EXRULE"},
			{"-","-","-","-","-","-","-","-","2","FREEBUSY","FREEBUSY"},
			{"-","1","1","-","-","-","-","-","-","GEO","GEO"},
			{"2","2","2","2","-","-","-","-","-","IMAGE","IMAGE"},
			{"1","1","1","1","-","1","-","-","-","LAST_MOD","LAST-MODIFIED"},
			{"-","1","1","-","-","-","-","-","-","LOCATION","LOCATION"},
			{"1","-","-","-","-","-","-","-","-","METHOD","METHOD"},
			{"2","-","-","-","-","-","-","-","-","NAME","NAME"},
			{"-","1","1","1","-","-","-","-","1","ORGANIZER","ORGANIZER"},
			{"-","-","1","-","-","-","-","-","-","PERCENT","PERCENT-COMPLETE"},
			{"-","1","1","-","-","-","-","-","-","PRIORITY","PRIORITY"},
			{"1","-","-","-","-","-","-","-","-","PRODID","PRODID"},
			{"-","2","2","2","-","-","2","2","-","RDATE","RDATE"},
			{"-","1","1","1","-","-","-","-","-","RECURID","RECURRENCE-ID"},
			{"1","-","-","-","-","-","-","-","-","REFRESH","REFRESH-INTERVAL"},
			{"-","2","2","2","-","-","-","-","-","RELATED","RELATED-TO"},
			{"-","-","-","-","1","-","-","-","-","REPEAT","REPEAT"},
			{"-","2","2","2","-","-","-","-","2","RSTATUS","REQUEST-STATUS"},
			{"-","2","2","-","-","-","-","-","-","RESOURCES","RESOURCES"},
			{"-","1","1","1","-","-","1","1","-","RRULE","RRULE"},
			{"-","1","1","1","-","-","-","-","-","SEQ","SEQUENCE"},
			{"1","-","-","-","-","-","-","-","-","SOURCE","SOURCE"},
			{"-","1","1","1","-","-","-","-","-","STATUS","STATUS"},
			{"-","1","1","1","1","-","-","-","-","SUMMARY","SUMMARY"},
			{"-","1","-","-","-","-","-","-","-","TRANSP","TRANSP"},
			{"-","-","-","-","1","-","-","-","-","TRIGGER","TRIGGER"},
			{"-","-","-","-","-","1","-","-","-","TZID","TZID"},
			{"-","-","-","-","-","-","2","2","-","TZNAME","TZNAME"},
			{"-","-","-","-","-","-","1","1","-","TZOFFSETFROM","TZOFFSETFROM"},
			{"-","-","-","-","-","-","1","1","-","TZOFFSETTO","TZOFFSETTO"},
			{"-","-","-","-","-","1","-","-","-","TZURL","TZURL"},
			{"1","1","1","1","-","-","-","-","1","UID","UID"},
			{"1","1","1","1","-","-","-","-","1","URL","URL"},
			{"1","-","-","-","-","-","-","-","-","VERSION","VERSION"}
//			,{"8","8","8","8","8","8","8","8","8","X_PROP","X-"},
//			{"-","-","-","-","-","-","-","-","-","Restinformationen"}
	};
	/* @formatter:on */

	/* @formatter:off*/
//
//	Parameters Registry
//
//	RFC 5545, 
//	RFC 7986
//
//	+----------------+---------+--------------------------+-----------------+
//	| Parameter		 | Status  | Reference		          | Literal		
//	+----------------+---------+--------------------------+-----------------+
//	| ALTREP		 | Current | RFC 5545, Section 3.2.1  | ALTREP		    | 
//	+----------------+---------+--------------------------+-----------------+
//	| CN			 | Current | RFC 5545, Section 3.2.2  | CN				|
//	+----------------+---------+--------------------------+-----------------+
//	| CUTYPE         | Current | RFC 5545, Section 3.2.3  | CUTYPE			|
//	+----------------+---------+--------------------------+-----------------+
//	| DELEGATED-FROM | Current | RFC 5545, Section 3.2.4  | DELFROM			|
//	+----------------+---------+--------------------------+-----------------+
//	| DELEGATED-TO   | Current | RFC 5545, Section 3.2.5  | DELTO			|
//	+----------------+---------+--------------------------+-----------------+
//	| DIR    	 	 | Current | RFC 5545, Section 3.2.6  | DIR				|
//	+----------------+---------+--------------------------+-----------------+
//	| DISPLAY  	 	 | Current | RFC 7986, Section 6.1    | DISPLAY			|
//	+----------------+---------+--------------------------+-----------------+
//	| EMAIL    	 	 | Current | RFC 7986, Section 6.2    | EMAIL			|
//	+----------------+---------+--------------------------+-----------------+
//	| ENCODING		 | Current | RFC 5545, Section 3.2.7  | ENCODING		|
//	+----------------+---------+--------------------------+-----------------+
//	| FBTYPE		 | Current | RFC 5545, Section 3.2.9  | FBTYPE		 	|
//	+----------------+---------+--------------------------+-----------------+
//	| FEATURE 	 	 | Current | RFC 7986, Section 6.3    | FEATURE			|
//	+----------------+---------+--------------------------+-----------------+
//	| FMTTYPE		 | Current | RFC 5545, Section 3.2.8  | FMTTYPE		 	|
//	+----------------+---------+--------------------------+-----------------+
//	| LABEL    	 	 | Current | RFC 7986, Section 6.4    | LABEL			|
//	+----------------+---------+--------------------------+-----------------+
//	| LANGUAGE		 | Current | RFC 5545, Section 3.2.10 | LANGUAGE		|
//	+----------------+---------+--------------------------+-----------------+
//	| MEMBER		 | Current | RFC 5545, Section 3.2.11 | MEMBER		 	|
//	+----------------+---------+--------------------------+-----------------+
//	| PARTSTAT		 | Current | RFC 5545, Section 3.2.12 | PARTSTAT		|
//	+----------------+---------+--------------------------+-----------------+
//	| RANGE			 | Current | RFC 5545, Section 3.2.13 | RANGE			|
//	+----------------+---------+--------------------------+-----------------+
//	| RELATED		 | Current | RFC 5545, Section 3.2.14 | RELATED		 	|
//	+----------------+---------+--------------------------+-----------------+
//	| RELTYPE		 | Current | RFC 5545, Section 3.2.15 | RELTYPE		 	|
//	+----------------+---------+--------------------------+-----------------+
//	| ROLE			 | Current | RFC 5545, Section 3.2.16 | ROLE			|
//	+----------------+---------+--------------------------+-----------------+
//	| RSVP			 | Current | RFC 5545, Section 3.2.17 | RSVP			|
//	+----------------+---------+--------------------------+-----------------+
//	| SENT-BY		 | Current | RFC 5545, Section 3.2.18 | SENTBY	      	|
//	+----------------+---------+--------------------------+-----------------+
//	| TZID			 | Current | RFC 5545, Section 3.2.19 | TZID			|
//	+----------------+---------+--------------------------+-----------------+
//	| VALUE			 | Current | RFC 5545, Section 3.2.20 | VALUE			|
//	+----------------+---------+--------------------------+-----------------+
//	| X- 			 | Current | RFC 5545, Section 3.2.20 | X_PARM			|
//	+----------------+---------+--------------------------+-----------------+
//
// Bedeutung der einzelnen Kennziffern:
//	0 String mehrfach (Restinformationen ),
//  1 String einfach			
//	2 String mehrfach,

	public String [] [] parameter = { 	
			{"1","ALTREP","ALTREP"},  	  
			{"1","CN","CN"},				
			{"1","CUTYPE","CUTYPE"},
			{"1","DELFROM","DELEGATED-FROM"},
			{"1","DELTO","DELEGATED-TO"},
			{"1","DIR","DIR"},
			{"1","DISPLAY","DISPLAY"},
			{"1","EMAIL","EMAIL"},
			{"1","ENCODING","ENCODING"},
			{"1","FBTYPE","FBTYPE"},
			{"1","FEATURE","FEATURE"},
			{"1","FMTTYPE","FMTTYPE"},
			{"1","LABEL","LABEL"},
			{"1","LANGUAGE","LANGUAGE"},
			{"1","MEMBER","MEMBER"},
			{"1","PARTSTAT","PARTSTAT"},
			{"1","RANGE","RANGE"},
			{"1","RELATED","RELATED"},
			{"1","RELTYPE","RELTYPE"},
			{"1","ROLE","ROLE"},
			{"1","RSVP","RSVP"},
			{"1","SENTBY","SENT-BY"},
			{"1","TZID","TZID"},
			{"1","VALUETYPE","VALUE"}
//			,{"2","X_PARM","X-"}
	};
	
	/* @formatter:on */

	String verzeichnis = "../../Git-Repositories/GuKKiCal/IgnoreForGit/";
	String version = "V 0.0.3 " + " (RFC 5545, RFC 7968) "
			+ new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss").format(new Date());

	/**
	 * Konstruktor
	 */
	public JavaTeileGenerieren() {
		generierenPropertyMethodenteile();
		generierenComponentMethodenteile();
	}

	/**
	 * static main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JavaTeileGenerieren temp = new JavaTeileGenerieren();
	}

	private void generierenPropertyMethodenteile() {
		System.out.println("Generieren der Codebestandteile für Klasse GuKKiCalcProperty " + version);

		try (PrintWriter pWriter = new PrintWriter(
				new FileWriter(new File(verzeichnis + "GuKKiCalcProperty-generiert")));) {
			
			System.out.println("Generieren der Methode parameterBestimmen für GuKKiCalcProperty");
			
			pWriter.println("    /**");
			pWriter.println("     * übertragen der Parameter in das korrekte Feld");
			pWriter.println("     * Version " + version);
			pWriter.println("     */");
			pWriter.println("    void parameterBestimmen(String parameter, String trenner) {");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");

			String elseif = "";
			for (String[] strings : parameter) {
				pWriter.println("        " + elseif + "if (parameter.length() > " + (strings[2].length() + 1)
						+ " && parameter.substring(0, " + (strings[2].length() + 1) + ").equals(\"" + strings[2]
						+ "=\")) {");
				if (strings[0].equals("1")) {
					pWriter.println("            this." + strings[1] + " = parameter.substring("
							+ (strings[2].length() + 1) + ");");
				} else if (strings[0].equals("2")) {
					pWriter.println("            this." + strings[1] + "Sammlung.add(parameter + trenner);");
				}
				elseif = "} else ";
			}
			pWriter.println(" ");
			pWriter.println("        /* Abschluss und Fallbackparameter */");
			pWriter.println(" ");
			pWriter.println(
					"        " + elseif + "if (parameter.length() > 2 && parameter.substring(0, 2).equals(\"X-\")) {");
			pWriter.println("            this.X_PARMSammlung.add(parameter + trenner);");
			pWriter.println("        } else {");
			pWriter.println("            this.Restinformationen.add(parameter + trenner);");
			pWriter.println("        }");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
			pWriter.println("    } // Ende der generierten Methode parameterBestimmen für GuKKiCalcProperty " + version);
			pWriter.println(" ");
			
			System.out.println("Generieren der Methode kopieren für GuKKiCalcProperty");

			pWriter.println("    /**");
			pWriter.println("     * Kopieren aller Parameter der Eigenschaft");
			pWriter.println("     * Version " + version);
			pWriter.println("     *");
			pWriter.println("     * @return GuKKiCalcProperty");
			pWriter.println("     */");
			pWriter.println("    protected GuKKiCalcProperty kopieren() {");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");
			pWriter.println("        GuKKiCalcProperty temp = new GuKKiCalcProperty();");
			pWriter.println("        temp.literal = this.literal;");
			pWriter.println("        temp.wert = this.wert;");
			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println("        temp." + strings[1] + " = this." + strings[1] + " == null ? null : this."
							+ strings[1] + ";");
				} else if (strings[0].equals("2")) {
					pWriter.println("        for (String string : " + strings[1] + "Sammlung) {");
					pWriter.println("            temp." + strings[1] + "Sammlung.add(string);");
					pWriter.println("        }");

				}
			}
			pWriter.println(" ");
			pWriter.println("        /* Abschluss und Fallbackparameter */");
			pWriter.println(" ");
			pWriter.println("        for (String X_PARM : this.X_PARMSammlung) {");
			pWriter.println("            temp.X_PARMSammlung.add(X_PARM);");
			pWriter.println("        }");
			pWriter.println("        for (String Restinformation : this.Restinformationen) {");
			pWriter.println("            temp.Restinformationen.add(Restinformation);");
			pWriter.println("        }");
			pWriter.println("        temp.status = GuKKiCalcStatus.KOPIERT;");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
			pWriter.println("        return temp;");
			pWriter.println("    } // Ende der generierten Methode kopieren für GuKKiCalcProperty " + version);
			pWriter.println(" ");

		System.out.println("Generieren der Methode istGleich für GuKKiCalcProperty");

			pWriter.println("    /**");
			pWriter.println("     * Vergleichen aller Parameter des Attributs");
			pWriter.println("     * Version " + version);
			pWriter.println("     *");
			pWriter.println("     * @return boolean");
			pWriter.println("     */");
			pWriter.println("    protected boolean istGleich(Object dasAndere) {");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");
			pWriter.println("        if (!dasAndere.getClass().equals(this.getClass())) {");
			pWriter.println("            return false;");
			pWriter.println("        }");
			pWriter.println("        GuKKiCalcProperty temp = (GuKKiCalcProperty) dasAndere;");
			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println(
							"        if (!((temp." + strings[1] + " == null && this." + strings[1] + " == null)");
					pWriter.println("          || (temp." + strings[1] + " != null && this." + strings[1]
							+ " != null && temp." + strings[1] + ".equals(this." + strings[1] + ")))) {");
					pWriter.println("            return false;");
					pWriter.println("         }");
				} else if (strings[0].equals("2")) {
					pWriter.println("         if (temp." + strings[1] + "Sammlung.size() != this." + strings[1]
							+ "Sammlung.size()) {");
					pWriter.println(" 			  return false;");
					pWriter.println("		  }");
					pWriter.println("        for (int i =0; i < " + strings[1] + "Sammlung.size(); i++) {");
					pWriter.println("            if (!temp." + strings[1] + "Sammlung.get(i).equals(this." + strings[1]
							+ "Sammlung.get(i))) {");
					pWriter.println("                return false; ");
					pWriter.println("            }");
					pWriter.println("        }");
				}
			}
			pWriter.println(" ");
			pWriter.println("        /* Abschluss und Fallbackparameter */");
			pWriter.println(" ");
			pWriter.println("        if (temp.X_PARMSammlung.size() != this.X_PARMSammlung.size()) {");
			pWriter.println(" 			  return false;");
			pWriter.println("        }");
			pWriter.println("        for (int i =0; i < X_PARMSammlung.size(); i++) {");
			pWriter.println("            if (!temp.X_PARMSammlung.get(i).equals(this.X_PARMSammlung.get(i))) {");
			pWriter.println("                return false; ");
			pWriter.println("            }");
			pWriter.println("        }");
			pWriter.println("        if (temp.Restinformationen.size() != this.Restinformationen.size()) {");
			pWriter.println(" 			  return false;");
			pWriter.println("		 }");
			pWriter.println("        for (int i =0; i < Restinformationen.size(); i++) {");
			pWriter.println("            if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {");
			pWriter.println("                return false; ");
			pWriter.println("            }");
			pWriter.println("        }");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
			pWriter.println("        return true;");
			pWriter.println("    } // Ende der generierten Methode istGleich für GuKKiCalcProperty " + version);
			pWriter.println(" ");

			System.out.println("Generieren der Methode ausgeben für GuKKiCalcProperty");

			pWriter.println("    /**");
			pWriter.println("     * Datenstromausgabe aller Parameter des Attributs");
			pWriter.println("     * Version " + version);
			pWriter.println("     *");
			pWriter.println("     * @return String");
			pWriter.println("     */");
			pWriter.println("    protected String ausgeben() {");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");
			pWriter.println("        String schreibText = \"\";");
			pWriter.println("        String sammelText = \"\";");
			pWriter.println("        String endeText = \"\";");
			pWriter.println("        String trenner = \"\";");

			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println("        schreibText += this." + strings[1] + " == null ? \"\" : \";" + strings[2]
							+ "=\" + this." + strings[1] + ";");
				} else if (strings[0].equals("2")) {
					pWriter.println("        sammelText = \"\";");
					pWriter.println("        for (String param : this." + strings[1] + "Sammlung) {");
					pWriter.println("            sammelText += param;");
					pWriter.println("        }");
					pWriter.println("        if (sammelText.length() > 0 ) {");
					pWriter.println("            if (sammelText.substring(sammelText.length()-1).equals(\":\")) {");
					pWriter.println("                endeText = sammelText.substring(0,sammelText.length()-1);");
					pWriter.println("            }");
					pWriter.println("            else {");
					pWriter.println(
							"                schreibText += \";\" + sammelText.substring(0,sammelText.length()-1);");
					pWriter.println("            }");
					pWriter.println("        }");
					pWriter.println("        ");
				}
			}
			pWriter.println(" ");
			pWriter.println("        /* Abschluss und Fallbackparameter */");
			pWriter.println(" ");
			pWriter.println("        sammelText = \"\";");
			pWriter.println("        for (String X_PARM : this.X_PARMSammlung) {");
			pWriter.println("            sammelText += X_PARM;");
			pWriter.println("        }");
			pWriter.println("        if (sammelText.length() > 0 ) {");
			pWriter.println("            if (sammelText.substring(sammelText.length()-1).equals(\":\")) {");
			pWriter.println("                endeText = sammelText.substring(0,sammelText.length()-1);");
			pWriter.println("            }");
			pWriter.println("            else {");
			pWriter.println("                schreibText += \";\" + sammelText.substring(0,sammelText.length()-1);");
			pWriter.println("            }");
			pWriter.println("        }");
			pWriter.println("        sammelText = \"\";");
			pWriter.println("        for (String Restinformation : this.Restinformationen) {");
			pWriter.println("            sammelText += Restinformation;");
			pWriter.println("        }");
			pWriter.println("        if (sammelText.length() > 0 ) {");
			pWriter.println("            if (sammelText.substring(sammelText.length()-1).equals(\":\")) {");
			pWriter.println("                endeText = sammelText.substring(0,sammelText.length()-1);");
			pWriter.println("            }");
			pWriter.println("            else {");
			pWriter.println("                schreibText += \";\" + sammelText.substring(0,sammelText.length()-1);");
			pWriter.println("            }");
			pWriter.println("        }");
			pWriter.println("        if (endeText.length() > 0) {");
			pWriter.println("            schreibText += \";\"+endeText;");
			pWriter.println("        }");
			pWriter.println("        if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
			pWriter.println("        return this.literal + schreibText + \":\" + this.wert;");
			pWriter.println("    } // Ende der generierten Methode ausgeben für GuKKiCalcProperty " + version);
			pWriter.println(" ");

			System.out.println("Generieren der Getter- und Setter-Methoden für GuKKiCalcProperty");
			
			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println("    public String get" + strings[1] + "() {");
					pWriter.println("        return "+ strings[1] +";");
					pWriter.println("    }");
					pWriter.println("    public boolean set" + strings[1] + "(String p" + strings[1] + ") {");
					pWriter.println("        this." + strings[1] + " = p" + strings[1] + ";");
					pWriter.println("        return true;");
					pWriter.println("    }");
					pWriter.println(" ");

				} else if (strings[0].equals("2")) {
					pWriter.println("    public String [] getAll" + strings[1] + "() {");
					pWriter.println("        return "+ strings[1] +"Sammlung.toArray();");
					pWriter.println("    }");
//					pWriter.println("    public boolean set" + strings[1] + "(String p" + strings[1] + ") {");
//					pWriter.println("        this." + strings[1] + " = p" + strings[1] + ";");
//					pWriter.println("        return true;");
//					pWriter.println("    }");
					pWriter.println(" ");
				}
			}
			pWriter.println("    public String [] getAllX_PARM() {");
			pWriter.println("        return X_PARMSammlung.toArray();");
			pWriter.println("    }");
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

		}

	}

	/**
	 * Generiert die Methoden für die einzelnen Komponenten
	 */

	private void generierenComponentMethodenteile() {
		for (int i = 0; i < component.length; i++) {
			System.out.println(" ");
			System.out.println(
					"Generieren Codeteile für " + component[i][2] + " (Klasse " + component[i][1] + ") " + version);

			String elseLit = "";
			/*
			 * Parameterfeld für getrennte Verarbeitung von Komponente und
			 * Subkomponenten
			 */
			String parameter = "this";
			for (int j = 0; j < subcomponent[i].length; j++) {
				parameter += "," + subcomponent[i][j][0];
			}
			System.out.println("Parameter=" + parameter);

			String einzug = "";
			if (component[i][4].equals("4")) {
				einzug = "    ";
			} else if (component[i][4].equals("8")) {
				einzug = "        ";
			}

			try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + component[i][1] + "-generiert"));) {
			System.out.println("Generieren der Methode neueZeile für " + component[i][1]);
				pWriter.println(einzug + "/**");
				pWriter.println(
						einzug + " * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen");
				pWriter.println(einzug + " * untersucht und die jeweilige Eigenschaft wird abgespeichert");

				pWriter.println(einzug + " * Version " + version);
				pWriter.println(einzug + " */");
				pWriter.println(einzug + "protected void neueZeile(String zeile) throws Exception {");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {");
				pWriter.println(einzug + "        logger.log(logLevel, \"begonnen\");");
				pWriter.println(einzug + "    }");

				elseLit = "";

				if (subcomponent[i].length > 0) {
					pWriter.println(einzug + "    if (bearbeiteSubKomponente) {");
					for (int j = 0; j < subcomponent[i].length; j++) {
						pWriter.println(
								einzug + "        " + elseLit + "if (" + subcomponent[i][j][0] + "Bearbeiten) {");
						pWriter.println(
								einzug + "            if (zeile.equals(\"END:" + subcomponent[i][j][2] + "\")) {");
						pWriter.println(einzug + "                " + subcomponent[i][j][0] + "Neu.abschliessen();");
						pWriter.println(einzug + "                " + subcomponent[i][j][0] + "Sammlung.add("
								+ subcomponent[i][j][0] + "Neu);");
						pWriter.println(einzug + "                " + subcomponent[i][j][0] + "Bearbeiten = false;");
						pWriter.println(einzug + "                bearbeiteSubKomponente = false;");
						pWriter.println(einzug + "            }");
						pWriter.println(einzug + "            else {");
						pWriter.println(einzug + "                " + subcomponent[i][j][0] + "Neu.neueZeile(zeile);");
						pWriter.println(einzug + "            }");
						pWriter.println(einzug + "        }");
						elseLit = "else ";
					}
					pWriter.println(einzug + "    }");
					pWriter.println(einzug + "    else {");
					elseLit = "";
					for (int j = 0; j < subcomponent[i].length; j++) {
						pWriter.println(einzug + "        " + elseLit + "if (zeile.equals(\"BEGIN:"
								+ subcomponent[i][j][2] + "\")) {");
						elseLit = "} else ";
						pWriter.println(einzug + "            " + subcomponent[i][j][0] + "Neu = new "
								+ subcomponent[i][j][1] + "();");
						pWriter.println(einzug + "            " + subcomponent[i][j][0] + "Bearbeiten = true;");
						pWriter.println(einzug + "            bearbeiteSubKomponente = true;");
					}
					einzug = einzug + "    ";
				}
				for (int j = 0; j < property.length; j++) {
					if (property[j][i].equals("1")) {
						pWriter.println(einzug + "    " + elseLit + "if (zeile.length() > " + property[j][10].length()
								+ " && zeile.substring(0, " + property[j][10].length() + ").equals(\"" + property[j][10]
								+ "\")) {");
						pWriter.println(einzug + "        " + property[j][9] + " = new GuKKiCalcProperty(zeile, \""
								+ property[j][10] + "\");");
						elseLit = "} else ";
					} else if (property[j][i].equals("2")) {
						pWriter.println(einzug + "    " + elseLit + "if (zeile.length() > " + property[j][10].length()
								+ " && zeile.substring(0, " + property[j][10].length() + ").equals(\"" + property[j][10]
								+ "\")) {");
						pWriter.println(einzug + "        " + property[j][9]
								+ "Sammlung.add(new GuKKiCalcProperty(zeile, \"" + property[j][10] + "\"));");
						elseLit = "} else ";
					} else if (property[j][i].equals("8")) {
						pWriter.println(einzug + "    " + elseLit + " if (zeile.length() > " + property[j][10].length()
								+ " && zeile.substring(0, " + property[j][10].length() + ").equals(\"" + property[j][10]
								+ "\")) {");
						pWriter.println(einzug + "        " + property[j][9] + "Sammlung.add(zeile);");
						elseLit = "} else ";
					} else if (property[j][i].equals("9")) {
						pWriter.println(einzug + "    " + elseLit + " if (zeile.length() > " + property[j][10].length()
								+ " && zeile.substring(0, " + property[j][10].length() + ").equals(\"" + property[j][10]
								+ "\")) {");
						pWriter.println(einzug + "        " + property[j][9] + " = zeile;");
						elseLit = "} else ";
					}
				}
				pWriter.println(" ");
				pWriter.println("/* Abschluss und Fallbackparameter */");
				pWriter.println(" ");
				pWriter.println(
						einzug + "    " + elseLit + "if (zeile.length() > 2 && zeile.substring(0,2).equals(\"X-\")) {");
				pWriter.println(einzug + "        X_PROPSammlung.add(zeile);");
//				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    } else {");
				pWriter.println(einzug + "        Restinformationen.add(zeile);");
				pWriter.println(einzug + "    }");
				if (subcomponent[i].length > 0) {
					pWriter.println(einzug + "}");
				}
				pWriter.println("        if (logger.isLoggable(logLevel)) {");
				pWriter.println("            logger.log(logLevel, \"beendet\");");
				pWriter.println("        }");
				pWriter.println("    } // Ende neueZeile " + version);
				pWriter.println(" ");
//			} catch (IOException ioe) {
//				ioe.printStackTrace();
//			} finally {
//
//			}



			System.out.println("Generieren der Methode kopieren für " + component[i][1]);
//			try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + component[i][1] + "-kopieren"));) {
				pWriter.println(einzug + "/**");
				pWriter.println(einzug + " * Diese Methode kopiert die iCalendar-Komponente");
				pWriter.println(einzug + " * " + component[i][1] + " und gibt diese Kopie zurück");
				pWriter.println(einzug + " * Version " + version);
				pWriter.println(einzug + " */");
				pWriter.println(einzug + "protected " + component[i][1] + " kopieren() {");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");
				pWriter.println(einzug + "    " + component[i][1] + " temp = new " + component[i][1] + "();");
				pWriter.println(einzug + "    temp.kennung = this.kennung;");
				for (int j = 0; j < property.length; j++) {
					if (property[j][i].equals("1")) {
						pWriter.println(einzug + "    temp." + property[j][9] + " = this." + property[j][9]
								+ " == null ? null : this." + property[j][9] + ".kopieren();");
					} else if (property[j][i].equals("2")) {
						pWriter.println(einzug + "    for (GuKKiCalcProperty " + property[j][9] + " : " + property[j][9]
								+ "Sammlung) {");
						pWriter.println(einzug + "        temp." + property[j][9] + "Sammlung.add(" + property[j][9]
								+ ".kopieren());");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("8")) {
						pWriter.println(
								einzug + "    for (String " + property[j][9] + " : " + property[j][9] + "Sammlung) {");
						pWriter.println(
								einzug + "        temp." + property[j][9] + "Sammlung.add(" + property[j][9] + ");");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("9")) {
						pWriter.println(einzug + "    temp." + property[j][9] + " = this." + property[j][9]
								+ " == null ? null : this." + property[j][9] + ";");
					}
				}
				for (int j = 0; j < subcomponent[i].length; j++) {
					pWriter.println(einzug + "    for (" + subcomponent[i][j][1] + " " + subcomponent[i][j][0]
							+ " : this." + subcomponent[i][j][0] + "Sammlung) {");
					pWriter.println(einzug + "        temp." + subcomponent[i][j][0] + "Sammlung.add("
							+ subcomponent[i][j][0] + ".kopieren());");
					pWriter.println(einzug + "    }");
				}
				pWriter.println(" ");
				pWriter.println("/* Abschluss und Fallbackparameter */");
				pWriter.println(" ");
				pWriter.println(einzug + "    for (String X_PROP : this.X_PROPSammlung) {");
				pWriter.println(einzug + "        temp.X_PROPSammlung.add(X_PROP);");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    for (String Restinformation : this.Restinformationen) {");
				pWriter.println(einzug + "        temp.Restinformationen.add(Restinformation);");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    temp.status = GuKKiCalcStatus.KOPIERT;");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
				pWriter.println(einzug + "    return temp;");
				pWriter.println(einzug + "} // Ende kopieren " + version);

//			} catch (IOException ioe) {
//				ioe.printStackTrace();
//			} finally {
//
//			}

			System.out.println("Generieren der Methode istGleich für " + component[i][1]);
//			try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + component[i][1] + "-istGleich"));) {

				pWriter.println(einzug + "/**");
				pWriter.println(einzug + " * Vergleichen aller Attribute der Komponente " + component[i][1]);
				pWriter.println(einzug + " * Version " + version);
				pWriter.println(einzug + " *");
				pWriter.println(einzug + " * @return boolean");
				pWriter.println(einzug + " */");
				pWriter.println(einzug + "protected boolean istGleich(Object dasAndere) {");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"begonnen\");}");
				pWriter.println(einzug + "    if (!dasAndere.getClass().equals(this.getClass())) {");
				pWriter.println(einzug + "        return false;");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    " + component[i][1] + " temp = (" + component[i][1] + ") dasAndere;");
// Vergleichen der einzelnen Eigenschaften
				for (int j = 0; j < property.length; j++) {
					if (property[j][i].equals("1")) {
						pWriter.println(einzug + "    if (!((temp." + property[j][9] + " == null && this."
								+ property[j][9] + " == null)");
						pWriter.println(einzug + "            || (temp." + property[j][9] + " != null && this."
								+ property[j][9] + " != null && temp." + property[j][9] + ".istGleich(this."
								+ property[j][9] + ")))) {");
						pWriter.println(einzug + "        return false;");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("2")) {
						pWriter.println(einzug + "    if (temp." + property[j][9] + "Sammlung.size() != this."
								+ property[j][9] + "Sammlung.size()) {");
						pWriter.println(einzug + "        return false;");
						pWriter.println(einzug + "    }");
						pWriter.println(
								einzug + "    for (int i = 0;i < " + property[j][9] + "Sammlung.size(); i++) {");
						pWriter.println(einzug + "        if (!temp." + property[j][9]
								+ "Sammlung.get(i).istGleich(this." + property[j][9] + "Sammlung.get(i))) {");
						pWriter.println(einzug + "            return false;");
						pWriter.println(einzug + "        }");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("8")) {
						pWriter.println(einzug + "    if (temp." + property[j][9] + "Sammlung.size() != this."
								+ property[j][9] + "Sammlung.size()) {");
						pWriter.println(einzug + "        return false;");
						pWriter.println(einzug + "    }");
						pWriter.println(
								einzug + "    for (int i = 0;i < " + property[j][9] + "Sammlung.size(); i++) {");
						pWriter.println(einzug + "        if (!temp." + property[j][9] + "Sammlung.get(i).equals(this."
								+ property[j][9] + "Sammlung.get(i))) {");
						pWriter.println(einzug + "           return false;");
						pWriter.println(einzug + "        }");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("9")) {
						pWriter.println(einzug + "    if (!((temp." + property[j][9] + " == null && this."
								+ property[j][9] + " == null)");
						pWriter.println(einzug + "            || (temp." + property[j][9] + " != null && this."
								+ property[j][9] + " != null && temp." + property[j][9] + ".equals(this."
								+ property[j][9] + ")))) {");
						pWriter.println(einzug + "        return false;");
						pWriter.println(einzug + "    }");
					}
				}
// Vergleichen der einzelnen Subkomponenten
				for (int j = 0; j < subcomponent[i].length; j++) {
					pWriter.println(einzug + "    if (temp." + subcomponent[i][j][0] + "Sammlung.size() != this."
							+ subcomponent[i][j][0] + "Sammlung.size()) {");
					pWriter.println(einzug + "        return false;");
					pWriter.println(einzug + "    }");
					pWriter.println(
							einzug + "    for (int i = 0; i < " + subcomponent[i][j][0] + "Sammlung.size(); i++) {");
					pWriter.println(einzug + "        if (!temp." + subcomponent[i][j][0]
							+ "Sammlung.get(i).istGleich(this." + subcomponent[i][j][0] + "Sammlung.get(i))) {");
					pWriter.println(einzug + "            return false;");
					pWriter.println(einzug + "        }");
					pWriter.println(einzug + "    }");
				}
				pWriter.println(" ");
				pWriter.println("/* Abschluss und Fallbackparameter */");
				pWriter.println(" ");
				pWriter.println(einzug + "    if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {");
				pWriter.println(einzug + "        return false;");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    for (int i = 0; i < X_PROPSammlung.size(); i++) {");
				pWriter.println(
						einzug + "        if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {");
				pWriter.println(einzug + "            return false; ");
				pWriter.println(einzug + "        }");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    if (temp.Restinformationen.size() != this.Restinformationen.size()) {");
				pWriter.println(einzug + "        return false;");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    for (int i = 0; i < Restinformationen.size(); i++) {");
				pWriter.println(
						einzug + "        if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {");
				pWriter.println(einzug + "            return false; ");
				pWriter.println(einzug + "        }");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {logger.log(logLevel, \"beendet\");}");
				pWriter.println(einzug + "    return true;");
				pWriter.println(einzug + "} // Ende istGleich " + version);
				pWriter.println(" ");
//			} catch (IOException ioe) {
//				ioe.printStackTrace();
//			} finally {
//
//			}
			
			System.out.println("Generieren der Methode ausgeben für " + component[i][1]);
//			try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + component[i][1] + "-ausgeben"));) {
				elseLit = "";
				pWriter.println(einzug + "/**");
				pWriter.println(einzug
						+ " * Mit dieser Methode werden die einzelnen Eigenschaften als gültige Parameterkette ausgegeben");
				pWriter.println(einzug + " * Version " + version);
				pWriter.println(einzug + " */");
				pWriter.println(einzug + "protected String ausgeben() throws Exception {");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {");
				pWriter.println(einzug + "        logger.log(logLevel, \"begonnen\");");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    String componentDatenstrom = ausgebenInDatenstrom(\"BEGIN:"
						+ component[i][2] + "\");");
				for (int j = 0; j < property.length; j++) {
					if (property[j][i].equals("1")) {
						pWriter.println(einzug + "    componentDatenstrom +=  this." + property[j][9]
								+ " == null ? \"\" : ausgebenInDatenstrom(this." + property[j][9] + ".ausgeben());");
					} else if (property[j][i].equals("2")) {
						pWriter.println(einzug + "    for (GuKKiCalcProperty " + property[j][9] + " : " + property[j][9]
								+ "Sammlung) {");
						pWriter.println(einzug + "        componentDatenstrom += ausgebenInDatenstrom("
								+ property[j][9] + ".ausgeben());");
						pWriter.println(einzug + "    }");
					} else if (property[j][i].equals("8")) {
						pWriter.println(einzug + "    for (GuKKiCalcProperty " + property[j][9] + " : " + property[j][9]
								+ "Sammlung) {");
						pWriter.println(einzug + "        componentDatenstrom += ausgebenInDatenstrom(ausgeben(this."
								+ property[j][9] + "));");
						pWriter.println(einzug + "    }");
						pWriter.println(einzug + "    componentDatenstrom += ausgebenInDatenstrom(ausgeben("
								+ property[j][9] + ");");
					} else if (property[j][i].equals("9")) {
						pWriter.println(einzug + "    componentDatenstrom +=  this." + property[j][9]
								+ " == null ? \"\" : ausgebenInDatenstrom(ausgeben(this." + property[j][9] + "));");
					}
				}
				for (int j = 0; j < subcomponent[i].length; j++) {
					pWriter.println(einzug + "    for (" + subcomponent[i][j][1] + " " + subcomponent[i][j][0]
							+ " : this." + subcomponent[i][j][0] + "Sammlung) {");
					pWriter.println(
							einzug + "        componentDatenstrom += " + subcomponent[i][j][0] + ".ausgeben();");
					pWriter.println(einzug + "    }");
				}
				pWriter.println(" ");
				pWriter.println("/* Abschluss und Fallbackparameter */");
				pWriter.println(" ");
				pWriter.println(einzug + "    for (String X_PROP : this.X_PROPSammlung) {");
				pWriter.println(einzug + "        componentDatenstrom += ausgebenInDatenstrom(X_PROP);");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    for (String Restinformation : this.Restinformationen) {");
				pWriter.println(einzug + "        componentDatenstrom += ausgebenInDatenstrom(Restinformation);");
				pWriter.println(einzug + "    }");
				pWriter.println(
						einzug + "    componentDatenstrom += ausgebenInDatenstrom(\"END:" + component[i][2] + "\");");
				pWriter.println(einzug + "    if (logger.isLoggable(logLevel)) {");
				pWriter.println(einzug + "        logger.log(logLevel, \"beendet\");");
				pWriter.println(einzug + "    }");
				pWriter.println(einzug + "    return componentDatenstrom;");
				pWriter.println(einzug + "} // Ende ausgeben " + version);
				pWriter.println(" ");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {

			}
		}
	}
}
