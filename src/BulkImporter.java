import Exceptions.InvalidGradeException;
import Exceptions.StudentNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class BulkImporter {
    private StudentManager studentManager;
    private GradeManager gradeManager;

    public BulkImporter(StudentManager studentManager,GradeManager gradeManager) {
        this.gradeManager = gradeManager;
        this.studentManager = studentManager;
    }

    public void importGrades(String filePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        int successCount = 0;
        int failureCount = 0;

        FileWriter log = new FileWriter("import_log.txt");

        log.write("IMPORT LOG\n------------------------------\n");

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(",");

            // CSV must have 4 values
            if (parts.length != 4) {
                log.write("Invalid format: " + line + "\n");
                failureCount++;
                continue;
            }

            String studentId = parts[0].trim();
            String subjectName = parts[1].trim();
            String subjectType = parts[2].trim();
            double gradeValue;

            try {
                gradeValue = Double.parseDouble(parts[3].trim());
            } catch (NumberFormatException e) {
                log.write("Invalid grade number: " + line + "\n");
                failureCount++;
                continue;
            }

            try {
                Student student = studentManager.findStudent(studentId);

                Subject subject;

                // Validate subject
                if (subjectType.equalsIgnoreCase("Core")) {
                    subject = new CoreSubject(subjectName, subjectName.substring(0,3).toUpperCase()+"101");
                } else if (subjectType.equalsIgnoreCase("Elective")) {
                    subject = new ElectiveSubject(subjectName, subjectName.substring(0,3).toUpperCase()+"201");
                } else {
                    log.write("Invalid subject type: " + line + "\n");
                    failureCount++;
                    continue;
                }

                // Add grade
                Grade newGrade = new Grade(studentId, subject, gradeValue);
                gradeManager.addGrade(newGrade);

                successCount++;

            } catch (InvalidGradeException | StudentNotFoundException e) {
                log.write("Error: " + e.getMessage() + " | Line: " + line + "\n");
                failureCount++;
            }
        }

        reader.close();

        log.write("\nSUMMARY\n-----------------------------\n");
        log.write("Successful imports : " + successCount + "\n");
        log.write("Failed imports     : " + failureCount + "\n");
        log.close();

        System.out.println("\nIMPORT COMPLETE!");
        System.out.println("Successful: " + successCount);
        System.out.println("Failed: " + failureCount);
        System.out.println("Log saved to import_log.txt");
    }

}
