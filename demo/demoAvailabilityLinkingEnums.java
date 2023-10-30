package demo;

import java.util.ArrayList;
import java.util.List;

import model.Period;
import model.TimeRange;

public class demoAvailabilityLinkingEnums {

	public static void main(String[] args) {
		TimeRange sectionTimeRange = new TimeRange(9, 0, 13, 0);
        List<Period> occupiedPeriods = getOccupiedPeriods(sectionTimeRange); //section.getTimeRange();

        System.out.println("Occupied Periods:");
        for (Period period : occupiedPeriods) {
            System.out.println(period + ": " + period.getTimeRange());
        }
        
//        DayOfWeek dayOfWeek = ...; // Get the specific day
//        int period = ...; // Get the specific period
//
//        boolean isAvailable = availability[dayOfWeek.getDay()][period];
	}
	
	public static List<Period> getOccupiedPeriods(TimeRange sectionTimeRange) {
        List<Period> occupiedPeriods = new ArrayList<>();

        for (Period period : Period.values()) {
            if (doTimeRangesOverlap(sectionTimeRange, period.getTimeRange())) {
                occupiedPeriods.add(period);
            }
        }
        return occupiedPeriods;
    }

	public static boolean doTimeRangesOverlap(TimeRange range1, TimeRange range2) {
        return range1.getStart().isBefore(range2.getEnd()) && range2.getStart().isBefore(range1.getEnd());
    }

}
