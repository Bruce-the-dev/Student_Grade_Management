import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentReportGenerator {

    private GradeManager gradeManager;

    public StudentReportGenerator(GradeManager gradeManager) {
        this.gradeManager = gradeManager;
    }

    public void exportReport(Student student,String fileName) throws IOException {

        fileName = "src/Reports/"+fileName+".txt";
        try (FileWriter writer = new FileWriter( fileName)) {

            // Header
            writer.write("GRADE REPORT\n");
            writer.write("Generated on: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.write("\n\n");

            // Student Info
            writer.write("Student ID   : " + student.getStudentId() + "\n");
            writer.write("Name         : " + student.getName() + "\n");
            writer.write("Type         : " + student.getStudentType() + "\n\n");

            writer.write("GRADE DETAILS\n");
            writer.write("--------------------------------------------\n");

            // Get grades
            Grade[] grades = gradeManager.getGradesForStudent(student.getStudentId());

            for (Grade g : grades) {
                writer.write(
                        g.getDate() + " | " +
                                g.getSubject().getSubjectName() + " | " +
                                g.getSubject().getSubjectType() + " | " +
                                g.getGrade() + "\n"
                );
            }

            writer.write("--------------------------------------------\n");

            double avg = gradeManager.calculateOverallAverage(student.getStudentId());
            writer.write("\nOverall Average: " + String.format("%.2f", avg) + "%\n");
            writer.write("Passing Status : " +
                    (avg >= student.getPassingGrade() ? "Passing" : "Failing") + "\n");
        }
    }
    public void displayExportSuccess(String filename) {
        File file = new File(filename+".txt");
        long fileSizeKB = file.length() / 1024;

        System.out.println("\n✓ Report exported successfully!");
        System.out.println("  File: " + file.getName());
        System.out.println("  Location: " + file.getAbsolutePath());
        System.out.println("  Size: " + (fileSizeKB > 0 ? fileSizeKB + " KB" : file.length() + " bytes"));
    }
}
