package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeSet;

import utils.DataPersistence;

public class InstructorSet implements Serializable {
	// lazy singleton - not threadsafe
	private static final long serialVersionUID = 1L;
	private static TreeSet<Instructor> set;
	private static InstructorSet instructorSet;
	private static LinkedList<String> seniority; // for order of data preservation

	private InstructorSet() {
		set = new TreeSet<>();
		seniority = new LinkedList<String>();

	}

	// InstructorSet.getInstructorSet() -> works from anywhere
	// InstructorSet.getInstructorSet().add(instructor);
	// implement bill pugh synch
	public static synchronized InstructorSet getInstructorSet() {
		if (instructorSet == null) {
			instructorSet = DataPersistence.deserializeInstructorSet();
			if (instructorSet == null) {
				instructorSet = new InstructorSet();
			}
		}
		return instructorSet;
	}

	public void add(Instructor instructor) {
		if (instructor.getId() == null || instructor.getId().isEmpty()) {
			System.out.println("Invalid or empty Instructor ID");
			return;
		}
		set.add(instructor);
		seniority.add(instructor.getId());
	}

	public static Instructor search(String id) {
		// System.out.println("Number of Instructors: " + set.size());
		// System.out.println("Searching for ID: " + id);
		if (id == null || id.isEmpty()) {
			// throw new IllegalArgumentException("Invalid or empty Instructor ID");
			System.out.println("Invalid or empty Instructor ID");
			return null;
		}

		for (Instructor instructor : set) {
			if (instructor.getId().equals(id)) {
				// System.out.println("Checking Instructor ID: " + instructor.getId());
				// System.out.println("Instructor found: " +
				// instructor.getName().getFirstName());
				return instructor;
			}
		}
		System.out.println("Instructor not found");
		return null;
	}

	public static LinkedList<String> getSeniority() {
		return seniority;
	}

	public static TreeSet<Instructor> getSet() {
		return set;
	}

	// readResolve guarantees same hashCode
	public Object readResolve() {
		return instructorSet;
	}

}
