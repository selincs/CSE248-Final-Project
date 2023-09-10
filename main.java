import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		List<Instructor> instructorList = new ArrayList<>();
		
		try (FileInputStream excelFile = new FileInputStream("Instructors.xlsx");
				Workbook workbook = new XSSFWorkbook(excelFile)) {
			Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet
			Instructor instructor = null;
			int rowCount = 0;
			boolean firstRowSkipped = false;

			for (Row row : sheet) {
				String cellValue = cellToString(row.getCell(0)); // Assuming ID is in the first column
				// Check if the first row hasn't been skipped yet and skip it -> Data start
				if (!firstRowSkipped) {
					firstRowSkipped = true;
					continue;
				}

				if (cellValue.equals("�")) {
					if (instructor != null) {
						instructorList.add(instructor);
					}
					instructor = new Instructor();
					rowCount = 0;
					continue;
				}

				rowCount++;
				
				String[] parts = new String[15]; // Assuming you have 15 columns (A-O)
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
				instructorList.add(instructor);
			}
			for (Instructor instructors : instructorList) {
				System.out.println(instructors.toString());
			}
		}
		catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseRow1(Instructor instructor, String[] parts) {
		instructor.setIdNum(parts[0]);
		instructor.setName(parts[1]);
		instructor.setHomePhone(parts[2]);
		instructor.setRank(parts[3]);
		instructor.setOnlineCert(parts[4]);
		instructor.setCampusAvailability(parts[5]);
		instructor.setSecondCourse(parts[6]);
		instructor.setNumEves(parts[7]);
		instructor.setAmDays(parts[8]);
		instructor.setPmDays(parts[9]);
		instructor.setSat(parts[10]);
		instructor.setLateAft(parts[11]);
		instructor.setEves(parts[12]);
		//skip Int parts[13] // skip Fall Wrkload parts[14]
	}

	private static void parseRow2(Instructor instructor, String[] parts) {
		instructor.setHomeCampus(parts[0]);
		instructor.setAddress(parts[1]);
		instructor.setStartDate(parts[2]);
		instructor.setThirdCourse(parts[6]);
		instructor.setAmMTWTF(parts[8]);
		instructor.setPmMTWTF(parts[9]);
		instructor.setSun(parts[10]);
	}

	private static void parseRow3(Instructor instructor, String[] parts) {
		instructor.setBusPhone(parts[0]);
		instructor.setCityStateZip(parts[1]);
		instructor.setCourseLoad(parts[2]);
	}
	
	private static void parseRow4(Instructor instructor, String[] parts) {
		instructor.setCourseLoad(instructor.getCourseLoad() + parts[2]);
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
