package model;

public enum Period {
	EARLY_MORNING(new TimeRange(7, 0, 8, 0), 0), MORNING(new TimeRange(8, 0, 12, 0), 1),
	AFTERNOON(new TimeRange(12, 0, 15, 0), 2), MID_AFTERNOON(new TimeRange(15, 0, 16, 0), 3),
	LATE_AFTERNOON(new TimeRange(16, 0, 18, 0), 4), EVENING(new TimeRange(18, 0, 22, 0), 5);

	private final TimeRange timeRange;
	private int periodIndex;

	private Period(TimeRange timeRange, int periodIndex) {
		this.timeRange = timeRange;
		this.periodIndex = periodIndex;
	}

	public TimeRange getTimeRange() {
		return timeRange;
	}

	public int getPeriodIndex() {
		return periodIndex;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
