package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @formatter:off
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

	public GuKKiCalvJournal () {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
		kennung = GuKKiCalcKennung.JOURNAL;
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
	}
	public GuKKiCalvJournal(String x) {
		if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
// @formatter:off    	 
// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
 
//// Subkomponente: vAlarm GuKKiCalvAlarm VALARM
//        if (vAlarmDatenArray.size() != 0) {
//            vAlarmSammlungAnlegen();
//        }
// 
//        status = GuKKiCalcStatus.GELESEN;
// 
//        if (Restinformationen.size() > 0) {
//            for (String Restinformation : Restinformationen) {
//                logger.log(Level.INFO, "Restinformation:" + "-->" + Restinformation + "<--");
//            }
//        }
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "beendet");
//        }
    }
 
// Generieren der Methoden für den Aufbau der Komponentensammlungen
 
// Subkomponente: vAlarm GuKKiCalvAlarm VALARM
//    private void vAlarmSammlungAnlegen() throws Exception {
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "begonnen");
//        }
// 
//        String vAlarmDaten = "";
// 
//        for (String zeile : vAlarmDatenArray) {
//            if (zeile.equals("BEGIN:VALARM")) {
//                vAlarmDaten = zeile + nz;
//            } else if (zeile.equals("END:VALARM")) {
//                vAlarmDaten += zeile + nz;
//                vAlarmSammlung.add(new GuKKiCalvAlarm(vAlarmDaten));
//                vAlarmDaten = "";
//            } else {
//                vAlarmDaten += zeile + nz;
//            }
//        }
// 
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "beendet");
//        }
//    }
//// Anfang der generierten Methoden für GuKKiCalvJournal 0.1 Wed Dec 08 23:39:38 CET 2021
// 
//    /**
//     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
//     * untersucht und die jeweilige Eigenschaft wird abgespeichert
//     */
//    @Override
//    protected void verarbeitenZeile(String zeile) throws Exception {
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "begonnen");
//        }
//        if (!zeile.equals("BEGIN:VJOURNAL") & !zeile.equals("END:VJOURNAL")) {
// 
//// Subkomponente: vAlarm GuKKiCalvAlarm VALARM)
//            if (zeile.equals("BEGIN:VALARM")) {
//                vAlarmDatenSammeln = true;
//                vAlarmDatenArray.add(zeile);
//            } else if (zeile.equals("END:VALARM")) {
//                vAlarmDatenSammeln = false;
//                vAlarmDatenArray.add(zeile);
//            } else if (vAlarmDatenSammeln) {
//                vAlarmDatenArray.add(zeile);
// 
//// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("ATTACH")) {
//                ATTACHSammlung.add(new GuKKiCalProperty(zeile, "ATTACH"));
// 
//// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 8 && zeile.substring(0, 8).equals("ATTENDEE")) {
//                ATTENDEESammlung.add(new GuKKiCalProperty(zeile, "ATTENDEE"));
// 
//// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("CATEGORIES")) {
//                CATEGORIESSammlung.add(new GuKKiCalProperty(zeile, "CATEGORIES"));
// 
//// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("CLASS")) {
//                CLASS = new GuKKiCalProperty(zeile, "CLASS");
// 
//// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("COLOR")) {
//                COLOR = new GuKKiCalProperty(zeile, "COLOR");
// 
//// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
//                COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
// 
//// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 7 && zeile.substring(0, 7).equals("CONTACT")) {
//                CONTACTSammlung.add(new GuKKiCalProperty(zeile, "CONTACT"));
// 
//// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("CREATED")) {
//                CREATED = new GuKKiCalProperty(zeile, "CREATED");
// 
//// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 11 && zeile.substring(0, 11).equals("DESCRIPTION")) {
//                DESCRIPTIONSammlung.add(new GuKKiCalProperty(zeile, "DESCRIPTION"));
// 
//// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTAMP")) {
//                DTSTAMP = new GuKKiCalProperty(zeile, "DTSTAMP");
// 
//// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
//                DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
// 
//// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("EXDATE")) {
//                EXDATESammlung.add(new GuKKiCalProperty(zeile, "EXDATE"));
// 
//// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("IMAGE")) {
//                IMAGESammlung.add(new GuKKiCalProperty(zeile, "IMAGE"));
// 
//// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("LAST-MODIFIED")) {
//                LAST_MOD = new GuKKiCalProperty(zeile, "LAST-MODIFIED");
// 
//// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 9 && zeile.substring(0, 9).equals("ORGANIZER")) {
//                ORGANIZER = new GuKKiCalProperty(zeile, "ORGANIZER");
// 
//// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
//                RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
// 
//// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 13 && zeile.substring(0, 13).equals("RECURRENCE-ID")) {
//                RECURID = new GuKKiCalProperty(zeile, "RECURRENCE-ID");
// 
//// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 10 && zeile.substring(0, 10).equals("RELATED-TO")) {
//                RELATEDSammlung.add(new GuKKiCalProperty(zeile, "RELATED-TO"));
// 
//// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
//            } else  if (zeile.length() > 14 && zeile.substring(0, 14).equals("REQUEST-STATUS")) {
//                RSTATUSSammlung.add(new GuKKiCalProperty(zeile, "REQUEST-STATUS"));
// 
//// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
//                RRULE = new GuKKiCalProperty(zeile, "RRULE");
// 
//// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 8 && zeile.substring(0, 8).equals("SEQUENCE")) {
//                SEQ = new GuKKiCalProperty(zeile, "SEQUENCE");
// 
//// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("STATUS")) {
//                STATUS = new GuKKiCalProperty(zeile, "STATUS");
// 
//// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("SUMMARY")) {
//                SUMMARY = new GuKKiCalProperty(zeile, "SUMMARY");
// 
//// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("UID")) {
//                UID = new GuKKiCalProperty(zeile, "UID");
// 
//// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
//            } else if (zeile.length() > 3 && zeile.substring(0, 3).equals("URL")) {
//                URL = new GuKKiCalProperty(zeile, "URL");
// 
//// Eigenschaft: X_PROP String auftreten 0:n
//            } else  if (zeile.length() > 2 && zeile.substring(0, 2).equals("X-")) {
//                X_PROPSammlung.add(zeile);
// 
//// Abschluss und Fallbackparameter
// 
//            } else {
//                Restinformationen.add(zeile);
//            }
//        }
//        if (logger.isLoggable(logLevel)) {
//            logger.log(logLevel, "beendet");
//        }
//    } // Ende verarbeitenZeile
// 
//    /**
//     * Diese Methode kopiert die iCalendar-Komponente
//     * GuKKiCalvJournal und gibt diese Kopie zurück
//     */
    protected GuKKiCalvJournal kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCalvJournal temp = new GuKKiCalvJournal();
 
