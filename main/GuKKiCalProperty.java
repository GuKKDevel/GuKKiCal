package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
 * 
 * 	RFC 7986 (October 2016) item 3.; p. 14
 * 
 * 		6.1. DISPLAY Property Parameter
 * 		6.2. EMAIL Property Parameter
 * 		6.3. FEATURE Property Parameter
 * 		6.4. LABEL Property Parameter
 *
 * @formatter:on
 * 
 * 
 */
class GuKKiCalProperty {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;
	/*
	 * Notwendige Werte
	 */
	private GuKKiCalcKennung kennung = GuKKiCalcKennung.PROPERTY;
	private GuKKiCalcStatus status = GuKKiCalcStatus.UNDEFINIERT;

	private String literal = "";
	private String wert = "";

	/*
	 * Es folgen die Parameter für other-parms
	 */
	private String ALTREP = null;
	private String CN = null;
	private String CUTYPE = null;
	private String DELFROM = null;
	private String DELTO = null;
	private String DIR = null;
	private String DISPLAY = null;
	private String EMAIL = null;
	private String ENCODING = null;
	private String FBTYPE = null;
	private String FEATURE = null;
	private String FMTTYPE = null;
	private String LABEL = null;
	private String LANGUAGE = null;
	private String MEMBER = null;
	private String PARTSTAT = null;
	private String RANGE = null;
	private String RELATED = null;
	private String RELTYPE = null;
	private String ROLE = null;
	private String RSVP = null;
	private String SENTBY = null;
	private String TZID = null;
	private String VALUETYPE = null;
	/*
	 * X-Name Parameter
	 */
	private ArrayList<String> X_PARMSammlung = new ArrayList<String>();

	private ArrayList<String> Restinformationen = new ArrayList<String>();

