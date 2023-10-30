package demo;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {
	private final StringProperty name = new SimpleStringProperty();
	private final IntegerProperty age = new SimpleIntegerProperty();

	public Person(String name, int age) {
		this.name.set(name);
		this.age.set(age);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public IntegerProperty ageProperty() {
		return age;
	}

	public ObservableValue<Integer> ageObservableValue() {
		return age.asObject();
	}

}
