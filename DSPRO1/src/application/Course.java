package application;

public class Course {
//define attributes
	private String courseName;
	private String courseID;
	private int year;
	private String startTime;
	private String finishTime;
	private int maxNumberOfStudent;
	private String days;
	private String semester;
	private LinkedList list = new LinkedList();

	public Course() {
		super();
	}

	// set a constructor with a signature
	public Course(String courseName, String courseID, int year, String startTime, String finishTime, String semester,
			String days) {
		super();
		this.courseName = courseName;
		this.courseID = courseID;
		this.year = year;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.days = days;
		this.semester = semester;
		this.days = days;
	}

	// set the set and get methods for the attributes
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getMaxNumberOfStudent() {
		return list.size();
	}

	

	@Override
	public String toString() {
		return "Course [courseName=" + courseName + ", courseID=" + courseID + ", year=" + year + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", maxNumberOfStudent=" + maxNumberOfStudent + ", days="
				+ days + ", semester=" + semester + "]";
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public LinkedList getList() {
		return list;
	}

}
