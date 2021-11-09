package main;

import java.util.ArrayList;
/**
 * 
 * @author gukkdevel 
 *
 */
public class GuKKiCal {
	
	private ArrayList<GuKKiCalvCalendar> auflistungvCalendar = new ArrayList<GuKKiCalvCalendar>();
	private ArrayList<GuKKiCalvEvent> auflistungvEvent = new ArrayList<GuKKiCalvEvent>();
	private ArrayList<GuKKiCalvTodo> auflistungvTodo = new ArrayList<GuKKiCalvTodo>();
	private ArrayList<GuKKiCalvJournal> auflistungvJournal = new ArrayList<GuKKiCalvJournal>();
	private ArrayList<GuKKiCalvFreeBusy> auflistungvFreeBusy = new ArrayList<GuKKiCalvFreeBusy>();
	private ArrayList<GuKKiCalvTimezone> auflistungvTimezone = new ArrayList<GuKKiCalvTimezone>();
	private ArrayList<GuKKiCalvAlarm> auflistungvAlarm = new ArrayList<GuKKiCalvAlarm>();
	
	
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
		auflistungvCalendar.add(vCalendar);
		return true;
	}

}
