package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class Instructor implements Serializable, Comparable<Instructor> {
	// Fix this with rugged array / eNums
	// Will need to implement a recently taught courses list
	private final int DAYS = 7;
	private final int PERIODS = 6;

	private Name name;
	private String id;
	private String rank;
	private boolean certifiedOnline;
	private boolean requestSecondCourse;
	private boolean requestThirdCourse;
	private String homeCampus;
	private ArrayList<Character> preferredCampuses = new ArrayList<>(); // max is 4 ArrList is fine
	private boolean[][] availability = new boolean[DAYS][PERIODS]; // 7 days a week, 6 periods per day, Sat+Sun = All
																	// Day <- change to rugged array for sat/sun
	private ArrayList<String> certifiedCourses = new ArrayList<>(); // arrList is okay for now
	private Section[] assignedSections = new Section[3];
//	private TreeMap<String, Section> frequencyMap = new TreeMap<>(); // Come back 2 dis 

	public Instructor(Name name, String id) {
		this.name = name;
		this.id = id;
	}

	public Instructor() {

	}
	
	public InstructorRow toInstructorRow() {
	    return new InstructorRow(this.getName().getFullName(), this.getId());
	}

	public void setAvailability(boolean[][] availability) {
		this.availability = availability;

	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getHomeCampus() {
		return homeCampus;
	}

	public void setHomeCampus(String homeCampus) {
		this.homeCampus = homeCampus;
	}

	public boolean isCertifiedOnline() {
		return certifiedOnline;
	}

	public void setCertifiedOnline(String certifiedOnline) {
		if ("Y".equalsIgnoreCase(certifiedOnline)) {
			this.certifiedOnline = true;
		} else {
			this.certifiedOnline = false; // Set to false if not "Y" <- Starts false so can remove
		}
	}

	public boolean isRequestSecondCourse() {
		return requestSecondCourse;
	}

	public void setRequestSecondCourse(String requestSecondCourse) {
		if ("Y".equalsIgnoreCase(requestSecondCourse)) {
			this.requestSecondCourse = true;
		} else {
			this.requestSecondCourse = false; // Set to false if not "Y" <- Starts false so can remove
		}
	}

	public boolean isRequestThirdCourse() {
		return requestThirdCourse;
	}

	public void setRequestThirdCourse(String requestThirdCourse) {
		if ("Y".equalsIgnoreCase(requestThirdCourse)) {
			this.requestThirdCourse = true;
		} else {
			this.requestThirdCourse = false; // Set to false if not "Y" <- Starts false so can remove
		}
	}

	public ArrayList<Character> getPreferredCampuses() {
		return preferredCampuses;
	}

	public void setPreferredCampuses(String preferredCampusData) {
		// Split the input string by spaces to get individual letters
		String[] campusLetters = preferredCampusData.split(" ");

		// Add the individual letters to the ArrayList
		for (String letter : campusLetters) {
			// Check if the string has exactly one character
			if (letter.length() == 1) {
				char singleChar = letter.charAt(0);
//				System.out.println("The character: " + singleChar);
				preferredCampuses.add(singleChar); // Add the char to the ArrayList
			} else {
				System.out.println("The input string is not a one-character string.");
			}
		}
	}

	public boolean[][] getAvailability() {
		return availability;
	}

	public ArrayList<String> getCertifiedCourses() {
		return certifiedCourses;
	}

	public void setCertifiedCourses(String course) {
		if (course.isEmpty()) {
//			System.out.println("course is empty");
			return;
		}
			certifiedCourses.add(course);
		// Split input string by spaces to get individual courses
//		String[] certCourses = course.split(" ");
//		// Add individual courses to ArrayList
//		for (String course : certCourses) {
//			certifiedCourses.add(course);
//		}
	}

	@Override
	public int compareTo(Instructor o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return "Instructor [name=" + name.getFullName() + ", id=" + id + ", rank=" + rank + ", certifiedOnline="
				+ certifiedOnline + ", requestSecondCourse=" + requestSecondCourse + ", requestThirdCourse="
				+ requestThirdCourse + ", homeCampus=" + homeCampus + ", preferredCampuses=" + preferredCampuses
				+ ", certifiedCourses=" + certifiedCourses + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Instructor)) {
			return false;
		}
		Instructor instr = (Instructor) obj;
		return instr.getId().equals(this.id);
	}

	public Section[] getAssignedSections() {
		return assignedSections;
	}

	public void assignSection(Section sectionAssignment) {
		if (this.getAssignedSections()[0] == null) {
			this.getAssignedSections()[0] = sectionAssignment;
			return;
		} else if (this.isRequestSecondCourse() && this.getAssignedSections()[1] == null) {
			this.getAssignedSections()[1] = sectionAssignment;
			return;
		} else if (this.isRequestThirdCourse() && this.getAssignedSections()[2] == null) {
			this.getAssignedSections()[2] = sectionAssignment;
			return;
		} else {
			System.out.println("No assignments possible for " + this.getName());
			return;
		}
	}

	public int getTotalCredits() { // Max ever should be 12 credits
		int totalCreds = 0;
		if (this.getAssignedSections()[0] == null) { // No courses assigned, return 0
			return totalCreds;
		} else {
			for (int i = 0; i < this.getAssignedSections().length; i++) {
				if (this.getAssignedSections()[i] == null) { // if !isRequestSecond || !isRequestThird
					break;
				}
				int courseCredit = this.getAssignedSections()[i].getCourse().getCredits();
				totalCreds = totalCreds + courseCredit;
			}
		}
		return totalCreds;
	}

	// boolean default = false
	// when reading data, if Y -> True, otherwise leave

}
