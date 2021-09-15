package main;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class Ausprobieren {

	public Ausprobieren() {
		// TODO Automatisch generierter Konstruktorstub
	}

	public static void main(String[] args) {
		// TODO Automatisch generierter Methodenstub

 /* Bestimmen des Betriebssysteme */
		String OS = System.getProperty("os.name");
		System.out.println(OS);

 /* eindeutige UID generieren lassen (für neue Events) */		
//		String uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);
//		 uniqueID = UUID.randomUUID().toString();
//		System.out.println(uniqueID);

 /* Erkennen der korrekten Dateienstruktur auf Windows-Laptop */

//		BufferedReader temp = null;
//		String inPath = "C:\\users\\GuKKDevel\\Desktop\\Programmierung\\gitRepos\\IgnoreForGit\\iCalender\\Testkalender.ics";
//		try {
//			temp = new BufferedReader(new InputStreamReader(new FileInputStream(inPath), "UTF-8"));
//			String zeile = temp.readLine();
//			System.out.println(zeile);
//		}
//		finally {
//			
//		}
	}

}
