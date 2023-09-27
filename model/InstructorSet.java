package model;

import java.util.TreeSet;

public class InstructorSet {
	// lazy singleton - not threadsafe
		private static TreeSet<Instructor> set;
		private static InstructorSet instructorSet;
		//linkedList() for order of data preservation
		
		private InstructorSet() {
			set = new TreeSet<>();
		}
		
		//InstructorSet.getInstructorSet -> works from anywhere
		//InstructorSet.getInstructorSet.add(instructor);
		public static synchronized InstructorSet getInstructorSet() {
			if (instructorSet == null) {
				instructorSet = new InstructorSet();
			}
			return instructorSet;
		}
		
		public void add(Instructor instructor) {
			set.add(instructor);
		}
		
		public static Instructor search(String id) {
			System.out.println("Number of Instructors: " + set.size());
		    System.out.println("Searching for ID: " + id);
		    if (id == null || id.isEmpty()) {
		        System.out.println("Invalid or empty ID");
		        return null;
		    }
		    
			for(Instructor instructor : set) {
				System.out.println("Checking Instructor ID: " + instructor.getId());
				if (instructor.getId().equals(id)) {
					System.out.println("Instructor found: " + instructor.getName().getFirstName());
					return instructor;
				}
			}
			   System.out.println("Instructor not found");
		return null;
		}

		public static TreeSet<Instructor> getSet() {
			return set;
		}
}
