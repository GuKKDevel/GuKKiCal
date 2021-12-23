package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * Die Klasse GuKKiCalvJournal enthält alle Daten für eine VJOURNAL-Komponente im iCal Format
 *         
 * @author GuKKDevel
 *  
 * @formatter:off
 * 
 * 
 * 	RFC 5545 (september 2009) item 3.6.3; p. 57
 *  
 * 	Component Name: VJOURNAL
 * 
 * 	Purpose: Provide a grouping of component properties that describe a journal entry.
 * 
 * Description: A "VJOURNAL" calendar component is a grouping of
 * 		component properties that represent one or more descriptive text
 * 		notes associated with a particular calendar date. The "DTSTART"
 * 		property is used to specify the calendar date with which the
 * 		journal entry is associated. Generally, it will have a DATE value
 * 		data type, but it can also be used to specify a DATE-TIME value
 * 		data type. Examples of a journal entry include a daily record of
 * 		a legislative body or a journal entry of individual telephone
 * 		contacts for the day or an ordered list of accomplishments for the
 * 		day. The "VJOURNAL" calendar component can also be used to
 * 		associate a document with a calendar date.
 *		
 *		The "VJOURNAL" calendar component does not take up time on a
 *		calendar. Hence, it does not play a role in free or busy time
 *		searches -- it is as though it has a time transparency value of
 *		TRANSPARENT. It is transparent to any such searches.
 *		
 *		The "VJOURNAL" calendar component cannot be nested within another
 *		calendar component. However, "VJOURNAL" calendar components can
 *		be related to each other or to a "VEVENT" or to a "VTODO" calendar
 *		component, with the "RELATED-TO" property.
 *		
 *	Format Definition: A "VJOURNAL" calendar component is defined by the following notation:
 *
 *		journalc = "BEGIN" ":" "VJOURNAL" CRLF
 *
 *			jourprop
 *
 *			"END" ":" "VJOURNAL" CRLF
 *
 *			jourprop = *(
 *
 *		The following are REQUIRED, but MUST NOT occur more than once.
 *	
 *				dtstamp / uid /
 *
 *		The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				class / created / dtstart /
 *				last-mod / organizer / recurid / seq /
 *				status / summary / url /
 *
 *		The following is OPTIONAL, but SHOULD NOT occur more than once.
 *
 *				rrule /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				attach / attendee / categories / comment /
 *				contact / description / exdate / related / rdate /
 *				rstatus / x-prop / iana-prop
 *
 *				)
 *
 *	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-	-
 *	Modifications by RFC 7986 (October 2016) item 4.; p. 4
 *
 *			jourprop =/ *(
 *
 *		The following are OPTIONAL, but MUST NOT occur more than once.
 *
 *				color /
 *
 *		The following are OPTIONAL, and MAY occur more than once.
 *
 *				image
 *
 *				)
 *
 * @formmatter:on
 *
 */
