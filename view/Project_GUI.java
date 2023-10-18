package view;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Instructor;
import model.InstructorSet;
import utils.ImportInstructors;

public class Project_GUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// BorderPane -> 2nd Inner BorderPane -> Labels go in top of innerBP -> Searched
	// Instructor in Center of BP

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Used in searching and data storage
		InstructorSet.getInstructorSet();

		BorderPane root = new BorderPane();
		//Probably want to generate InstructorPane immediately

		HBox searchBox = new HBox(10);
		searchBox.setAlignment(Pos.CENTER);
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);

		Label[] instructorLabels = { new Label(), new Label(), new Label(), new Label(), new Label(), new Label(),
				new Label(), new Label() };
		// Eventually this portion a method to be able to display multiple instructors? -> Just the important one, auto display most senior
		GridPane instructorPane = instructorPane(instructorLabels);
//		GridPane instructorPane = new GridPane();
//		instructorPane.setHgap(10); // Horizontal gap between columns
//		instructorPane.setVgap(10); // Vertical gap between rows
//		instructorPane.setAlignment(Pos.CENTER);
//
//		Label[] headerLabels = { new Label("ID"), new Label("Name"), new Label("Rank"), new Label("Home Campus"),
//				new Label("Pref Campus"), new Label("Online Cert"), new Label("2nd/3rd"),
//				new Label("Certified Courses") };
//
//		for (int i = 0; i < headerLabels.length; i++) {
//			GridPane.setConstraints(headerLabels[i], i, 0); // Place header labels in the first row
//			instructorPane.getChildren().add(headerLabels[i]);
//		}

		//How can i pass these labels if i generate them in my pane
		// Labels that change to display searched instructor
		// {Indices: ID[0], Name[1], Rank[2], Home Campus[3], Preferred Campus [4],
		// Certified Course List[5], Online Certified [6], 2nd/3rd Request[7] };
//		Label[] instructorLabels = { new Label(), new Label(), new Label(), new Label(), new Label(), new Label(),
//				new Label(), new Label() };
//
//		for (int i = 0; i < instructorLabels.length; i++) {
//			GridPane.setConstraints(instructorLabels[i], i, 1); // Place instructor labels in the second row
//			instructorPane.getChildren().add(instructorLabels[i]);
//		}

		// Create a VBox layout
		VBox menuVbox = createMenuBar(primaryStage);
		// Add the MenuBar to the BorderPane
		root.setTop(menuVbox);

		TextField idField = new TextField();
		TextField nameField = new TextField();
		searchBox.getChildren().addAll(idField, nameField);

		// Button box
		Button searchBtn = new Button("Search");
		searchBtn.setPrefSize(100, 30);
		Button cancelBtn = new Button("Clear"); // Clear all fields button go here
		cancelBtn.setPrefSize(100, 30);
		buttonBox.setSpacing(60);
		buttonBox.getChildren().addAll(searchBtn, cancelBtn);

		// Make this a border pane with a center GridPane, set center to gridpane with
		// instructor
		// ported to it from search function, database? singleton?
		
		//Finish instructorPane() method
		GridPane instructorGrid = new GridPane();

		instructorGrid.setAlignment(Pos.CENTER);
		instructorGrid.setPadding(new Insets(20));
		instructorGrid.setHgap(10);
		instructorGrid.setVgap(10);
//		instructorGrid.setStyle("-fx-background-color:rgba(2, 48, 32, .7)");

		// Labels for Search area
		Label idSearchLbl = new Label("Search by ID");
		Label nameSearchLbl = new Label("Search by Name");

		HBox searchLblBox = new HBox();
		searchLblBox.setAlignment(Pos.CENTER);
		searchLblBox.setSpacing(80);
		searchLblBox.getChildren().addAll(idSearchLbl, nameSearchLbl);

		instructorGrid.add(searchLblBox, 0, 0);
		instructorGrid.add(searchBox, 0, 1);
		instructorGrid.add(buttonBox, 0, 2);
		instructorGrid.add(instructorPane, 0, 3);

		// Set center of BorderPane
		root.setCenter(instructorGrid);

		// name
		// rank
		// home camp
		// pref camp
		// online cert
		// cerrt to teach
		// 2nd/3rd req