	/**
	 * leerer Konstruktor
	 */
	protected GuKKiCalProperty() {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Konstruktor
	 * 
	 */

	protected GuKKiCalProperty(String literal) {

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		this.literal = literal;

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	public GuKKiCalProperty(String propertyDaten, String literal) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.literal = literal;

		if (propertyDaten.substring(literal.length(), literal.length() + 1).equals(":")) {
			this.wert = propertyDaten.substring(literal.length() + 1);
		} else {
			parameterAnalysieren(propertyDaten.substring(literal.length()));
		}

		if (Restinformationen.size() > 0) {
			for (String Restinformation : Restinformationen) {
				logger.log(Level.INFO, "Restinformation:" + "-->" + Restinformation + "<--");
			}
		}

		status = GuKKiCalcStatus.GELESEN;
//		if (!propertyDaten.equals(ausgeben())) {
//			logger.fine("Eingang:" + propertyDaten);
//			logger.fine("Ausgang:" + ausgeben());
//		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende Konstruktor (String, String)

	void parameterAnalysieren(String propertyDaten) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}

		boolean istBackslash = false;
		boolean istLiteral = false;
		String trenner = "";
		String zeichen = "";
		String parameter = "";

		for (int i = 0; i < propertyDaten.length(); i++) {
			zeichen = propertyDaten.substring(i, i + 1);

			switch (zeichen) {

				case ("\\"): {
					if (istBackslash) {
						istBackslash = false;
					} else {
						istBackslash = true;
					}
					parameter += zeichen;
					break;
				}
				case ("\""): {
					if (!istBackslash) {
						if (!istLiteral) {
							istLiteral = true;
						} else {
							istLiteral = false;
						}
					} else {
						istBackslash = false;
					}
					parameter += zeichen;
					break;
				}
				case (":"): {
					if (!istBackslash && !istLiteral && !trenner.equals(":")) {
						if (parameter.length() > 0) {
							parameterBestimmen(parameter, ":");
						}
						trenner = zeichen;
						parameter = "";
					} else {
						parameter += zeichen;
						istBackslash = false;
					}
					break;
				}
				case (";"): {
					if (!istBackslash && !istLiteral && !trenner.equals(":")) {
						if (parameter.length() > 0) {
							parameterBestimmen(parameter, ";");
						}
						trenner = zeichen;
						parameter = "";
					} else {
						parameter += zeichen;
						istBackslash = false;
					}
					break;
				}
				default: {
					parameter += zeichen;
					istBackslash = false;
				}
			} // Ende switch

		} // Ende for-Schleife

		if (parameter.length() > 0) {
			this.wert = parameter;
		}

		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

    /**
     * übertragen der Parameter in das korrekte Feld
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     */
    void parameterBestimmen(String parameter, String trenner) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (parameter.length() > 7 && parameter.substring(0, 7).equals("ALTREP=")) {
            this.ALTREP = parameter.substring(7);
        } else if (parameter.length() > 3 && parameter.substring(0, 3).equals("CN=")) {
            this.CN = parameter.substring(3);
        } else if (parameter.length() > 7 && parameter.substring(0, 7).equals("CUTYPE=")) {
            this.CUTYPE = parameter.substring(7);
        } else if (parameter.length() > 15 && parameter.substring(0, 15).equals("DELEGATED-FROM=")) {
            this.DELFROM = parameter.substring(15);
        } else if (parameter.length() > 13 && parameter.substring(0, 13).equals("DELEGATED-TO=")) {
            this.DELTO = parameter.substring(13);
        } else if (parameter.length() > 4 && parameter.substring(0, 4).equals("DIR=")) {
            this.DIR = parameter.substring(4);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("DISPLAY=")) {
            this.DISPLAY = parameter.substring(8);
        } else if (parameter.length() > 6 && parameter.substring(0, 6).equals("EMAIL=")) {
            this.EMAIL = parameter.substring(6);
        } else if (parameter.length() > 9 && parameter.substring(0, 9).equals("ENCODING=")) {
            this.ENCODING = parameter.substring(9);
        } else if (parameter.length() > 7 && parameter.substring(0, 7).equals("FBTYPE=")) {
            this.FBTYPE = parameter.substring(7);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("FEATURE=")) {
            this.FEATURE = parameter.substring(8);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("FMTTYPE=")) {
            this.FMTTYPE = parameter.substring(8);
        } else if (parameter.length() > 6 && parameter.substring(0, 6).equals("LABEL=")) {
            this.LABEL = parameter.substring(6);
        } else if (parameter.length() > 9 && parameter.substring(0, 9).equals("LANGUAGE=")) {
            this.LANGUAGE = parameter.substring(9);
        } else if (parameter.length() > 7 && parameter.substring(0, 7).equals("MEMBER=")) {
            this.MEMBER = parameter.substring(7);
        } else if (parameter.length() > 9 && parameter.substring(0, 9).equals("PARTSTAT=")) {
            this.PARTSTAT = parameter.substring(9);
        } else if (parameter.length() > 6 && parameter.substring(0, 6).equals("RANGE=")) {
            this.RANGE = parameter.substring(6);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("RELATED=")) {
            this.RELATED = parameter.substring(8);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("RELTYPE=")) {
            this.RELTYPE = parameter.substring(8);
        } else if (parameter.length() > 5 && parameter.substring(0, 5).equals("ROLE=")) {
            this.ROLE = parameter.substring(5);
        } else if (parameter.length() > 5 && parameter.substring(0, 5).equals("RSVP=")) {
            this.RSVP = parameter.substring(5);
        } else if (parameter.length() > 8 && parameter.substring(0, 8).equals("SENT-BY=")) {
            this.SENTBY = parameter.substring(8);
        } else if (parameter.length() > 5 && parameter.substring(0, 5).equals("TZID=")) {
            this.TZID = parameter.substring(5);
        } else if (parameter.length() > 6 && parameter.substring(0, 6).equals("VALUE=")) {
            this.VALUETYPE = parameter.substring(6);
 
        /* Abschluss und Fallbackparameter */
 
        } else if (parameter.length() > 2 && parameter.substring(0, 2).equals("X-")) {
            this.X_PARMSammlung.add(parameter + trenner);
        } else {
            this.Restinformationen.add(parameter + trenner);
        }
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
    } // Ende der generierten Methode parameterBestimmen für GuKKiCalProperty V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
    /**
     * Kopieren aller Parameter der Eigenschaft
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return GuKKiCalProperty
     */
    protected GuKKiCalProperty kopieren() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        GuKKiCalProperty temp = new GuKKiCalProperty();
        temp.literal = this.literal;
        temp.wert = this.wert;
        temp.ALTREP = this.ALTREP == null ? null : this.ALTREP;
        temp.CN = this.CN == null ? null : this.CN;
        temp.CUTYPE = this.CUTYPE == null ? null : this.CUTYPE;
        temp.DELFROM = this.DELFROM == null ? null : this.DELFROM;
        temp.DELTO = this.DELTO == null ? null : this.DELTO;
        temp.DIR = this.DIR == null ? null : this.DIR;
        temp.DISPLAY = this.DISPLAY == null ? null : this.DISPLAY;
        temp.EMAIL = this.EMAIL == null ? null : this.EMAIL;
        temp.ENCODING = this.ENCODING == null ? null : this.ENCODING;
        temp.FBTYPE = this.FBTYPE == null ? null : this.FBTYPE;
        temp.FEATURE = this.FEATURE == null ? null : this.FEATURE;
        temp.FMTTYPE = this.FMTTYPE == null ? null : this.FMTTYPE;
        temp.LABEL = this.LABEL == null ? null : this.LABEL;
        temp.LANGUAGE = this.LANGUAGE == null ? null : this.LANGUAGE;
        temp.MEMBER = this.MEMBER == null ? null : this.MEMBER;
        temp.PARTSTAT = this.PARTSTAT == null ? null : this.PARTSTAT;
        temp.RANGE = this.RANGE == null ? null : this.RANGE;
        temp.RELATED = this.RELATED == null ? null : this.RELATED;
        temp.RELTYPE = this.RELTYPE == null ? null : this.RELTYPE;
        temp.ROLE = this.ROLE == null ? null : this.ROLE;
        temp.RSVP = this.RSVP == null ? null : this.RSVP;
        temp.SENTBY = this.SENTBY == null ? null : this.SENTBY;
        temp.TZID = this.TZID == null ? null : this.TZID;
        temp.VALUETYPE = this.VALUETYPE == null ? null : this.VALUETYPE;
 
