package model;

public enum Period {
	EARLY_MORNING(new TimeRange(7, 0, 8, 0)), MORNING(new TimeRange(8, 0, 12, 0)),
	AFTERNOON(new TimeRange(12, 0, 15, 0)), MID_AFTERNOON(new TimeRange(15, 0, 16, 0)),
	LATE_AFTERNOON(new TimeRange(16, 0, 18, 0)), EVENING(new TimeRange(18, 0, 22, 0));

	private final TimeRange timeRange;

	private Period(TimeRange timeRange) {
		this.timeRange = timeRange;
	}

	public TimeRange getTimeRange() {
		return timeRange;
	}
}