//		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 400, 300);
//		Scene scene = new Scene(instructorPane());

		// call instructorPane and set Center to searched instructor
		// If no instructors are loaded account for that with a message
		// Maybe instead of a fileMenu I just load from search button if doesnt exist ->
		// or check database
		searchBtn.setOnAction(e -> {
			String name = nameField.getText().trim();
			String id = idField.getText().trim();
			System.out.println("Searching for instructor with Name: " + name + " and ID: " + id);
			Instructor foundInstructor = InstructorSet.search(id);

			if (/* name.isEmpty() && */ id.isEmpty()) {
				// Handle the case where both name and id fields are empty 
				// Display an error message or take appropriate action
				//Create warning label or error
				System.out.println("ID Field empty");
				return;
			}
			System.out.println("Search pressed");
			displayInstructor(foundInstructor, instructorLabels, instructorGrid); //Instructor, Label[], GridPane
		});

		cancelBtn.setOnAction(e -> {
			nameField.clear();
			idField.clear();
		});

		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//helper method to get instructorLabels and instructorGrid? getters?
	public void displaySeniorInstructor(Label[] instructorLabels, GridPane instructorGrid){
		Instructor seniorInstructor = InstructorSet.search(InstructorSet.getSeniority().getFirst()); //Get Most Senior Instructor
		displayInstructor(seniorInstructor, instructorLabels, instructorGrid);

	}

	public void displayInstructor(Instructor foundInstructor, Label[] instructorLabels, GridPane instructorGrid) {
		if (foundInstructor != null) {
			instructorLabels[0].setText(foundInstructor.getId());
			instructorLabels[1].setText(foundInstructor.getName().getFullName());
			instructorLabels[2].setText(foundInstructor.getRank());
			instructorLabels[3].setText(foundInstructor.getHomeCampus());
			instructorLabels[4].setText(foundInstructor.getPreferredCampuses().toString());
			instructorLabels[5].setText(onlineCertification(foundInstructor.isCertifiedOnline()));
			instructorLabels[6].setText(isRequesting2nd3rd(foundInstructor.isRequestSecondCourse(),
					foundInstructor.isRequestThirdCourse()));
			instructorLabels[7].setText(foundInstructor.getCertifiedCourses().toString());

			GridPane availGrid = createAvailabilityGrid(foundInstructor.getAvailability());
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

		//How can i pass these labels if i generate them in my pane -> Initialize labels outside pane
		// Labels that change to display searched instructor
		// {Indices: ID[0], Name[1], Rank[2], Home Campus[3], Preferred Campus [4],
		// Certified Course List[5], Online Certified [6], 2nd/3rd Request[7] };
//		Label[] instructorLabels = { new Label(), new Label(), new Label(), new Label(), new Label(), new Label(),
//				new Label(), new Label() };

		for (int i = 0; i < instructorLabels.length; i++) {
			GridPane.setConstraints(instructorLabels[i], i, 1); // Place instructor labels in the second row
			instructorPane.getChildren().add(instructorLabels[i]);
		}
		return instructorPane;
	}

	// Pass instructors here through search function
	private GridPane createAvailabilityGrid(boolean[][] availabilityData) {
		GridPane availGrid = new GridPane();
		availGrid.setHgap(10);
		availGrid.setVgap(10);
		availGrid.setPadding(new Insets(20));

		String availableGreen = "-fx-background-color: green;";
		String unavailableRed = "-fx-background-color: red;";

		Label titleLabel = new Label("Weekly Availability");
		GridPane.setConstraints(titleLabel, 4, 0);
		GridPane.setColumnSpan(titleLabel, 7);
		availGrid.getChildren().add(titleLabel);

		String[] timeSlots = { "", "7-8AM", "8-12PM", "12-3PM", "3-4PM", "4-6PM", "7-10PM" };
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

		String[] daysOfWeek = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
		for (int day = 0; day < daysOfWeek.length; day++) {
			Label dayLabel = new Label(daysOfWeek[day]);
			dayLabel.setBackground(
					new Background(new BackgroundFill(Color.LAVENDERBLUSH, CornerRadii.EMPTY, Insets.EMPTY)));
			GridPane.setConstraints(dayLabel, 1, day + 2);
			availGrid.getChildren().add(dayLabel);

			for (int period = 0; period < timeSlots.length - 1; period++) { // Exclude the last time slot
				Button availBtn = new Button(" ");

				// Set button styles based on availabilityData
				if (availabilityData[day][period]) {
					availBtn.setStyle(availableGreen);
				} else {
					availBtn.setStyle(unavailableRed);
				}

				GridPane.setConstraints(availBtn, period + 2, day + 2);
				availBtn.prefWidthProperty().bind(periodLabels[1].widthProperty());
				availGrid.getChildren().add(availBtn);
			}
		}

		return availGrid;
	}

	// Method to create the menu bar and file open functionality
	private VBox createMenuBar(Stage primaryStage) {
		VBox menuVbox = new VBox();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem openMenuItem = new MenuItem("Open Excel File");

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

		File initialDirectory = new File("C:\\Users\\selin\\eclipse-workspace1\\Saracoglu_Project_1");
		fileChooser.setInitialDirectory(initialDirectory);

		openMenuItem.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				ImportInstructors.importInstructors(selectedFile.getAbsolutePath());
				System.out.println("Selected Excel file: " + selectedFile.getAbsolutePath());
			}
		});

		fileMenu.getItems().add(openMenuItem);
		menuBar.getMenus().add(fileMenu);

		menuVbox.getChildren().add(menuBar);

		return menuVbox;
	}

	// Create a separate method to display the instructor details
	// Pass a gridpane here, maybe could display multiple instructors at once this
	// way?
//	private GridPane displayInstructor(Instructor instructor, GridPane instructorPane) {
////	    instructorID.setText(instructor.getId());
////	    instructorName.setText(instructor.getName().toString());
////	    instructorRank.setText(instructor.getRank());
////	    instructorHomeCampus.setText(instructor.getHomeCampus());
////	    instructorPrefCampus.setText(instructor.getPreferredCampuses().toString());
////
////	    if (instructor.isCertifiedOnline()) {
////	        instructorOnlineCert.setText("Yes");
////	    } else {
////	        instructorOnlineCert.setText("No");
////	    }
////
////	    if (instructor.isRequestSecondCourse()) {
////	        if (instructor.isRequestThirdCourse()) {
////	            instructor2nd3rdCourse.setText("Y Y");
////	        } else {
////	            instructor2nd3rdCourse.setText("Y N");
////	        }
////	    } else {
////	        instructor2nd3rdCourse.setText("N N");
////	    }
//		return instructorPane;
//	}

}
