package model;

public enum DayOfWeek {
    MONDAY(0, 'M'), TUESDAY(1, 'T'), WEDNESDAY(2, 'W'), THURSDAY(3, 'R'), FRIDAY(4, 'F'), SATURDAY(5, 'S'), SUNDAY(6, 'U');

    private final int dayIndex;
    private final char daySymbol;

    DayOfWeek(int day, char symbol) {
        this.dayIndex = day;
        this.daySymbol = symbol;
    }

    public int getDayIndex() {
        return dayIndex;
    }

	public char getDaySymbol() {
		return daySymbol;
	}
    
    public static DayOfWeek getByIndex(int index) {
        for (DayOfWeek dayOfWeek : values()) {
            if (dayOfWeek.getDayIndex() == index) {
                return dayOfWeek;
            }
        }
        return null; // Handle invalid index
    }

    
}