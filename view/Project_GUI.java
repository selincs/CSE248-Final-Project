package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.SectionRow;
import model.TimeRange;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Course;
import model.CourseSet;
import model.DayOfWeek;
import model.Instructor;
import model.InstructorRow;
import model.InstructorSet;
import model.Period;
import model.Section;
import utils.DataPersistence;
import utils.ImportCourses;
import utils.ImportInstructors;
//Selin Saracoglu Final Project Ver
public class Project_GUI extends Application {
	private int instructorIdx = 0;
	private boolean secondRequestsDone = false;
	private boolean thirdRequestsDone = false;
	private boolean seniorityCreated = false;
	private ArrayList<Instructor> seniorityList;
	private GridPane instructorGrid;
	private Label[] instructorLabels = { new Label(), new Label(), new Label(), new Label(), new Label(), new Label(),
			new Label(), new Label() };
	private String currentInstructorID;
	private TableView<SectionRow> sectionTable;

	public static void main(String[] args) {
		launch(args);
	}

	// BorderPane -> 2nd Inner BorderPane -> Labels go in top of innerBP -> Searched
	// Instructor in Center of BP
	// Can create buttons to display things and disable buttons until conditions are
	// met

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load InstructorSet
		InstructorSet instructorSet = DataPersistence.deserializeInstructorSet();
		
		if (instructorSet == null) {
		    // Handle the case where deserialization fails or the file doesn't exist
			// Used in searching and data storage
			InstructorSet.getInstructorSet();
		}
		// Load CourseSet
		CourseSet courseSet = DataPersistence.deserializeCourseSet();
		if (courseSet == null) {
		    // Handle the case where deserialization fails or the file doesn't exist
			CourseSet.getCourseSet();
		}
		
		
		BorderPane root = new BorderPane();

		HBox searchBox = new HBox(10);
		searchBox.setAlignment(Pos.CENTER);
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);

		GridPane instructorPane = instructorPane(instructorLabels);

		// Create a VBox layout
		VBox menuVbox = createMenuBar(primaryStage, root);
		// Add the MenuBar to the BorderPane
		root.setTop(menuVbox);

		TextField idField = new TextField();
//		TextField nameField = new TextField();
		Label assignNextLbl = new Label("Click Next to iterate by Seniority");
		searchBox.getChildren().addAll(idField, assignNextLbl);

		// Button box
		Button searchBtn = new Button("Search");
		searchBtn.setPrefSize(100, 30);
		Button nextBtn = new Button("Next Instructor"); // Clear all fields button go here
		nextBtn.setPrefSize(100, 30);
		buttonBox.setSpacing(60);
		buttonBox.getChildren().addAll(searchBtn, nextBtn);

		// Holds form of my current instructor GUI in instructorGrid
		instructorGrid = new GridPane();

		instructorGrid.setAlignment(Pos.CENTER);
		instructorGrid.setPadding(new Insets(20));
		instructorGrid.setHgap(10);
		instructorGrid.setVgap(10);
//		instructorGrid.setStyle("-fx-background-color:rgba(2, 48, 32, .7)");

		// Labels for Search area
		Label idSearchLbl = new Label("Search by ID");
		Label nextLbl = new Label("Assign Sections by Seniority");

		HBox searchLblBox = new HBox();
		searchLblBox.setAlignment(Pos.CENTER);
		searchLblBox.setSpacing(80);
		searchLblBox.getChildren().addAll(idSearchLbl, nextLbl);

		instructorGrid.add(searchLblBox, 0, 0);
		instructorGrid.add(searchBox, 0, 1);
		instructorGrid.add(buttonBox, 0, 2);
		instructorGrid.add(instructorPane, 0, 3);

		// Set center of BorderPane
		root.setCenter(instructorGrid);

		// Set left of BorderPane
		HBox instructorTableHBox = instructorTableDisplay();
		root.setLeft(instructorTableHBox);

		// Set right of BorderPane
		HBox sectionTableHBox = sectionTableDisplay();
		root.setRight(sectionTableHBox);

