import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import main.GuKKiCalProperty;

public class JavaTeileGenerieren {
	String verzeichnis = "../../Git-Repositories/GuKKiCal/GenerierteMethoden/";
	/* @formatter:off*/
	public String [] [] parameter = { // Bedeutung: 1 Object einfach, 2 Object mehrfach 
			{"1", "ALTREP"},          //            9 String einfach, 8 String mehrfach  
			{"1", "CN"},
			{"1", "CUTYPE"},
			{"1", "DELFROM"},
			{"1", "DELTO"},
			{"1", "DIR"},
			{"1", "ENCODING"},
			{"1", "FBTYPE"},
			{"1", "FMTTYPE"},
			{"1", "LANGUAGE"},
			{"1", "MEMBER"},
			{"1", "PARTSTAT"},
			{"1", "RANGE"},
			{"1", "RELATED"},
			{"1", "RELTYPE"},
			{"1", "ROLE"},
			{"1", "RSVP"},
			{"1", "SENTBY"},
			{"1", "TZID"},
			{"1", "VALUETYPE"},
			{"1", "X_UID"},
			{"9", "Restinformationen"},
	};
	/* @formatter:on */
	/* @formatter:off*/
	public String [] [] property = {					// Liste der einzelnen Properties/Komponente
			{"-","2","-","-","-","-","-","-","-","ATTACH"},
			{"-","2","-","-","-","-","-","-","-","ATTENDEE"},
			{"1","-","-","-","-","-","-","-","-","CALSCALE"},
			{"2","2","-","-","-","-","-","-","-","CATEGORIES"},
			{"-","1","-","-","-","-","-","-","-","CLASS"},
			{"1","1","-","-","-","-","-","-","-","COLOR"},
			{"-","2","-","-","-","-","-","-","-","COMMENT"},
			{"-","2","-","-","-","-","-","-","-","CONFERENCE"},
			{"-","2","-","-","-","-","-","-","-","CONTACT"},
			{"-","1","-","-","-","-","-","-","-","CREATED"},
			{"2","1","-","-","-","-","-","-","-","DESCRIPTION"},
			{"-","1","-","-","-","-","-","-","-","DTEND"},
			{"-","1","-","-","-","-","-","-","-","DTSTAMP"},
			{"-","1","-","-","-","-","-","-","-","DTSTART"},
			{"-","1","-","-","-","-","-","-","-","DURATION"},
			{"-","2","-","-","-","-","-","-","-","EXDATE"},
			{"-","1","-","-","-","-","-","-","-","GEO"},
			{"2","2","-","-","-","-","-","-","-","IMAGE"},
			{"1","1","-","-","-","-","-","-","-","LAST_MOD"},
			{"-","1","-","-","-","-","-","-","-","LOCATION"},
			{"1","-","-","-","-","-","-","-","-","METHOD"},
			{"2","-","-","-","-","-","-","-","-","NAME"},
			{"-","1","-","-","-","-","-","-","-","ORGANIZER"},
			{"-","1","-","-","-","-","-","-","-","PRIORITY"},
			{"1","-","-","-","-","-","-","-","-","PRODID"},
			{"-","1","-","-","-","-","-","-","-","RECURID"},
			{"1","-","-","-","-","-","-","-","-","REFRESH"},
			{"-","1","-","-","-","-","-","-","-","RRULE"},
			{"-","1","-","-","-","-","-","-","-","SEQUENCE"},
			{"1","-","-","-","-","-","-","-","-","SOURCE"},
			{"-","1","-","-","-","-","-","-","-","STATUS"},
			{"-","1","-","-","-","-","-","-","-","SUMMARY"},
			{"-","1","-","-","-","-","-","-","-","TRANSP"},
			{"1","-","-","-","-","-","-","-","-","VERSION"},
			{"1","1","-","-","-","-","-","-","-","UID"},
			{"-","1","-","-","-","-","-","-","-","URL"},
			{"0","0","-","-","-","-","-","-","-","Restinformationen"}
	};
	/*
	{"-","2","-","-","-","-","-","-","-","RSTATUS"},
	{"-","2","-","-","-","-","-","-","-","RELATED"},
	/* 
	private ArrayList<GuKKiCalProperty> Sammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> IMAGESammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> Sammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> Sammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RELATED_TOSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RESOURCESSammlung = new ArrayList<GuKKiCalProperty>();
	private ArrayList<GuKKiCalProperty> RDATESammlung = new ArrayList<GuKKiCalProperty>();
	 
*/
	/* @formatter:on */
	/* @formatter:off*/
	public String [] [] component = {					// Liste der einzelnen Komponenten
			{"0", "GuKKiCaliCalendar", "VCALENDAR"},
			{"1", "GuKKiCalvEvent", "VEVENT"},
			{"2", "GuKKiCalvTodo", "VTODO"},
			{"3", "GuKKiCalvJournal", "VJOURNAL"},
			{"4", "GuKKiCalvAlarm", "VALARM"},
			{"5", "GuKKiCalvTimezone"},
			{"6", "GuKKiCalcDaylight","DAYLIGHT"},
			{"7", "GuKKiCalcStandard","STANDARD"},
			{"8", "GuKKiCalvFreeBusy","VFREEBUSY"}
	};
	/* @formatter:on */
	/* @formatter:off*/
	public String [] [] subcomponent = {				// Liste der zugehörigen Sub-Komponenten
			{"VEVENT","VTODO","VJOURNAL","VTIMEZONE","VFREEBUSY"},
			{"VALARM"},
			{"VALARM"},
			{"VALARM"},
			{},
			{"DAYLIGHT","STANDARD"},
			{},
			{},
			{}
	};
	/* @formatter:on */
	public JavaTeileGenerieren() {
		propertiesGenerieren();
	}

