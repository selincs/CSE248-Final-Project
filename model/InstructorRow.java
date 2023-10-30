package model;

import javafx.beans.property.SimpleStringProperty;

public class InstructorRow {
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty ID = new SimpleStringProperty();

    public InstructorRow(String name, String id) {
        this.name.set(name);
        this.ID.set(id);
    }
    
    public InstructorRow(Instructor instructor) {
        this.name.set(instructor.getName().getFullName());
        this.ID.set(instructor.getId());
    }

	public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String id) {
        this.ID.set(id);
    }

    public SimpleStringProperty idProperty() {
        return ID;
    }
}
