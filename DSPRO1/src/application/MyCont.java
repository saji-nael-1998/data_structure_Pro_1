package application;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MyCont implements Initializable {
	static LinkedList courses = new LinkedList();
	static LinkedList students = new LinkedList();

	// Main pane
	@FXML
	private Pane mainPane;
	// =============================================================
	// Define course page nodes
	@FXML
	private Button coursePageButton;
	@FXML
	private Pane CoursePane;
	@FXML
	private VBox courselistVBox;
	// =============================================================
//Define course student page node
	@FXML
	private Pane courseStudentPane;
	@FXML
	private VBox courseStudentVBox;
	// =============================================================
	// define students page nodes
	@FXML
	private Pane studentsPagePane;
	@FXML
	private Button studentsPageButtton;
	@FXML
	private VBox studentsPageVBox;
	// =============================================================
	// define schedule of student page nodes
	@FXML
	private Pane schedulePagePane;
	@FXML
	private Button schedulePageButton;
	@FXML
	private TextField SchdeuleNameTextField;
	@FXML
	private TextField SchdeuleIDTextField;
	@FXML
	private ToggleButton t1;
	@FXML
	private ToggleButton t2;
	@FXML
	private VBox schedulePageVBox;
	@FXML
	private Button searchSheduleButton;

	// =============================================================
	// edit schedule Page nodes
	@FXML
	private Button editSchedulePage;
	@FXML
	private Pane editSchedulePane;
	@FXML
	private TextField studentIDETextField;
	@FXML
	private TextField studentNameETextField;

	@FXML
	private VBox eSchaduleVBox;
	@FXML
	private Button searchEButton;
	@FXML
	private RadioButton r1;
	@FXML
	private RadioButton r2;
	// =============================================================
	@FXML
	private Pane homePage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// read details from file and store it in arrays
		readCourses();
		readStudents();

	}

	public void readCourses() {

		try {
			// define the location of file
			File f = new File("courses.txt");
			// scanner for reading
			Scanner input = new Scanner(f);
//read each line
			while (input.hasNext()) {
				// spliting the line
				String[] split = input.nextLine().trim().split("#");
				String courseName = split[0];
				String courseID = split[1];
				int year = Integer.parseInt(split[2]);
				String startTime = split[3];
				String finishTime = split[4];
				String semester = split[5];
				String days = split[6];
				// store details in object of course and insert it in array
				courses.insert(new Course(courseName, courseID, year, startTime, finishTime, semester, days));

			}
			// sort the courses depends on year
			sortCoursesDependsOnYear();
		} catch (Exception e) {
			// print any exception if error occurs
			System.out.println(e);
		}
	}

	public void readStudents() {

		// catching errors
		try {
			// define object of file to read from it
			File studentFile = new File("data.txt");
			// define object of scanner to scan data
			Scanner input = new Scanner(studentFile);
			while (input.hasNextLine()) {
				// splitting the string
				String splitString[] = input.nextLine().trim().split("#");
				// create an object of student to store each course
				Student s = new Student();
				String name = splitString[0];
				s.setName(name);
				String stID = splitString[1];
				s.setStId(stID);
				// store course to student courses list
				for (int counter = 2; counter < splitString.length; counter++) {
					// add courses from file to list of course to each student
					String course = splitString[counter];
					for (int c2 = 0; c2 < courses.size(); c2++) {
						String co = ((Course) courses.get(c2)).getCourseID();
						if (co.equals(course)) {
							// add each course to student course array
							s.getStCourses().insert(courses.get(c2));
						}
					}

				}

				// storing student to student list
				students.insert(s);

			}
//stores student in course student array
			storeStudentInCourses();
		} catch (Exception e) {
			// print it if the file does not exist
			System.out.println("No Such File");
		}
	}

	// set actions for the main button
