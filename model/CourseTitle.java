package model;

import java.util.Objects;

public class CourseTitle implements Comparable<CourseTitle> {
	private String subject; // Column B
	private String courseNum; // Column C
	private String courseTitle; // Column D

	// Course Title = ColB + ColC + "for" + ColD
	public CourseTitle(String subject, String courseNum, String courseTitle) {
		super();
		this.subject = subject;
		this.setCourseNum(courseNum);
		this.courseTitle = courseTitle;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNumString) {
		// Cast to ensure data does not have trailing zeroes
		int cNum = (int) Double.parseDouble(courseNumString);
		courseNumString = Integer.toString(cNum);
	
		if (courseNumString.isEmpty()) {
			throw new IllegalArgumentException("Invalid or empty Course Number");
		}
		if (courseNumString.length() == 3) {
			if (courseNumString.equals("111")) {
				this.courseNum = courseNumString + 'L';
			} else {
				this.courseNum = courseNumString;
			}
		} else if (courseNumString.length() == 2) {
			this.courseNum = "0" + courseNumString;
		} else if (courseNumString.length() == 1) {
			if (courseNumString.equals("1")) {
				this.courseNum = "00" + courseNumString + "L";
			}
			else {
			this.courseNum = "00" + courseNumString;
			}
		} else {
			throw new IllegalArgumentException("Invalid Course Number: " + courseNumString);
		}
	}

	// Is this done right? Should I save full title as this?
	// Course name : ColB + ColC + " for " + ColD
	public String getFullCourseTitle() {
		return this.subject + this.courseNum + " for " + this.courseTitle;
	}

	public String getCourseSubjNum() {
		return this.subject + this.courseNum;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseNum, courseTitle, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CourseTitle))
			return false;
		CourseTitle other = (CourseTitle) obj;
		return Objects.equals(courseNum, other.courseNum) && Objects.equals(courseTitle, other.courseTitle)
				&& Objects.equals(subject, other.subject);
	}

	@Override
	public int compareTo(CourseTitle otherCourseTitle) {
		// Compare CourseTitle objects based on their fields
		// You can choose the order of comparison based on your requirements
		int subjectComparison = this.subject.compareTo(otherCourseTitle.subject);

		if (subjectComparison != 0) {
			return subjectComparison;
		}

		int courseNumComparison = this.courseNum.compareTo(otherCourseTitle.courseNum);

		if (courseNumComparison != 0) {
			return courseNumComparison;
		}

		return this.courseTitle.compareTo(otherCourseTitle.courseTitle);
	}

	// need an equals here to compare courseTitles in courseSet
	// if i need to display all instances of a Course offered

}
