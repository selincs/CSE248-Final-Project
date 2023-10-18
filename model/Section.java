package model;

import java.util.Objects;

//needs comparable 
public class Section implements Comparable<Section> {
	// Each section has specific instances of a course (Time, Dates, etc)
	private String crn;
	private String partOfTerm;
	private String instructionMethod; //TR - In person, BLBD - online or blended
	private String meetingDays;		// Days course meets - Column S
	private DateRange dateRange;	// Start + End Date
	private TimeRange classTime; 	// Beginning + Ending time -> Must be attached to meetingDays
	//eNum goes above, TimeRange classTime replaced by eNum
	
	
	public Section() {
		super();
	}

	public Section(String crn) {
		super();
		this.crn = crn;
	}
	
	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public TimeRange getClassTime() {
		return classTime;
	}

	public void setClassTime(TimeRange classTime) {
		this.classTime = classTime;
	}

	public String getMeetingDays() {
		return meetingDays;
	}

	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	public String getPartOfTerm() {
		return partOfTerm;
	}

	public void setPartOfTerm(String partOfTerm) {
		this.partOfTerm = partOfTerm;
	}
	
	public String getInstructionMethod() {
		return instructionMethod;
	}

	public void setInstructionMethod(String instructionMethod) {
		this.instructionMethod = instructionMethod;
	}

	@Override
	public int hashCode() {
		return Objects.hash(crn);
	}
	
	 @Override
	    public int compareTo(Section otherSection) {
	        return this.crn.compareTo(otherSection.crn);
	    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Section))
			return false;
		Section other = (Section) obj;
		return this.crn.equalsIgnoreCase(other.crn);
	}

	@Override
	public String toString() {
		return "Section [crn=" + crn + ", partOfTerm=" + partOfTerm + ", instructionMethod=" + instructionMethod
				+ ", meetingDays=" + meetingDays + ", dateRange=" + dateRange + ", classTime=" + classTime + "]";
	}
	
	

}
