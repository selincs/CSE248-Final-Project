package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SectionRow {
	private final StringProperty title = new SimpleStringProperty();
	private final StringProperty crn = new SimpleStringProperty();
	private final StringProperty meetingDays = new SimpleStringProperty();	// probably removed
	private final StringProperty classTimes = new SimpleStringProperty();	// probably removed
	private Section section;
	
	public SectionRow(String title, String crn, String meetingDays, String classTimes, Section section) {
		this.title.set(title);
		this.crn.set(crn);
		this.meetingDays.set(meetingDays);
		this.classTimes.set(classTimes);
		this.section = section;
	}
	
	public SectionRow(String title, String crn, String meetingDays, Section section) {
		this.title.set(title);
		this.crn.set(crn);
		this.meetingDays.set(meetingDays);
		this.classTimes.set("");
		this.section = section; // Set the associated section
	}

	public String getTitle() {
		return title.get();
	}

	public StringProperty titleProperty() {
		return title;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getCRN() {
		return crn.get();
	}

	public StringProperty crnProperty() {
		return crn;
	}

	public void setCRN(String crn) {
		this.crn.set(crn);
	}

	public String getMeetingDays() {
		return meetingDays.get();
	}

	public StringProperty meetingDaysProperty() {
		return meetingDays;
	}

	public void setMeetingDays(String meetingDays) {
		this.meetingDays.set(meetingDays);
	}

	public String getClassTimes() {
		return classTimes.get();
	}

	public StringProperty classTimesProperty() {
		return classTimes;
	}

	public void setClassTimes(String classTimes) {
		this.classTimes.set(classTimes);
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
}