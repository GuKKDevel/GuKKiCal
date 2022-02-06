package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import enumerations.*;
import exceptions.*;
import component.GuKKiCalvCalendar;

/**
 * @author Karl-Heinz Gödderz (GuKKDevel)
 *
 */
public class GuKKiCalParser {
	Logger logger = Logger.getLogger("GuKKiCal");
	Level logLevel = Level.FINEST;

	ArrayList<GuKKiCalvCalendar> vCalendarSammlung = new ArrayList<GuKKiCalvCalendar>();
	GuKKiCalvCalendar vCalendarNeu = null;
	/*
	 * Hilfsdaten zur Weiterverarbeitung der Kalenderinformationen
	 */
	String kalenderPfad = "";
	String kalenderName = "";
	int kalenderNummer = 0;

	/**
	 * Der Klasse GuKKiCalParser dient dazu, über die Methode kalenderEinlesen,
	 * aus der Datei, deren Name übergeben wird, einen Kalender aufzubauen und
	 * der Kalendersammlung hinzuzufügen.
	 */
	public GuKKiCalParser() throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	}

	/**
	 * Die Methode kalenderEinlesen liest den übergebenen Datenstrom zeilenweise
	 * ein und unterteilt ihn in mehrere Datenströme für die verschiedenen
	 * vCalendar-Komponenten
	 * 
	 * @param inPath           Pfad der Eingabedatei für den Kalender als String
	 * @param kalendersammlung Sammlung aller Kalender und deren Komponenten
	 * 
	 * @throws IOException
	 */

	public void kalenderEinlesen(ArrayList<GuKKiCalvCalendar> vCalendarSammlung, String inPath) throws Exception {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		this.vCalendarSammlung = vCalendarSammlung;
		this.kalenderPfad = inPath;
		this.kalenderName = bestimmenKalenderName(inPath);
		this.kalenderNummer = 0;
		/*
		 * Datenstrom zur Verarbeitung der Kalenderdaten
		 */
		BufferedReader vCalendarDatenstrom = null;
		String zeile = "";
		String folgezeile = "";
		boolean datenVorhanden = true;

		try {
			vCalendarDatenstrom = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));
			zeile = vCalendarDatenstrom.readLine();
			if (zeile == null) {
				datenVorhanden = false;
			}
			while (datenVorhanden) {
				folgezeile = vCalendarDatenstrom.readLine();

				if (folgezeile == null) {
					neueZeile(zeile);
					datenVorhanden = false;
				} else {
					if (folgezeile.length() > 0) {
						if (folgezeile.substring(0, 1).equals(" ")) {
							zeile = zeile.substring(0, zeile.length()) + folgezeile.substring(1);
						} else {
							neueZeile(zeile);
							zeile = folgezeile;
						}
					}
				}
			} /* end while-Schleife */
		} finally {

			if (vCalendarDatenstrom != null) {
				vCalendarDatenstrom.close();
			}
		}
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
	} // Ende kalenderEinlesen

	void neueZeile(String zeile) throws Exception{
		if (zeile.equals("BEGIN:VCALENDAR")) {
			kalenderNummer++;
			vCalendarNeu = new GuKKiCalvCalendar();
		}
		else if (zeile.equals("END:VCALENDAR")) {
			vCalendarNeu.abschliessen(kalenderName+String.format("%03d", kalenderNummer), kalenderPfad);
			vCalendarSammlung.add(vCalendarNeu);
		}
		else {
			vCalendarNeu.neueZeile(zeile);
		}
	}

	/**
	 * Bestimmt aus der Pfadangabe für den Kalender den Kalendernamen aus dem
	 * Dateinamen
	 * 
	 * @param vCalendarPfad
	 * @return
	 */
	private String bestimmenKalenderName(String kalenderPfad) {
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "begonnen");
		}
		// System.out.println("GuKKiCalvCalendar.bestimmenKalenderName
		// begonnen");
		/*
		 * Bestimmen, welches Betriebssystem gerade läuft, um den
		 * Verzeichnis-Trenner zu setzen
		 */
		String vzt = "\\";
		if (System.getProperty("os.name").equals("Linux"))
			vzt = "/";

		/*
		 * Analysieren des Pfades um den Kalendernamen zu bestimmen
		 */
		String stringNeu = kalenderPfad;
		int startIndex = stringNeu.length();
		int endeIndex = stringNeu.length();
		while (startIndex > 0 && stringNeu.substring(startIndex, endeIndex).indexOf(vzt) < 0) {
//			System.out.println("StartIndex=" + startIndex + " Substring="
//					+ stringNeu.substring(startIndex, endeIndex));
			startIndex -= 1;
		}

//		System.out.println("StartIndex=" + startIndex + " EndeIndex="
//				+ endeIndex + "Kalenderkennung="
//				+ stringNeu.substring(startIndex + 1, endeIndex - 4));
//		System.out.println("GuKKiCalvCalendar.bestimmenKalenderName beendet");
		if (logger.isLoggable(logLevel)) {
			logger.log(logLevel, "beendet");
		}
		return stringNeu.substring(startIndex + 1, endeIndex - 4);
	}
}
