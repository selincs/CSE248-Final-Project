package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.TimeRange;

public class TimeRangeConverter {
	 public static TimeRange createTimeRange(String startTimeString, String endTimeString) {
		 // Validate input strings
	        if (startTimeString == null || endTimeString == null || startTimeString.isEmpty() || endTimeString.isEmpty()) {
	            // Find out appropriate validation for empty time string and put here
	            return null; // Or throw an exception or return a default value
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
