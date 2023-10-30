package utils;

import model.Section;
import model.SectionSet;
import model.CourseSet;
import model.CourseTitle;
import model.DayOfWeek;
import model.Course;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ImportCourses {

	private static CourseSet courses;

	public static void importCourses(String fileName) {
		courses = CourseSet.getCourseSet();

		// Load the Excel file
		// Make sure to change this to fileName eventually
		// Switch case based on file type?
		// if file is CSV call CSV converter

		try (FileInputStream fis = new FileInputStream("CourseInformation.xlsx");
				Workbook workbook = new XSSFWorkbook(fis)) { // XLSX format
			Sheet sheet = workbook.getSheetAt(0);
			Course course = null;
			Section section = null;
			boolean firstRowSkipped = false;

			// Iterate through rows and columns to read data
			// Logic : Check if course exists -> if exists, check if section exists, if
			// exists, continue(skip line)
			// If course exists && !section, build section add to course
			// if course !exists, build course && section,
			for (Row row : sheet) {
				if (!firstRowSkipped) { // Skip to data start
					firstRowSkipped = true;
					continue;
				}
				String[] data = new String[26];

				for (int i = 0; i < 26; i++) {
					data[i] = cellToString(row.getCell(i)) + "";
				}

				// Build course
				// Returns null < this is where i can use optional and things from last class
				CourseTitle thisCourseTitle = new CourseTitle(data[1], data[2], data[3]);
				course = CourseSet.search(thisCourseTitle);
				// Implement Optional above as this can return as null

				// First course will always be null
				if (course == null) { // Course search returns null, build & add course
					Course newCourse = new Course();
					buildCourse(thisCourseTitle, newCourse, data);
					course = newCourse;
					courses.add(course);
					System.out.println(course.toString());
				}

				// Build section
				String sectionCRN = String.valueOf((int) Double.parseDouble(data[4]));
				section = course.getSectionSet().search(sectionCRN);
				// Implement Optional above as this can return as null

				if (section == null) { // If section search returns null, build & add section
					Section newSection = new Section();
					buildSection(newSection, data);
					newSection.setCourse(course); // assign course associated with section
					section = newSection;
					course.getSectionSet().add(section);
					System.out.println(section.toString());
				} else { // CRN exists, dont add section //This is where i can add more data on to a
							// class that has diff dates actually
					// implement logic to add extra date + class times to a preexisting CRN, should
					// not occur otherwise
					
					section.setAlternateClassTime(TimeRangeConverter.createTimeRange(data[19], data[20]));
					if (section.getAlternateClassTime() != null) {
						Section temp = new Section();
						temp.parseMeetingDays(data[18]);
						section.getAlternateClassTime().setDays(temp.getMeetingDaysSet());
						System.out.println(section.toString());
		//				section.parseMeetingDays(data[18]);		// This line will concat all total meeting days, do I want this to happen?
		//				continue;
					}
				}
			}

			// Close the workbook and file input stream
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void buildCourse(CourseTitle thisCourseTitle, Course course, String[] data) {
		course.setCourseTitle(thisCourseTitle);
		course.setTerm(String.valueOf((int) Double.parseDouble(data[0])));
		course.setCredits(course);
	}

	private static void buildSection(Section section, String[] data) {
		section.setCrn(String.valueOf((int) Double.parseDouble(data[4]))); // CRN
		section.setPartOfTerm(data[6]); // PoT
		section.setCampus(data[7]); // Campus section is offered
		section.setInstructionMethod(data[9]); // Ins method
		section.setDateRange(DateRangeConverter.createDateRange(dateConversion(data[16]), dateConversion(data[17])));
		section.parseMeetingDays(data[18]);
		section.setClassTime(TimeRangeConverter.createTimeRange(data[19], data[20])); // Col 20 + 21
		if (section.getClassTime() != null) {
			section.getClassTime().setDays(section.getMeetingDaysSet());
		}
	}

	private static String dateConversion(String serializedDate) {
		// Excel stores dates as serialized numbers, must convert this value to a
		// readable String format
		double serialNumber = Double.parseDouble(serializedDate); // Assuming data[16] is "45313.0"

		// Adjust for Excel's 1900 leap year bug
		if (serialNumber > 59) {
			serialNumber -= 1;
		}

		LocalDate date = LocalDate.of(1900, 1, 1).plusDays((long) serialNumber);

		// Format the date as "M/d/yyyy"
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String dateString = date.format(dateFormatter);
		return dateString;
	}

	private static String cellToString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC: // Could potentially add functionality here to return Dates/Times
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}

	}
}