//set action for course page button
	public void b1ActionEvent(ActionEvent e) {
		homePage.setVisible(false);
		CoursePane.setVisible(true);
		courseStudentPane.setVisible(false);
		studentsPagePane.setVisible(false);
		schedulePagePane.setVisible(false);
		editSchedulePane.setVisible(false);
		showCourse();
	}

	// set action for student page button
	public void b2ActionEvent(ActionEvent e) {
		homePage.setVisible(false);
		CoursePane.setVisible(false);
		courseStudentPane.setVisible(false);
		studentsPagePane.setVisible(true);
		schedulePagePane.setVisible(false);
		editSchedulePane.setVisible(false);
		showStudent();
	}

	// set action for student schedule page button
	public void b3ActionEvent(ActionEvent e) {
		clearShedulePage();
		homePage.setVisible(false);
		CoursePane.setVisible(false);
		courseStudentPane.setVisible(false);
		studentsPagePane.setVisible(false);
		schedulePagePane.setVisible(true);
		editSchedulePane.setVisible(false);

	}

	// set action for edit schedule page button
	public void b4ActionEvent(ActionEvent e) {
		clearEditSch();
		homePage.setVisible(false);
		CoursePane.setVisible(false);
		courseStudentPane.setVisible(false);
		studentsPagePane.setVisible(false);
		schedulePagePane.setVisible(false);
		editSchedulePane.setVisible(true);
	}

	private void clearEditSch() {
		studentIDETextField.clear();
		studentNameETextField.clear();

	}

//======================================================================
//set course page
	// create array of button

	static Button[] clickShowStudents;

//used to show courses on course page
	public void showCourse() {
		try {
			// clear vbox
			courselistVBox.getChildren().clear();
			// set space between rows
			courselistVBox.setSpacing(20);
			clickShowStudents = new Button[courses.size()];
			// loop for course list
			for (int c1 = 0; c1 < courses.size(); c1++) {
				// bring and store each course for the array
				Course co = (Course) courses.get(c1);
				// create hbox
				HBox hb = new HBox();
				// set details in labels
				Label l1 = new Label(co.getCourseName());
				l1.setPrefWidth(353);
				l1.setPrefHeight(50);
				l1.setFont(Font.font(20));
				Label l2 = new Label(co.getCourseID());
				l2.setPrefWidth(129);
				l2.setFont(Font.font(20));
				l2.setPrefHeight(50);
				Label l3 = new Label(co.getSemester());
				l3.setPrefWidth(129);
				l3.setPrefHeight(50);
				l3.setFont(Font.font(20));
				l3.setPadding(new Insets(0, 0, 0, 40));
				Label l4 = new Label(co.getStartTime());
				l4.setPrefWidth(124);
				l4.setPrefHeight(50);
				l4.setFont(Font.font(20));
				Label l5 = new Label(co.getFinishTime());
				l5.setPrefWidth(115);
				l5.setPrefHeight(50);
				l5.setFont(Font.font(20));
				Label l6 = new Label(co.getDays());
				l6.setPrefWidth(107);
				l6.setPrefHeight(50);
				l6.setFont(Font.font(20));
				Label l7 = new Label("" + co.getList().size());
				l7.setPrefWidth(118);
				l7.setPrefHeight(50);
				l7.setFont(Font.font(20));
				l7.setPadding(new Insets(0, 0, 0, 40));
				// set button
				clickShowStudents[c1] = new Button("Show");
				clickShowStudents[c1].setPrefWidth(100);
				clickShowStudents[c1].setOnAction(this::showStudentOfCourse);
				// add node to hbox
				hb.getChildren().addAll(l1, l2, l3, l4, l5, l6, l7, clickShowStudents[c1]);
				// add hbox to vbox
				courselistVBox.getChildren().add(hb);

			}
		} catch (Exception e) {
			// print any error if it occurs
			System.out.println("The exception" + e);
		}

	}

	public void showStudentOfCourse(ActionEvent e) {
		// check when button was pressed
		for (int x = 0; x < clickShowStudents.length; x++) {
			if (e.getSource() == clickShowStudents[x]) {
				// send the index of button was pressed
				showPage(x);
			}
		}
	}

	public void showPage(int index) {
		try {

			Course c = (Course) courses.get(index);
			// check the number of student in each course
			if (c.getList().size() == 0) {
//show alert if the arrays is empty
				throw new Exception();
			} else {
				// create radix sort object
				RadixSort rSort = new RadixSort();
				// call sort method
				rSort.sort(students);
				// show pane
				courseStudentPane.setVisible(true);
				// clear vbox
				courseStudentVBox.getChildren().clear();
				for (int x = 0; x < c.getList().size(); x++) {
					// show details
					Student s = (Student) c.getList().get(x);
					Label l1 = new Label(s.getName());
					l1.setPrefSize(360, 50);
					l1.setFont(Font.font(20));
					Label l2 = new Label(s.getStId());
					l2.setPrefSize(150, 50);
					l2.setFont(Font.font(20));
					// create hbox
					HBox h = new HBox();
					// add node to hbox
					h.getChildren().addAll(l1, l2);
					// add hbox to vbox
					courseStudentVBox.getChildren().addAll(h);
				}
			}
		} catch (Exception e) {
			// set alert
			Alert alertInformation = new Alert(AlertType.INFORMATION);
			alertInformation.setTitle("Error Information ");
			alertInformation.setHeaderText("Information Alert Header");
			alertInformation.setContentText("Information Alert Content:\nNo Student Enrolled In This Course");
			alertInformation.showAndWait();
		}
	}

	// ======================================================================
	// set students page
	public void showStudent() {
		try {
			// sort depends on name
			sortDependsOnName();
			// clear vbox
			studentsPageVBox.getChildren().clear();
			for (int x = 0; x < students.size(); x++) {
				// show details
				Student s = (Student) students.get(x);
				Label l1 = new Label(s.getName());
				l1.setPrefSize(270, 50);
				l1.setFont(Font.font(20));
				Label l2 = new Label(s.getStId());
				l2.setPrefSize(150, 50);
				l2.setFont(Font.font(20));
				// create hbox
				HBox h = new HBox();
				// add nodes to hbox
				h.getChildren().addAll(l1, l2);
				// add hbox to vbox
				studentsPageVBox.getChildren().addAll(h);
			}

		} catch (Exception ex) {
			// print any error if it occurs
			System.out.println(ex);
		}
	}

