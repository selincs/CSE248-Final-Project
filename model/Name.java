package model;

import java.util.Objects;

public class Name {
	private String firstName;
	private String lastName;
	private char middleInitial;

	public Name(String lastName, String firstName, char middleInitial) {
		super();
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
	}

	public Name(String lastName, String firstName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Name() {
		super();
	}

	public String getFullName() {
		if (this.middleInitial == '\0') {
			return lastName + ", " + firstName;
		} else {
			return lastName + ", " + firstName + " " + middleInitial;
		}
	}
	/*
	 * 		if (this.middleInitial == '\0') {
			return firstName + " " + lastName;
		} else {
			return firstName + " " + middleInitial + " " + lastName;
		}
	}
*/

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public char getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(char middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "[firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName=" + lastName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Name name = (Name) obj;
		return name.firstName.equalsIgnoreCase(this.firstName);
	}

}
