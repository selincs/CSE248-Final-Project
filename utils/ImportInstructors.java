package utils;

import model.Instructor;
import model.InstructorSet;
import model.Name;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ImportInstructors {

	private static InstructorSet instructorSet;
//	static int count = 0;
	public static void importInstructors(String fileName) {
		instructorSet = InstructorSet.getInstructorSet();

		//Issue -> I am looping one extra time, throwing an exception as it tries to parse empty strings
		try (FileInputStream excelFile = new FileInputStream(fileName);
				Workbook workbook = new XSSFWorkbook(excelFile)) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet
			Instructor instructor = null;
			int rowCount = 0;
			boolean getFirstInstructor = false;

			for (Row row : sheet) {
				String cellValue = cellToString(row.getCell(0)); //Checking for new Instructors
				// Skip to Data start (Only for Instructors V1)
//				if (!firstRowSkipped) {
//					firstRowSkipped = true;
//					continue;
//				}
				if (!getFirstInstructor) {
						getFirstInstructor = true;
						instructor = new Instructor();
				}

				if (cellValue.equals("-------------------------------------------------------------------------------------------------")) {
					if (instructor != null) {
						instructorSet.add(instructor);
					}
					instructor = new Instructor();
					rowCount = 0;
					continue;
				}

				rowCount++;

				String[] parts = new String[15]; // Assuming you have 15 columns (A-O) <-- Can probably be 13 as 14/15
													// arent important
				for (int i = 0; i < 15; i++) {
					parts[i] = cellToString(row.getCell(i));
				}

				// Populate Instructor object
				if (instructor != null) {
//					System.out.println("Content of parts[2]" + "'" + parts[2]+ "'");
					if (parts[2] == "") {
						break;
					}
					switch (rowCount) {
					case 1:
						parseRow1(instructor, parts);
						break;
					case 2:
						parseRow2(instructor, parts);
						break;
					case 3:
						parseRow3(instructor, parts);
						break;
					case 4:
						parseRow4(instructor, parts);
						break;
					}

				}
			}
			// Add the last instructor to the list
			if (instructor != null) {
				instructorSet.add(instructor);
			}
			//	InstructorSet.getSet();
			for (Instructor instructors : instructorSet.getInstructorSet().getSet()) {
				System.out.println(instructors.toString());
			}
			
			//Newly added lines
			workbook.close();
			excelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseRow1(Instructor instructor, String[] parts) {
		instructor.setId(parts[0]);
		String[] nameParts = parts[1].split(", ");
		String lastName = nameParts[0];
//		String firstName = nameParts[1];
		
//		System.out.println(nameParts.length + " Loop : " + count);
//		count++;
		
		String[] firstAndMiddle = nameParts[1].split(" ");
		String firstName = firstAndMiddle[0];
		if (firstAndMiddle.length == 2) {
			char middleInitial = (firstAndMiddle.length > 1) ? firstAndMiddle[1].charAt(0) : '\0'; // '\0' represents an
			instructor.setName(new Name(lastName, firstName, middleInitial));
		} else {
			instructor.setName(new Name(lastName, firstName));
		}

//		instructor.setHomePhone(parts[2]); //Make contactInfo class
		instructor.setRank(parts[3]);
		instructor.setCertifiedOnline(parts[4]);
		instructor.setPreferredCampuses(parts[5]);
		instructor.setRequestSecondCourse(parts[6]);
//		instructor.setNumEves(parts[7]); Unneeded, delete when okay
		boolean[][] availabilityData = instructor.getAvailability();
		parseEarlyAMAvailability(availabilityData, parts[8]);
		parse3to4PMAvailability(availabilityData, parts[9]);
		saturdayAvailability(availabilityData, parts[10]);
		parseLateAft(availabilityData, parts[11]);
		parseEves(availabilityData, parts[12]);
		instructor.setAvailability(availabilityData);
		// skip Int parts[13] // skip Fall Wrkload parts[14]
	}

	private static void parseRow2(Instructor instructor, String[] parts) {
		instructor.setHomeCampus(parts[0]);
//		instructor.setAddress(parts[1]); //Make contactInfo class
//		instructor.setStartDate(parts[2]);
		instructor.setRequestThirdCourse(parts[6]);
		boolean[][] availabilityData = instructor.getAvailability();
		parseMorningAvailability(availabilityData, parts[8]);
		parseAfternoonAvailability(availabilityData, parts[9]);
		sundayAvailability(availabilityData, parts[10]);
		instructor.setAvailability(availabilityData);
	}

	private static void parseRow3(Instructor instructor, String[] parts) {
//		instructor.setBusPhone(parts[0]);	  //Make contactInfo class
//		instructor.setCityStateZip(parts[1]); //Make contactInfo class
		instructor.setCertifiedCourses(parts[2]);
		instructor.setCertifiedCourses(parts[3]);
		instructor.setCertifiedCourses(parts[4]);
		instructor.setCertifiedCourses(parts[5]);
		instructor.setCertifiedCourses(parts[6]);
		instructor.setCertifiedCourses(parts[7]);
		instructor.setCertifiedCourses(parts[8]);
		instructor.setCertifiedCourses(parts[9]);
		instructor.setCertifiedCourses(parts[10]);
		
	}

	private static void parseRow4(Instructor instructor, String[] parts) {
		instructor.setCertifiedCourses(parts[2]);
		instructor.setCertifiedCourses(parts[3]);
		instructor.setCertifiedCourses(parts[4]);
		instructor.setCertifiedCourses(parts[5]);
		instructor.setCertifiedCourses(parts[6]);
		instructor.setCertifiedCourses(parts[7]);
		instructor.setCertifiedCourses(parts[8]);
		instructor.setCertifiedCourses(parts[9]);
		instructor.setCertifiedCourses(parts[10]);
	}

	// I can probably find a way to combine all of my parse availability methods now
	// Period 1 - 7-8 AM
	private static boolean[][] parseEarlyAMAvailability(boolean[][] availabilityData, String availabilityString) {
		// Assuming availabilityString is something like "*MTWT"
		// Iterate through the list of availability strings and update availability
		boolean tuesdayPast = false;
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case '*':
				// Handle asterisk character
				break;
			case 'M':
				// Handle Monday
				availabilityData[0][0] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][0] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][0] = true;
				break;
			case 'W':
				// Handle Wednesday
				availabilityData[2][0] = true;
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][0] = true;
				break;
			}
		}
		return availabilityData;
	}

	// Period 2 - 8 - 12 AM
	private static boolean[][] parseMorningAvailability(boolean[][] availabilityData, String availabilityString) {
		boolean tuesdayPast = false; // Should I make Thursdays char become R?
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case 'M':
				// Handle Monday
				availabilityData[0][1] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][1] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][1] = true;
				break;
			case 'W':
				// Handle Wednesday
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				availabilityData[2][1] = true;
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][1] = true;
				break;
			}
		}
		return availabilityData;
	}

	// Period 3 - 12-3PM
	private static boolean[][] parseAfternoonAvailability(boolean[][] availabilityData, String availabilityString) {
		boolean tuesdayPast = false; // Should I make Thursdays char become R?
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case 'M':
				// Handle Monday
				availabilityData[0][2] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][2] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][2] = true;
				break;
			case 'W':
				// Handle Wednesday
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				availabilityData[2][2] = true;
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][2] = true;
				break;
			}
		}
		return availabilityData;
	}

	// Period 4 - 3-4PM
	private static boolean[][] parse3to4PMAvailability(boolean[][] availabilityData, String availabilityString) {
		boolean tuesdayPast = false; // Should I make Thursdays char become R?
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case '*':
				// Handle asterisk character
				break;
			case 'M':
				// Handle Monday
				availabilityData[0][3] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][3] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][3] = true;
				break;
			case 'W':
				// Handle Wednesday
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				availabilityData[2][3] = true;
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][3] = true;
				break;
			}
		}
		return availabilityData;
	}

	// Period 5 - 4-6PM
	private static boolean[][] parseLateAft(boolean[][] availabilityData, String availabilityString) {
		boolean tuesdayPast = false;
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case 'M':
				// Handle Monday
				availabilityData[0][4] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][4] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][4] = true;
				break;
			case 'W':
				// Handle Wednesday
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				availabilityData[2][4] = true;
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][4] = true;
				break;
			}
		}
		return availabilityData;
	}

	// Period 5 - 7-10PM
	private static boolean[][] parseEves(boolean[][] availabilityData, String availabilityString) {
		boolean tuesdayPast = false; // Should I make Thursdays char become R?
		if (availabilityString.isEmpty()) {
			return availabilityData;
		}

		for (char dayChar : availabilityString.toCharArray()) {
			switch (dayChar) {
			case 'M':
				// Handle Monday
				availabilityData[0][5] = true;
				break;
			case 'T':
				// Handle Tuesday or Thursday
				if (tuesdayPast) {
					// Handle Thursday
					availabilityData[3][5] = true;
				}
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				// Handle Tuesday
				availabilityData[1][5] = true;
				break;
			case 'W':
				// Handle Wednesday
				if (!tuesdayPast) {
					tuesdayPast = true;
				}
				availabilityData[2][5] = true;
				break;
			case 'F':
				// Handle Friday
				availabilityData[4][5] = true;
				break;
			}
		}
		return availabilityData;
	}

	private static String cellToString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}

	}

	private static boolean[][] saturdayAvailability(boolean[][] availabilityData, String availabilityString) {
		if (availabilityString.equals("Sat")) {
			availabilityData[5][0] = true;
			availabilityData[5][1] = true;
			availabilityData[5][2] = true;
			availabilityData[5][3] = true;
			availabilityData[5][4] = true;
			availabilityData[5][5] = true;
		}
		return availabilityData;
	}

	private static boolean[][] sundayAvailability(boolean[][] availabilityData, String availabilityString) {
		if (availabilityString.equals("Sun")) {
			availabilityData[6][0] = true;
			availabilityData[6][1] = true;
			availabilityData[6][2] = true;
			availabilityData[6][3] = true;
			availabilityData[6][4] = true;
			availabilityData[6][5] = true;
		}
		return availabilityData;
	}
}
