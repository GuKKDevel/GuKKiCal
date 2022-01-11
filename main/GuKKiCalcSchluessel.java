package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.GuKKiCalF_FalscheKomponente;

/**
 * Schluessel der einzelnen Componenten
 * 
 * @author GuKKDevel
 */
class GuKKiCalcSchluessel {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	// Komponente, für die der Schlüssel gilt
	GuKKiCalcKennung komponente = GuKKiCalcKennung.UNDEFINIERT;

	int ersteZeile = 0;
	int letzteZeile = 0;
	int addierenZeilen = 0;
	/* Schlüssel für Kalender */
	private String NAME = "";
	/* Schlüssel für Event, Todo, Journal */
	private String UID = "";
	private String SEQ = "";
	private String RECURID = "";
	/* Schlüssel für Alarm */
	private String ACTION = "";
	private String TRIGGER = "";
	/* Schlüssel für Timezone */
	private String TZID = "";
	/* Schlüssel für Daylight, Standard */
	private String DTSTART = "";
	String TZOFFSETFROM = "";
	/* Schlüssel für FreeBusy */
//	private String UID = "";
	private String DTSTAMP = "";

	/**
	 * Konstruktor für Komponente vCalendar
	 * 
	 * @param komponente
	 * @param parameter  - pNAME
	 * 
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String pNAME) throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.komponente = komponente;
		switch (komponente) {
			case CALENDAR:
				this.NAME = pNAME;
				break;
			case TIMEZONE:
			case EVENT:
			case TODO:
			case JOURNAL:
			case ALARM:
			case DAYLIGHT:
			case STANDARD:
			case FREEBUSY:
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Konstruktor für Komponente vTimezone
	 * 
	 * @param komponente
	 * @param parameter  - pNAME
	 * @param parameter  - pTZID
	 * 
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String pNAME, String pTZID)
			throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.komponente = komponente;
		switch (komponente) {
			case TIMEZONE:
				this.NAME = pNAME;
				this.TZID = pTZID;
				break;
			case CALENDAR:
			case EVENT:
			case TODO:
			case JOURNAL:
			case ALARM:
			case DAYLIGHT:
			case STANDARD:
			case FREEBUSY:
			default:
				throw new GuKKiCalF_FalscheKomponente();

		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Konstruktor für Komponente vFREEBUSY <br>
	 * 
	 * @param komponente
	 * @param parameter  - pNAME
	 * @param parameter  - pUID
	 * @param parameter  - pDTSTAMP
	 * 
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String pNAME, String pUID, String pDTSTAMP)
			throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.komponente = komponente;
		switch (komponente) {
			case FREEBUSY:
				this.NAME = pNAME;
				this.UID = pUID;
				this.DTSTAMP = pDTSTAMP;
				break;
			case CALENDAR:
			case EVENT:
			case TODO:
			case JOURNAL:
			case ALARM:
			case TIMEZONE:
			case DAYLIGHT:
			case STANDARD:
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Konstruktor für Komponente vEVENT, vTodo, vJournal, vDaylight, vStandard
	 * <br>
	 * 
	 * @param komponente
	 * @param parameter  - NAME
	 * @param parameter  - UID od. TZID
	 * @param parameter  - SEQ od. DTSTART
	 * @param parameter  - RECURID od. TZOFFSETFROM
	 * 
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String pNAME, String pUID_TZID, String pSEQ_DTSTART,
			String pRECURID_TZOFFSETFROM) throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.komponente = komponente;
		switch (komponente) {
			case EVENT:
			case TODO:
			case JOURNAL:
				logger.info(komponente.toString());
				logger.info(pNAME+"#"+pUID_TZID+"#"+pRECURID_TZOFFSETFROM);
				this.NAME = pNAME;
				this.UID = pUID_TZID;
				this.SEQ = pSEQ_DTSTART;
				this.RECURID = pRECURID_TZOFFSETFROM;
				break;
			case DAYLIGHT:
			case STANDARD:
				this.NAME = pNAME;
				this.TZID = pUID_TZID;
				this.DTSTART = pSEQ_DTSTART;
				this.TZOFFSETFROM = pRECURID_TZOFFSETFROM;
				break;
			case CALENDAR:
			case ALARM:
			case TIMEZONE:
			case FREEBUSY:
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Konstruktor für Komponente vALARM <br>
	 * 
	 * @param komponente
	 * @param NAME
	 * @param UID
	 * @param SEQ
	 * @param RECURID
	 * @param ACTION
	 * @param TRIGGER
	 * 
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String pNAME, String pUID, String pSEQ, String pRECURID,
			String pACTION, String pTRIGGER) throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.komponente = komponente;
		switch (komponente) {
			case ALARM:
				this.NAME = pNAME;
				this.UID = pUID;
				this.SEQ = pSEQ;
				this.RECURID = pRECURID;
				this.ACTION = pACTION;
				this.TRIGGER = pTRIGGER;
				break;
			case CALENDAR:
			case EVENT:
			case TODO:
			case JOURNAL:
			case TIMEZONE:
			case DAYLIGHT:
			case STANDARD:
			case FREEBUSY:
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	public boolean istGleich(GuKKiCalcSchluessel anderer) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String temp;
		try {
			temp = vergleichen(anderer);
		} catch (GuKKiCalF_FalscheKomponente fk) {
			return false;
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp.equals("0");
	}

	public String vergleichen(GuKKiCalcSchluessel anderer) throws GuKKiCalF_FalscheKomponente {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String temp = null;
		if (this.komponente != anderer.komponente) {
			throw new GuKKiCalF_FalscheKomponente();
		}
		switch (komponente) {
			case CALENDAR:
				temp = String.valueOf(this.NAME.compareTo(anderer.NAME));
				break;
			case EVENT:
			case TODO:
			case JOURNAL:
				temp = String.valueOf((this.NAME + this.UID + this.SEQ + this.RECURID)
						.compareTo(anderer.NAME + anderer.UID + anderer.SEQ + anderer.RECURID));
				break;
			case ALARM:
				temp = String.valueOf((this.NAME + this.UID + this.SEQ + this.RECURID + this.ACTION + this.TRIGGER)
						.compareTo(anderer.NAME + anderer.UID + anderer.SEQ + anderer.RECURID + anderer.ACTION
								+ anderer.TRIGGER));
				break;
			case TIMEZONE:
				temp = String.valueOf((this.NAME + this.TZID).compareTo(anderer.NAME + anderer.TZID));
				break;
			case DAYLIGHT:
			case STANDARD:
				temp = String.valueOf((this.NAME + this.TZID + this.DTSTART + this.TZOFFSETFROM)
						.compareTo(anderer.NAME + anderer.TZID + anderer.DTSTART + anderer.TZOFFSETFROM));
				break;
			case FREEBUSY:
				temp = String.valueOf(
						(this.NAME + this.UID + this.DTSTAMP).compareTo(anderer.NAME + anderer.UID + anderer.DTSTAMP));
				break;
			default:
				throw new GuKKiCalF_FalscheKomponente();

		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp;
	}

	@Override
	public String toString() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		String temp = "";
		switch (komponente) {
			case CALENDAR:
				temp = "C " + NAME + " ";
				break;
			case EVENT:
				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case TODO:
				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case JOURNAL:
				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case ALARM:
				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
				break;
			case TIMEZONE:
				temp = "Z " + NAME + "," + TZID + " ";
				break;
			case DAYLIGHT:
				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case STANDARD:
				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case FREEBUSY:
				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
				break;
			default:

		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return temp;
	}
}
