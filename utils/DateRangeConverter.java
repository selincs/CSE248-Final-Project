package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.DateRange;

public class DateRangeConverter {
	public static DateRange createDateRange(String startDateString, String endDateString) {
		// Validate input strings
		if (startDateString == null || endDateString == null || startDateString.isEmpty() || endDateString.isEmpty()) {
			// Handle null or empty strings (you can customize this part)
			return null; // Or throw an exception or return a default value
		}

		// Probably temporary code here until appropriate date values is done
		// Check if the first character in startDateString is a digit
		if (!Character.isDigit(startDateString.charAt(0))) {
			// Handle the case where the first character is not a digit
			return null; // Or throw an exception or return a default value
		}

		// Check if the first character in endDateString is a digit
		if (!Character.isDigit(endDateString.charAt(0))) {
			// Handle the case where the first character is not a digit
			return null; // Or throw an exception or return a default value
		}
		// End temp code

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

		try {
			// Parse the date strings into LocalDate objects
			LocalDate startDate = LocalDate.parse(startDateString, dateFormatter);
			LocalDate endDate = LocalDate.parse(endDateString, dateFormatter);

			// Create a DateRange object
			return new DateRange(startDate, endDate);
		} catch (Exception e) {
			// Handle parsing exceptions (e.g., invalid date format)
			e.printStackTrace();
			return null; // Or throw an exception or return a default value
		}
	}
}
