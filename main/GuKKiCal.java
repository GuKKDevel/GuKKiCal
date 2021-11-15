package main;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author GuKKDevel 
 *
 */
public class GuKKiCal {
	
	private ArrayList<GuKKiCalvCalendar> vCalendarSammlung = new ArrayList<GuKKiCalvCalendar>();
	private ArrayList<GuKKiCalvEvent> vEventSammlung = new ArrayList<GuKKiCalvEvent>();
	private ArrayList<GuKKiCalvTodo> vTodoSammlung = new ArrayList<GuKKiCalvTodo>();
	private ArrayList<GuKKiCalvJournal> vJournalSammlung = new ArrayList<GuKKiCalvJournal>();
	private ArrayList<GuKKiCalvFreeBusy> vFreeBusySammlung = new ArrayList<GuKKiCalvFreeBusy>();
	private HashMap<String, GuKKiCalvTimezone> vTimezoneSammlung = new HashMap<String, GuKKiCalvTimezone>();
	
	
	public GuKKiCal() {
		// TODO Automatisch generierter Konstruktorstub
	}
	/**
	 * Diese Funktion fügt einen kompletten vCalendar in die Sammlung ein
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendar
	 * @return boolean
	 */
	public boolean addvCalendar (GuKKiCal kalendersammlung, GuKKiCalvCalendar vCalendar) {
		vCalendarSammlung.add(vCalendar);
		return true;
	}
	/**
	 * Diese Funktion fügt ein vEvent in die Sammlung ein
	 * 
	 * @param vCalendarSammlung
	 * @param vCalendar
	 * @return boolean
	 */
	public boolean addvEvent (GuKKiCal kalendersammlung, GuKKiCalvEvent vEvent) {
		vEventSammlung.add(vEvent);
		return true;
	}
}
