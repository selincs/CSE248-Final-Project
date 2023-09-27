package model;

import java.util.ArrayList;

public class Instructor implements Comparable<Instructor> {
	private final int DAYS = 7;
	private final int PERIODS = 6;

	private Name name;
	private String id;
	private String rank;
	private boolean certifiedOnline;
	private boolean requestSecondCourse;
	private boolean requestThirdCourse;
	private String homeCampus;
	private ArrayList<String> preferredCampuses = new ArrayList<>(); // max is 4 ArrList is fine
	private boolean[][] availability = new boolean[DAYS][PERIODS]; // 7 days a week, 6 periods per day, Sat+Sun = All Day <- change to rugged array for sat/sun
	private ArrayList<String> certifiedCourses = new ArrayList<>(); // arrList is okay for now
	// linked list

	public Instructor(Name name, String id) {
		this.name = name;
		this.id = id;
	}

	public Instructor() {

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

	public ArrayList<String> getPreferredCampuses() {
		return preferredCampuses;
	}

	public void setPreferredCampuses(String preferredCampusData) {
	    // Split the input string by spaces to get individual letters
	    String[] campusLetters = preferredCampusData.split(" ");

	    // Clear the existing preferredCampuses ArrayList (if any)
	    //Unnecessary
//	    preferredCampuses.clear();

	    // Add the individual letters to the ArrayList
	    for (String letter : campusLetters) {
	        preferredCampuses.add(letter);
	    }
	}

	public boolean[][] getAvailability() {
		return availability;
	}



	public ArrayList<String> getCertifiedCourses() {
		return certifiedCourses;
	}

	public void setCertifiedCourses(String courseLoad) {
		//Split input string by spaces to get individual courses
		String[] certCourses = courseLoad.split(" ");
		// Add individual courses to ArrayList
		for (String course : certCourses) {
			certifiedCourses.add(course);
		}
	}

	@Override
	public int compareTo(Instructor o) {
		return this.id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return "Instructor [" + name.toString() + ", id=" + id + ", rank=" + rank + ", certifiedOnline=" + certifiedOnline
				+ ", requestSecondCourse=" + requestSecondCourse + ", requestThirdCourse=" + requestThirdCourse
				+ ", homeCampus=" + homeCampus + ", preferredCampuses=" + preferredCampuses + ", certifiedCourses="
				+ certifiedCourses + "]";
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

	// boolean default = false
	// when reading data, if Y -> True, otherwise leave

}
