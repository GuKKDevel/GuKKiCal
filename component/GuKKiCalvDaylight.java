package component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import enumerations.*;
import exceptions.*;

/**
 * Die Klasse vDaylight ist eine SubKlasse für die TimezoneKomponente
 * 
 * @author GuKKDevel
 *
 */
class GuKKiCalvDaylight extends GuKKiCalvComponent {

	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	/*
	 * The following are REQUIRED, but MUST NOT occur more than once.
	 */
	GuKKiCalcProperty DTSTART = null;
	GuKKiCalcProperty TZOFFSETFROM = null;
	GuKKiCalcProperty TZOFFSETTO = null;
	/*
	 * The following is OPTIONAL, but SHOULD NOT occur more than once.
	 */
	GuKKiCalcProperty RRULE = null;
	/*
	 * The following are OPTIONAL, and MAY occur more than once.
	 */
	private List<GuKKiCalcProperty> COMMENTSammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> RDATESammlung = new LinkedList<GuKKiCalcProperty>();
	private List<GuKKiCalcProperty> TZNAMESammlung = new LinkedList<GuKKiCalcProperty>();
	/*
	 * Here are the x-prop and iana-prop are to be stored
	 */
	private List<String> X_PROPSammlung = new LinkedList<String>();
	private List<String> Restinformationen = new LinkedList<String>();

	protected GuKKiCalvDaylight() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		kennung = GuKKiCalcKennung.DAYLIGHT;
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}
	protected void abschliessen(String pNAME, String pTZID) throws GuKKiCalException{
		String tempDTSTART = this.DTSTART == null ? "" : this.DTSTART.getWert();
		String tempTZOFFSETFROM = this.TZOFFSETFROM == null ? "" : this.TZOFFSETFROM.getWert();
		this.schluessel = new GuKKiCalcSchluessel(this.kennung, pNAME,pTZID, tempDTSTART, tempTZOFFSETFROM);
		status = GuKKiCalcStatus.GELESEN;
	}
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected void neueZeile(String zeile) {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
            COMMENTSammlung.add(new GuKKiCalcProperty(zeile, "COMMENT"));
        } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
            DTSTART = new GuKKiCalcProperty(zeile, "DTSTART");
        } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
            RDATESammlung.add(new GuKKiCalcProperty(zeile, "RDATE"));
        } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
            RRULE = new GuKKiCalcProperty(zeile, "RRULE");
        } else if (zeile.length() > 6 && zeile.substring(0, 6).equals("TZNAME")) {
            TZNAMESammlung.add(new GuKKiCalcProperty(zeile, "TZNAME"));
        } else if (zeile.length() > 12 && zeile.substring(0, 12).equals("TZOFFSETFROM")) {
            TZOFFSETFROM = new GuKKiCalcProperty(zeile, "TZOFFSETFROM");
        } else if (zeile.length() > 10 && zeile.substring(0, 10).equals("TZOFFSETTO")) {
            TZOFFSETTO = new GuKKiCalcProperty(zeile, "TZOFFSETTO");
 
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
     * GuKKiCalvDaylight und gibt diese Kopie zurück
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    protected GuKKiCalvDaylight kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        GuKKiCalvDaylight temp = new GuKKiCalvDaylight();
        temp.kennung = this.kennung;
        for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(COMMENT.kopieren());
        }
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
        for (GuKKiCalcProperty RDATE : RDATESammlung) {
            temp.RDATESammlung.add(RDATE.kopieren());
        }
        temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
        for (GuKKiCalcProperty TZNAME : TZNAMESammlung) {
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
     * Vergleichen aller Attribute der Komponente GuKKiCalvDaylight
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
        GuKKiCalvDaylight temp = (GuKKiCalvDaylight) dasAndere;
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
        String componentDatenstrom = ausgebenInDatenstrom("BEGIN:DAYLIGHT");
        for (GuKKiCalcProperty COMMENT : COMMENTSammlung) {
            componentDatenstrom += ausgebenInDatenstrom(COMMENT.ausgeben());
        }
        componentDatenstrom +=  this.DTSTART == null ? "" : ausgebenInDatenstrom(this.DTSTART.ausgeben());
        for (GuKKiCalcProperty RDATE : RDATESammlung) {
            componentDatenstrom += ausgebenInDatenstrom(RDATE.ausgeben());
        }
        componentDatenstrom +=  this.RRULE == null ? "" : ausgebenInDatenstrom(this.RRULE.ausgeben());
        for (GuKKiCalcProperty TZNAME : TZNAMESammlung) {
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
        componentDatenstrom += ausgebenInDatenstrom("END:DAYLIGHT");
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
        return componentDatenstrom;
    } // Ende ausgeben V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    
    /**
	 * Gibt Identifikationsdaten der VTIMEZONE-Komponente aus
	 */
    @Override
	public String toString() {
		return this.schluessel.toString();
	}

	/**
	 * Gibt einige Daten der VTIMEZONE-Komponente aus
	 */
	public String toString(String ausgabeLevel) {
		return "Daylight-Identifikation=" + this.toString()+nz;
	}
}
