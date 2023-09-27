import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Instructor;
import model.Name;

import java.io.FileInputStream;
import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// List<Instructor> instructorList = new ArrayList<>();
		Set<Instructor> instructorSet = new TreeSet<>();

		try (FileInputStream excelFile = new FileInputStream("Instructors.xlsx");
				Workbook workbook = new XSSFWorkbook(excelFile)) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet
			Instructor instructor = null;
			int rowCount = 0;
			boolean firstRowSkipped = false;

			for (Row row : sheet) {
				String cellValue = cellToString(row.getCell(0)); // Assuming ID is in the first column
				// Skip to Data start
				if (!firstRowSkipped) {
					firstRowSkipped = true;
					continue;
				}

				if (cellValue.equals("—")) {
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
			for (Instructor instructors : instructorSet) {
				System.out.println(instructors.toString());
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseRow1(Instructor instructor, String[] parts) {
		instructor.setId(parts[0]);
		String[] nameParts = parts[1].split(", ");
		String lastName = nameParts[0];
		String[] firstAndMiddle = nameParts[1].split(" ");
		String firstName = firstAndMiddle[0];
		char middleInitial = (firstAndMiddle.length > 1) ? firstAndMiddle[1].charAt(0) : '\0'; // '\0' represents an
		instructor.setName(new Name(lastName, firstName, middleInitial));
//		instructor.setHomePhone(parts[2]);
		instructor.setRank(parts[3]);
		instructor.setCertifiedOnline(parts[4]);
		instructor.setPreferredCampuses(parts[5]);
		instructor.setRequestSecondCourse(parts[6]);
//		instructor.setNumEves(parts[7]);
//		instructor.setAmDays(parts[8]);
//		instructor.setPmDays(parts[9]);
//		instructor.setSat(parts[10]);
//		instructor.setLateAft(parts[11]);
//		instructor.setEves(parts[12]);
		// skip Int parts[13] // skip Fall Wrkload parts[14]
	}

	private static void parseRow2(Instructor instructor, String[] parts) {
		instructor.setHomeCampus(parts[0]);
//		instructor.setAddress(parts[1]);
//		instructor.setStartDate(parts[2]);
		instructor.setRequestThirdCourse(parts[6]);
//		instructor.setAmMTWTF(parts[8]);
//		instructor.setPmMTWTF(parts[9]);
///		instructor.setSun(parts[10]);
	}

	private static void parseRow3(Instructor instructor, String[] parts) {
//		instructor.setBusPhone(parts[0]);
//		instructor.setCityStateZip(parts[1]);
		instructor.setCertifiedCourses(parts[2]);
	}

	private static void parseRow4(Instructor instructor, String[] parts) {
		instructor.setCertifiedCourses(parts[2]);
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

}
