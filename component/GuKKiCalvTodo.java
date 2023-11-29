package component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import enumerations.*;
import exceptions.*;

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
public class GuKKiCalvTodo extends GuKKiCalvComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	private GuKKiCalcProperty DTSTAMP = null;
	private GuKKiCalcProperty UID = null;
	/*
	 * The following are OPTIONAL, but MUST NOT occur more than once.
	 */
	private GuKKiCalcProperty CLASS = null;
	private GuKKiCalcProperty COLOR = null;
	private GuKKiCalcProperty COMPLETED = null;
	private GuKKiCalcProperty CREATED = null;
	private GuKKiCalcProperty DESCRIPTION = null;
	private GuKKiCalcProperty DTSTART = null;
	private GuKKiCalcProperty GEO = null;
	private GuKKiCalcProperty LAST_MOD = null;
	private GuKKiCalcProperty LOCATION = null;
	private GuKKiCalcProperty ORGANIZER = null;
	private GuKKiCalcProperty PERCENT = null;
	private GuKKiCalcProperty PRIORITY = null;
	private GuKKiCalcProperty RECURID = null;
	private GuKKiCalcProperty SEQ = null;
	private GuKKiCalcProperty STATUS = null;
	private GuKKiCalcProperty SUMMARY = null;
	private GuKKiCalcProperty URL = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalcProperty RRULE = null;
	/*
	 * Either ’dtend’ or ’duration’ MAY appear in a ’todoprop’, but ’dtend’ and
	 * ’duration’ MUST NOT occur in the same ’todoprop’.
	 */
	private GuKKiCalcProperty DUE = null;
	// TODO vTodoDUE
	private GuKKiCalcProperty DURATION = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private List<GuKKiCalcProperty> ATTACHSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> ATTENDEESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> CATEGORIESSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> CONFERENCESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> COMMENTSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> CONTACTSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> EXDATESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> IMAGESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RSTATUSSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RELATEDSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RESOURCESSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RDATESammlung = new LinkedList<GuKKiCalcProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private List<String> X_PROPSammlung = new LinkedList<String>();
	private List<String> Restinformationen = new LinkedList<String>();
	/*
	 * Sammlungen der VTODO-Komponenten
	 */
	private List<GuKKiCalvAlarm> vAlarmSammlung = new LinkedList<GuKKiCalvAlarm>();

	/*
	 * allgemeine Variablen
	 */
	
	/**
	 * GuKKiCalvTodo bildet die iCal-Komponente VTODO ab
	 * 
	 * @author GuKKDevel
	 */
	public GuKKiCalvTodo() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.kennung = GuKKiCalcKennung.TODO;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende GuKKiCalvTodo()

	protected void abschliessen(String pName) throws Exception {
		String tempUID = this.UID == null ? "" : this.UID.getWert();
		String tempSEQ = this.SEQ == null ? "" : this.SEQ.getWert();
		String tempRECURID = this.RECURID == null ? "" : this.RECURID.getWert();
		this.schluessel = new GuKKiCalcSchluessel(this.kennung, pName, tempUID, tempSEQ, tempRECURID);
		for (Iterator iterator = vAlarmSammlung.iterator(); iterator.hasNext();) {
			GuKKiCalvAlarm vAlarm = (GuKKiCalvAlarm) iterator.next();
			vAlarm.abschliessen(pName, tempUID, tempSEQ, tempRECURID);
		}
		status = GuKKiCalcStatus.GELESEN;
	}

	/**
	 * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten)
	 * Zeilen untersucht und die jeweilige Eigenschaft wird abgespeichert
	 * Version V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22
	 */
	protected void neueZeile(String zeile) /* throws GuKKiCalException */ {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (bearbeiteSubKomponente) {
			if (vAlarmBearbeiten) {
				if (zeile.equals("END:VALARM")) {
//                    vAlarmNeu.abschliessen();
					vAlarmSammlung.add(vAlarmNeu);
					vAlarmBearbeiten = false;
					bearbeiteSubKomponente = false;
				} else {
					vAlarmNeu.neueZeile(zeile);
				}
			}
		} else {
			if (zeile.equals("BEGIN:VALARM")) {
				vAlarmNeu = new GuKKiCalvAlarm();
				vAlarmBearbeiten = true;
				bearbeiteSubKomponente = true;
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
				ATTACHSammlung.add(new GuKKiCalcProperty(zeile, "ATTACH"));
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
				ATTENDEESammlung.add(new GuKKiCalcProperty(zeile, "ATTENDEE"));
			} else if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
				CATEGORIESSammlung.add(new GuKKiCalcProperty(zeile, "CATEGORIES"));
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("CLASS")) {
				CLASS = new GuKKiCalcProperty(zeile, "CLASS");
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
				COLOR = new GuKKiCalcProperty(zeile, "COLOR");
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
				COMMENTSammlung.add(new GuKKiCalcProperty(zeile, "COMMENT"));
			} else if (zeile.length() > 9 && zeile.substring(0, 9).equals("COMPLETED")) {
				COMPLETED = new GuKKiCalcProperty(zeile, "COMPLETED");
			} else if (zeile.length() > 10 && zeile.substring(0, 10).equals("CONFERENCE")) {
				CONFERENCESammlung.add(new GuKKiCalcProperty(zeile, "CONFERENCE"));
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
				CONTACTSammlung.add(new GuKKiCalcProperty(zeile, "CONTACT"));
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CREATED")) {
				CREATED = new GuKKiCalcProperty(zeile, "CREATED");
			} else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
				DESCRIPTION = new GuKKiCalcProperty(zeile, "DESCRIPTION");
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
				DTSTAMP = new GuKKiCalcProperty(zeile, "DTSTAMP");
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
				DTSTART = new GuKKiCalcProperty(zeile, "DTSTART");
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("DUE")) {
				DUE = new GuKKiCalcProperty(zeile, "DUE");
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("DURATION")) {
				DURATION = new GuKKiCalcProperty(zeile, "DURATION");
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("EXDATE")) {
				EXDATESammlung.add(new GuKKiCalcProperty(zeile, "EXDATE"));
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("GEO")) {
				GEO = new GuKKiCalcProperty(zeile, "GEO");
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
				IMAGESammlung.add(new GuKKiCalcProperty(zeile, "IMAGE"));
			} else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
				LAST_MOD = new GuKKiCalcProperty(zeile, "LAST-MODIFIED");
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("LOCATION")) {
				LOCATION = new GuKKiCalcProperty(zeile, "LOCATION");
			} else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
				ORGANIZER = new GuKKiCalcProperty(zeile, "ORGANIZER");
			} else if (zeile.length() > 16 && zeile.substring(0, 16).equals("PERCENT-COMPLETE")) {
				PERCENT = new GuKKiCalcProperty(zeile, "PERCENT-COMPLETE");
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("PRIORITY")) {
				PRIORITY = new GuKKiCalcProperty(zeile, "PRIORITY");
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
				RDATESammlung.add(new GuKKiCalcProperty(zeile, "RDATE"));
			} else if (zeile.length() > 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
				RECURID = new GuKKiCalcProperty(zeile, "RECURRENCE-ID");
			} else if (zeile.length() > 10 && zeile.substring(0, 10).equals("RELATED-TO")) {
				RELATEDSammlung.add(new GuKKiCalcProperty(zeile, "RELATED-TO"));
			} else if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
				RSTATUSSammlung.add(new GuKKiCalcProperty(zeile, "REQUEST-STATUS"));
			} else if (zeile.length() > 9 && zeile.substring(0, 9).equals("RESOURCES")) {
				RESOURCESSammlung.add(new GuKKiCalcProperty(zeile, "RESOURCES"));
			} else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
				RRULE = new GuKKiCalcProperty(zeile, "RRULE");
			} else if (zeile.length() > 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
				SEQ = new GuKKiCalcProperty(zeile, "SEQUENCE");
			} else if (zeile.length() > 6 && zeile.substring(0, 6).equals("STATUS")) {
				STATUS = new GuKKiCalcProperty(zeile, "STATUS");
			} else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
				SUMMARY = new GuKKiCalcProperty(zeile, "SUMMARY");
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
				UID = new GuKKiCalcProperty(zeile, "UID");
			} else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
				URL = new GuKKiCalcProperty(zeile, "URL");

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
	 * Diese Methode kopiert die iCalendar-Komponente GuKKiCalvTodo und gibt
	 * diese Kopie zurück Version V 0.0.3 (RFC 5545, RFC 7968)
	 * 2021-12-22T15-12-22
	 */
	protected GuKKiCalvTodo kopieren() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		GuKKiCalvTodo temp = new GuKKiCalvTodo();
		temp.kennung = this.kennung;
		for (GuKKiCalcProperty ATTACH : ATTACHSammlung) {
			temp.ATTACHSammlung.add(ATTACH.kopieren());
		}
		for (GuKKiCalcProperty ATTENDEE : ATTENDEESammlung) {
			temp.ATTENDEESammlung.add(ATTENDEE.kopieren());
		}
		for (GuKKiCalcProperty CATEGORIES : CATEGORIESSammlung) {
			temp.CATEGORIESSammlung.add(CATEGORIES.kopieren());
		}
		temp.CLASS = this.CLASS == null ? null : this.CLASS.kopieren();
		temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
		for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
			temp.COMMENTSammlung.add(COMMENT.kopieren());
		}
		temp.COMPLETED = this.COMPLETED == null ? null : this.COMPLETED.kopieren();
		for (GuKKiCalcProperty CONFERENCE : CONFERENCESammlung) {
			temp.CONFERENCESammlung.add(CONFERENCE.kopieren());
		}
		for (GuKKiCalcProperty CONTACT : CONTACTSammlung) {
			temp.CONTACTSammlung.add(CONTACT.kopieren());
		}
		temp.CREATED = this.CREATED == null ? null : this.CREATED.kopieren();
		temp.DESCRIPTION = this.DESCRIPTION == null ? null : this.DESCRIPTION.kopieren();
		temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
		temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
		temp.DUE = this.DUE == null ? null : this.DUE.kopieren();
		temp.DURATION = this.DURATION == null ? null : this.DURATION.kopieren();
		for (GuKKiCalcProperty EXDATE : EXDATESammlung) {
			temp.EXDATESammlung.add(EXDATE.kopieren());
		}
		temp.GEO = this.GEO == null ? null : this.GEO.kopieren();
		for (GuKKiCalcProperty IMAGE : IMAGESammlung) {
			temp.IMAGESammlung.add(IMAGE.kopieren());
		}
		temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
		temp.LOCATION = this.LOCATION == null ? null : this.LOCATION.kopieren();
		temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
		temp.PERCENT = this.PERCENT == null ? null : this.PERCENT.kopieren();
		temp.PRIORITY = this.PRIORITY == null ? null : this.PRIORITY.kopieren();
		for (GuKKiCalcProperty RDATE : RDATESammlung) {
			temp.RDATESammlung.add(RDATE.kopieren());
		}
		temp.RECURID = this.RECURID == null ? null : this.RECURID.kopieren();
		for (GuKKiCalcProperty RELATED : RELATEDSammlung) {
			temp.RELATEDSammlung.add(RELATED.kopieren());
		}
		for (GuKKiCalcProperty RSTATUS : RSTATUSSammlung) {
			temp.RSTATUSSammlung.add(RSTATUS.kopieren());
		}
		for (GuKKiCalcProperty RESOURCES : RESOURCESSammlung) {
			temp.RESOURCESSammlung.add(RESOURCES.kopieren());
		}
		temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
		temp.SEQ = this.SEQ == null ? null : this.SEQ.kopieren();
		temp.STATUS = this.STATUS == null ? null : this.STATUS.kopieren();
		temp.SUMMARY = this.SUMMARY == null ? null : this.SUMMARY.kopieren();
		temp.UID = this.UID == null ? null : this.UID.kopieren();
		temp.URL = this.URL == null ? null : this.URL.kopieren();
		for (GuKKiCalvAlarm vAlarm : this.vAlarmSammlung) {
			temp.vAlarmSammlung.add(vAlarm.kopieren());
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
	 * Vergleichen aller Attribute der Komponente GuKKiCalvTodo Version V 0.0.3
	 * (RFC 5545, RFC 7968) 2021-12-22T15-12-22
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
		GuKKiCalvTodo temp = (GuKKiCalvTodo) dasAndere;
		if (temp.ATTACHSammlung.size() != this.ATTACHSammlung.size()) {
			return false;
		}
		for (int i = 0; i < ATTACHSammlung.size(); i++) {
			if (!temp.ATTACHSammlung.get(i).istGleich(this.ATTACHSammlung.get(i))) {
				return false;
			}
		}
		if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
			return false;
		}
		for (int i = 0; i < ATTENDEESammlung.size(); i++) {
			if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
				return false;
			}
		}
		if (temp.CATEGORIESSammlung.size() != this.CATEGORIESSammlung.size()) {
			return false;
		}
		for (int i = 0; i < CATEGORIESSammlung.size(); i++) {
			if (!temp.CATEGORIESSammlung.get(i).istGleich(this.CATEGORIESSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.CLASS == null && this.CLASS == null)
				|| (temp.CLASS != null && this.CLASS != null && temp.CLASS.istGleich(this.CLASS)))) {
			return false;
		}
		if (!((temp.COLOR == null && this.COLOR == null)
				|| (temp.COLOR != null && this.COLOR != null && temp.COLOR.istGleich(this.COLOR)))) {
			return false;
		}
		if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
			return false;
		}
		for (int i = 0; i < COMMENTSammlung.size(); i++) {
			if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.COMPLETED == null && this.COMPLETED == null)
				|| (temp.COMPLETED != null && this.COMPLETED != null && temp.COMPLETED.istGleich(this.COMPLETED)))) {
			return false;
		}
		if (temp.CONFERENCESammlung.size() != this.CONFERENCESammlung.size()) {
			return false;
		}
		for (int i = 0; i < CONFERENCESammlung.size(); i++) {
			if (!temp.CONFERENCESammlung.get(i).istGleich(this.CONFERENCESammlung.get(i))) {
				return false;
			}
		}
		if (temp.CONTACTSammlung.size() != this.CONTACTSammlung.size()) {
			return false;
		}
		for (int i = 0; i < CONTACTSammlung.size(); i++) {
			if (!temp.CONTACTSammlung.get(i).istGleich(this.CONTACTSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.CREATED == null && this.CREATED == null)
				|| (temp.CREATED != null && this.CREATED != null && temp.CREATED.istGleich(this.CREATED)))) {
			return false;
		}
		if (!((temp.DESCRIPTION == null && this.DESCRIPTION == null) || (temp.DESCRIPTION != null
				&& this.DESCRIPTION != null && temp.DESCRIPTION.istGleich(this.DESCRIPTION)))) {
			return false;
		}
		if (!((temp.DTSTAMP == null && this.DTSTAMP == null)
				|| (temp.DTSTAMP != null && this.DTSTAMP != null && temp.DTSTAMP.istGleich(this.DTSTAMP)))) {
			return false;
		}
		if (!((temp.DTSTART == null && this.DTSTART == null)
				|| (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
			return false;
		}
		if (!((temp.DUE == null && this.DUE == null)
				|| (temp.DUE != null && this.DUE != null && temp.DUE.istGleich(this.DUE)))) {
			return false;
		}
		if (!((temp.DURATION == null && this.DURATION == null)
				|| (temp.DURATION != null && this.DURATION != null && temp.DURATION.istGleich(this.DURATION)))) {
			return false;
		}
		if (temp.EXDATESammlung.size() != this.EXDATESammlung.size()) {
			return false;
		}
		for (int i = 0; i < EXDATESammlung.size(); i++) {
			if (!temp.EXDATESammlung.get(i).istGleich(this.EXDATESammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.GEO == null && this.GEO == null)
				|| (temp.GEO != null && this.GEO != null && temp.GEO.istGleich(this.GEO)))) {
			return false;
		}
		if (temp.IMAGESammlung.size() != this.IMAGESammlung.size()) {
			return false;
		}
		for (int i = 0; i < IMAGESammlung.size(); i++) {
			if (!temp.IMAGESammlung.get(i).istGleich(this.IMAGESammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
				|| (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
			return false;
		}
		if (!((temp.LOCATION == null && this.LOCATION == null)
				|| (temp.LOCATION != null && this.LOCATION != null && temp.LOCATION.istGleich(this.LOCATION)))) {
			return false;
		}
		if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
				|| (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
			return false;
		}
		if (!((temp.PERCENT == null && this.PERCENT == null)
				|| (temp.PERCENT != null && this.PERCENT != null && temp.PERCENT.istGleich(this.PERCENT)))) {
			return false;
		}
		if (!((temp.PRIORITY == null && this.PRIORITY == null)
				|| (temp.PRIORITY != null && this.PRIORITY != null && temp.PRIORITY.istGleich(this.PRIORITY)))) {
			return false;
		}
		if (temp.RDATESammlung.size() != this.RDATESammlung.size()) {
			return false;
		}
		for (int i = 0; i < RDATESammlung.size(); i++) {
			if (!temp.RDATESammlung.get(i).istGleich(this.RDATESammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.RECURID == null && this.RECURID == null)
				|| (temp.RECURID != null && this.RECURID != null && temp.RECURID.istGleich(this.RECURID)))) {
			return false;
		}
		if (temp.RELATEDSammlung.size() != this.RELATEDSammlung.size()) {
			return false;
		}
		for (int i = 0; i < RELATEDSammlung.size(); i++) {
			if (!temp.RELATEDSammlung.get(i).istGleich(this.RELATEDSammlung.get(i))) {
				return false;
			}
		}
		if (temp.RSTATUSSammlung.size() != this.RSTATUSSammlung.size()) {
			return false;
		}
		for (int i = 0; i < RSTATUSSammlung.size(); i++) {
			if (!temp.RSTATUSSammlung.get(i).istGleich(this.RSTATUSSammlung.get(i))) {
				return false;
			}
		}
		if (temp.RESOURCESSammlung.size() != this.RESOURCESSammlung.size()) {
			return false;
		}
		for (int i = 0; i < RESOURCESSammlung.size(); i++) {
			if (!temp.RESOURCESSammlung.get(i).istGleich(this.RESOURCESSammlung.get(i))) {
				return false;
			}
		}
		if (!((temp.RRULE == null && this.RRULE == null)
				|| (temp.RRULE != null && this.RRULE != null && temp.RRULE.istGleich(this.RRULE)))) {
			return false;
		}
		if (!((temp.SEQ == null && this.SEQ == null)
				|| (temp.SEQ != null && this.SEQ != null && temp.SEQ.istGleich(this.SEQ)))) {
			return false;
		}
		if (!((temp.STATUS == null && this.STATUS == null)
				|| (temp.STATUS != null && this.STATUS != null && temp.STATUS.istGleich(this.STATUS)))) {
			return false;
		}
		if (!((temp.SUMMARY == null && this.SUMMARY == null)
				|| (temp.SUMMARY != null && this.SUMMARY != null && temp.SUMMARY.istGleich(this.SUMMARY)))) {
			return false;
		}
		if (!((temp.UID == null && this.UID == null)
				|| (temp.UID != null && this.UID != null && temp.UID.istGleich(this.UID)))) {
			return false;
		}
		if (!((temp.URL == null && this.URL == null)
				|| (temp.URL != null && this.URL != null && temp.URL.istGleich(this.URL)))) {
			return false;
		}
		if (temp.vAlarmSammlung.size() != this.vAlarmSammlung.size()) {
			return false;
		}
		for (int i = 0; i < vAlarmSammlung.size(); i++) {
			if (!temp.vAlarmSammlung.get(i).istGleich(this.vAlarmSammlung.get(i))) {
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
		String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VTODO");
		for (GuKKiCalcProperty ATTACH : ATTACHSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(ATTACH.ausgeben());
		}
		for (GuKKiCalcProperty ATTENDEE : ATTENDEESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(ATTENDEE.ausgeben());
		}
		for (GuKKiCalcProperty CATEGORIES : CATEGORIESSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(CATEGORIES.ausgeben());
		}
		componentDatenstrom += this.CLASS == null ? "" : ausgebenInDatenstrom(this.CLASS.ausgeben());
		componentDatenstrom += this.COLOR == null ? "" : ausgebenInDatenstrom(this.COLOR.ausgeben());
		for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(COMMENT.ausgeben());
		}
		componentDatenstrom += this.COMPLETED == null ? "" : ausgebenInDatenstrom(this.COMPLETED.ausgeben());
		for (GuKKiCalcProperty CONFERENCE : CONFERENCESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(CONFERENCE.ausgeben());
		}
		for (GuKKiCalcProperty CONTACT : CONTACTSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(CONTACT.ausgeben());
		}
		componentDatenstrom += this.CREATED == null ? "" : ausgebenInDatenstrom(this.CREATED.ausgeben());
		componentDatenstrom += this.DESCRIPTION == null ? "" : ausgebenInDatenstrom(this.DESCRIPTION.ausgeben());
		componentDatenstrom += this.DTSTAMP == null ? "" : ausgebenInDatenstrom(this.DTSTAMP.ausgeben());
		componentDatenstrom += this.DTSTART == null ? "" : ausgebenInDatenstrom(this.DTSTART.ausgeben());
		componentDatenstrom += this.DUE == null ? "" : ausgebenInDatenstrom(this.DUE.ausgeben());
		componentDatenstrom += this.DURATION == null ? "" : ausgebenInDatenstrom(this.DURATION.ausgeben());
		for (GuKKiCalcProperty EXDATE : EXDATESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(EXDATE.ausgeben());
		}
		componentDatenstrom += this.GEO == null ? "" : ausgebenInDatenstrom(this.GEO.ausgeben());
		for (GuKKiCalcProperty IMAGE : IMAGESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(IMAGE.ausgeben());
		}
		componentDatenstrom += this.LAST_MOD == null ? "" : ausgebenInDatenstrom(this.LAST_MOD.ausgeben());
		componentDatenstrom += this.LOCATION == null ? "" : ausgebenInDatenstrom(this.LOCATION.ausgeben());
		componentDatenstrom += this.ORGANIZER == null ? "" : ausgebenInDatenstrom(this.ORGANIZER.ausgeben());
		componentDatenstrom += this.PERCENT == null ? "" : ausgebenInDatenstrom(this.PERCENT.ausgeben());
		componentDatenstrom += this.PRIORITY == null ? "" : ausgebenInDatenstrom(this.PRIORITY.ausgeben());
		for (GuKKiCalcProperty RDATE : RDATESammlung) {
			componentDatenstrom += ausgebenInDatenstrom(RDATE.ausgeben());
		}
		componentDatenstrom += this.RECURID == null ? "" : ausgebenInDatenstrom(this.RECURID.ausgeben());
		for (GuKKiCalcProperty RELATED : RELATEDSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(RELATED.ausgeben());
		}
		for (GuKKiCalcProperty RSTATUS : RSTATUSSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(RSTATUS.ausgeben());
		}
		for (GuKKiCalcProperty RESOURCES : RESOURCESSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(RESOURCES.ausgeben());
		}
		componentDatenstrom += this.RRULE == null ? "" : ausgebenInDatenstrom(this.RRULE.ausgeben());
		componentDatenstrom += this.SEQ == null ? "" : ausgebenInDatenstrom(this.SEQ.ausgeben());
		componentDatenstrom += this.STATUS == null ? "" : ausgebenInDatenstrom(this.STATUS.ausgeben());
		componentDatenstrom += this.SUMMARY == null ? "" : ausgebenInDatenstrom(this.SUMMARY.ausgeben());
		componentDatenstrom += this.UID == null ? "" : ausgebenInDatenstrom(this.UID.ausgeben());
		componentDatenstrom += this.URL == null ? "" : ausgebenInDatenstrom(this.URL.ausgeben());
		for (GuKKiCalvAlarm vAlarm : this.vAlarmSammlung) {
			componentDatenstrom += vAlarm.ausgeben();
		}

		/* Abschluss und Fallbackparameter */

		for (String X_PROP : this.X_PROPSammlung) {
			componentDatenstrom += ausgebenInDatenstrom(X_PROP);
		}
		for (String Restinformation : this.Restinformationen) {
			componentDatenstrom += ausgebenInDatenstrom(Restinformation);
		}
		componentDatenstrom += ausgebenInDatenstrom("END:VTODO");
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return componentDatenstrom;
	} // Ende ausgeben V 0.0.3 (RFC 5545, RFC 7968) 2021-12-22T15-12-22

	/**
	 * Gibt statt der Adresse die UID des vEvent zurück
	 */
	@Override
	public String toString() {
		return this.schluessel.toString();
	} // Ende toString()

	/**
	 * Gibt sämtliche Schlüssel des vTodo und die aller Subkomponenten aus
	 */
	public String toString(String ausgabeLevel) {
		String ausgabeString = "ToDo-Identifikation=" + this.toString() + "<-->"
				+ (SUMMARY == null ? "" : SUMMARY.getWert())+nz;
		if (ausgabeLevel.toUpperCase().indexOf("A") >= 0) {
			for (GuKKiCalvAlarm vAlarm : vAlarmSammlung) {
				ausgabeString += vAlarm.toString(ausgabeLevel);
			}
		}
		return ausgabeString;

	} // Ende toString(String)
}
