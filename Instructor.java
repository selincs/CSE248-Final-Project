
public class Instructor {
	private String idNum;
	private String homeCampus;
	private String busPhone;
	private String name;
	private String address;
	private String CityStateZip;
	private String homePhone;
	private String startDate;
	private String courseLoad; //delimit by spaces
	private String rank;
	private String onlineCert;
	private String campusAvailability; //char[] max size 4
	private String secondCourse; //if 2ndcourse, check, else third is N
	private String thirdCourse;
	private String numEves;
	private String amDays;
	private String amMTWTF;
	private String pmDays;
	private String pmMTWTF;
	private String Sat;
	private String Sun;
	private String LateAft;
	private String Eves;
	private String Int;
	private String FallWrkload;
	
	
	public String getNumEves() {
		return numEves;
	}
	public void setNumEves(String numEves) {
		this.numEves = numEves;
	}
	@Override
	public String toString() {
		return "Instructor [idNum=" + idNum + ", homeCampus=" + homeCampus + ", busPhone=" + busPhone + ", name=" + name
				+ ", address=" + address + ", CityStateZip=" + CityStateZip + ", homePhone=" + homePhone
				+ ", startDate=" + startDate + ", courseLoad=" + courseLoad + ", rank=" + rank + ", onlineCert="
				+ onlineCert + ", campusAvailability=" + campusAvailability + ", secondCourse=" + secondCourse
				+ ", thirdCourse=" + thirdCourse + ", numEves=" + numEves + ", amDays=" + amDays + ", amMTWTF="
				+ amMTWTF + ", pmDays=" + pmDays + ", pmMTWTF=" + pmMTWTF + ", Sat=" + Sat + ", Sun=" + Sun
				+ ", LateAft=" + LateAft + ", Eves=" + Eves + ", Int=" + Int + ", FallWrkload=" + FallWrkload + "]";
	}
	public String getCityStateZip() {
		return CityStateZip;
	}
	public void setCityStateZip(String cityStateZip) {
		CityStateZip = cityStateZip;
	}
	public void setAmDays(String amDays) {
		this.amDays = amDays;
	}
	public String getAmDays() {
		return amDays;
	}
	public String getPmDays() {
		return pmDays;
	}
	public void setPmDays(String pmDays) {
		this.pmDays = pmDays;
	}
	public String getAmMTWTF() {
		return amMTWTF;
	}
	public void setAmMTWTF(String amMTWTF) {
		this.amMTWTF = amMTWTF;
	}
	public String getPmMTWTF() {
		return pmMTWTF;
	}
	public void setPmMTWTF(String pmMTWTF) {
		this.pmMTWTF = pmMTWTF;
	}
	public String getSat() {
		return Sat;
	}
	public void setSat(String sat) {
		Sat = sat;
	}
	public String getSun() {
		return Sun;
	}
	public void setSun(String sun) {
		Sun = sun;
	}
	public String getLateAft() {
		return LateAft;
	}
	public void setLateAft(String lateAft) {
		LateAft = lateAft;
	}
	public String getEves() {
		return Eves;
	}
	public void setEves(String eves) {
		Eves = eves;
	}
	public String getInt() {
		return Int;
	}
	public void setInt(String i) {
		Int = i;
	}
	public String getFallWrkload() {
		return FallWrkload;
	}
	public void setFallWrkload(String fallWrkload) {
		FallWrkload = fallWrkload;
	}
	public String getSecondCourse() {
		return secondCourse;
	}
	public String getThirdCourse() {
		return thirdCourse;
	}
	public String getOnlineCert() {
		return onlineCert;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getHomeCampus() {
		return homeCampus;
	}
	public void setHomeCampus(String homeCampus) {
		this.homeCampus = homeCampus;
	}
	public String getBusPhone() {
		return busPhone;
	}
	public void setBusPhone(String busPhone) {
		this.busPhone = busPhone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getCourseLoad() {
		return courseLoad;
	}
	public void setCourseLoad(String courseLoad) {
		this.courseLoad = courseLoad;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String isOnlineCert() {
		return onlineCert;
	}
	public void setOnlineCert(String onlineCert) {
		this.onlineCert = onlineCert;
	}
	public String getCampusAvailability() {
		return campusAvailability;
	}
	public void setCampusAvailability(String campusAvailability) {
		this.campusAvailability = campusAvailability;
	}
	public String isSecondCourse() {
		return secondCourse;
	}
	public void setSecondCourse(String parts) {
		this.secondCourse = parts;
	}
	public String isThirdCourse() {
		return thirdCourse;
	}
	public void setThirdCourse(String thirdCourse) {
		this.thirdCourse = thirdCourse;
	}
}