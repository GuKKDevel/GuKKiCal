package main;

import exceptions.GuKKiCalF_FalscheKomponente;
import exceptions.GuKKiCalW_FalscheKomponente;

/**
 * 
 * @author GuKKDevel
 *
 *         Schluessel der einzelnen Componenten
 */
class GuKKiCalcSchluessel {

	// Status des Schlüssels
	GuKKiCalcStatus status = GuKKiCalcStatus.UNDEFINIERT;
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
	 * Konstruktor nur für das Anlegen einer beliebigen Komponente
	 * 
	 * @param komponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente) {
		this.komponente = komponente;
		this.status = GuKKiCalcStatus.UNDEFINIERT;
	}

	/**
	 * Konstruktor für Komponente
	 * 
	 * vCalendar (nichts) <br>
	 * vEvent (nichts) <br>
	 * vTodo (nichts) <br>
	 * vJournal (nichts) <br>
	 * vAlarm (nichts) <br>
	 * vTimezone (nur TZID) - fehlt NAME <br>
	 * vDaylight (nichts) <br>
	 * vStandard (nichts) <br>
	 * vFreeBusy (nichts)
	 * 
	 * @param komponente
	 * @param parameter1 - NAME bzw TZID
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String parameter1) throws GuKKiCalF_FalscheKomponente {
		switch (komponente) {
			case CALENDAR:
//				temp = "C " + NAME + " ";
				this.NAME = parameter1;
				this.status = GuKKiCalcStatus.KOMPLETT;
				break;
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";
				this.TZID = parameter1;
				this.status = GuKKiCalcStatus.TEILWEISE;
				break;
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";			case ALARM:
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
	}

	/**
	 * Konstruktor für Komponente <br>
	 * 
	 * vCalendar (nichts) <br>
	 * vEvent (nichts) <br>
	 * vTodo (nichts) <br>
	 * vJournal (nichts) <br>
	 * vAlarm (nur ACTION und TRIGGER) - fehlt NAME, UID, SEQ, RECURID <br>
	 * vTimezone (nichts <br>
	 * vDaylight (nur DTSTART und TZOFFSETFROM) - fehlt NAME und TZID <br>
	 * vStandard (nichts) <br>
	 * vFreeBusy (nur UID und DTSTAMP) - fehlt NAME
	 * 
	 * @param komponente
	 * @param parameter1 - ACTION bzw. DTSTART bzw. UID
	 * @param parameter2 - TRIGGER bzw. TZOFFSETFROM bzw. DTSTAMP
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String parameter1, String parameter2)
			throws GuKKiCalF_FalscheKomponente {
		switch (komponente) {
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";				this.ACTION = parameter1;
				this.TRIGGER = parameter2;
				this.status = GuKKiCalcStatus.TEILWEISE;
				break;
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				this.DTSTART = parameter1;
				this.TZOFFSETFROM = parameter2;
				this.status = GuKKiCalcStatus.TEILWEISE;
				break;
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
				this.UID = parameter1;
				this.DTSTAMP = parameter2;
				this.status = GuKKiCalcStatus.TEILWEISE;
				break;
			case CALENDAR:
//				temp = "C " + NAME + " ";
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";	
			default:
				throw new GuKKiCalF_FalscheKomponente();

		}
	}

	/**
	 * Konstruktor für Komponente <br>
	 * 
	 * vCalendar (nichts) <br>
	 * vEvent (nur UID, SEQ und RECURID) - fehlt NAME <br>
	 * vTodo (nur UID, SEQ und RECURID) - fehlt NAME <br>
	 * vJournal (nur UID, SEQ und RECURID) - fehlt NAME <br>
	 * vAlarm (nichts) <br>
	 * vTimezone (nichts <br>
	 * vDaylight (nichts) <br>
	 * vStandard (nichts) <br>
	 * vFreeBusy (nichts)
	 * 
	 * @param komponente
	 * @param parameter1
	 * @param parameter2
	 * @param parameter3
	 * @throws GuKKiCalF_FalscheKomponente
	 */
	public GuKKiCalcSchluessel(GuKKiCalcKennung komponente, String parameter1, String parameter2, String parameter3)
			throws GuKKiCalF_FalscheKomponente {
		switch (komponente) {
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				this.UID = parameter1;
				this.SEQ = parameter2;
				this.RECURID = parameter3;
				this.status = GuKKiCalcStatus.TEILWEISE;
				break;
			case CALENDAR:
//				temp = "C " + NAME + " ";
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
	}

	/**
	 * Einfügen des Teilschlüssels für vCalendar
	 * 
	 * @param pNAME
	 */
	public void einfuegenCSchluessel(String pNAME)throws GuKKiCalF_FalscheKomponente  {
		this.NAME = pNAME;
		switch (komponente) {
			case CALENDAR:
//				temp = "C " + NAME + " ";
				this.status = GuKKiCalcStatus.KOMPLETT;
				break;
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				if (this.UID.equals("") && this.SEQ.equals("") && this.RECURID.equals("")) {
					this.status = GuKKiCalcStatus.TEILWEISE;
				} else {
					this.status = GuKKiCalcStatus.KOMPLETT;
				}
				break;
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
				if (this.UID.equals("") && this.SEQ.equals("") && this.RECURID.equals("") || this.ACTION.equals("")
						|| this.TRIGGER.equals("")) {
					this.status = GuKKiCalcStatus.TEILWEISE;
				} else {
					this.status = GuKKiCalcStatus.KOMPLETT;
				}
				break;
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";
				if (this.TZID.equals("")) {
					this.status = GuKKiCalcStatus.TEILWEISE;
				} else {
					this.status = GuKKiCalcStatus.KOMPLETT;
				}
				break;
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				if (this.TZID.equals("") || this.DTSTART.equals("") && this.TZOFFSETFROM.equals("")) {
					this.status = GuKKiCalcStatus.TEILWEISE;
				} else {
					this.status = GuKKiCalcStatus.KOMPLETT;
				}
				break;
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
				if (this.UID.equals("") && this.DTSTAMP.equals("")) {
					this.status = GuKKiCalcStatus.TEILWEISE;
				} else {
					this.status = GuKKiCalcStatus.KOMPLETT;
				}
				break;
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
	}

	/**
	 * Einfügen des Teilschluessels für vEvent
	 * 
	 * @param pUID
	 * @param pSEQ
	 * @param pRECURID
	 */
	public void einfuegenESchluessel(String pUID, String pSEQ, String pRECURID) throws GuKKiCalF_FalscheKomponente {
		einfuegenETJSchluessel(pUID, pSEQ, pRECURID);
	}

	/**
	 * Einfügen des Teilschluessels für vTodo
	 * 
	 * @param pUID
	 * @param pSEQ
	 * @param pRECURID
	 */
	public void einfuegenTSchluessel(String pUID, String pSEQ, String pRECURID)throws GuKKiCalF_FalscheKomponente  {
		einfuegenETJSchluessel(pUID, pSEQ, pRECURID);
	}

	/**
	 * Einfügen des Teilschluessels für vJournal
	 * 
	 * @param pUID
	 * @param pSEQ
	 * @param pRECURID
	 */
	public void einfuegenJSchluessel(String pUID, String pSEQ, String pRECURID) throws GuKKiCalF_FalscheKomponente {
		einfuegenETJSchluessel(pUID, pSEQ, pRECURID);
	}

	/**
	 * Einfügen des Teilschluessels für vEvent, vTodo oder vJournal
	 * 
	 * @param pUID
	 * @param pSEQ
	 * @param pRECURID
	 */
	public void einfuegenETJSchluessel(String pUID, String pSEQ, String pRECURID)throws GuKKiCalF_FalscheKomponente  {
		this.UID = pUID;
		this.SEQ = pSEQ;
		this.RECURID = pRECURID;
		switch (komponente) {
			case CALENDAR:
//				temp = "C " + NAME + " ";
				break;
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
				break;
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";
				break;
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
				break;
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
	}

	public void einfuegenZSchluessel(String pTZID) throws GuKKiCalF_FalscheKomponente {
		this.TZID = pTZID;
		switch (komponente) {
			case CALENDAR:
//				temp = "C " + NAME + " ";
				break;
			case EVENT:
//				temp = "E " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case TODO:
//				temp = "T " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case JOURNAL:
//				temp = "J " + NAME + "," + UID + "," + SEQ + "," + RECURID + " ";
				break;
			case ALARM:
//				temp = "A " + NAME + "," + UID + "," + SEQ + "," + RECURID + "," + ACTION + "," + TRIGGER + " ";
				break;
			case TIMEZONE:
//				temp = "Z " + NAME + "," + TZID + " ";
				break;
			case DAYLIGHT:
//				temp = "D " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case STANDARD:
//				temp = "S " + NAME + "," + TZID + "," + DTSTART + "," + TZOFFSETFROM + " ";
				break;
			case FREEBUSY:
//				temp = "F " + NAME + "," + UID + "," + DTSTAMP + " ";
				break;
			default:
				throw new GuKKiCalF_FalscheKomponente();
		}
	}

	@Override
	public String toString() {
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
		return temp;
	}

	public boolean istGleich(GuKKiCalcSchluessel anderer) {
		String temp;
		try {
			temp = vergleichen(anderer);
		} catch (GuKKiCalW_FalscheKomponente fk) {
			return false;
		}
		return temp.equals("0");
	}

	public String vergleichen(GuKKiCalcSchluessel anderer) throws GuKKiCalW_FalscheKomponente {
		String temp = null;
		if (this.komponente != anderer.komponente) {
			throw new GuKKiCalW_FalscheKomponente();
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
		}
		return temp;
	}
}