//============================================================================
	public void clearShedulePage() {
//clear any data in shedule page
		SchdeuleNameTextField.clear();
		SchdeuleIDTextField.clear();
		schedulePageVBox.getChildren().clear();
		t1.setDisable(true);
		t2.setDisable(true);
	}

	// set schedule page
	public void searchShedulePageButtonAction(ActionEvent ec) {
		try {
//set toggle group 
			ToggleGroup group1 = new ToggleGroup();
			t1.setToggleGroup(group1);
			t2.setToggleGroup(group1);

			// convert from string to int
			int id = Integer.parseInt(SchdeuleIDTextField.getText());
			// find the position of student in list
			int position = checkTheStudentIDForSchedulePage(id);
			SchdeuleNameTextField.setText(((Student) students.get(position)).getName());
			System.out.println(((Student) students.get(position)).getStCourses().size());
			// set togglebutton on
			t1.setDisable(false);
			t2.setDisable(false);
			// set action for each togglebutton
			t1.setOnAction(e -> {
				// call method and send the semester was chosen
				showScheduleOfStudent(position, 1);
			});
			t2.setOnAction(e -> {// call method and send the semester was chosen
				showScheduleOfStudent(position, 2);
			});

		} catch (Exception ex) {
			// clear textfield
			SchdeuleIDTextField.clear();
			// show alert when the student does not exist
			Alert alertInformation = new Alert(AlertType.INFORMATION);
			alertInformation.setTitle("Error Information ");
			alertInformation.setHeaderText("Information Alert Header");
			alertInformation.setContentText("Information Alert Content:\nWrong Student ID!!\nTry Again");
			alertInformation.showAndWait();
		}
	}

	public void showScheduleOfStudent(int position, int semester) {
		schedulePageVBox.getChildren().clear();

		// store size of courses
		int size = ((Student) students.get(position)).getStCourses().size();
		// show courses on screen
		for (int x = 0; x < size; x++) {
			Course course1 = (Course) ((Student) students.get(position)).getStCourses().get(x);
			// check which semester was chosen
			if (semester == 1) {

				// clear vbox
				// print courses of semester 1
				if (course1.getSemester().equals("1")) {
//create hbox
					HBox hb = new HBox();
//show details in labels
					Label l1 = new Label(course1.getCourseID());

					l1.setPrefSize(185, 30);
					l1.setFont(Font.font(25));
					l1.setTextFill(Color.WHITE);
					Label l2 = new Label(course1.getStartTime());
					l2.setPrefSize(185, 30);
					l2.setFont(Font.font(25));
					l2.setTextFill(Color.WHITE);
					Label l3 = new Label(course1.getFinishTime());
					l3.setPrefSize(185, 30);
					l3.setFont(Font.font(25));
					l3.setTextFill(Color.WHITE);
					Label l4 = new Label(course1.getDays());
					l4.setPrefSize(185, 30);
					l4.setFont(Font.font(25));
					l4.setTextFill(Color.WHITE);
//add nodes to hbox
					hb.getChildren().addAll(l1, l2, l3, l4);
					// add hbox to vbox
					schedulePageVBox.getChildren().add(hb);
				}
			} else {// print courses of semester 2
				// clear vbox
				if (course1.getSemester().equals("2")) {
					// create hbox
					HBox hb = new HBox();
					// show details in labels
					Label l1 = new Label(course1.getCourseID());
					l1.setPrefSize(185, 30);
					l1.setFont(Font.font(25));
					l1.setTextFill(Color.WHITE);
					Label l2 = new Label(course1.getStartTime());
					l2.setPrefSize(185, 30);
					l2.setFont(Font.font(25));
					l2.setTextFill(Color.WHITE);
					Label l3 = new Label(course1.getFinishTime());
					l3.setPrefSize(185, 30);
					l3.setFont(Font.font(25));
					l3.setTextFill(Color.WHITE);
					Label l4 = new Label(course1.getDays());
					l4.setPrefSize(185, 30);
					l4.setFont(Font.font(25));
					l4.setTextFill(Color.WHITE);
					// add node to hbox
					hb.getChildren().addAll(l1, l2, l3, l4);
					// add hbox to vbox
					schedulePageVBox.getChildren().add(hb);
				}

			}
		}
	}

	public static int checkTheStudentIDForSchedulePage(int studentID) throws Exception {

		boolean check = false;
		int position = 0;
		// searching in student linked list
		for (int c1 = 0; c1 < students.size(); c1++) {
			Student s = (Student) students.get(c1);
			int sID = Integer.parseInt(s.getStId());
			if (sID == studentID) {
				check = true;
				position = c1;
				break;
			}
		}
		if (check == false)
			throw new Exception();
//send the positio of student in list
		return position;
	}

	// ======================================================================
	// set EditSchedule page
