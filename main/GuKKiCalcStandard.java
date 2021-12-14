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

	protected GuKKiCalcStandard(String cStandardDaten) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		
		this.kennung = GuKKiCalcKennung.STANDARD;
		
		einlesenAusDatenstrom(cStandardDaten);

// @formatter:off    	 
// Generieren der restlichen Verarbeitungsschritte im Konstruktor für den Datenstrom
 
        status = GuKKiCalcStatus.GELESEN;
 
        if (Restinformationen.size() > 0) {
            for (String Restinformation : Restinformationen) {
                logger.log(Level.INFO, "Restinformation:" + "-->" + Restinformation + "<--");
            }
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    }
 
// Generieren der Methoden für den Aufbau der Komponentensammlungen
// Anfang der generierten Methoden für GuKKiCalcStandard 0.1 Wed Dec 08 23:39:38 CET 2021
 
    /**
     * Mit dieser Methode werden die einzelnen kompletten (zusammengesetzten) Zeilen
     * untersucht und die jeweilige Eigenschaft wird abgespeichert
     */
    @Override
    protected void verarbeitenZeile(String zeile) throws Exception {
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "begonnen");
        }
        if (!zeile.equals("BEGIN:STANDARD") & !zeile.equals("END:STANDARD")) {
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
             if (zeile.length() > 7 && zeile.substring(0, 7).equals("COMMENT")) {
                COMMENTSammlung.add(new GuKKiCalProperty(zeile, "COMMENT"));
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 7 && zeile.substring(0, 7).equals("DTSTART")) {
                DTSTART = new GuKKiCalProperty(zeile, "DTSTART");
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 5 && zeile.substring(0, 5).equals("RDATE")) {
                RDATESammlung.add(new GuKKiCalProperty(zeile, "RDATE"));
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 5 && zeile.substring(0, 5).equals("RRULE")) {
                RRULE = new GuKKiCalProperty(zeile, "RRULE");
 
// Eigenschaft: TZNAME GuKKiCalProperty auftreten 0:n
            } else  if (zeile.length() > 6 && zeile.substring(0, 6).equals("TZNAME")) {
                TZNAMESammlung.add(new GuKKiCalProperty(zeile, "TZNAME"));
 
// Eigenschaft: TZOFFSETFROM GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 12 && zeile.substring(0, 12).equals("TZOFFSETFROM")) {
                TZOFFSETFROM = new GuKKiCalProperty(zeile, "TZOFFSETFROM");
 
// Eigenschaft: TZOFFSETTO GuKKiCalProperty auftreten 0:1
            } else if (zeile.length() > 10 && zeile.substring(0, 10).equals("TZOFFSETTO")) {
                TZOFFSETTO = new GuKKiCalProperty(zeile, "TZOFFSETTO");
 
// Eigenschaft: X_PROP String auftreten 0:n
            } else  if (zeile.length() > 2 && zeile.substring(0, 2).equals("X-")) {
                X_PROPSammlung.add(zeile);
 
// Abschluss und Fallbackparameter
 
            } else {
                Restinformationen.add(zeile);
            }
        }
        if (logger.isLoggable(logLevel)) {
            logger.log(logLevel, "beendet");
        }
    } // Ende verarbeitenZeile
 
    /**
     * Diese Methode kopiert die iCalendar-Komponente
     * GuKKiCalcStandard und gibt diese Kopie zurück
     */
    protected GuKKiCalcStandard kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        GuKKiCalcStandard temp = new GuKKiCalcStandard();
 
        temp.kennung = this.kennung;
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pCOMMENT : COMMENTSammlung) {
            temp.COMMENTSammlung.add(pCOMMENT.kopieren());
        }
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
        temp.DTSTART = this.DTSTART == null ? null : this.DTSTART.kopieren();
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pRDATE : RDATESammlung) {
            temp.RDATESammlung.add(pRDATE.kopieren());
        }
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
        temp.RRULE = this.RRULE == null ? null : this.RRULE.kopieren();
 
// Eigenschaft: TZNAME GuKKiCalProperty auftreten 0:n
        for (GuKKiCalProperty pTZNAME : TZNAMESammlung) {
            temp.TZNAMESammlung.add(pTZNAME.kopieren());
        }
 
