package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.TimeRange;

public class TimeRangeConverter {
	 public static TimeRange createTimeRange(String startTimeString, String endTimeString) {
		 // Validate input strings
	        if (startTimeString == null || endTimeString == null || startTimeString.isEmpty() || endTimeString.isEmpty()) {
	            // Unassigned Class Time case
	            return null;
	        }
		 // Define a DateTimeFormatter for parsing time
	        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

	        // Parse the time strings into LocalTime objects
	        LocalTime startTime = LocalTime.parse(startTimeString, timeFormatter);
	        LocalTime endTime = LocalTime.parse(endTimeString, timeFormatter);

	        // Return a TimeRange object
	        return new TimeRange(startTime, endTime);
	    }

}
