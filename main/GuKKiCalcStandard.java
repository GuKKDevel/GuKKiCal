package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class GuKKiCalcStandard extends GuKKiCalComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

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
	private ArrayList<GuKKiCalProperty> COMMENTSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RDATESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> TZNAMESammlung = new ArrayList<GuKKiCalProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private ArrayList<String> X_PROPSammlung = new ArrayList<String>();
	private ArrayList<String> Restinformationen = new ArrayList<String>();


	protected GuKKiCalcStandard() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.kennung = GuKKiCalcKennung.STANDARD;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

//	protected GuKKiCalcStandard(String cStandardDaten) throws Exception {
//		if (logger.isLoggable(logLevel)) {
//			logger.log(logLevel, "begonnen");
//		}
//		
//		this.kennung = GuKKiCalcKennung.STANDARD;
//		
//		einlesenAusDatenstrom(cStandardDaten);
//
//// @formatter:off    	 
//// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
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
//    }
 
	   /**
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected void neueZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
            COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
            DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
        } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
            RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
        } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
            RRULE = new GuKKiCalProperty(zeile, "RRULE");
        } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("TZNAME")) {
            TZNAMESammlung.add(new GuKKiCalProperty(zeile, "TZNAME"));
        } else if (zeile.length() > 12 && zeile.substring(0, 12).equals("TZOFFSETFROM")) {
            TZOFFSETFROM = new GuKKiCalProperty(zeile, "TZOFFSETFROM");
        } else if (zeile.length() > 10 && zeile.substring(0, 10).equals("TZOFFSETTO")) {
            TZOFFSETTO = new GuKKiCalProperty(zeile, "TZOFFSETTO");
 
/* Abschluss und Fallbackparameter */
 
        } else if (zeile.length() > 2 && zeile.substring(0,2).equals("X-")) {
            X_PROPSammlung.add(zeile);
        } else {
            Restinformationen.add(zeile);
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    } // Ende neueZeile V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
    /**
     * Diese Methode kopiert die iCalendar-Komponente
     * GuKKiCalcStandard und gibt diese Kopie zurück
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected GuKKiCalcStandard kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        GuKKiCalcStandard temp = new GuKKiCalcStandard();
        temp.kennung = this.kennung;
        for (GuKKiCalProperty COMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(COMMENT.kopieren());
        }
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
        for (GuKKiCalProperty RDATE : RDATESammlung) {
            temp.RDATESammlung.add(RDATE.kopieren());
        }
        temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
        for (GuKKiCalProperty TZNAME : TZNAMESammlung) {
            temp.TZNAMESammlung.add(TZNAME.kopieren());
        }
        temp.TZOFFSETFROM = this.TZOFFSETFROM == null ? null : this.TZOFFSETFROM.kopieren();
        temp.TZOFFSETTO = this.TZOFFSETTO == null ? null : this.TZOFFSETTO.kopieren();
 
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
     * Vergleichen aller Attribute der Komponente GuKKiCalcStandard
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
        GuKKiCalcStandard temp = (GuKKiCalcStandard) dasAndere;
        if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
            return false;
        }
        for (int i = 0;i < COMMENTSammlung.size(); i++) {
            if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.DTSTART == null && this.DTSTART == null)
                || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
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
        if (!((temp.RRULE == null && this.RRULE == null)
                || (temp.RRULE != null && this.RRULE != null && temp.RRULE.istGleich(this.RRULE)))) {
            return false;
        }
        if (temp.TZNAMESammlung.size() != this.TZNAMESammlung.size()) {
            return false;
        }
        for (int i = 0;i < TZNAMESammlung.size(); i++) {
            if (!temp.TZNAMESammlung.get(i).istGleich(this.TZNAMESammlung.get(i))) {
                return false;
            }
        }
        if (!((temp.TZOFFSETFROM == null && this.TZOFFSETFROM == null)
                || (temp.TZOFFSETFROM != null && this.TZOFFSETFROM != null && temp.TZOFFSETFROM.istGleich(this.TZOFFSETFROM)))) {
            return false;
        }
        if (!((temp.TZOFFSETTO == null && this.TZOFFSETTO == null)
                || (temp.TZOFFSETTO != null && this.TZOFFSETTO != null && temp.TZOFFSETTO.istGleich(this.TZOFFSETTO)))) {
            return false;
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
        String componentDatenstrom = ausgebenInDatenstrom("BEGIN:STANDARD");
        for (GuKKiCalProperty COMMENT : COMMENTSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(COMMENT.ausgeben());
        }
        componentDatenstrom +=  this.DTSTART == null ? "" : ausgebenInDatenstrom(this.DTSTART.ausgeben());
        for (GuKKiCalProperty RDATE : RDATESammlung) {
            componentDatenstrom += ausgebenInDatenstrom(RDATE.ausgeben());
        }
        componentDatenstrom +=  this.RRULE == null ? "" : ausgebenInDatenstrom(this.RRULE.ausgeben());
        for (GuKKiCalProperty TZNAME : TZNAMESammlung) {
            componentDatenstrom += ausgebenInDatenstrom(TZNAME.ausgeben());
        }
        componentDatenstrom +=  this.TZOFFSETFROM == null ? "" : ausgebenInDatenstrom(this.TZOFFSETFROM.ausgeben());
        componentDatenstrom +=  this.TZOFFSETTO == null ? "" : ausgebenInDatenstrom(this.TZOFFSETTO.ausgeben());
 
/* Abschluss und Fallbackparameter */
 
        for (String X_PROP : this.X_PROPSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(X_PROP);
        }
        for (String Restinformation : this.Restinformationen) {
            componentDatenstrom += ausgebenInDatenstrom(Restinformation);
        }
        componentDatenstrom += ausgebenInDatenstrom("END:STANDARD");
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
        return componentDatenstrom;
    } // Ende ausgeben V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    protected void abschliessen(){status = GuKKiCalcStatus.GELESEN;}
 
}
