package model;

import java.time.LocalDate;

public class DateRange {
	private LocalDate start;
	private LocalDate end;
	//Column Q + R
	
	public DateRange(LocalDate start, LocalDate end) {
		super();
		this.start = start;
		this.end = end;
	}

	
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "DateRange [start=" + start + ", end=" + end + "]";
	}
	
}
