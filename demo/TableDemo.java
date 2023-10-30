package demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableDemo extends Application {

	//Can be used to display Instructor name + ID
	
	@Override
    public void start(Stage stage) {
        TableView<Person> tableView = new TableView<>();
        
        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageObservableValue());

        tableView.getColumns().addAll(nameColumn, ageColumn);

        // Create and filter your list of objects (e.g., Person objects)
        ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Alice", 30),
            new Person("Bob", 25),
            new Person("Charlie", 35)
        );
        
        // Apply filtering conditions to data
        // For example, filter to show only people older than 30
        ObservableList<Person> filteredData = data.filtered(person -> person.getAge() >= 30);
        //Used to display instructors who do not have full courseLoads yet

        tableView.setItems(filteredData);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Filtered TableView Example");
        stage.show();
    }


	public static void main(String[] args) {
		launch(args);
	}

}
