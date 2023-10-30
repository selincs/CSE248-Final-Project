package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class TimeRange {
	private LocalTime start;
	private LocalTime end;
	private Set<DayOfWeek> days;
	
	//Column T + U
	public TimeRange(LocalTime start, LocalTime end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public TimeRange (int h1, int m1, int h2, int m2) {
		this.start = LocalTime.of(h1, m1);
		this.end = (LocalTime.of(h2, m2));
	}
	
    public TimeRange(LocalTime startTime, LocalTime endTime, Set<DayOfWeek> days) {
        this.start = startTime;
        this.end = endTime;
        this.days = days;
    }
	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

    public Set<DayOfWeek> getDays() {
    	if (days == null) {
    		return null;
    	}
        return days;
    }

	public void setDays(Set<DayOfWeek> days) {
		this.days = days;
	}

	@Override
	public String toString() {
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
	        String startTime = start.format(formatter);
	        String endTime = end.format(formatter);
	        return startTime + " - " + endTime;
	    }

}