public class GuKKiCalvJournal extends GuKKiCalComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

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
	private GuKKiCalProperty CREATED = null;
	private GuKKiCalProperty DTSTART = null;
	private GuKKiCalProperty LAST_MOD = null;
	private GuKKiCalProperty ORGANIZER = null;
	private GuKKiCalProperty RECURID = null;
	private GuKKiCalProperty SEQ = null;
	private GuKKiCalProperty STATUS = null;
	private GuKKiCalProperty SUMMARY = null;
	private GuKKiCalProperty URL = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	private GuKKiCalProperty RRULE = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private ArrayList<GuKKiCalProperty> ATTACHSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> ATTENDEESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CATEGORIESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> COMMENTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> CONTACTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> DESCRIPTIONSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> EXDATESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> IMAGESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RSTATUSSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATEDSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RDATESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * X-Name Properties
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();

	private ArrayList<String> Restinformationen = new ArrayList<String>();
	/*
	 * Sammlungen der VEVENT-Komponenten
	 */
	private ArrayList<GuKKiCalvAlarm> vAlarmSammlung = new ArrayList<GuKKiCalvAlarm>();

	
	public GuKKiCalvJournal () {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		kennung = GuKKiCalcKennung.JOURNAL;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	}
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected void neueZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (bearbeiteSubKomponente) {
            if (vAlarmBearbeiten) {
                if (zeile.equals("END:VALARM")) {
                    vAlarmNeu.abschliessen();
                    vAlarmSammlung.add(vAlarmNeu);
                    vAlarmBearbeiten = false;
                    bearbeiteSubKomponente = false;
                }
                else {
                    vAlarmNeu.neueZeile(zeile);
                }
            }
        }
        else {
            if (zeile.equals("BEGIN:VALARM")) {
                vAlarmNeu = new GuKKiCalvAlarm();
                vAlarmBearbeiten = true;
                bearbeiteSubKomponente = true;
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
                ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
                ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
            } else if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
                CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("CLASS")) {
                CLASS = new GuKKiCalProperty(zeile, "CLASS");
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
                COLOR = new GuKKiCalProperty(zeile, "COLOR");
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
                COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
                CONTACTSammlung.add(new GuKKiCalProperty(zeile, "CONTACT"));
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CREATED")) {
                CREATED = new GuKKiCalProperty(zeile, "CREATED");
            } else if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
                DESCRIPTIONSammlung.add(new GuKKiCalProperty(zeile, "DESCRIPTION"));
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
                DTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
                DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("EXDATE")) {
                EXDATESammlung.add(new GuKKiCalProperty(zeile, "EXDATE"));
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
                IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
                LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
            } else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
                ORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
                RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
                RECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
            } else if (zeile.length() > 10 && zeile.substring(0, 10).equals("RELATED-TO")) {
                RELATEDSammlung.add(new GuKKiCalProperty(zeile, "RELATED-TO"));
            } else if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
                RSTATUSSammlung.add(new GuKKiCalProperty(zeile, "REQUEST-STATUS"));
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
                RRULE = new GuKKiCalProperty(zeile, "RRULE");
            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
                SEQ = new GuKKiCalProperty(zeile, "SEQUENCE");
            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("STATUS")) {
                STATUS = new GuKKiCalProperty(zeile, "STATUS");
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
                SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
                UID = new GuKKiCalProperty(zeile, "UID");
            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
                URL = new GuKKiCalProperty(zeile, "URL");
 
/* Abschluss und Fallbackparameter */
 
            } else if (zeile.length() > 2 && zeile.substring(0,2).equals("X-")) {
                X_PROPSammlung.add(zeile);
            } else {
                Restinformationen.add(zeile);
            }
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    } // Ende neueZeile V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
        /**
         * Diese Methode kopiert die iCalendar-Komponente
         * GuKKiCalvJournal und gibt diese Kopie zurück
         * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
         */
        protected GuKKiCalvJournal kopieren() {
            if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
            GuKKiCalvJournal temp = new GuKKiCalvJournal();
            temp.kennung = this.kennung;
            for (GuKKiCalProperty ATTACH : ATTACHSammlung) {
                temp.ATTACHSammlung.add(ATTACH.kopieren());
            }
            for (GuKKiCalProperty ATTENDEE : ATTENDEESammlung) {
                temp.ATTENDEESammlung.add(ATTENDEE.kopieren());
            }
            for (GuKKiCalProperty CATEGORIES : CATEGORIESSammlung) {
                temp.CATEGORIESSammlung.add(CATEGORIES.kopieren());
            }
            temp.CLASS = this.CLASS == null ? null : this.CLASS.kopieren();
            temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
            for (GuKKiCalProperty COMMENT : COMMENTSammlung) {
                temp.COMMENTSammlung.add(COMMENT.kopieren());
            }
            for (GuKKiCalProperty CONTACT : CONTACTSammlung) {
                temp.CONTACTSammlung.add(CONTACT.kopieren());
            }
            temp.CREATED = this.CREATED == null ? null : this.CREATED.kopieren();
            for (GuKKiCalProperty DESCRIPTION : DESCRIPTIONSammlung) {
                temp.DESCRIPTIONSammlung.add(DESCRIPTION.kopieren());
            }
            temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
            temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
            for (GuKKiCalProperty EXDATE : EXDATESammlung) {
                temp.EXDATESammlung.add(EXDATE.kopieren());
            }
            for (GuKKiCalProperty IMAGE : IMAGESammlung) {
                temp.IMAGESammlung.add(IMAGE.kopieren());
            }
            temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
            temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
            for (GuKKiCalProperty RDATE : RDATESammlung) {
                temp.RDATESammlung.add(RDATE.kopieren());
            }
            temp.RECURID = this.RECURID == null ? null : this.RECURID.kopieren();
            for (GuKKiCalProperty RELATED : RELATEDSammlung) {
                temp.RELATEDSammlung.add(RELATED.kopieren());
            }
            for (GuKKiCalProperty RSTATUS : RSTATUSSammlung) {
                temp.RSTATUSSammlung.add(RSTATUS.kopieren());
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
            if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
            return temp;
        } // Ende kopieren V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
        /**
         * Vergleichen aller Attribute der Komponente GuKKiCalvJournal
         * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
         *
         * @return boolean
         */
        protected boolean istGleich(Object dasAndere) {
            if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
            if (!dasAndere.getClass().equals(this.getClass())) {
                return false;
            }
            GuKKiCalvJournal temp = (GuKKiCalvJournal) dasAndere;
            if (temp.ATTACHSammlung.size() != this.ATTACHSammlung.size()) {
                return false;
            }
            for (int i = 0;i < ATTACHSammlung.size(); i++) {
                if (!temp.ATTACHSammlung.get(i).istGleich(this.ATTACHSammlung.get(i))) {
                    return false;
                }
            }
            if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
                return false;
            }
            for (int i = 0;i < ATTENDEESammlung.size(); i++) {
                if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
                    return false;
                }
            }
            if (temp.CATEGORIESSammlung.size() != this.CATEGORIESSammlung.size()) {
                return false;
            }
            for (int i = 0;i < CATEGORIESSammlung.size(); i++) {
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
            for (int i = 0;i < COMMENTSammlung.size(); i++) {
                if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
                    return false;
                }
            }
            if (temp.CONTACTSammlung.size() != this.CONTACTSammlung.size()) {
                return false;
            }
            for (int i = 0;i < CONTACTSammlung.size(); i++) {
                if (!temp.CONTACTSammlung.get(i).istGleich(this.CONTACTSammlung.get(i))) {
                    return false;
                }
            }
            if (!((temp.CREATED == null && this.CREATED == null)
                    || (temp.CREATED != null && this.CREATED != null && temp.CREATED.istGleich(this.CREATED)))) {
                return false;
            }
            if (temp.DESCRIPTIONSammlung.size() != this.DESCRIPTIONSammlung.size()) {
                return false;
            }
            for (int i = 0;i < DESCRIPTIONSammlung.size(); i++) {
                if (!temp.DESCRIPTIONSammlung.get(i).istGleich(this.DESCRIPTIONSammlung.get(i))) {
                    return false;
                }
            }
            if (!((temp.DTSTAMP == null && this.DTSTAMP == null)
                    || (temp.DTSTAMP != null && this.DTSTAMP != null && temp.DTSTAMP.istGleich(this.DTSTAMP)))) {
                return false;
            }
            if (!((temp.DTSTART == null && this.DTSTART == null)
                    || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
                return false;
            }
            if (temp.EXDATESammlung.size() != this.EXDATESammlung.size()) {
                return false;
            }
            for (int i = 0;i < EXDATESammlung.size(); i++) {
                if (!temp.EXDATESammlung.get(i).istGleich(this.EXDATESammlung.get(i))) {
                    return false;
                }
            }
            if (temp.IMAGESammlung.size() != this.IMAGESammlung.size()) {
                return false;
            }
            for (int i = 0;i < IMAGESammlung.size(); i++) {
                if (!temp.IMAGESammlung.get(i).istGleich(this.IMAGESammlung.get(i))) {
                    return false;
                }
            }
            if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
                    || (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
                return false;
            }
            if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
                    || (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
                return false;
            }
            if (temp.RDATESammlung.size() != this.RDATESammlung.size()) {
                return false;
            }
            for (int i = 0;i < RDATESammlung.size(); i++) {
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
            for (int i = 0;i < RELATEDSammlung.size(); i++) {
                if (!temp.RELATEDSammlung.get(i).istGleich(this.RELATEDSammlung.get(i))) {
                    return false;
                }
            }
            if (temp.RSTATUSSammlung.size() != this.RSTATUSSammlung.size()) {
                return false;
            }
            for (int i = 0;i < RSTATUSSammlung.size(); i++) {
                if (!temp.RSTATUSSammlung.get(i).istGleich(this.RSTATUSSammlung.get(i))) {
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
            if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
            return true;
        } // Ende istGleich V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
        /**
         * Mit dieser Methode werden die einzelnen Eigenschaften als gültige Parameterkette ausgegeben
         * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
         */
        protected String ausgeben() throws Exception {
            if (logger.isLoggable(logLevel)) {
                logger.log(logLevel, "begonnen");
            }
            String componentDatenstrom = ausgebenInDatenstrom("BEGIN:VJOURNAL");
            for (GuKKiCalProperty ATTACH : ATTACHSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(ATTACH.ausgeben());
            }
            for (GuKKiCalProperty ATTENDEE : ATTENDEESammlung) {
                componentDatenstrom += ausgebenInDatenstrom(ATTENDEE.ausgeben());
            }
            for (GuKKiCalProperty CATEGORIES : CATEGORIESSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(CATEGORIES.ausgeben());
            }
            componentDatenstrom +=  this.CLASS == null ? "" : ausgebenInDatenstrom(this.CLASS.ausgeben());
            componentDatenstrom +=  this.COLOR == null ? "" : ausgebenInDatenstrom(this.COLOR.ausgeben());
            for (GuKKiCalProperty COMMENT : COMMENTSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(COMMENT.ausgeben());
            }
            for (GuKKiCalProperty CONTACT : CONTACTSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(CONTACT.ausgeben());
            }
            componentDatenstrom +=  this.CREATED == null ? "" : ausgebenInDatenstrom(this.CREATED.ausgeben());
            for (GuKKiCalProperty DESCRIPTION : DESCRIPTIONSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(DESCRIPTION.ausgeben());
            }
            componentDatenstrom +=  this.DTSTAMP == null ? "" : ausgebenInDatenstrom(this.DTSTAMP.ausgeben());
            componentDatenstrom +=  this.DTSTART == null ? "" : ausgebenInDatenstrom(this.DTSTART.ausgeben());
            for (GuKKiCalProperty EXDATE : EXDATESammlung) {
                componentDatenstrom += ausgebenInDatenstrom(EXDATE.ausgeben());
            }
            for (GuKKiCalProperty IMAGE : IMAGESammlung) {
                componentDatenstrom += ausgebenInDatenstrom(IMAGE.ausgeben());
            }
            componentDatenstrom +=  this.LAST_MOD == null ? "" : ausgebenInDatenstrom(this.LAST_MOD.ausgeben());
            componentDatenstrom +=  this.ORGANIZER == null ? "" : ausgebenInDatenstrom(this.ORGANIZER.ausgeben());
            for (GuKKiCalProperty RDATE : RDATESammlung) {
                componentDatenstrom += ausgebenInDatenstrom(RDATE.ausgeben());
            }
            componentDatenstrom +=  this.RECURID == null ? "" : ausgebenInDatenstrom(this.RECURID.ausgeben());
            for (GuKKiCalProperty RELATED : RELATEDSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(RELATED.ausgeben());
            }
            for (GuKKiCalProperty RSTATUS : RSTATUSSammlung) {
                componentDatenstrom += ausgebenInDatenstrom(RSTATUS.ausgeben());
            }
            componentDatenstrom +=  this.RRULE == null ? "" : ausgebenInDatenstrom(this.RRULE.ausgeben());
            componentDatenstrom +=  this.SEQ == null ? "" : ausgebenInDatenstrom(this.SEQ.ausgeben());
            componentDatenstrom +=  this.STATUS == null ? "" : ausgebenInDatenstrom(this.STATUS.ausgeben());
            componentDatenstrom +=  this.SUMMARY == null ? "" : ausgebenInDatenstrom(this.SUMMARY.ausgeben());
            componentDatenstrom +=  this.UID == null ? "" : ausgebenInDatenstrom(this.UID.ausgeben());
            componentDatenstrom +=  this.URL == null ? "" : ausgebenInDatenstrom(this.URL.ausgeben());
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
            componentDatenstrom += ausgebenInDatenstrom("END:VJOURNAL");
            if (logger.isLoggable(logLevel)) {
                logger.log(logLevel, "beendet");
            }
            return componentDatenstrom;
        } // Ende ausgeben V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    protected void abschliessen(){status = GuKKiCalcStatus.GELESEN;}	
}