//set the action for search button

	public void searchEButtonAction(ActionEvent e) {
		try {
			// clear vbox
			eSchaduleVBox.getChildren().clear();
			// convert the studentIDETextField text to int to check if it is integer or not
			int studentID = Integer.parseInt(studentIDETextField.getText());
			// check the existence of id in student linkedlist
			int postion = checkTheStudentID(studentID);

			Student s = (Student) students.get(postion);
			// create toggle group object
			ToggleGroup group = new ToggleGroup();
			r1.setToggleGroup(group);
			r2.setToggleGroup(group);
			// set radiobuttons on
			r1.setDisable(false);
			r2.setDisable(false);
			// set radiobutton selected
			r1.setSelected(true);

			// set the name of student in studentNameETextField textfield
			studentNameETextField.setText(s.getName());
			// show course on page
			showCoursesOnEditSchedulePage(s);

		} catch (Exception ex1) {
			studentIDETextField.clear();
			// show the alert when the student id
			Alert alertInformation = new Alert(AlertType.INFORMATION);
			alertInformation.setTitle("Error Information ");
			alertInformation.setHeaderText("Information Alert Header");
			alertInformation.setContentText("Information Alert Content:\nWrong Student ID!!\nTry Again");
			alertInformation.showAndWait();
		}
	}