	public static void main(String[] args) {
		JavaTeileGenerieren temp = new JavaTeileGenerieren();
	}

	private void propertiesGenerieren() {
		/* @formatter:off */
		String kopfteilPropertyKopieren [] = {
				" ",
				"    /**",
				"     * Kopieren der ganzen Property",
				"     *" ,
				"     * @return GuKKiCalProperty",
				"     */",
				"    protected GuKKiCalProperty kopieren() {",
				"        if (logger.isLoggable(logLevel)) {",
				"            logger.log(logLevel, \"begonnen\");",
				"        }",
				" ",
				"        GuKKiCalProperty temp = new GuKKiCalProperty();",
				" ",
				"        temp.literal = this.literal;",
				"        temp.wert = this.wert;",
				" "
				};
		/* @formatter:on */
		/* @formatter:off */
		String fussteilPropertyKopieren [] = {
				" ",
				"        temp.status = GuKKiCalcStatus.KOPIERT;",
				" ",
				"        if (logger.isLoggable(logLevel)) {",
				"            logger.log(logLevel, \"beendet\");",
				"        }",
				"        return temp;",
				"    }"
		};
		/* @formatter:on */
		/* @formatter:off */
		String kopfteilPropertyVergleichen [] = {
				" ",
				"    /**",
				"     * Vergleichen des ganzen Property",
				"     *" ,
				"     * @return boolean",
				"     */",
				"    protected boolean vergleichen(Object dasAndere) {",
				"        if (logger.isLoggable(logLevel)) {",
				"            logger.log(logLevel, \"begonnen\");",
				"        }",
				" ",
				"        if (!dasAndere.getClass().equals(this.getClass())) {",
				"            return false;",
				"        }",
				" ",
				"        GuKKiCalProperty temp = (GuKKiCalProperty) dasAndere;",
				" "
				};
		/* @formatter:on */
		/* @formatter:off */
		String fussteilPropertyVergleichen [] = {
				" ",
				"        if (logger.isLoggable(logLevel)) {",
				"            logger.log(logLevel, \"beendet\");",
				"        }",
				"        return true;",
				"    }"
		};
		/* @formatter:on */

		System.out.println("Überprüfung");
		for (String[] strings : parameter) {
			System.out.println(strings[1] + "->" + strings[0]);
		}

		System.out.println("Property Generieren Methode kopieren");

		try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + "GuKKiCalProperty-kopieren"));) {
			for (String zeile : kopfteilPropertyKopieren) {
				pWriter.println(zeile);
			}
			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println("        temp." + strings[1] + " = this." + strings[1] + " == null ? null : this."
							+ strings[1] + ";");
				} else if (strings[0].equals("8")) {
					pWriter.println(" ");
					pWriter.println("        temp." + strings[1] + " = this." + strings[1] + ";");
				}
			}
			for (String zeile : fussteilPropertyKopieren) {
				pWriter.println(zeile);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

		}

		System.out.println("Property Generieren Methode vergleichen");
		try (PrintWriter pWriter = new PrintWriter(new FileWriter(verzeichnis + "GuKKiCalProperty-vergleichen"));) {
			for (String zeile : kopfteilPropertyVergleichen) {
				pWriter.println(zeile);
			}
			for (String[] strings : parameter) {
				if (strings[0].equals("1")) {
					pWriter.println("        if (!((temp." + strings[1] + " == null && this." + strings[1] + " == null)");
					pWriter.println("          || (temp." + strings[1] + " != null && this." + strings[1] + " != null && temp." + strings[1] + ".equals(this." + strings[1] + ")))) {");
					pWriter.println("            return false;");
					pWriter.println("         }");
				} else if (strings[0].equals("8")) {
					pWriter.println("        if (!(temp." + strings[1] + ".equals(this." + strings[1] + ") {");
					pWriter.println("            return false;");
					pWriter.println("         }");
				}
			}
			for (String zeile : fussteilPropertyVergleichen) {
				pWriter.println(zeile);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

		}
	}
}
