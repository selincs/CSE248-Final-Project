package model;

import java.time.LocalTime;

public class TimeRange {
	private LocalTime start;
	private LocalTime end;
	
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

	@Override
	public String toString() {
		return "TimeRange [start=" + start + ", end=" + end + "]";
	}

}
