import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a Grade Report for a Student, containing grades for multiple subjects.
 */
public class GradeReport implements Serializable {
    private Student student;
    private HashMap<String, Integer> grades; // Subject name as key, points as value

    public GradeReport(Student student) {
        this.student = student;
        this.grades = new HashMap<>();
    }

    /**
     * Adds a grade (as points) for a specific subject.
     * @param subjectName - The subject name.
     * @param points - The grade points (10, 9, 8, etc.).
     */
    public void addGrade(String subjectName, int points) {
        grades.put(subjectName, points);
    }

    /**
     * Calculates the GPA based on the points for all subjects.
     * @return The GPA (average of the points).
     */
    public double calculateGPA() {
        double totalPoints = 0;
        for (int points : grades.values()) {
            totalPoints += points;
        }
        return grades.isEmpty() ? 0 : totalPoints / grades.size();
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();
        report.append(student.toString()).append("\nGrades:\n");
        report.append("Subject\tGrade\n");
        grades.forEach((subject, points) -> report.append(subject).append("\t").append(points).append("\n"));
        report.append("GPA: ").append(String.format("%.2f", calculateGPA())); // GPA calculation
        return report.toString();
    }

    public Student getStudent() {
        return student;
    }

    public HashMap<String, Integer> getGrades() {
        return grades;
    }
}
