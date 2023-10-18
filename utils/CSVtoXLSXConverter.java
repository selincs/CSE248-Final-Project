package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CSVtoXLSXConverter {
	
	//Incomplete, decide if I want to adjust code to directly parse both XLSX && CSV files or always convert to XLSX first
	public static void main(String[] args) {
		String csvFilePath = "CourseInformation.csv"; // Replace with the path to your CSV file
		String xlsxFilePath = "output.xlsx"; // Replace with the desired output XLSX file path

		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Sheet1");

			BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
			String line;
			int rowNumber = 0;
			while ((line = br.readLine()) != null) {
				Row row = sheet.createRow(rowNumber++);
				String[] data = line.split(",");
				for (int i = 0; i < data.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(data[i]);
				}
			}
			br.close();

			FileOutputStream outputStream = new FileOutputStream(xlsxFilePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

			System.out.println("CSV file converted to XLSX successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
