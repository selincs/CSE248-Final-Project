package model;

import java.util.Objects;

public class Course implements Comparable<Course> {
	// Search for course by CRN (MAT103)
	// Held by Section
	
	private CourseTitle courseTitle; // Contains Subject/Course Num/Course Title
	private String campus; //Campus course is offered (Section?)
	private String term; // Semester course is offered -> Important for whether Instructor
	private String credits = "4"; //Amount of credits not yet subject to change
	private SectionSet sectionSet;
	//eligible to teach course via recently taught courses(general)
	
	//Column Y will be where assigned instructors go
	//Unimportant : Room, Building, Capacity, Contract type,
	
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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {
		return "Course [" + courseTitle.getFullCourseTitle() + ", campus=" + campus + ", term=" + term + ", credits=" + credits
				+ "]";
	}

	@Override
	 public int compareTo(Course otherCourse) {
		 // Compare courses based on the courseTitle
        return this.courseTitle.compareTo(otherCourse.courseTitle);
	}

	@Override
	public int hashCode() {
		return Objects.hash(campus, courseTitle, credits, term);
	}

	//Revisit
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		return Objects.equals(campus, other.campus) && Objects.equals(courseTitle, other.courseTitle)
				&& Objects.equals(credits, other.credits) && Objects.equals(term, other.term);
	}
	
	
	
}
