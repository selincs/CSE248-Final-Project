package model;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import utils.TimeRangeConverter;

//needs comparable 
public class Section implements Comparable<Section> {
	// Each section has specific instances of a course (Time, Dates, etc)
	private Course course; // associate course and section
	private String crn;
	private String partOfTerm;
	private String instructionMethod; // TR - In person, BLBD - online or blended
	private Set<DayOfWeek> meetingDaysSet; // Column S
	private DateRange dateRange; // Start + End Date
	private TimeRange classTime; // Beginning + Ending time -> set of DayOfWeek enum attached
	private TimeRange alternateClassTime; // Beginning + Ending time -> set of DayOfWeek enum attached
	private Instructor assignedInstructor;
	private char campus; // Campus Section is offered on

	public Section() {
		super();
	}

	public SectionRow toSectionRow() {

		if (this.getClassTime() != null) { // Retrieve string of daySymbols
			String daySymbols = this.getClassTime().getDays().stream().map(DayOfWeek::getDaySymbol).map(String::valueOf)
					.collect(Collectors.joining("")); // Join the symbols with a delimiter
			return new SectionRow(this.getCourse().getCourseTitle().getCourseSubjNum(), this.getCrn(), daySymbols,
					this.getClassTime().toString(), this);
		}
		// Retrieve string of daySymbols
		String daySymbols = this.meetingDaysSet.stream().map(DayOfWeek::getDaySymbol).map(String::valueOf)
				.collect(Collectors.joining("")); // Join the symbols with a delimiter, Convert char to String
		return new SectionRow(this.getCourse().getCourseTitle().getCourseSubjNum(), this.getCrn(), daySymbols, this);
	}

	public SectionRow toSecondSectionRow() {
		if (this.getClassTime() != null) { // Retrieve string of daySymbols
			String daySymbols = this.getAlternateClassTime().getDays().stream().map(DayOfWeek::getDaySymbol)
					.map(String::valueOf) // Convert char to String
					.collect(Collectors.joining("")); // Join the symbols with a delimiter
			return new SectionRow(this.getCourse().getCourseTitle().getCourseSubjNum(), this.getCrn(), daySymbols,
					this.getAlternateClassTime().toString(), this);
		}
		// Retrieve string of daySymbols
		String daySymbols = this.meetingDaysSet.stream().map(DayOfWeek::getDaySymbol).map(String::valueOf)
				.collect(Collectors.joining("")); // Join the symbols with a delimiter // Convert char to String
		return new SectionRow(this.getCourse().getCourseTitle().getCourseSubjNum(), this.getCrn(), daySymbols, this);
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

	public char getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus.charAt(0);
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

	public TimeRange getAlternateClassTime() {
		return alternateClassTime;
	}

	public void setAlternateClassTime(TimeRange alternateClassTime) {
		this.alternateClassTime = alternateClassTime;
	}

	public void parseMeetingDays(String meetingDays) {
		if (this.meetingDaysSet == null) {
			this.meetingDaysSet = new TreeSet<>();
		}
		for (char dayOfWeek : meetingDays.toCharArray()) {
			switch (dayOfWeek) {
			case 'M':
				this.meetingDaysSet.add(DayOfWeek.MONDAY);
				break;
			case 'T':
				this.meetingDaysSet.add(DayOfWeek.TUESDAY);
				break;
			case 'W':
				this.meetingDaysSet.add(DayOfWeek.WEDNESDAY);
				break;
			case 'R':
				this.meetingDaysSet.add(DayOfWeek.THURSDAY);
				break;
			case 'F':
				this.meetingDaysSet.add(DayOfWeek.FRIDAY);
				break;
			case 'S':
				this.meetingDaysSet.add(DayOfWeek.SATURDAY);
				break;
			case 'U':
				this.meetingDaysSet.add(DayOfWeek.SUNDAY);
				break;
			default:
				// Handle invalid abbreviation if needed -> Unassigned courses
				break;
			}
		}
	}

	public void setAlternateClassTimeFromData(String meetingDays, String startTime, String endTime) {
		parseMeetingDays(meetingDays);
		setAlternateClassTime(TimeRangeConverter.createTimeRange(startTime, endTime));
	}

	public Set<DayOfWeek> getMeetingDaysSet() {
		return meetingDaysSet;
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
		if (this.getAlternateClassTime() != null) {
			return "Section [crn=[" + crn + "], partOfTerm=[" + partOfTerm + "], instructionMethod=[" + instructionMethod
					+ "], meetingDays=" + this.getClassTime().getDays().toString() + ", classTime=[" + classTime + "], alternateMeetingDays=" + this.getAlternateClassTime().getDays().toString() + ", alternateClassTime=["
					+ alternateClassTime + "], " + dateRange + ", Campus =['" + campus + "'" + "]]";
		}
		return "Section [crn=[" + crn + "], partOfTerm=[" + partOfTerm + "], instructionMethod=[" + instructionMethod
				+ "], meetingDays=" + meetingDaysSet.toString() + ", classTime=[" + classTime + "], " + dateRange
				+ ", Campus =['" + campus + "']]";
	}

	public Instructor getAssignedInstructor() {
		return assignedInstructor;
	}

	public void setAssignedInstructor(Instructor assignedInstructor) {
		this.assignedInstructor = assignedInstructor;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