        /* Abschluss und Fallbackparameter */
 
        for (String X_PARM : this.X_PARMSammlung) {
            temp.X_PARMSammlung.add(X_PARM);
        }
        for (String Restinformation : this.Restinformationen) {
            temp.Restinformationen.add(Restinformation);
        }
        temp.status = GuKKiCalcStatus.KOPIERT;
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
        return temp;
    } // Ende der generierten Methode kopieren für GuKKiCalProperty V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
 
    /**
     * Vergleichen aller Parameter des Attributs
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return boolean
     */
    protected boolean istGleich(Object dasAndere) {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        if (!dasAndere.getClass().equals(this.getClass())) {
            return false;
        }
        GuKKiCalProperty temp = (GuKKiCalProperty) dasAndere;
        if (!((temp.ALTREP == null && this.ALTREP == null)
          || (temp.ALTREP != null && this.ALTREP != null && temp.ALTREP.equals(this.ALTREP)))) {
            return false;
         }
        if (!((temp.CN == null && this.CN == null)
          || (temp.CN != null && this.CN != null && temp.CN.equals(this.CN)))) {
            return false;
         }
        if (!((temp.CUTYPE == null && this.CUTYPE == null)
          || (temp.CUTYPE != null && this.CUTYPE != null && temp.CUTYPE.equals(this.CUTYPE)))) {
            return false;
         }
        if (!((temp.DELFROM == null && this.DELFROM == null)
          || (temp.DELFROM != null && this.DELFROM != null && temp.DELFROM.equals(this.DELFROM)))) {
            return false;
         }
        if (!((temp.DELTO == null && this.DELTO == null)
          || (temp.DELTO != null && this.DELTO != null && temp.DELTO.equals(this.DELTO)))) {
            return false;
         }
        if (!((temp.DIR == null && this.DIR == null)
          || (temp.DIR != null && this.DIR != null && temp.DIR.equals(this.DIR)))) {
            return false;
         }
        if (!((temp.DISPLAY == null && this.DISPLAY == null)
          || (temp.DISPLAY != null && this.DISPLAY != null && temp.DISPLAY.equals(this.DISPLAY)))) {
            return false;
         }
        if (!((temp.EMAIL == null && this.EMAIL == null)
          || (temp.EMAIL != null && this.EMAIL != null && temp.EMAIL.equals(this.EMAIL)))) {
            return false;
         }
        if (!((temp.ENCODING == null && this.ENCODING == null)
          || (temp.ENCODING != null && this.ENCODING != null && temp.ENCODING.equals(this.ENCODING)))) {
            return false;
         }
        if (!((temp.FBTYPE == null && this.FBTYPE == null)
          || (temp.FBTYPE != null && this.FBTYPE != null && temp.FBTYPE.equals(this.FBTYPE)))) {
            return false;
         }
        if (!((temp.FEATURE == null && this.FEATURE == null)
          || (temp.FEATURE != null && this.FEATURE != null && temp.FEATURE.equals(this.FEATURE)))) {
            return false;
         }
        if (!((temp.FMTTYPE == null && this.FMTTYPE == null)
          || (temp.FMTTYPE != null && this.FMTTYPE != null && temp.FMTTYPE.equals(this.FMTTYPE)))) {
            return false;
         }
        if (!((temp.LABEL == null && this.LABEL == null)
          || (temp.LABEL != null && this.LABEL != null && temp.LABEL.equals(this.LABEL)))) {
            return false;
         }
        if (!((temp.LANGUAGE == null && this.LANGUAGE == null)
          || (temp.LANGUAGE != null && this.LANGUAGE != null && temp.LANGUAGE.equals(this.LANGUAGE)))) {
            return false;
         }
        if (!((temp.MEMBER == null && this.MEMBER == null)
          || (temp.MEMBER != null && this.MEMBER != null && temp.MEMBER.equals(this.MEMBER)))) {
            return false;
         }
        if (!((temp.PARTSTAT == null && this.PARTSTAT == null)
          || (temp.PARTSTAT != null && this.PARTSTAT != null && temp.PARTSTAT.equals(this.PARTSTAT)))) {
            return false;
         }
        if (!((temp.RANGE == null && this.RANGE == null)
          || (temp.RANGE != null && this.RANGE != null && temp.RANGE.equals(this.RANGE)))) {
            return false;
         }
        if (!((temp.RELATED == null && this.RELATED == null)
          || (temp.RELATED != null && this.RELATED != null && temp.RELATED.equals(this.RELATED)))) {
            return false;
         }
        if (!((temp.RELTYPE == null && this.RELTYPE == null)
          || (temp.RELTYPE != null && this.RELTYPE != null && temp.RELTYPE.equals(this.RELTYPE)))) {
            return false;
         }
        if (!((temp.ROLE == null && this.ROLE == null)
          || (temp.ROLE != null && this.ROLE != null && temp.ROLE.equals(this.ROLE)))) {
            return false;
         }
        if (!((temp.RSVP == null && this.RSVP == null)
          || (temp.RSVP != null && this.RSVP != null && temp.RSVP.equals(this.RSVP)))) {
            return false;
         }
        if (!((temp.SENTBY == null && this.SENTBY == null)
          || (temp.SENTBY != null && this.SENTBY != null && temp.SENTBY.equals(this.SENTBY)))) {
            return false;
         }
        if (!((temp.TZID == null && this.TZID == null)
          || (temp.TZID != null && this.TZID != null && temp.TZID.equals(this.TZID)))) {
            return false;
         }
        if (!((temp.VALUETYPE == null && this.VALUETYPE == null)
          || (temp.VALUETYPE != null && this.VALUETYPE != null && temp.VALUETYPE.equals(this.VALUETYPE)))) {
            return false;
         }
 
        /* Abschluss und Fallbackparameter */
 
        if (temp.X_PARMSammlung.size() != this.X_PARMSammlung.size()) {
 			  return false;
        }
        for (int i =0; i < X_PARMSammlung.size(); i++) {
            if (!temp.X_PARMSammlung.get(i).equals(this.X_PARMSammlung.get(i))) {
                return false; 
            }
        }
        if (temp.Restinformationen.size() != this.Restinformationen.size()) {
 			  return false;
		 }
        for (int i =0; i < Restinformationen.size(); i++) {
            if (!temp.Restinformationen.get(i).equals(this.Restinformationen.get(i))) {
                return false; 
            }
        }
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
        return true;
    } // Ende der generierten Methode istGleich für GuKKiCalProperty V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    /**
     * Datenstromausgabe aller Parameter des Attributs
     * Version V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
     *
     * @return String
     */
    protected String ausgeben() {
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "begonnen");}
        String schreibText = "";
        String sammelText = "";
        String endeText = "";
        String trenner = "";
        schreibText += this.ALTREP == null ? "" : ";ALTREP=" + this.ALTREP;
        schreibText += this.CN == null ? "" : ";CN=" + this.CN;
        schreibText += this.CUTYPE == null ? "" : ";CUTYPE=" + this.CUTYPE;
        schreibText += this.DELFROM == null ? "" : ";DELEGATED-FROM=" + this.DELFROM;
        schreibText += this.DELTO == null ? "" : ";DELEGATED-TO=" + this.DELTO;
        schreibText += this.DIR == null ? "" : ";DIR=" + this.DIR;
        schreibText += this.DISPLAY == null ? "" : ";DISPLAY=" + this.DISPLAY;
        schreibText += this.EMAIL == null ? "" : ";EMAIL=" + this.EMAIL;
        schreibText += this.ENCODING == null ? "" : ";ENCODING=" + this.ENCODING;
        schreibText += this.FBTYPE == null ? "" : ";FBTYPE=" + this.FBTYPE;
        schreibText += this.FEATURE == null ? "" : ";FEATURE=" + this.FEATURE;
        schreibText += this.FMTTYPE == null ? "" : ";FMTTYPE=" + this.FMTTYPE;
        schreibText += this.LABEL == null ? "" : ";LABEL=" + this.LABEL;
        schreibText += this.LANGUAGE == null ? "" : ";LANGUAGE=" + this.LANGUAGE;
        schreibText += this.MEMBER == null ? "" : ";MEMBER=" + this.MEMBER;
        schreibText += this.PARTSTAT == null ? "" : ";PARTSTAT=" + this.PARTSTAT;
        schreibText += this.RANGE == null ? "" : ";RANGE=" + this.RANGE;
        schreibText += this.RELATED == null ? "" : ";RELATED=" + this.RELATED;
        schreibText += this.RELTYPE == null ? "" : ";RELTYPE=" + this.RELTYPE;
        schreibText += this.ROLE == null ? "" : ";ROLE=" + this.ROLE;
        schreibText += this.RSVP == null ? "" : ";RSVP=" + this.RSVP;
        schreibText += this.SENTBY == null ? "" : ";SENT-BY=" + this.SENTBY;
        schreibText += this.TZID == null ? "" : ";TZID=" + this.TZID;
        schreibText += this.VALUETYPE == null ? "" : ";VALUE=" + this.VALUETYPE;
 
        /* Abschluss und Fallbackparameter */
 
        sammelText = "";
        for (String X_PARM : this.X_PARMSammlung) {
            sammelText += X_PARM;
        }
        if (sammelText.length() > 0 ) {
            if (sammelText.substring(sammelText.length()-1).equals(":")) {
                endeText = sammelText.substring(0,sammelText.length()-1);
            }
            else {
                schreibText += ";" + sammelText.substring(0,sammelText.length()-1);
            }
        }
        sammelText = "";
        for (String Restinformation : this.Restinformationen) {
            sammelText += Restinformation;
        }
        if (sammelText.length() > 0 ) {
            if (sammelText.substring(sammelText.length()-1).equals(":")) {
                endeText = sammelText.substring(0,sammelText.length()-1);
            }
            else {
                schreibText += ";" + sammelText.substring(0,sammelText.length()-1);
            }
        }
        if (endeText.length() > 0) {
            schreibText += ";"+endeText;
        }
        if (logger.isLoggable(logLevel)) {logger.log(logLevel, "beendet");}
        return this.literal + schreibText + ":" + this.wert;
    } // Ende der generierten Methode ausgeben für GuKKiCalProperty V 0.0.3  (RFC 5545, RFC 7968) 2021-12-22T15-12-22
    
	String getWert() {
		return wert;
	}

	void setWert(String propertyWert) {
		this.wert = propertyWert;
	}

	String getLiteral() {
		return literal;
	}

	void setLiteral(String propertyLiteral) {
		this.literal = propertyLiteral;
	}

