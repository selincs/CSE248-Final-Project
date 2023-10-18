package utils;

import model.Section;
import model.SectionSet;
import model.CourseSet;
import model.CourseTitle;
import model.Course;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ImportCourses {

	private static CourseSet courses;

	public static void importCourses(String fileName) {
		courses = CourseSet.getCourseSet();

		// Load the Excel file
		//Make sure to change this to fileName eventually
		//Switch case based on file type?
		//if file is CSV call CSV converter
		
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
					data[i] = cellToString(row.getCell(i));
				}
				
				// Build course
				//Returns null < this is where i can use optional and things from last class
				CourseTitle thisCourseTitle = new CourseTitle(data[1], data[2], data[3]);
				course = CourseSet.search(thisCourseTitle);
				//Implement Optional above as this can return as null
				
				//First course will always be null
				if (course == null) { // Course search returns null, build & add course
					Course newCourse = new Course();
					buildCourse(thisCourseTitle, newCourse, data);
					course = newCourse;
					courses.add(course);
					System.out.println(course.toString());
				}

				// Build section
				String sectionCRN = data[4];
				section = course.getSectionSet().search(sectionCRN);		
				//Implement Optional above as this can return as null

				if (section == null) {	// If section search returns null, build & add section
					Section newSection = new Section();
					buildSection(newSection, data);
					section = newSection;
					course.getSectionSet().add(section);
					System.out.println(section.toString());
				} else { // CRN exists, dont add section
					continue;
				}
			}

			
//			for (Course printCourse : CourseSet.getCourses()) {
//			System.out.println(printCourse.toString());
//			}

			// Close the workbook and file input stream
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void buildCourse(CourseTitle thisCourseTitle, Course course, String[] data) {
		course.setCourseTitle(thisCourseTitle);
		course.setCampus(data[7]);
		course.setTerm(data[0]);
//		course.setCredits(4);
	}

	private static void buildSection(Section section, String[] data) {
		section.setCrn(data[4]); // CRN
		section.setPartOfTerm(data[6]); // PoT
		section.setInstructionMethod(data[9]); // Ins method
		section.setMeetingDays(data[18]); // Days
	//	section.setDateRange(DateRangeConverter.createDateRange(data[16], data[17]));
		section.setClassTime(TimeRangeConverter.createTimeRange(data[19], data[20])); //Col 20 + 21
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
