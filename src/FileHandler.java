import java.io.*;
import java.util.ArrayList;

/**
 * Handles saving and loading grade reports to/from a file.
 */
public class FileHandler {

    /**
     * Saves the list of reports to a file.
     * @param fileName - The name of the file.
     * @param reports - The list of grade reports to save.
     */
    public static void saveReports(String fileName, ArrayList<GradeReport> reports) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(reports); // Serialize the reports list
        } catch (IOException e) {
            System.out.println("Error saving reports: " + e.getMessage());
        }
    }

    /**
     * Loads the list of reports from a file.
     * @param fileName - The name of the file.
     * @return The list of grade reports, or an empty list if the file doesn't exist.
     */
    public static ArrayList<GradeReport> loadReports(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Explicitly cast the object as an ArrayList of GradeReport
            @SuppressWarnings("unchecked")
            ArrayList<GradeReport> reports = (ArrayList<GradeReport>) ois.readObject();
            return reports;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading reports: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
