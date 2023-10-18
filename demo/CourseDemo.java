package demo;

import java.io.File;

import model.CourseSet;
import utils.ImportCourses;

public class CourseDemo {

	public static void main(String[] args) {
		CourseSet.getCourseSet();
		System.out.println("Current working directory: " + System.getProperty("user.dir"));
		
		File file = new File("CourseInformation.xlsx");
		if (file.exists()) {
		    // Proceed to open and read the file
			ImportCourses.importCourses(file.getAbsolutePath());
		} else {
		    System.err.println("File not found: CourseInformation.xlsx");
		}
	}

}