// Eigenschaft: TZOFFSETFROM GuKKiCalProperty auftreten 0:1
        temp.TZOFFSETFROM = this.TZOFFSETFROM == null ? null : this.TZOFFSETFROM.kopieren();
 
// Eigenschaft: TZOFFSETTO GuKKiCalProperty auftreten 0:1
        temp.TZOFFSETTO = this.TZOFFSETTO == null ? null : this.TZOFFSETTO.kopieren();
 
// Eigenschaft: X_PROP String auftreten 0:n
        for (String pX_PROP : X_PROPSammlung) {
            temp.X_PROPSammlung.add(pX_PROP);
        }
 
// Abschluss und Fallbackparameter
        for (String Restinformation : this.Restinformationen) {
            temp.Restinformationen.add(Restinformation);
        }
 
        temp.status = GuKKiCalcStatus.KOPIERT;
 
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
 
        return temp;
    } // Ende kopieren
 
    /**
     * Vergleichen aller Attribute der Komponente GuKKiCalcStandard
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
 
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
 
        GuKKiCalcStandard temp = (GuKKiCalcStandard) dasAndere;
 
// Eigenschaft: COMMENT GuKKiCalProperty auftreten 0:n
        if (temp.COMMENTSammlung.size() != this.COMMENTSammlung.size()) {
            return false;
        }
        for (int i = 0;i < COMMENTSammlung.size(); i++) {
            if (!temp.COMMENTSammlung.get(i).istGleich(this.COMMENTSammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: DTSTART GuKKiCalProperty auftreten 0:1
        if (!((temp.DTSTART == null && this.DTSTART == null)
                || (temp.DTSTART != null && this.DTSTART != null && temp.DTSTART.istGleich(this.DTSTART)))) {
            return false;
        }
 
// Eigenschaft: RDATE GuKKiCalProperty auftreten 0:n
        if (temp.RDATESammlung.size() != this.RDATESammlung.size()) {
            return false;
        }
        for (int i = 0;i < RDATESammlung.size(); i++) {
            if (!temp.RDATESammlung.get(i).istGleich(this.RDATESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: RRULE GuKKiCalProperty auftreten 0:1
        if (!((temp.RRULE == null && this.RRULE == null)
                || (temp.RRULE != null && this.RRULE != null && temp.RRULE.istGleich(this.RRULE)))) {
            return false;
        }
 
// Eigenschaft: TZNAME GuKKiCalProperty auftreten 0:n
        if (temp.TZNAMESammlung.size() != this.TZNAMESammlung.size()) {
            return false;
        }
        for (int i = 0;i < TZNAMESammlung.size(); i++) {
            if (!temp.TZNAMESammlung.get(i).istGleich(this.TZNAMESammlung.get(i))) {
                return false;
            }
        }
 
// Eigenschaft: TZOFFSETFROM GuKKiCalProperty auftreten 0:1
        if (!((temp.TZOFFSETFROM == null && this.TZOFFSETFROM == null)
                || (temp.TZOFFSETFROM != null && this.TZOFFSETFROM != null && temp.TZOFFSETFROM.istGleich(this.TZOFFSETFROM)))) {
            return false;
        }
 
// Eigenschaft: TZOFFSETTO GuKKiCalProperty auftreten 0:1
        if (!((temp.TZOFFSETTO == null && this.TZOFFSETTO == null)
                || (temp.TZOFFSETTO != null && this.TZOFFSETTO != null && temp.TZOFFSETTO.istGleich(this.TZOFFSETTO)))) {
            return false;
        }
 
// Eigenschaft: X_PROP String auftreten 0:n
        if (temp.X_PROPSammlung.size() != this.X_PROPSammlung.size()) {
            return false;
        }
        for (int i = 0;i < X_PROPSammlung.size(); i++) {
            if (!temp.X_PROPSammlung.get(i).equals(this.X_PROPSammlung.get(i))) {
               return false;
            }
        }
 
// Abschluss und Fallbackparameter
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
    } // Ende istGleich
 
// Ende der generierten Methoden für GuKKiCalcStandard
// @formatter:on    
}
