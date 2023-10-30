package demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SectionDemo extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	//Dont need meeting days or class times -> These just become filters based on selected buttons
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Section Demo");

		TableView<SectionRow> sectionTable = new TableView<>();
		sectionTable.setItems(getSampleSections());

		TableColumn<SectionRow, String> titleColumn = new TableColumn<>("Section Title");
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

		TableColumn<SectionRow, String> crnColumn = new TableColumn<>("Section CRN");
		crnColumn.setCellValueFactory(cellData -> cellData.getValue().crnProperty());

		TableColumn<SectionRow, String> meetingDaysColumn = new TableColumn<>("Section Meeting Days");
		meetingDaysColumn.setCellValueFactory(cellData -> cellData.getValue().meetingDaysProperty());

		TableColumn<SectionRow, String> classTimesColumn = new TableColumn<>("Section Class Times");
		classTimesColumn.setCellValueFactory(cellData -> cellData.getValue().classTimesProperty());

		sectionTable.getColumns().addAll(titleColumn, crnColumn, meetingDaysColumn, classTimesColumn);

		VBox vbox = new VBox(sectionTable);
		Scene scene = new Scene(vbox, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public ObservableList<SectionRow> getSampleSections() {
		ObservableList<SectionRow> sectionRows = FXCollections.observableArrayList();
		sectionRows.add(new SectionRow("Section A", "CRN123", "MWF", "9:00 AM - 10:30 AM"));
		sectionRows.add(new SectionRow("Section B", "CRN456", "TTh", "1:00 PM - 2:30 PM"));
		// Add more sample sections as needed
		return sectionRows;
	}

}