//        temp.kennung = this.kennung;
// 
//// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pATTACH : ATTACHSammlung) {
//            temp.ATTACHSammlung.add(pATTACH.kopieren());
//        }
// 
//// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pATTENDEE : ATTENDEESammlung) {
//            temp.ATTENDEESammlung.add(pATTENDEE.kopieren());
//        }
// 
//// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pCATEGORIES : CATEGORIESSammlung) {
//            temp.CATEGORIESSammlung.add(pCATEGORIES.kopieren());
//        }
// 
//// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
//        temp.CLASS = this.CLASS == null ? null : this.CLASS.kopieren();
// 
//// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
//        temp.COLOR = this.COLOR == null ? null : this.COLOR.kopieren();
// 
//// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pCOMMENT : COMMENTSammlung) {
//            temp.COMMENTSammlung.add(pCOMMENT.kopieren());
//        }
// 
//// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pCONTACT : CONTACTSammlung) {
//            temp.CONTACTSammlung.add(pCONTACT.kopieren());
//        }
// 
//// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
//        temp.CREATED = this.CREATED == null ? null : this.CREATED.kopieren();
// 
//// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pDESCRIPTION : DESCRIPTIONSammlung) {
//            temp.DESCRIPTIONSammlung.add(pDESCRIPTION.kopieren());
//        }
// 
//// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
//        temp.DTSTAMP = this.DTSTAMP == null ? null : this.DTSTAMP.kopieren();
// 
//// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
//        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
// 
//// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pEXDATE : EXDATESammlung) {
//            temp.EXDATESammlung.add(pEXDATE.kopieren());
//        }
// 
//// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pIMAGE : IMAGESammlung) {
//            temp.IMAGESammlung.add(pIMAGE.kopieren());
//        }
// 
//// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
//        temp.LAST_MOD = this.LAST_MOD == null ? null : this.LAST_MOD.kopieren();
// 
//// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
//        temp.ORGANIZER = this.ORGANIZER == null ? null : this.ORGANIZER.kopieren();
// 
//// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pRDATE : RDATESammlung) {
//            temp.RDATESammlung.add(pRDATE.kopieren());
//        }
// 
//// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
//        temp.RECURID = this.RECURID == null ? null : this.RECURID.kopieren();
// 
//// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pRELATED : RELATEDSammlung) {
//            temp.RELATEDSammlung.add(pRELATED.kopieren());
//        }
// 
//// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
//        for (GuKKiCalProperty pRSTATUS : RSTATUSSammlung) {
//            temp.RSTATUSSammlung.add(pRSTATUS.kopieren());
//        }
// 
//// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
//        temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
// 
//// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
//        temp.SEQ = this.SEQ == null ? null : this.SEQ.kopieren();
// 
//// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
//        temp.STATUS = this.STATUS == null ? null : this.STATUS.kopieren();
// 
//// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
//        temp.SUMMARY = this.SUMMARY == null ? null : this.SUMMARY.kopieren();
// 
//// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
//        temp.UID = this.UID == null ? null : this.UID.kopieren();
// 
//// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
//        temp.URL = this.URL == null ? null : this.URL.kopieren();
// 
//// Eigenschaft: X_PROP String auftreten 0:n
//        for (String pX_PROP : X_PROPSammlung) {
//            temp.X_PROPSammlung.add(pX_PROP);
//        }
// 
//// Subkomponente: vAlarm GuKKiCalvAlarm auftreten 0:n
//        for (GuKKiCalvAlarm vAlarm : this.vAlarmSammlung) {
//            temp.vAlarmSammlung.add(vAlarm.kopieren());
//        }
// 
//// Abschluss und Fallbackparameter
//        for (String Restinformation : this.Restinformationen) {
//            temp.Restinformationen.add(Restinformation);
//        }
// 
//        temp.status = GuKKiCalcStatus.KOPIERT;
// 
//        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
// 
        return temp;
    } // Ende kopieren
 
    /**
     * Vergleichen aller Attribute der Komponente GuKKiCalvJournal
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
//        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
// 
//        if (!dasAndere.getClass().equals(this.getClass())) {
//            return false;
//        }
// 
//        GuKKiCalvJournal temp = (GuKKiCalvJournal) dasAndere;
// 
//// Eigenschaft: ATTACH GuKKiCalProperty auftreten 0:n
//        if (temp.ATTACHSammlung.size() != this.ATTACHSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < ATTACHSammlung.size(); i++) {
//            if (!temp.ATTACHSammlung.get(i).istGleich(this.ATTACHSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: ATTENDEE GuKKiCalProperty auftreten 0:n
//        if (temp.ATTENDEESammlung.size() != this.ATTENDEESammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < ATTENDEESammlung.size(); i++) {
//            if (!temp.ATTENDEESammlung.get(i).istGleich(this.ATTENDEESammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: CATEGORIES GuKKiCalProperty auftreten 0:n
//        if (temp.CATEGORIESSammlung.size() != this.CATEGORIESSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < CATEGORIESSammlung.size(); i++) {
//            if (!temp.CATEGORIESSammlung.get(i).istGleich(this.CATEGORIESSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: CLASS GuKKiCalProperty auftreten 0:1
//        if (!((temp.CLASS == null && this.CLASS == null)
//                || (temp.CLASS != null && this.CLASS != null && temp.CLASS.istGleich(this.CLASS)))) {
//            return false;
//        }
// 
//// Eigenschaft: COLOR GuKKiCalProperty auftreten 0:1
//        if (!((temp.COLOR == null && this.COLOR == null)
//                || (temp.COLOR != null && this.COLOR != null && temp.COLOR.istGleich(this.COLOR)))) {
//            return false;
//        }
// 
//// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
//        if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < COMMENTSammlung.size(); i++) {
//            if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: CONTACT GuKKiCalProperty auftreten 0:n
//        if (temp.CONTACTSammlung.size() != this.CONTACTSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < CONTACTSammlung.size(); i++) {
//            if (!temp.CONTACTSammlung.get(i).istGleich(this.CONTACTSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: CREATED GuKKiCalProperty auftreten 0:1
//        if (!((temp.CREATED == null && this.CREATED == null)
//                || (temp.CREATED != null && this.CREATED != null && temp.CREATED.istGleich(this.CREATED)))) {
//            return false;
//        }
// 
//// Eigenschaft: DESCRIPTION GuKKiCalProperty auftreten 0:n
//        if (temp.DESCRIPTIONSammlung.size() != this.DESCRIPTIONSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < DESCRIPTIONSammlung.size(); i++) {
//            if (!temp.DESCRIPTIONSammlung.get(i).istGleich(this.DESCRIPTIONSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: DTSTAMP GuKKiCalProperty auftreten 0:1
//        if (!((temp.DTSTAMP == null && this.DTSTAMP == null)
//                || (temp.DTSTAMP != null && this.DTSTAMP != null && temp.DTSTAMP.istGleich(this.DTSTAMP)))) {
//            return false;
//        }
// 
//// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
//        if (!((temp.DTSTART == null && this.DTSTART == null)
//                || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
//            return false;
//        }
// 
//// Eigenschaft: EXDATE GuKKiCalProperty auftreten 0:n
//        if (temp.EXDATESammlung.size() != this.EXDATESammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < EXDATESammlung.size(); i++) {
//            if (!temp.EXDATESammlung.get(i).istGleich(this.EXDATESammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: IMAGE GuKKiCalProperty auftreten 0:n
//        if (temp.IMAGESammlung.size() != this.IMAGESammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < IMAGESammlung.size(); i++) {
//            if (!temp.IMAGESammlung.get(i).istGleich(this.IMAGESammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: LAST_MOD GuKKiCalProperty auftreten 0:1
//        if (!((temp.LAST_MOD == null && this.LAST_MOD == null)
//                || (temp.LAST_MOD != null && this.LAST_MOD != null && temp.LAST_MOD.istGleich(this.LAST_MOD)))) {
//            return false;
//        }
// 
//// Eigenschaft: ORGANIZER GuKKiCalProperty auftreten 0:1
//        if (!((temp.ORGANIZER == null && this.ORGANIZER == null)
//                || (temp.ORGANIZER != null && this.ORGANIZER != null && temp.ORGANIZER.istGleich(this.ORGANIZER)))) {
//            return false;
//        }
// 
//// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
//        if (temp.RDATESammlung.size() != this.RDATESammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < RDATESammlung.size(); i++) {
//            if (!temp.RDATESammlung.get(i).istGleich(this.RDATESammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: RECURID GuKKiCalProperty auftreten 0:1
//        if (!((temp.RECURID == null && this.RECURID == null)
//                || (temp.RECURID != null && this.RECURID != null && temp.RECURID.istGleich(this.RECURID)))) {
//            return false;
//        }
// 
//// Eigenschaft: RELATED GuKKiCalProperty auftreten 0:n
//        if (temp.RELATEDSammlung.size() != this.RELATEDSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < RELATEDSammlung.size(); i++) {
//            if (!temp.RELATEDSammlung.get(i).istGleich(this.RELATEDSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: RSTATUS GuKKiCalProperty auftreten 0:n
//        if (temp.RSTATUSSammlung.size() != this.RSTATUSSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < RSTATUSSammlung.size(); i++) {
//            if (!temp.RSTATUSSammlung.get(i).istGleich(this.RSTATUSSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
//        if (!((temp.RRULE == null && this.RRULE == null)
//                || (temp.RRULE != null && this.RRULE != null && temp.RRULE.istGleich(this.RRULE)))) {
//            return false;
//        }
// 
//// Eigenschaft: SEQ GuKKiCalProperty auftreten 0:1
//        if (!((temp.SEQ == null && this.SEQ == null)
//                || (temp.SEQ != null && this.SEQ != null && temp.SEQ.istGleich(this.SEQ)))) {
//            return false;
//        }
// 
//// Eigenschaft: STATUS GuKKiCalProperty auftreten 0:1
//        if (!((temp.STATUS == null && this.STATUS == null)
//                || (temp.STATUS != null && this.STATUS != null && temp.STATUS.istGleich(this.STATUS)))) {
//            return false;
//        }
// 
//// Eigenschaft: SUMMARY GuKKiCalProperty auftreten 0:1
//        if (!((temp.SUMMARY == null && this.SUMMARY == null)
//                || (temp.SUMMARY != null && this.SUMMARY != null && temp.SUMMARY.istGleich(this.SUMMARY)))) {
//            return false;
//        }
// 
//// Eigenschaft: UID GuKKiCalProperty auftreten 0:1
//        if (!((temp.UID == null && this.UID == null)
//                || (temp.UID != null && this.UID != null && temp.UID.istGleich(this.UID)))) {
//            return false;
//        }
// 
//// Eigenschaft: URL GuKKiCalProperty auftreten 0:1
//        if (!((temp.URL == null && this.URL == null)
//                || (temp.URL != null && this.URL != null && temp.URL.istGleich(this.URL)))) {
//            return false;
//        }
// 
//// Eigenschaft: X_PROP String auftreten 0:n
//        if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
//            return false;
//        }
//        for (int i = 0;i < X_PROPSammlung.size(); i++) {
//            if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
//               return false;
//            }
//        }
// 
//// Subkomponente: vAlarm GuKKiCalvAlarm auftreten 0:n
//        if (temp.vAlarmSammlung.size() != this.vAlarmSammlung.size()) {
//            return false;
//        }
//        for (int i = 0; i < vAlarmSammlung.size(); i++) {
//            if (!temp.vAlarmSammlung.get(i).istGleich(this.vAlarmSammlung.get(i))) {
//                return false;
//            }
//        }
// 
//// Abschluss und Fallbackparameter
//        if (temp.Restinformationen.size() != this.Restinformationen.size()) {
//            return false;
//        }
//        for (int i = 0; i < Restinformationen.size(); i++) {
//            if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {
//                return false; 
//            }
//        }
// 
//        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
// 
        return true;
    } // Ende istGleich
 
// Ende der generierten Methoden für GuKKiCalvJournal
// @formatter:on    	
}