//create array of button
	static Button clickEditSch[];

	public void showCoursesOnEditSchedulePage(Student s) {
		try {

			clickEditSch = new Button[courses.size()];
//set button text 
			setButton(clickEditSch, s);
			// clear vbox
			eSchaduleVBox.getChildren().clear();
			// set space
			eSchaduleVBox.setSpacing(20);
			for (int c1 = 0; c1 < courses.size(); c1++) {
				// show details for each course
				Course course1 = (Course) courses.get(c1);
				// create hbox
				HBox hb = new HBox();
				// show details using labels
				hb.setPrefHeight(50);
				Label l1 = new Label(course1.getCourseName());
				l1.setPrefSize(470, 30);
				l1.setFont(Font.font(20));
				l1.setPadding(new Insets(0, 0, 0, 5));
				Label l2 = new Label(course1.getCourseID());
				l2.setPrefSize(120, 30);
				l2.setFont(Font.font(20));
				Label l3 = new Label(course1.getYear() + "/" + course1.getSemester());
				l3.setPrefSize(130, 30);
				l3.setFont(Font.font(20));
				Label l4 = new Label(course1.getStartTime());
				l4.setPrefSize(120, 30);
				l4.setFont(Font.font(20));
				Label l5 = new Label(course1.getFinishTime());
				l5.setPrefSize(100, 30);
				l5.setFont(Font.font(20));
				Label l6 = new Label("" + course1.getDays());
				l6.setPrefSize(150, 30);
				l6.setFont(Font.font(20));
				// set button action
				clickEditSch[c1].setOnAction(this::clickEditSchAction);
				// add hbox to vbox
				hb.getChildren().addAll(l1, l2, l3, l4, l5, l6, clickEditSch[c1]);
				// add hbox to vbox
				eSchaduleVBox.getChildren().add(hb);

			}

		} catch (Exception ex) {
			// print any error if it occurs
			System.out.println(ex);
		}

	}