//	String getRestinformationen() {
//		return Restinformationen.toString();
//	}

//	void setRestinformationen(String Restinformationen) {
//		this.Restinformationen = Restinformationen;
//	}

	@Override

	public String toString() {
		String nz = "\n";
		String ausgabe = "Literal=" + literal + nz;

		if (ALTREP != null)
			ausgabe += "altrepparm=" + ALTREP + nz;
		if (CN != null)
			ausgabe += "cnparam=" + CN + nz;
		if (CUTYPE != null)
			ausgabe += "cutypeparam=" + CUTYPE + nz;
		if (DELFROM != null)
			ausgabe += "delfromparam=" + DELFROM + nz;
		if (DELTO != null)
			ausgabe += "deltoparam=" + DELTO + nz;
		if (DIR != null)
			ausgabe += "dirparam=" + DIR + nz;
		if (ENCODING != null)
			ausgabe += "encodingparam=" + ENCODING + nz;
		if (FBTYPE != null)
			ausgabe += "fbtypeparam=" + FBTYPE + nz;
		if (FMTTYPE != null)
			ausgabe += "fmttypeparam=" + FMTTYPE + nz;
		if (LANGUAGE != null)
			ausgabe += "languageparam=" + LANGUAGE + nz;
		if (MEMBER != null)
			ausgabe += "memberparam=" + MEMBER + nz;
		if (PARTSTAT != null)
			ausgabe += "partstatparam=" + PARTSTAT + nz;
		if (RANGE != null)
			ausgabe += "rangeparamm=" + RANGE + nz;
		if (RELATED != null)
			ausgabe += "trigrelparam=" + RELATED + nz;
		if (RELTYPE != null)
			ausgabe += "reltypeparam=" + RELTYPE + nz;
		if (ROLE != null)
			ausgabe += "roleparam=" + ROLE + nz;
		if (RSVP != null)
			ausgabe += "rsvpparam=" + RSVP + nz;
		if (SENTBY != null)
			ausgabe += "sentbyparam=" + SENTBY + nz;
		if (TZID != null)
			ausgabe += "tzidparam=" + TZID + nz;
		if (VALUETYPE != null)
			ausgabe += "valuetypeparam=" + VALUETYPE + nz;
		/*
		 * X-Name Parameter
		 */
		if (X_PARMSammlung.size() > 0) {
			for (String parm : X_PARMSammlung) {
				ausgabe += parm + nz;
			}
		}
		if (Restinformationen.size() > 0) {
			for (String Restinformation : Restinformationen) {
				ausgabe += Restinformation + nz;
			}
		}

		ausgabe += "wert=" + wert + nz;

		return ausgabe.substring(0, ausgabe.length() - 1);
	}

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
	private boolean checkALTREP() {
		return true;
	};

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
	private boolean checkCN() {
		return true;
	}

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
	private boolean checkCUTYPE() {
		return true;
	}

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
	private boolean checkDELFROM() {
		return true;
	}

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
	private boolean checkDELTO() {
		return true;
	}

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
	private boolean checkDIR() {
		return true;
	}

	/**
	 *  DISPLAY Property Parameter
	 * 
	 * 	RFC 7986 (October 2016) item 6.1; p. 14 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: DISPLAY
	 * 
	 * 	Purpose: To specify different ways in which an image for a calendar
	 * 		or component can be displayed.
	 *
	 *	Description: This property parameter MAY be specified on "IMAGE"
	 *		properties. In the absence of this parameter, the default value
	 *		"BADGE" MUST be used. The value determines how a client ought to
	 *		present an image supplied in iCalendar data to the user.
	 *		Values for this parameter are registered with IANA as per
	 *		Section 9.3.1. New values can be added to this registry following
	 *		the procedure outlined in Section 8.2.1 of [RFC5545].
	 *		Servers and clients MUST handle x-name and iana-token values they
	 *		don’t recognize by not displaying any image at all.
	 *
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		displayparam = "DISPLAY" "=" displayval *("," displayval)
	 * 
	 * 			displayval = ("BADGE" /		image inline with the title of the event
	 * 
	 * 						"GRAPHIC" /		a full image replacement for the event itself
	 * 
	 * 						"FULLSIZE" /	an image that is used to enhance the event
	 * 
	 * 						"THUMBNAIL" /	a smaller variant of "FULLSIZE" to be used when space for the image is constrained
	 * 
	 * 						x-name /		Experimental type
	 * 						iana-token)		Other IANA-registered type
	 * 
	 * 		Default is BADGE
	 * 
	 * @formatter:on
	 * 	 
	 */
	private boolean checkDISPLAY() {
		return true;
	}

	/**
	 *  EMAIL Property Parameter
	 * 
	 * 	RFC 7986 (October 2016) item 6.2; p. 15 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: EMAIL
	 * 
	 * 	Purpose: To specify an email address that is used to identify or
	 * 		contact an organizer or attendee.
	 *
	 * 	Description: This property parameter MAY be specified on "ORGANIZER"
	 * 		or "ATTENDEE" properties. This property can be used in situations
	 * 		where the calendar user address value of the "ORGANIZER" and
	 * 		"ATTENDEE" properties is not likely to be an identifier that
	 * 		recipients of scheduling messages could use to match the calendar
	 * 		user with, for example, an address book entry. The value of this
	 * 		property is an email address that can easily be matched by
	 * 		recipients. Recipients can also use this value as an alternative
	 * 		means of contacting the calendar user via email. If a recipient’s
	 * 		calendar user agent allows the recipient to save contact
	 * 		information based on the "ORGANIZER" or "ATTENDEE" properties,
	 * 		those calendar user agents SHOULD use any "EMAIL" property
	 * 		parameter value for the email address of the contact over any
	 * 		mailto: calendar user address specified as the value of the
	 * 		property. Calendar user agents SHOULD NOT include an "EMAIL"
	 * 		property parameter when its value matches the calendar user
	 * 		address specified as the value of the property.
	 * 
	 * Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		emailparam = "EMAIL" "=" param-value
	 * 
	 * @formatter:on
	 * 	 
	 */
	private boolean checkEMAIL() {
		return true;
	}

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
	private boolean checkENCODING() {
		return true;
	}

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
	private boolean checkFBTYPE() {
		return true;
	}

	/**
	 *  FEATURE Property Parameter
	 * 
	 * 	RFC 7986 (October 2016) item 6.3.; p. 16 
	 * 
	 * @formatter:off
	 * 
	 * 	Parameter Name: FEATURE
	 * 
	 * 	Purpose: To specify a feature or features of a conference or
	 * 		broadcast system.
	 * 	
	 * 	Description: This property parameter MAY be specified on the
	 * 		"CONFERENCE" property. Multiple values can be specified. The
	 * 		"MODERATOR" value is used to indicate that the property value is
	 * 		specific to the owner/initiator of the conference and contains a
	 * 		URI that "activates" the system (e.g., a "moderator" access code
	 * 		for a phone conference system that is different from the "regular"
	 * 		access code).
	 * 
	 * 	Format Definition: This property parameter is defined by the following notation:
	 * 
	 * 		featureparam = "FEATURE" "=" featuretext *("," featuretext)
	 * 
	 * 			featuretext = ("AUDIO" /			Audio capability
	 * 						   	"CHAT" /			Chat or instant messaging
	 * 						   	"FEED" /			Blog or Atom feed
	 * 						   	"MODERATOR" / 		Moderator dial-in code
	 *						   	"PHONE" /			Phone conference
	 *						   	"SCREEN" /			Screen sharing
	 *							"VIDEO" /			Video capability
	 *
	 *							x-name /			Experimental type
	 *							iana-token)			Other IANA-registered type
	 * 	 
     * @formatter:on
	 * 	 
	 */
	private boolean checkFEATURE() {
		return true;
	}

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
	private boolean checkFMTTYPE() {
		return true;
	}

	/**
	 *  LABEL Property Parameter
	 * 
	 * 	RFC 7986 (October 2016) item 6.4.; p. 17 
	 * 
	 * @formatter:off
	 *
	 * 	Parameter Name: LABEL
	 * 
	 * 	Purpose: To provide a human-readable label.
	 * 
	 *	Description: This property parameter MAY be specified on the
	 *		"CONFERENCE" property. It is anticipated that other extensions to
	 *		iCalendar will reuse this property parameter on new properties
	 *		that they define. As a result, clients MUST expect to find this
	 *		property parameter present on many different properties. It
	 *		provides a human-readable label that can be presented to calendar
	 *		users to allow them to discriminate between properties that might
	 *		be similar or provide additional information for properties that
	 *		are not self-describing. The "LANGUAGE" property parameter can be
	 *		used to specify the language of the text in the parameter value
	 *		(as per Section 3.2.10 of [RFC5545]).
	 *
	 *	Format Definition: This property parameter is defined by the following notation:
	 *
	 *		labelparam = "LABEL" "=" param-value
	 * 
	 * @formatter:on
	 * 	 
	 */
	private boolean checkLABEL() {
		return true;
	}

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
	private boolean checkLANGUAGE() {
		return true;
	}

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
	private boolean checkMEMBER() {
		return true;
	}

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
	private boolean checkPARTSTAT() {
		return true;
	}

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
	private boolean checkRANGE() {
		return true;
	}

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
	private boolean checkRELATED() {
		return true;
	}

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
	private boolean checkRELTYPE() {
		return true;
	}

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
	private boolean checkROLE() {
		return true;
	}

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
	private boolean checkRSVP() {
		return true;
	}

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
	private boolean checkSENTBY() {
		return true;
	}

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
	private boolean checkTZID() {
		return true;
	}

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
	private boolean checkVALUETYPE() {
		return true;
	}

}
