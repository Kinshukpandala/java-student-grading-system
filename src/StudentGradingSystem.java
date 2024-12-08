/**
 * Please Refer to the README.md
 * Name: Kinshuk Pandala
 * Reg No: 23BAC10026
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class that drives the Student Grading System.
 * Combines functionalities for managing students, grades, and file operations.
 */
public class StudentGradingSystem {

    public static void main(String[] args) {
        // Initialize Scanner for user input and load any existing grade reports.
        Scanner scanner = new Scanner(System.in);
        ArrayList<GradeReport> reports = FileHandler.loadReports("GradeReports.ser");

        // Display system title
        System.out.println("=================================");
        System.out.println("      STUDENT GRADING SYSTEM     ");
        System.out.println("=================================");

        // Main menu loop
        while (true) {
            System.out.println("\nMenu:");
            System.out.println();
            System.out.println("[1] Add Student Report");
            System.out.println("[2] View All Reports");
            System.out.println("[3] Delete Student Report");
            System.out.println("[4] Save and Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> addStudentReport(scanner, reports); // Option to add a new report
                case 2 -> viewAllReports(reports);            // Option to view all reports
                case 3 -> deleteStudentReport(scanner, reports); // Option to delete a report
                case 4 -> {
                    FileHandler.saveReports("GradeReports.ser", reports); // Save reports to file
                    System.out.println("Reports saved. Exiting...");
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Converts letter grades to GPA points.
     * S = 10, A = 9, B = 8, C = 7, D = 6, E = 5, F = 0
     * @param grade Letter grade to be converted.
     * @return Corresponding GPA points.
     */
    private static int convertGradeToPoints(String grade) {
        switch (grade.toUpperCase()) {
            case "S": return 10;
            case "A": return 9;
            case "B": return 8;
            case "C": return 7;
            case "D": return 6;
            case "E": return 5;
            case "F": return 0;
            default: throw new IllegalArgumentException("Invalid grade. Please enter a valid letter grade (S, A, B, C, D, E, F).");
        }
    }

    /**
     * Adds a new student report to the list of reports.
     * scanner - Scanner object for user input.
     * reports - List of existing grade reports.
     */
    private static void addStudentReport(Scanner scanner, ArrayList<GradeReport> reports) {
        // Display the note for the user
        System.out.println("----------");
        System.out.println("Note: Enter the student's name and ID, then add subjects and their respective grades.");
        System.out.println("Note: While Entering the Grades be sure to select letters among {S,A,B,C,D,E,F} where S is the highest and F is the least");
        System.out.println("----------");

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        // Check for duplicate student entry
        for (GradeReport report : reports) {
            if (report.getStudent().getName().equalsIgnoreCase(name) || report.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
                System.out.println("Student with this name or ID already exists.");
                return;
            }
        }

        Student student = new Student(name, studentId);
        GradeReport report = new GradeReport(student);

        // Ask user how many subjects they want to add (between 1 and 7)
        int numSubjects = 0;
        while (numSubjects < 1 || numSubjects > 7) {
            System.out.print("How many subjects do you want to add? (1 to 7): ");
            numSubjects = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            if (numSubjects < 1 || numSubjects > 7) {
                System.out.println("Invalid number. Please enter a number between 1 and 7.");
            }
        }

        // Collect grades for the specified number of subjects
        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Enter Subject " + i + " Name: ");
            String subjectName = scanner.nextLine();
            System.out.print("Enter Grade for " + subjectName + ":");
            String grade = scanner.nextLine().toUpperCase();

            try {
                int points = convertGradeToPoints(grade); // Convert grade to points
                report.addGrade(subjectName, points); // Add grade (as points) for the subject
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid grade: " + e.getMessage());
            }
        }

        reports.add(report); // Add the report to the list
        System.out.println("Report added successfully!");
    }

    /**
     * Displays all grade reports in the system in a tabular format.
     * reports - List of grade reports to display.
     */
    private static void viewAllReports(ArrayList<GradeReport> reports) {
        if (reports.isEmpty()) {
            System.out.println("No reports available.");
        } else {
            System.out.println("\n=================================");
            System.out.println("        ALL STUDENT REPORTS");
            System.out.println("=================================");
            int serialNo = 1;
            for (GradeReport report : reports) {
                System.out.println("Serial No: " + serialNo++);
                System.out.println(report.getStudent()); // Display student info
                System.out.println("=================================");
                System.out.printf("%-20s %-10s\n", "Subject", "Grade");
                System.out.println("---------------------------------");
                for (String subject : report.getGrades().keySet()) {
                    int gradePoints = report.getGrades().get(subject);
                    System.out.printf("%-20s %-10s\n", subject, gradePoints);
                }
                System.out.println("=================================");
                System.out.println("GPA: " + String.format("%.2f", report.calculateGPA())); // Display GPA
                System.out.println("\n=================================");
            }
        }
    }

    /**
     * Deletes a student report based on their admission number.
     * scanner - Scanner object for user input.
     * reports - List of grade reports to modify.
     */
    private static void deleteStudentReport(Scanner scanner, ArrayList<GradeReport> reports) {
        System.out.print("Enter Student ID to delete: ");
        String studentId = scanner.nextLine();

        for (GradeReport report : reports) {
            if (report.getStudent().getStudentId().equals(studentId)) {
                reports.remove(report);
                System.out.println("Student report deleted successfully.");
                return;
            }
        }

        System.out.println("Student with this ID not found.");
    }
}