//set the action for button
	public void setButton(Button[] clickEditSch2, Student s) {
		for (int c1 = 0; c1 < clickEditSch2.length; c1++) {
			clickEditSch2[c1] = new Button("Add");

		}
		for (int c1 = 0; c1 < s.getStCourses().size(); c1++) {
			Course cSt = (Course) s.getStCourses().get(c1);
			for (int c2 = 0; c2 < courses.size(); c2++) {
				Course c = (Course) courses.get(c2);
				if (cSt.getCourseID().equals(c.getCourseID())) {
					clickEditSch2[c2] = new Button("remove");
				}

			}
		}
	}

	public void clickEditSchAction(ActionEvent e) {
		for (int c1 = 0; c1 < clickEditSch.length; c1++) {
			if (e.getSource() == clickEditSch[c1]) {
				// check which button was pressed
				checkCourses(c1);

			}
		}
	}

	public void checkCourses(int coursePostion) {
		try {

			Course c = (Course) courses.get(coursePostion);
			// convert the studentIDETextField text to int
			int studentID = Integer.parseInt(studentIDETextField.getText());
			// check the existence of id in student linkedlist and get the position
			int postion = checkTheStudentID(studentID);
			Student s = (Student) students.get(postion);
			// count the amount of courses each semester
			int coursesOfSemester1 = 0;
			int coursesOfSemester2 = 0;
			for (int c1 = 0; c1 < s.getStCourses().size(); c1++) {
				Course cSt = (Course) s.getStCourses().get(c1);
				// count courses for each semester
				if (cSt.getSemester().equals("1")) {
					coursesOfSemester1++;
				} else {
					coursesOfSemester2++;
				}
			}

			String textOfButton = clickEditSch[coursePostion].getText();
			// remove the course
			if (textOfButton.equals("remove")) {
				// check the existence of course in student course list
				for (int c1 = 0; c1 < s.getStCourses().size(); c1++) {
					Course cSt = (Course) s.getStCourses().get(c1);
					if (cSt.getCourseID().equals(c.getCourseID()) == true) {
						// remove the course
						((Student) students.get(postion)).getStCourses().delete(c1);
						// reset the button
						clickEditSch[coursePostion].setText("add");
						// reprint in file
						printOnStudentFile();
						throw new Exception("remove has been done");
					}
				}
			}

			// take the first 3 digit of student number
			int yearOfStudent = studentID / 10000;
			if (yearOfStudent <= c.getYear()) {
				// set variable for course semester
				int semesterCourse = Integer.parseInt(c.getSemester());
				// check number of courses each semester for student
				if (semesterCourse == 1) {
					// check radiobutton1 if it is selected
					if (r1.isSelected()) {
						
						// check the number of courses for semester 1
						if (coursesOfSemester1 < 6) {
							// variable for check time
							boolean check = true;
							for (int c1 = 0; c1 < s.getStCourses().size(); c1++) {
								// check each courses that student enrolled
								Course cSt = (Course) s.getStCourses().get(c1);

								if (cSt.getSemester().equals("1")) {
									String day1 = cSt.getDays();
									String day2 = c.getDays();
									if (day2.equals(day1) == true) {
										check = checkT(c.getStartTime(), c.getFinishTime(), cSt.getStartTime(),
												cSt.getFinishTime());
										// throw exception
										if (check == false) {
											throw new Exception("error3");
										}
									}
								}
							}
							// check the max of student
							if (c.getMaxNumberOfStudent() < 45) {// reset the text of button
								clickEditSch[coursePostion].setText("remove");
								// add course to student course list
								((Student) students.get(postion)).getStCourses().insert(c);
								// add student to course student list
								((Course) courses.get(coursePostion)).getList().insert(s);
								// reprint the data
								printOnStudentFile();
								throw new Exception("done");
							} else {
								throw new Exception("max");
							}
						} else {
							throw new Exception("error1");
						}
					} else {
						throw new Exception("error5");
					}
				} else if (semesterCourse == 2) {

					// check if radiobutton was selected
					if (r2.isSelected()) {
						
						// check the count of courses
						if (coursesOfSemester2 < 6) {
							boolean check = true;
							for (int c1 = 0; c1 < s.getStCourses().size(); c1++) {
								// check each courses that student enrolled
								Course cSt = (Course) s.getStCourses().get(c1);

								if (cSt.getSemester().equals("2")) {
									String day1 = cSt.getDays();
									String day2 = c.getDays();
									if (day2.equals(day1) == true) {
										check = checkT(c.getStartTime(), c.getFinishTime(), cSt.getStartTime(),
												cSt.getFinishTime());
										// throw exception
										if (check == false) {
											throw new Exception("error3");
										}
									}
								}
							}
							// check the max of student
							if (c.getMaxNumberOfStudent() < 45) {// reset the text of button
								clickEditSch[coursePostion].setText("remove");
								// add course to student course list
								((Student) students.get(postion)).getStCourses().insert(c);
								// add student to course student list
								((Course) courses.get(coursePostion)).getList().insert(s);
								// reprint the data
								printOnStudentFile();
								throw new Exception("done");
							} else {
								throw new Exception("max");
							}
						} else {
							throw new Exception("error1");
						}
					} else {
						throw new Exception("error5");
					}
				}
			} else {
				throw new Exception("error2");
			}

		} catch (Exception ex) {
			// check which alert must be shown
			String error = ex.getMessage();
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Alert Title");
			alertError.setHeaderText("Error Alert Header");
			if (error.equals("error1")) {
				alertError.setContentText(
						"Error Alert Content \nSorry,you can not to add more course\nyou must remove one");
				alertError.showAndWait();
			} else if (error.equals("error2")) {
				alertError.setContentText("Error Alert Content \nSorry,you are not allowed to enrolled the course");
				alertError.showAndWait();
			} else if (error.equals("error3")) {
				alertError.setContentText("Error Alert Content \nTime Conflict!!");
				alertError.showAndWait();
			} else if (error.equals("error5")) {
				alertError.setContentText("Error Alert Content \nThe semester was chosen is wrong!!");
				alertError.showAndWait();
			} else if (error.equals("remove has been done")) {
				alertError = new Alert(AlertType.CONFIRMATION);
				alertError.setContentText("Error Alert Content \nThe course has been removed");
				alertError.showAndWait();
			} else if (error.equals("done")) {
				alertError = new Alert(AlertType.CONFIRMATION);
				alertError.setContentText("Error Alert Content \nThe course has been added");
				alertError.showAndWait();
			}
			if (error.equals("max")) {
				alertError = new Alert(AlertType.CONFIRMATION);
				alertError.setContentText("Error Alert Content \nThe course has reached the max of student");
				alertError.showAndWait();
			}

		}

	}

