package model;

import java.util.TreeSet;

public class CourseSet {
	private static TreeSet<Course> courses;
	private static CourseSet courseSet;

	private CourseSet() {
		courses = new TreeSet<Course>();
	}

	public static synchronized CourseSet getCourseSet() {
		if (courseSet == null) {
			courseSet = new CourseSet();
		}
		return courseSet;
	}

	public void add(Course course) {
		courses.add(course);
	}

	//FIX THIS
	//Maybe I just check by equality of course objects
	//Check by CourseNum? Only one MAT001 course in all of MAT
	public static Course search(CourseTitle courseTitle) {
		if (courseTitle == null) {
			// throw new IllegalArgumentException("Invalid or empty Course Title");
			System.out.println("Invalid or empty Course");
			return null;
		}
		//Iterate through CourseSet, course check by courseTitle
		for (Course course : courses) {
			if (course.getCourseTitle().equals(courseTitle) ) {
				return course;
			}
		}
		return null;
	}

	public static TreeSet<Course> getCourses() {
		return courses;
	}

}
