package application;



public class Student {
	private String name;
	private String stId;
	private LinkedList stCourses=new LinkedList();
	
	public Student(String name, String stId, LinkedList stCourses1) {
		super();
		this.name = name;
		this.stId = stId;
		this.stCourses = stCourses1;
	}
	public Student() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	
	public LinkedList getStCourses() {
		return stCourses;
	}
	public void setStCourses(LinkedList stCourses2) {
		this.stCourses = stCourses2;
	}
	
}