//==============================================================================
	// set helping methods
	public void sortCoursesDependsOnYear() {
		// sorting
		for (int c1 = 0; c1 < courses.size(); c1++) {
			Course course1 = (Course) courses.get(c1);
			for (int c2 = 0; c2 < courses.size(); c2++) {
				Course course2 = (Course) courses.get(c2);
				// comparing between courses depend on year
				if (course1.getYear() < course2.getYear()) {
					// swapping objects
					courses.swap(c1, c2);
				}
			}
		}

	}

	public void sortDependsOnName() {
		for (int c1 = 0; c1 < students.size(); c1++) {
			Student s1 = (Student) students.get(c1);
			for (int c2 = 0; c2 < students.size(); c2++) {
				Student s2 = (Student) students.get(c2);
				// comparing between courses depend on year
				if (s1.getName().compareToIgnoreCase(s2.getName()) < 0) {
					// swapping objects
					students.swap(c1, c2);
				}
			}
		}
	}

	public static int checkTheStudentID(int studentID) throws Exception {

		boolean check = false;
		int position = 0;
		// searching in student linked list
		for (int c1 = 0; c1 < students.size(); c1++) {
			Student s = (Student) students.get(c1);
			int sID = Integer.parseInt(s.getStId());
			if (sID == studentID) {
				check = true;
				position = c1;
				break;
			}
		}
		if (check == false)
			throw new Exception();

		return position;
	}

	public static boolean checkT(String t1, String t2, String t3, String t4) {
		//convert from string to int and ceiling them
		t1 = t1.replace(":", "");
		int St1 = Integer.parseInt("" + t1.charAt(0) + t1.charAt(1));
		int mst1 = Integer.parseInt("" + t1.charAt(2) + t1.charAt(3));
		if (mst1 >= 30) {
			St1 += 1;
			mst1 = 0;
		} else {
			mst1 = 0;
		}

		t2 = t2.replace(":", "");
		int ft1 = Integer.parseInt("" + t2.charAt(0) + t2.charAt(1));
		int mft1 = Integer.parseInt("" + t2.charAt(2) + t2.charAt(3));
		if (mft1 >= 30) {
			ft1 += 1;
			mft1 = 0;
		} else {
			mft1 = 0;
		}

		t3 = t3.replace(":", "");
		int St2 = Integer.parseInt("" + t3.charAt(0) + t3.charAt(1));
		int mst2 = Integer.parseInt("" + t3.charAt(2) + t3.charAt(3));
		if (mst2 >= 30) {
			St2 += 1;
			mst2 = 0;
		} else {
			mst2 = 0;
		}

		t4 = t4.replace(":", "");
		int ft2 = Integer.parseInt("" + t4.charAt(0) + t4.charAt(1));
		int mft2 = Integer.parseInt("" + t4.charAt(2) + t4.charAt(3));
		if (mft2 >= 30) {
			ft2 += 1;
			mft2 = 0;
		} else {
			mft2 = 0;
		}

		if (St1 > St2) {
			if (St1 <= ft2) {

				return false;
			} else {

				return true;
			}
		} else if (St1 < St2) {
			if (St2 <= ft1) {

				return false;
			} else {

				return true;
			}
		} else {

			return false;
		}
	}

	public void printOnStudentFile() {
		try {
			// location of file
			File f = new File("data.txt");
			PrintWriter p = new PrintWriter(f);
			// print data
			String line = "";
			for (int c1 = 0; c1 < students.size(); c1++) {
				Student s = (Student) students.get(c1);

				line = s.getName().concat("#").concat(s.getStId()).concat("#");
				for (int c2 = 0; c2 < s.getStCourses().size(); c2++) {
					Course c = (Course) s.getStCourses().get(c2);
					line = line.concat(c.getCourseID()).concat("#");
				}
				p.println(line);
			}
			p.close();
		} catch (Exception ex) {
			System.out.println("No Such File!!");
		}
	}

	public void storeStudentInCourses() {
		// loop for student list

		for (int c1 = 0; c1 < students.size(); c1++) {
			Student s = (Student) students.get(c1);
			// loop for student course list
			for (int c2 = 0; c2 < s.getStCourses().size(); c2++) {
				Course cSt = (Course) s.getStCourses().get(c2);
				// loop for course list
				for (int c3 = 0; c3 < courses.size(); c3++) {
					Course c = (Course) courses.get(c3);
					// check for existence
					if (c.getCourseID().equals(cSt.getCourseID())) {
						// store student in course student list
						((Course) courses.get(c3)).getList().insert(s);
					}
				}
			}
		}

	}

}
