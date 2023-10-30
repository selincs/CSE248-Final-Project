package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import model.Course;
import model.CourseSet;
import model.Instructor;
import model.InstructorSet;

public class DataPersistence {
	
    public static void serializeInstructorSet(InstructorSet instructorSet) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("instructorSet.data"))) {
            out.writeObject(instructorSet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {

		}
    }

    public static InstructorSet deserializeInstructorSet() {
        InstructorSet instructorSet = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("instructorSet.data"))) {
            instructorSet = (InstructorSet) in.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {

		}
        return instructorSet;
    }

    public static void serializeCourseSet(CourseSet courseSet) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("courseSet.data"))) {
            out.writeObject(courseSet);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {

		}
    }

    public static CourseSet deserializeCourseSet() {
        CourseSet courseSet = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("courseSet.data"))) {
            courseSet = (CourseSet) in.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {

		}
        return courseSet;
    }
}
