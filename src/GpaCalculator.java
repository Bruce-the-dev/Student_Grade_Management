import Exceptions.GpaErrorException;
import Exceptions.StudentNotFoundException;

import java.util.Arrays;

public class GpaCalculator {
    private GradeManager gradeManager;

    public GpaCalculator(GradeManager gradeManager) {
        this.gradeManager = gradeManager;
    }

    // 1. Convert % grade → GPA scale
    public double convertToGPA(double percentage) {
        if (percentage < 0 || percentage > 100)
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        if (percentage >= 93) return 4.0;
        if (percentage >= 90) return 3.7;
        if (percentage >= 87) return 3.3;
        if (percentage >= 83) return 3.0;
        if (percentage >= 80) return 2.7;
        if (percentage >= 77) return 2.3;
        if (percentage >= 73) return 2.0;
        if (percentage >= 70) return 1.7;
        if (percentage >= 67) return 1.3;
        if (percentage >= 60) return 1.0;
        return 0.0;
    }



    // 3. Convert GPA → letter grade (A, B, C...)
    public String getLetterGrade(double gpa) {
        if (gpa < 0 || gpa > 4) throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        if (gpa == 4.0) return "A";
        if (gpa >= 3.7) return "A-";
        if (gpa >= 3.3) return "B+";
        if (gpa >= 3.0) return "B";
        if (gpa >= 2.7) return "B-";
        if (gpa >= 2.3) return "C+";
        if (gpa >= 2.0) return "C";
        if (gpa >= 1.7) return "C-";
        if (gpa >= 1.3) return "D+";
        if (gpa >= 1.0) return "D";
        return "F";
    }

    // Method to calculate GPA for a student and return it
    public double calculateGPA(String studentId) throws GpaErrorException {
        Grade[] grades = gradeManager.getGradesForStudent(studentId);
        if (grades.length == 0) {
            throw new GpaErrorException("The student has no grades");
        }

        double totalGpa = 0;
        for (Grade g : grades) {
            totalGpa += convertToGPA(g.getGrade());
        }

        return totalGpa / grades.length;
    }

    // Method to display GPA report
    public void displayGPAReport(String studentId) throws GpaErrorException {
        Grade[] grades = gradeManager.getGradesForStudent(studentId);
        if (grades.length == 0) {
            throw new GpaErrorException("The student has no grades");
        }

        System.out.println("\nGPA REPORT");
        System.out.println("-----------------------------------------------");
        System.out.printf("%-15s | %-7s | %-10s%n", "Subject", "Grade", "GPA Points");
        System.out.println("-----------------------------------------------");

        for (Grade g : grades) {
            double gpa = convertToGPA(g.getGrade());
            System.out.printf("%-15s | %-7s | %.1f (%s)%n",
                    g.getSubject().getSubjectName(),
                    String.format("%.0f%%", g.getGrade()),
                    gpa,
                    getLetterGrade(gpa)
            );
        }

        // Use the calculateGPA() method to get the final GPA
        double finalGpa = calculateGPA(studentId);

        System.out.println("-------------------------------------------------------------");
        System.out.printf("GPA: %.2f%n", finalGpa);
        System.out.println("Letter Grade: " + getLetterGrade(finalGpa));
    }

    public int getRankInClass(String studentId, StudentManager studentManager)
            throws GpaErrorException, StudentNotFoundException {

        int totalStudents = studentManager.getStudentCount();

        // Array of student GPAs
        double[] gpas = new double[totalStudents];
        double targetGpa = -1;

        for (int i = 0; i < totalStudents; i++) {
            Student s = studentManager.getStudentByIndex(i);

            double avg = gradeManager.calculateOverallAverage(s.getStudentId());
            double gpa = avg > 0 ? convertToGPA(avg) : 0.0;

            gpas[i] = gpa;

            if (s.getStudentId().equals(studentId)) {
                targetGpa = gpa;
            }
        }

        if (targetGpa < 0) {
            throw new StudentNotFoundException("Student ID not found: " + studentId);
        }

        Arrays.sort(gpas);
        // Reverse order
        for (int i = 0; i < gpas.length / 2; i++) {
            double tmp = gpas[i];
            gpas[i] = gpas[gpas.length - 1 - i];
            gpas[gpas.length - 1 - i] = tmp;
        }

        for (int i = 0; i < gpas.length; i++) {
            if (gpas[i] == targetGpa) {
                return i + 1;
            }
        }
        throw new IllegalStateException("Target GPA not found in GPA list.");
    }

}
