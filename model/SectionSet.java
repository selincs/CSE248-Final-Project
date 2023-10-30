package model;

import java.util.TreeSet;

public class SectionSet {
	// This class holds every Section(Subjects classes) (MAT, CSE, etc)
	private TreeSet<Section> sections;

	public SectionSet() {
		sections = new TreeSet<Section>();
	}

	public void add(Section section) {
		sections.add(section);
	}

	public Section search(String crn) {
		if (crn == null || crn.isEmpty()) {
			// throw new IllegalArgumentException("Invalid or empty CRN");
			System.out.println("Invalid or empty CRN");
			return null;
		}

		for (Section section : sections) {
			if (section.getCrn().equals(crn)) {
				return section;
			}
		}
		return null;
	}

	public TreeSet<Section> getSections() {
		return sections;
	}

	public void displayUnstaffedSections() {
		for (Section section : sections) {
			if (section.getAssignedInstructor() == null) {
				System.out.println(section.toString() + " remains unassigned.");
			}
		}
	}

}
