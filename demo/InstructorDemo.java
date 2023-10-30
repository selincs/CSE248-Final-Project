package demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InstructorDemo extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Instructor Table Demo");

		// TableView<InstructorRow> instructorTable =
		// InstructorRow.createInstructorTable();

		// Create a TableView of InstructorRow objects
		TableView<InstructorRow> instructorTable = new TableView<>();

		TableColumn<InstructorRow, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		TableColumn<InstructorRow, String> idColumn = new TableColumn<>("ID Number");
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

		instructorTable.getColumns().add(nameColumn);
		instructorTable.getColumns().add(idColumn);


		// Create a FilteredList to filter the data
		FilteredList<InstructorRow> filteredData = new FilteredList<>(getSampleInstructorRows(),
				p -> true);
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
	
		// Create a VBox to hold the table and filter field
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.getChildren().addAll(new Label("Instructor Table"), filterField, instructorTable);

		Scene scene = new Scene(vbox, 400, 400);
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static ObservableList<InstructorRow> getSampleInstructorRows() {
		ObservableList<InstructorRow> instructorRows = FXCollections.observableArrayList();
		instructorRows.add(new InstructorRow("John Doe", "00000000"));
		instructorRows.add(new InstructorRow("Jane Smith", "00000002"));
		// Add more rows as needed
		return instructorRows;
	}

}
