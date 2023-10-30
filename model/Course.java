package model;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable, Comparable<Course> {
	// Search for course by CRN (MAT103)
	// Held by Section

	private CourseTitle courseTitle; // Contains Subject/Course Num/Course Title
	private String term; // Semester course is offered -> Important for whether Instructor
	// eligible to teach course via recently taught courses(general) (Should this be in section?) (I think no, course overall is what instructor frequency is)
	private SectionSet sectionSet;
	private int credits;


	// Column Y will be where assigned instructors go
	// Unimportant : Room, Building, Capacity, Contract type,

	public Course() {
		this.sectionSet = new SectionSet();
	}

	public CourseTitle getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(CourseTitle courseTitle) {
		this.courseTitle = courseTitle;
	}

	public SectionSet getSectionSet() {
		return sectionSet;
	}

	public void setSectionSet(SectionSet sectionSet) {
		this.sectionSet = sectionSet;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(Course course) {
		if (course.getCourseTitle().getCourseSubjNum().equalsIgnoreCase("MAT103")
				|| course.getCourseTitle().getCourseSubjNum().equalsIgnoreCase("MAT210")) { // MAT103 + MAT210 == 3
																							// credits, all else == 4
			this.credits = 3;
			return;
		}
		this.credits = 4;
	}

	@Override
	public String toString() {
		return "Course [" + courseTitle.getFullCourseTitle() + ", term=" + term + ", credits=" + credits + "]";
	}

	@Override
	public int compareTo(Course otherCourse) {
		// Compare courses based on the courseTitle
		return this.courseTitle.compareTo(otherCourse.courseTitle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseTitle, credits, term);
	}

	// Revisit
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		return Objects.equals(courseTitle, other.courseTitle) && Objects.equals(credits, other.credits)
				&& Objects.equals(term, other.term);
	}
}
