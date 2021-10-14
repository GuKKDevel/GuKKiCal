package main;

public class GuKKiCalvComponent {

	public GuKKiCalvComponent() {
		// TODO Automatisch generierter Konstruktorstub
	}

	/**
	 * Bestimmen des Inhalts der property CLASS
	 * 
	 * @formatoff
	 * 
	 *            class = "CLASS" classparam ":" classvalue CRLF
	 * 
	 *            classparam = *(";" other-param)
	 * 
	 *            classvalue = "PUBLIC" / "PRIVATE" / "CONFIDENTIAL" / iana-token /
	 *            x-name
	 * 
	 *            Default is PUBLIC
	 *
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return gültige Eventklasse oder null
	 */
	String checkCLASS(String zeichenkette) {
		String zTemp = zeichenkette.substring(1);
		if (zTemp.equals("PUBLIC") | zTemp.equals("PRIVATE") | zTemp.equals("CONFIDENTIAL")) {
			return zTemp;
		} else {
			return null; // throw GuKKiCalWarnung
		}
	}

	/**
	 * Bestimmen des Inhalts der property CREATED
	 * 
	 * @formatoff
	 * 
	 *            created = "CREATED" creaparam ":" date-time CRLF
	 * 
	 *            creaparam = *(";" other-param)
	 * 
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return
	 */
	String checkCREATED(String zeichenkette) {
		// TODO Automatisch generierter Methodenstub
		return zeichenkette.substring(1);
	}

	/**
	 * Bestimmen des Inhalts der property DESCRIPTION
	 * 
	 * @formatoff
	 * 
	 *            description = "DESCRIPTION" descparam ":" text CRLF
	 * 
	 *            descparam = *(
	 * 
	 *            The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 *            (";" altrepparam) / (";" languageparam) /
	 * 
	 *            The following is OPTIONAL, and MAY occur more than once.
	 * 
	 *            (";" other-param) )
	 *
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return
	 */

	String checkDESCRIPTION(String zeichenkette) {
		// TODO Automatisch generierter Methodenstub
		return zeichenkette.substring(1);
	}

	/**
	 * Bestimmen des Inhalts der property DTSTAMP
	 *
	 * @formatoff
	 * 
	 *            dtstamp = "DTSTAMP" stmparam ":" date-time CRLF
	 * 
	 *            stmparam = *(";" other-param)
	 * 
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return
	 */
	String checkDTSTAMP(String zeichenkette) {
		System.out.println("Zeile="+zeichenkette);
		String zTemp = zeichenkette.substring(8);
		System.out.println("Temp="+zTemp);
		// TODO Automatisch generierter Methodenstub
		return zTemp;
	}

	/**
	 * Bestimmen des Inhalts der property DTSTART
	 * 
	 * @formatoff
	 * 
	 *            dtstart = "DTSTART" dtstparam ":" dtstval CRLF
	 * 
	 *            dtstparam = *(
	 *
	 *            The following are OPTIONAL, but MUST NOT occur more than once.
	 * 
	 *            (";" "VALUE" "=" ("DATE-TIME" / "DATE")) / (";" tzidparam) /
	 * 
	 *            The following is OPTIONAL, and MAY occur more than once.
	 *
	 *            (";" other-param) )
	 * 
	 *            dtstval = date-time / date
	 * 
	 *            Value MUST match value type
	 * 
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return
	 */
	String checkDTSTART(String zeichenkette) {
		System.out.println("GuKKiCalvComponent.checkDTSTART begonnen");
		// TODO Auto-generated method stub
		System.out.println("GuKKiCalvComponent.checkDTSTART beendet");
		return zeichenkette.substring(0);
	}

	/**
	 * Bestimmen des Inhalts der property UID
	 *
	 * @formatoff
	 * 
	 *            uid = "UID" uidparam ":" text CRLF
	 * 
	 *            uidparam = *(";" other-param)
	 * 
	 * @formaton
	 * 
	 * @param zeichenkette
	 * @return
	 */
	String checkUID(String zeichenkette) {
		// TODO Automatisch generierter Methodenstub
		return zeichenkette.substring(1);
	}
}