//		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 400, 300);

		// call instructorPane and set Center to searched instructor
		// If no instructors are loaded account for that with a message
		// Maybe instead of a fileMenu I just load from search button if doesnt exist ->
		// or check database

		searchBtn.setOnAction(e -> {
			// String name = nameField.getText().trim();
			String id = idField.getText().trim();
			System.out.println("Searching for instructor with ID: " + id);
			// Controller here
			Instructor foundInstructor = InstructorSet.search(id);

			if (id.isEmpty()) {
				// Handle case where id field is empty
				// Create warning label or error
				System.out.println("ID Field empty");
				return;
			}
			System.out.println("Search pressed");
			displayInstructor(foundInstructor);
		});

		nextBtn.setOnAction(e -> {
			idField.clear();
			final HBox updatedTable = instructorTableDisplay();
			root.setLeft(updatedTable);

			displaySeniorInstructor();
			// It works but it needs validation
		});

		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// how to ensure list is updated every time i iterate to a new instructor
	public HBox sectionTableDisplay() {
		Instructor displayedInstructor = InstructorSet.search(currentInstructorID);
		if (displayedInstructor == null) {
			System.out.println("Displayed instructor is null");
		}
		ObservableList<SectionRow> filteredSectionRows = getSectionRows(displayedInstructor);
		sectionTable = new TableView<>();
		sectionTable.setItems(filteredSectionRows);
		sectionTable.refresh();

		Button assignBtn = new Button("Assign Section");
		assignBtn.setPrefSize(100, 30);

		// Set the column resize policy to unconstrained
		sectionTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		TableColumn<SectionRow, String> titleColumn = new TableColumn<>("Course Title");
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

		TableColumn<SectionRow, String> crnColumn = new TableColumn<>("CRN");
		crnColumn.setCellValueFactory(cellData -> cellData.getValue().crnProperty());

		TableColumn<SectionRow, String> meetingDaysColumn = new TableColumn<>("Days");
		meetingDaysColumn.setCellValueFactory(cellData -> cellData.getValue().meetingDaysProperty());

		TableColumn<SectionRow, String> classTimesColumn = new TableColumn<>("Class Time");
		classTimesColumn.setCellValueFactory(cellData -> cellData.getValue().classTimesProperty());

		sectionTable.getColumns().addAll(titleColumn, crnColumn, meetingDaysColumn, classTimesColumn);

		VBox sectionTableVbox = new VBox();
		VBox.setVgrow(sectionTable, javafx.scene.layout.Priority.ALWAYS);
		sectionTableVbox.setSpacing(10);
		sectionTableVbox.setAlignment(Pos.TOP_CENTER);
		sectionTableVbox.getChildren().addAll(new Label(""), sectionTable, assignBtn, new Label(""));

		HBox sectionTableWrapper = new HBox();
		sectionTableWrapper.setSpacing(10);
		sectionTableWrapper.getChildren().addAll(sectionTableVbox, new Label(""));

		assignBtn.setOnAction(event -> {
			SectionRow selectedRow = sectionTable.getSelectionModel().getSelectedItem();
			Instructor assignmentInstructor = InstructorSet.search(currentInstructorID);

			if (assignmentInstructor.isRequestThirdCourse()) {
				if (assignmentInstructor.getAssignedSections()[2] != null) {
					System.out.println("third req assigned already");
					displaySeniorInstructor();
					return;
				}
			}

			if (assignmentInstructor.isRequestSecondCourse() && !(assignmentInstructor.isRequestThirdCourse())) {
				if (assignmentInstructor.getAssignedSections()[1] != null) {
					System.out.println("second req assigned already");
					displaySeniorInstructor();
					return;
				}
			}

			if (selectedRow != null) {
				Section assignment = selectedRow.getSection().getCourse().getSectionSet().search(selectedRow.getCRN());

				// Get Initial Meeting Days of Section
				if (assignment.getClassTime() != null) {
					Set<DayOfWeek> meetingDays = assignment.getClassTime().getDays();

					// Update the instructor's availability for each day of the assigned section
					for (DayOfWeek day : meetingDays) {
						int dayIndex = day.getDayIndex();
						List<Period> occupiedPeriods = getOccupiedPeriods(assignment.getClassTime());

						for (Period period : occupiedPeriods) {
							int periodIndex = period.getPeriodIndex();
//							if (assignmentInstructor.getAssignedSections()[2] == null) {
							assignmentInstructor.getAvailability()[dayIndex][periodIndex] = false;
//							}
						}
					}
				}

				if (assignment.getAlternateClassTime() != null) {
					TimeRange alternateClassTime = assignment.getAlternateClassTime();
					Set<DayOfWeek> alternateDays = alternateClassTime.getDays();
					for (DayOfWeek day : alternateDays) {
						int dayIndex = day.getDayIndex();
						List<Period> occupiedPeriods = getOccupiedPeriods(assignment.getAlternateClassTime());

						for (Period period : occupiedPeriods) {
							int periodIndex = period.getPeriodIndex();
//							if (assignmentInstructor.getAssignedSections()[2] == null) {
							assignmentInstructor.getAvailability()[dayIndex][periodIndex] = false;
						}
					}
				}

				// Apply assignment
				assignmentInstructor.assignSection(assignment);
				assignment.setAssignedInstructor(assignmentInstructor);
				
				//Assignment made : Serialize set
				DataPersistence.serializeInstructorSet(InstructorSet.getInstructorSet());
				DataPersistence.serializeCourseSet(CourseSet.getCourseSet());
				
				if (assignment.getAssignedInstructor() != null) {
					displayInstructor(assignmentInstructor);
				}
				// Display assignments in console
				if (assignment != null && assignmentInstructor != null) {
					System.out.println();
					System.out.println("Instructor assigned to : " + assignment);
					System.out.println("Section assigned to : " + assignmentInstructor);
					System.out.println();
				}

				// System.out.println("Selected Instructor: " + selectedRow.getName());
			}
//			if (assignmentInstructor.getAssignedSections()[2] != null) {
//		//		instructorIdx++;
//				displaySeniorInstructor();
//			}

		});

		return sectionTableWrapper;

	}

	public ObservableList<SectionRow> getSectionRows(Instructor currentInstructor) {
		ObservableList<SectionRow> sectionRows = FXCollections.observableArrayList();

		if (CourseSet.getCourses() != null) {
			for (Course course : CourseSet.getCourses()) {
				for (Section section : course.getSectionSet().getSections()) {
					if (meetsFilterRequirements(section.toSectionRow(), currentInstructor)) {
						sectionRows.add(section.toSectionRow());
						if (section.getAlternateClassTime() != null) {
							sectionRows.add(section.toSecondSectionRow());
						}
					}
				}
			}
		}
		return sectionRows; // Return the filtered sectionRows
	}

	// Implement the filtering logic based on instructor's qualifications
	public boolean meetsFilterRequirements(SectionRow sectionRow, Instructor currentInstructor) {
		boolean isOnlineCourse = false;
		if (currentInstructor == null) { // If no instructors are displayed, display full list of courses
			return true;
		}
		if (sectionRow.getSection().getAssignedInstructor() != null) {
			return false; // Section has already been assigned, cannot display
		}
		// Check the section's instructionMethod to filter online courses
		if (sectionRow.getSection().getInstructionMethod().equalsIgnoreCase("BLBD")) {
			// Check if the instructor is certified to teach online
			if (!currentInstructor.isCertifiedOnline()) {
				return false; // Section is online, but instructor not certified
			}
			isOnlineCourse = true;
		}

// Check if the instructor is certified for this specific course (based on Course SUB+Num)
		String courseSubjNum = sectionRow.getSection().getCourse().getCourseTitle().getCourseSubjNum();
		if (!currentInstructor.getCertifiedCourses().contains(courseSubjNum)) {
			return false; // Instructor is not certified for this course
		}

// Check if instructor is available to teach on the campus this in-person course is offered
		if (!isOnlineCourse) { // Campus unnecessary for Online Course
			if (!currentInstructor.getPreferredCampuses().contains(sectionRow.getSection().getCampus())) {
				return false; // Section campus doesn't match instructor's preferred campuses
			}
		}

		// Validation for courses with no assigned time, still display
		if (sectionRow.getSection().getClassTime() == null) {
			return true;
		}

		// Filter Section by Initial Class Time
		List<Period> occupiedPeriods = getOccupiedPeriods(sectionRow.getSection().getClassTime());
		for (DayOfWeek day : sectionRow.getSection().getClassTime().getDays()) {
			for (Period period : occupiedPeriods) {
				if (!currentInstructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]) {
					return false; // Instructor not available at this day and time period
				}
			}
		}
		// Filter Section by Alternate Meeting Day Class Times
		if (sectionRow.getSection().getAlternateClassTime() != null) {
			List<Period> occupiedPeriodsAlt = getOccupiedPeriods(sectionRow.getSection().getAlternateClassTime());

			for (DayOfWeek day : sectionRow.getSection().getAlternateClassTime().getDays()) {
				for (Period period : occupiedPeriodsAlt) {
					if (!currentInstructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]) {
						return false; // Instructor not available at this day and time period
					}
				}
			}
		}

		return true; // Section meets all the filtering requirements
	}

	public void updateFilteredSectionTable(Instructor instructor) {
		FilteredList<SectionRow> filteredSectionRows = new FilteredList<>(getSectionRows(instructor));

		filteredSectionRows.setPredicate(sectionRow -> meetsFilterRequirements(sectionRow, instructor));

		sectionTable.setItems(filteredSectionRows);
	}

	public void displaySectionsWithinPeriod(Instructor instructor, int dayIndex, int periodIndex) {
		FilteredList<SectionRow> filteredSectionRows = new FilteredList<>(getSectionRows(instructor));

// When I take the input day, I must also check for the associated days of the section being available afterwards
		filteredSectionRows.setPredicate(sectionRow -> {
			if (sectionRow.getSection().getClassTime() == null
					&& sectionRow.getSection().getMeetingDaysSet().isEmpty()) {
// Currently data only has instances of both vars == null, so this is okay
				return true;
			}
// Validation for if Instructor is not available at the selected day and period of button press -> Shouldn't be possible to do
			if (!instructor.getAvailability()[dayIndex][periodIndex]) {
				return false;
			}
			Set<DayOfWeek> sectionDays = sectionRow.getSection().getClassTime().getDays();
// Get the meeting DayOfWeeks and Occupied Periods for the section
//			if (sectionRow.getSection().getAlternateClassTime() != null) {
//				sectionDays.addAll(sectionRow.getSection().getAlternateClassTime().getDays());
//			}

// Get Class Times of all possible days
//			sectionRow.getSection().getClassTime().getDays();

			// Check if the selected day is one of the days when the section meets
			DayOfWeek selectedDayGUI = DayOfWeek.getByIndex(dayIndex); // Selected Day in GUI
			if (!sectionDays.contains(selectedDayGUI)) {
				return false;
			}

			// Check if the selected period overlaps with any occupied periods of the class
			// Does this check for differing class times? not yet
			List<Period> occupiedPeriods = getOccupiedPeriods(sectionRow.getSection().getClassTime());
			for (DayOfWeek day : sectionDays) {
				for (Period period : occupiedPeriods) {
					if (!instructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]) {
						System.out.println("Instructor not available at : "
								+ instructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]);
						return false;
					}
				}
			}

			// Filter Section by Alternate Meeting Day Class Times
			if (sectionRow.getSection().getAlternateClassTime() != null) {
				Set<DayOfWeek> sectionDaysAlt = sectionRow.getSection().getAlternateClassTime().getDays();
				List<Period> occupiedPeriodsAlt = getOccupiedPeriods(sectionRow.getSection().getAlternateClassTime());

				for (DayOfWeek day : sectionDaysAlt) {
					for (Period period : occupiedPeriodsAlt) {
						if (!instructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]) {
							System.out.println("Instructor not available at : "
									+ instructor.getAvailability()[day.getDayIndex()][period.getPeriodIndex()]);
							return false;
						}
					}
				}
			}

			// getClassTime + if getAlternateClassTime != null, check that too

			// Check if the instructor is available on that day and period
			// if instructor !available for all days and periods spanned in course, return
			// false
			return true; // Section meets the criteria
		});

		// Update the sectionTable with the filtered data
		sectionTable.setItems(filteredSectionRows);
	}

	public static List<Period> getOccupiedPeriods(TimeRange sectionTimeRange) {
		List<Period> occupiedPeriods = new ArrayList<>();

		for (Period period : Period.values()) {
			if (doTimeRangesOverlap(sectionTimeRange, period.getTimeRange())) {
				occupiedPeriods.add(period);
			}
		}
		return occupiedPeriods;
	}

	public static boolean doTimeRangesOverlap(TimeRange sectionTimeRange, TimeRange periodTimeRange) {
		return sectionTimeRange.getStart().isBefore(periodTimeRange.getEnd())
				&& periodTimeRange.getStart().isBefore(sectionTimeRange.getEnd());
	}

	public HBox instructorTableDisplay() {
		// Create a TableView of InstructorRow objects
		TableView<InstructorRow> instructorTable = new TableView<>();
		Button displayButton = new Button("Display");
		displayButton.setPrefSize(100, 30);

		TableColumn<InstructorRow, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		TableColumn<InstructorRow, String> idColumn = new TableColumn<>("ID Number");
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

		instructorTable.getColumns().add(nameColumn);
		instructorTable.getColumns().add(idColumn);

		// Create a FilteredList to filter the data
		FilteredList<InstructorRow> filteredData = new FilteredList<>(getInstructorRows(), p -> true);
		instructorTable.setItems(filteredData);

		// Create a TextField for filtering
		TextField filterField = new TextField();
		filterField.setPromptText("Filter Instructors by Name");
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(instructorRow -> {
				if (newValue == null || newValue.isEmpty()) {
					return true; // Show all rows if the filter text is empty
				}

				String filterText = newValue.toLowerCase();
				return instructorRow.getName().toLowerCase().contains(filterText);
			});
		});
		// Display selected Instructor
		displayButton.setOnAction(event -> {
			// Access the selected InstructorRow using
			// instructorTable.getSelectionModel().getSelectedItem()
			InstructorRow selectedRow = instructorTable.getSelectionModel().getSelectedItem();
			if (selectedRow != null) {
				System.out.println("Selected Instructor: " + selectedRow.getName());
				currentInstructorID = selectedRow.getID();
				displayInstructor(InstructorSet.search(selectedRow.getID()));
				// maybe if i define these as class variables i can make this work
				// displayInstructor(seniorInstructor, instructorLabels, instructorGrid);
				// Might need to change callCount to index of Instructor
			} else {
				System.out.println("No Instructor selected.");
			}
		});

		// Create a VBox to hold the table and filter field
		VBox instructorTableVbox = new VBox();
		instructorTableVbox.setSpacing(10);
		VBox.setVgrow(instructorTable, javafx.scene.layout.Priority.ALWAYS);
		instructorTableVbox.setAlignment(Pos.TOP_CENTER);
		instructorTableVbox.getChildren().addAll(new Label(""), filterField, instructorTable, displayButton,
				new Label(""));

		HBox instructorTableWrapper = new HBox();
		instructorTableWrapper.setSpacing(10);
		instructorTableWrapper.getChildren().addAll(new Label(""), instructorTableVbox);

		return instructorTableWrapper;
	}

	public static ObservableList<InstructorRow> getInstructorRows() {
		ObservableList<InstructorRow> instructorRows = FXCollections.observableArrayList();
		for (String id : InstructorSet.getSeniority()) {
			Instructor currentInstructor = InstructorSet.search(id);
			instructorRows.add(currentInstructor.toInstructorRow());
		}
		return instructorRows;
	}

	// whys it get CRUNCHY
	public void displaySeniorInstructor() {
		if (InstructorSet.getSeniority().isEmpty()) {
			return;
		}

		if (!seniorityCreated) {
			seniorityList = populateSeniorityList();
			seniorityCreated = true;
		}

		Instructor seniorInstructor = seniorityList.get(instructorIdx);
		if (seniorInstructor == null) {
			return;
		}
//		if (seniorInstructor.getAssignedSections()[2] != null) {
//			seniorInstructor = seniorityList.get(++instructorIdx);
//		}
//		currentInstructor = seniorInstructor.getId();
		displayInstructor(seniorInstructor);

		if (instructorIdx == seniorityList.size() - 1) {
// Reset callCount, introduce new Counter that when == 3, loop ends for three loops
			ArrayList<Instructor> unassignedFirstPass = new ArrayList<>();
			for (Instructor instructor : seniorityList) {
				if (instructor.getAssignedSections()[0] == null) {
					unassignedFirstPass.add(instructor);
				}
			}
			if (!unassignedFirstPass.isEmpty()) {
				seniorityList = unassignedFirstPass;
				instructorIdx = 0;
				return;
			}

			if (secondRequestsDone && !thirdRequestsDone) {
				ArrayList<Instructor> unassignedSecondPass = new ArrayList<>();
				for (Instructor instructor : seniorityList) {
					if (instructor.isRequestSecondCourse() && instructor.getAssignedSections()[1] == null) {
						unassignedSecondPass.add(instructor);
					}
				}
				if (!unassignedSecondPass.isEmpty()) {
					seniorityList = unassignedSecondPass;
					instructorIdx = 0;
					return;
				}
			}

			if (thirdRequestsDone) {
				ArrayList<Instructor> unassignedThirdPass = new ArrayList<>();
				for (Instructor instructor : seniorityList) {
					if (instructor.isRequestSecondCourse() && instructor.getAssignedSections()[2] == null) {
						unassignedThirdPass.add(instructor);
					}
				}
				if (!unassignedThirdPass.isEmpty()) {
					seniorityList = unassignedThirdPass;
					instructorIdx = 0;
					return;
				}
			}

			instructorIdx = 0; // Need to recreate seniority list with tightened parameters
			if (seniorityCreated) { // Can probably remove this var for (unassignedFirstPass.isEmpty()) {
				seniorityList = additionalCourseRequests();
			}
			System.out.println("Call ct == " + instructorIdx);
			System.out.println("Seniority size == " + seniorityList.size());
			return;
		}

		instructorIdx++;
	}

	public ArrayList<Instructor> additionalCourseRequests() {
		ArrayList<Instructor> seniority = new ArrayList<Instructor>();
		if (!secondRequestsDone) {
			for (String id : InstructorSet.getSeniority()) {
				// do i need to validate for null instructor here? Maybe i want to use optional
				Instructor currentInstructor = InstructorSet.search(id);
				if (currentInstructor != null && currentInstructor.isRequestSecondCourse()) {
					seniority.add(currentInstructor);
				}
			}
			secondRequestsDone = true;
			return seniority;
		}
		// This is where i can incorporate checking for a third pass, by else if
		// (secondRequestsDoen) {
		else if (secondRequestsDone && !thirdRequestsDone) { // Third Requests
			for (String id : InstructorSet.getSeniority()) {
				// do i need to validate for null instructor here? Controller should be called
				// in all of these instructor calls
				Instructor currentInstructor = InstructorSet.search(id);
				if (currentInstructor != null && currentInstructor.isRequestThirdCourse()) {
					seniority.add(currentInstructor);
				}
			}
			thirdRequestsDone = true;
			return seniority;
		} else { // everything is done, end loop, print
			System.out.println("Assignment Loop Completed!");
			String outputFileName = "completed_assignment_output";
			writeAssignmentOutput(outputFileName);
			return seniority; // What do I want to do here? Just print output and end program?
		}
	}

	public static void writeAssignmentOutput(String filename) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
			for (Instructor instructor : InstructorSet.getSet()) {
				writer.println("Instructor : " + instructor.getName().getFullName());
				writer.println("Instructor ID: " + instructor.getId());
				writer.println("Assigned Sections:");
				for (Section assignedSection : instructor.getAssignedSections()) {
					if (assignedSection != null) {
//						writer.println("- " + assignedSection.toString()+ "\n");
						writer.println("- CRN : " + assignedSection.getCrn() + "\n");
					}
				}
				writer.println("Total Assigned Credits : " + instructor.getTotalCredits());
				writer.println(); // Add a line break to separate entries
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Instructor> populateSeniorityList() {
		ArrayList<Instructor> seniority = new ArrayList<Instructor>();
		for (String CRN : InstructorSet.getSeniority()) {
			seniority.add(InstructorSet.search(CRN));
		}
		return seniority;
	}

	private void updateDisplayedInstructor(Instructor instructor) {
		currentInstructorID = instructor.getId();
		updateFilteredSectionTable(instructor);
	}

	public void displayInstructor(Instructor foundInstructor) {
		if (foundInstructor != null) {
			updateDisplayedInstructor(foundInstructor);
			instructorLabels[0].setText(foundInstructor.getId());
			instructorLabels[1].setText(foundInstructor.getName().getFullName());
			instructorLabels[2].setText(foundInstructor.getRank());
			instructorLabels[3].setText(foundInstructor.getHomeCampus());
			instructorLabels[4].setText(foundInstructor.getPreferredCampuses().toString());
			instructorLabels[5].setText(onlineCertification(foundInstructor.isCertifiedOnline()));
			instructorLabels[6].setText(isRequesting2nd3rd(foundInstructor.isRequestSecondCourse(),
					foundInstructor.isRequestThirdCourse()));
			instructorLabels[7].setText(foundInstructor.getCertifiedCourses().get(0));

			GridPane availGrid = createAvailabilityGrid(foundInstructor);
			HBox availabilityHBox = new HBox();
			availabilityHBox.setAlignment(Pos.CENTER);
			availabilityHBox.getChildren().addAll(availGrid);
			instructorGrid.add(availabilityHBox, 0, 5);
		} else {
			System.out.println("Instructor not found");
		}
	}

	private String onlineCertification(Boolean onlineCert) {
		if (onlineCert) {
			return "Yes";
		} else {
			return "No";
		}
	}

	private String isRequesting2nd3rd(Boolean requestsSecondCourse, Boolean requestsThirdCourse) {
		if (requestsSecondCourse) {
			if (requestsThirdCourse) {
				return "Y Y";
			}
			return "Y N";

		} else {
			return "N N";
		}
	}

	public GridPane instructorPane(Label[] instructorLabels) {
		GridPane instructorPane = new GridPane();
		instructorPane.setHgap(10); // Horizontal gap between columns
		instructorPane.setVgap(10); // Vertical gap between rows
		instructorPane.setAlignment(Pos.CENTER);

		Label[] headerLabels = { new Label("ID"), new Label("Name"), new Label("Rank"), new Label("Home Campus"),
				new Label("Pref Campus"), new Label("Online Cert"), new Label("2nd/3rd"),
				new Label("Certified Courses") };

		for (int i = 0; i < headerLabels.length; i++) {
			GridPane.setConstraints(headerLabels[i], i, 0); // Place header labels in the first row
			instructorPane.getChildren().add(headerLabels[i]);
		}
		// Labels that change to display searched instructor
		// {Indices: ID[0], Name[1], Rank[2], Home Campus[3], Preferred Campus [4],
		// Certified Course List[5], Online Certified [6], 2nd/3rd Request[7] };
		for (int i = 0; i < instructorLabels.length; i++) {
			GridPane.setConstraints(instructorLabels[i], i, 1); // Place instructor labels in the second row
			instructorPane.getChildren().add(instructorLabels[i]);
		}
		return instructorPane;
	}

	// Pass instructors here through search function
	private GridPane createAvailabilityGrid(Instructor foundInstructor) {
		boolean[][] availabilityData = foundInstructor.getAvailability();
		GridPane availGrid = new GridPane();
		availGrid.getChildren().clear();
		availGrid.setHgap(10);
		availGrid.setVgap(10);
		availGrid.setPadding(new Insets(20));

		String availableGreen = "-fx-background-color: green;";
		String unavailableRed = "-fx-background-color: red;";
//		String assignedGold = "-fx-background-color: gold;";

		Label titleLabel = new Label("Weekly Availability");
		GridPane.setConstraints(titleLabel, 4, 0);
		GridPane.setColumnSpan(titleLabel, 7);
		availGrid.getChildren().add(titleLabel);

		String[] timeSlots = { "", "7-8AM", "8-12PM", "12-3PM", "3-4PM", "4-6PM", "6-10PM" };
		Label[] periodLabels = new Label[timeSlots.length];
		// Sat + Sunday = one big button

		for (int i = 0; i < timeSlots.length; i++) {
			periodLabels[i] = new Label(timeSlots[i]);
			GridPane.setConstraints(periodLabels[i], i + 1, 1);
			availGrid.getChildren().add(periodLabels[i]);

			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setHgrow(javafx.scene.layout.Priority.ALWAYS);
			availGrid.getColumnConstraints().add(columnConstraints);
		}

		// Could maybe simplify this with enums with associated int day values
		String[] daysOfWeek = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
		for (int day = 0; day < daysOfWeek.length; day++) {
			Label dayLabel = new Label(daysOfWeek[day]);
			dayLabel.setBackground(
					new Background(new BackgroundFill(Color.LAVENDERBLUSH, CornerRadii.EMPTY, Insets.EMPTY)));
			GridPane.setConstraints(dayLabel, 1, day + 2);
			availGrid.getChildren().add(dayLabel);

			for (int period = 0; period < timeSlots.length - 1; period++) { // Exclude the last time slot indices
				Button availBtn = new Button(" ");

				// Set button styles based on availabilityData
				if (availabilityData[day][period]) {
					availBtn.setStyle(availableGreen);
					// Capture the row and column values for each button
					final int buttonRow = day;
					final int buttonCol = period;

					availBtn.setOnAction(event -> {
						System.out.println("Button at row " + buttonRow + ", column " + buttonCol + " clicked.");
						displaySectionsWithinPeriod(foundInstructor, buttonRow, buttonCol);

						// Define class parameter of courses available in this time filter
						// I already have exact entries for my boolean[][], now just add time and do
						// changes
						// Can use buttonRow and buttonCol to access correct button/data.
						// functionality for green (available) buttons here.
					});
				} else {
					availBtn.setStyle(unavailableRed);
				}
//				if (availBtn.getStyle().equals(availableGreen)) {
//					// button functionality for sections
//				}

				GridPane.setConstraints(availBtn, period + 2, day + 2);
				availBtn.prefWidthProperty().bind(periodLabels[1].widthProperty());
				availGrid.getChildren().add(availBtn);
			}
		}

		return availGrid;
	}

	// Method to create the menu bar and file open functionality
	private VBox createMenuBar(Stage primaryStage, BorderPane root) {
		VBox menuVbox = new VBox();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem openInstructorFile = new MenuItem("Upload Instructor Data");
		MenuItem openSectionFile = new MenuItem("Upload Course Data");
		MenuItem saveAssignments = new MenuItem("Save Instructor Assignments");

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		File initialDirectory = new File("C:\\Users\\selin\\eclipse-workspace1\\Saracoglu_Project_1");
		fileChooser.setInitialDirectory(initialDirectory);

		openInstructorFile.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			// Need to create validation for what kind of file is uploaded, they are all
			// excel files, but instructors vs freq vs cours
			if (selectedFile != null) {
				seniorityCreated = false; // incase new data is entered, reset
				ImportInstructors.importInstructors(selectedFile.getAbsolutePath());
				System.out.println("Selected Excel file: " + selectedFile.getAbsolutePath());
				HBox updatedTable = instructorTableDisplay();
				root.setLeft(updatedTable);
				displaySeniorInstructor();
				// Immediately serialize the updated InstructorSet
		        DataPersistence.serializeInstructorSet(InstructorSet.getInstructorSet());
			}
		});

		openSectionFile.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				System.out.println("Selected Excel file: " + selectedFile.getAbsolutePath());
				ImportCourses.importCourses(selectedFile.getAbsolutePath());

				HBox updatedTable = sectionTableDisplay();
				root.setRight(updatedTable);
				//Serialize the Course Set
				DataPersistence.serializeCourseSet(CourseSet.getCourseSet());
			}
		});

		saveAssignments.setOnAction(e -> {
			// Show the FileChooser and get the selected file
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				writeAssignmentOutput(file.getPath() + ".txt");
//				DataPersistence.serializeInstructorSet(InstructorSet.getInstructorSet());
//				DataPersistence.serializeCourseSet(CourseSet.getCourseSet());
			}
		});

		fileMenu.getItems().addAll(openInstructorFile, openSectionFile, saveAssignments);
		menuBar.getMenus().add(fileMenu);

		menuVbox.getChildren().add(menuBar);

		return menuVbox;
	}

}
