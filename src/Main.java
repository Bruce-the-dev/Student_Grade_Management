import Exceptions.InvalidGradeException;
import Exceptions.LoggerHandler;
import Exceptions.StudentNotFoundException;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManager studentManager = new StudentManager();
    private static GradeManager gradeManager = new GradeManager();


    public static void main(String[] args) throws InvalidGradeException, StudentNotFoundException {
        initializeStudents();
        showMenu();
    }

    private static void showMenu() throws StudentNotFoundException  {
        boolean running = true;

        while (running) {
            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║   STUDENT GRADE MANAGEMENT - MAIN MENU            ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Record Grade");
            System.out.println("4. View Grade Report");
            System.out.println("5. Export Grade Report\n" +
                    "6. Calculate Student GPA\n" +
                    "7. Bulk Import Grades\n" +
                    "8. View Class Statistics\n" +
                    "9. Search Students\n" +
                    "10. Exit");
            System.out.println();
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addStudentMenu();
                    break;
                case 2:
                    viewStudentsMenu();
                    break;
                case 3:
                    recordGrade();
                    break;
                case 4:
                    viewGradeReport();
                    break;
                case 5:
                    System.out.println("\n Not yet implemented grade Export report!");
                    break;
                case 6:
                    System.out.println("\n Not yet implemented Calculated student GPA!");
                    break;
                case 7:
                    System.out.println("\nNot yet implemented bulk import grades!");
                    running = false;
                    break;
                case 8:
                    System.out.println("\n Not yet implemented view class Statistics!");
                    break;
                case 9:
                    System.out.println("\nNot yet implemented search students!");
                    break;
                case 10:
                    System.out.println("\nExiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter 1-10.");
            }
        }

        scanner.close();
    }

    private static void viewGradeReport() {

        System.out.println("\nVIEW GRADE REPORT");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println();

        scanner.nextLine(); // Clear buffer
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
try{


        Student student = studentManager.findStudent(studentId);
        if (student == null) {
            System.out.println("\nError: Student not found.");
            return;
        }
        System.out.printf("%-8s | %-12s | %-15s | %-10s | %-6s%n",
                "GRD ID", "DATE", "SUBJECT", "TYPE", "GRADE");
        System.out.println("-------------------------------------------------------------");
        gradeManager.viewGradesByStudent(studentId);
            System.out.println("total number of subjects: "+gradeManager.getGradeCount(studentId));

    }catch (StudentNotFoundException snf){
    System.out.println("ERROR: " + snf.getMessage());
}
    }

    private static void addStudentMenu() {
        System.out.println("\nAdd STUDENT");
        System.out.println("───────────────────────────────────────────────────────────────────────────");
        System.out.println("enter the student Name");
        String studName = scanner.next();
        System.out.println("Enter student age:");
        int studAge = scanner.nextInt();
        System.out.println("Enter student email:");
        String studEmail = scanner.next();
        System.out.println("Enter student phone: ");
        String studPhone = scanner.next();
        System.out.println("\n");
        System.out.println("Student type:");
        System.out.println("1. Regular Student (Passing grade: 50%)\n" +
                "2. Honors Student (Passing grade: 60%, honors recognition)");
        System.out.print("Select type (1-2): ");
        int type = scanner.nextInt();
        Student student;
        if (type == 1) {
            student = new RegularStudent(studName, studAge, studEmail, studPhone);
            studentManager.addStudent(student);
            student.displayStudentDetails();
        } else if (type == 2) {
            student = new HonorsStudent(studName, studAge, studEmail, studPhone);
            studentManager.addStudent(student);
            student.displayStudentDetails();
        } else {
            System.out.println("wrong choice choose again");
            return;
        }


    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void viewStudentsMenu() {
        System.out.println("\nSTUDENT LISTING");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        // Table header
        System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s%n",
                "STU ID", "NAME", "TYPE", "AVG GRADE", "STATUS");
        System.out.println("───────────────────────────────────────────────────────────────────────────");

        // Display each student
        for (int i = 0; i < studentManager.getStudentCount(); i++) {
            Student student = studentManager.getStudentByIndex(i);

            if (student != null) {
                // Calculate average grade
                double avg = gradeManager.calculateOverallAverage(student.getStudentId());

                // Determine status
                String status;
                if (avg == 0) {
                    status = "No Grades";
                } else if (student.isPassing(avg)) {
                    status = "Passing";
                } else {
                    status = "Failing";
                }

                // Format average grade
                String avgStr = String.format("%.1f%%", avg);

                System.out.printf("%-10s | %-20s | %-10s | %-10s | %-10s%n",
                        student.getStudentId(),
                        student.getName(),
                        student.getStudentType(),
                        avgStr,
                        status);


                int enrolledSubjects = countEnrolledSubjects(student.getStudentId());
                System.out.print("           | Enrolled Subjects: " + enrolledSubjects +
                        " | Passing Grade: " + (int) student.getPassingGrade() + "%");

                if (student instanceof HonorsStudent hs) {
                    if (hs.checkHonorsEligibility()) {
                        System.out.print(" | Honors Eligible");
                    }
                }

                System.out.println();
                System.out.println();
            }
        }

        System.out.println("───────────────────────────────────────────────────────────────────────────");
        System.out.println();
        System.out.println("Total Students: " + studentManager.getStudentCount());
        System.out.printf("Average Class Grade: %.1f%%%n", studentManager.getAverageClassGrade(gradeManager));
        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Clear buffer
        scanner.nextLine(); // Wait for Enter

    }

    private static void initializeStudents() throws InvalidGradeException {

        // Student 1: Alice Johnson (Regular)
        Student alice = new RegularStudent("Alice Johnson", 20, "alice.johnson@school.edu", "+1-555-0001");
        studentManager.addStudent(alice);
        addInitialGrades(alice.getStudentId(), 78.5, 5);

        // Student 2: Bob Smith (Honors)
        Student bob = new HonorsStudent("Bob Smith", 21, "bob.smith@school.edu", "+1-555-0002");
        studentManager.addStudent(bob);
        addInitialGrades(bob.getStudentId(), 85.2, 6);

        // Student 3: Carol Martinez (Regular)
        Student carol = new RegularStudent("Carol Martinez", 19, "carol.martinez@school.edu", "+1-555-0003");
        studentManager.addStudent(carol);
        addInitialGrades(carol.getStudentId(), 45.0, 4);

        // Student 4: David Chen (Honors)
        Student david = new HonorsStudent("David Chen", 22, "david.chen@school.edu", "+1-555-0004");
        studentManager.addStudent(david);
        addInitialGrades(david.getStudentId(), 92.8, 6);

        // Student 5: Emma Wilson (Regular)
        Student emma = new RegularStudent("Emma Wilson", 20, "emma.wilson@school.edu", "+1-555-0005");
        studentManager.addStudent(emma);
        addInitialGrades(emma.getStudentId(), 67.3, 5);
    }

    private static void addInitialGrades(String studentId, double targetAverage, int numSubjects) throws InvalidGradeException {
        Subject[] subjects = {
                new CoreSubject("Mathematics", "MATH101"),
                new CoreSubject("English", "ENG101"),
                new CoreSubject("Science", "SCI101"),
                new ElectiveSubject("Music", "MUS101"),
                new ElectiveSubject("Art", "ART101"),
                new ElectiveSubject("Physical Education", "PE101")
        };

        double totalNeeded = targetAverage * numSubjects;
        double sum = 0;


        for (int i = 0; i < numSubjects - 1; i++) {
            sum += targetAverage;


            gradeManager.addGrade(new Grade(studentId, subjects[i], targetAverage));
        }

        double lastGrade = totalNeeded - sum;

        if (lastGrade > 100) {
            lastGrade = 100;
        } else if (lastGrade < 0) {
            lastGrade = 0;
        }

        gradeManager.addGrade(new Grade(studentId, subjects[numSubjects - 1], lastGrade));
        Student.setGradeManager(gradeManager);
    }

    private static int countEnrolledSubjects(String studentId) {
        return gradeManager.getGradeCount(studentId);
    }

    private static void recordGrade(){
        System.out.println("\nRECORD GRADE");
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println();

        scanner.nextLine();

        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        try {
            Student student = studentManager.findStudent(studentId);


            System.out.println("\nStudent Found:");
            student.displayStudentDetails();
        System.out.println("───────────────────────────────────────────────────");

        Subject subject = null;

        System.out.println("""
                Subject Type:
                1. Core Subject (Mathematics, English, Science)
                2. Elective Subject (Music, Art, Physical Education)
                """);

        System.out.print("Select type (1-2): ");
        int type = getIntInput();

        if (type == 1) {
            System.out.println("""
                    Available Core Subjects:
                    1. Mathematics
                    2. English
                    3. Science
                    """);

            System.out.print("Select subject (1-3): ");
            int s = getIntInput();

            switch (s) {
                case 1 -> subject = new CoreSubject("Mathematics", "MATH101");
                case 2 -> subject = new CoreSubject("English", "ENG101");
                case 3 -> subject = new CoreSubject("Science", "SCI101");
                default -> {
                    System.out.println("❌ Invalid choice!");
                    return;
                }
            }

        } else if (type == 2) {

            System.out.println("""
                    Available Elective Subjects:
                    1. Music
                    2. Art
                    3. Physical Education
                    """);

            System.out.print("Select subject (1-3): ");
            int s = getIntInput();

            switch (s) {
                case 1 -> subject = new ElectiveSubject("Music", "MUS101");
                case 2 -> subject = new ElectiveSubject("Art", "ART101");
                case 3 -> subject = new ElectiveSubject("Physical Education", "PE101");
                default -> {
                    System.out.println("❌ Invalid choice!");
                    return;
                }
            }
        } else {
            System.out.println("❌ Invalid type!");
            return;
        }

        System.out.print("\nEnter grade (0-100): ");
        double gradeValue = scanner.nextDouble();

//        // Validate grade
//        if (gradeValue < 0 || gradeValue > 100) {
//            System.out.println("❌ Invalid grade. Must be between 0 and 100.");
//            return;
//        }

        scanner.nextLine();

        Grade grade = new Grade(studentId, subject, gradeValue);
        System.out.println("\nGRADE CONFIRMATION");
        System.out.println("---------------------");
        System.out.println("GradeID: "+ grade.getGradeId());
        System.out.println("Student: " + student.getName());
        System.out.println("Subject: " + subject.getSubjectName());
        System.out.println("Grade:   " + gradeValue);

        System.out.print("\nSave this grade? (Y/N): ");
        String confirm = scanner.nextLine();


        if (!confirm.trim().equalsIgnoreCase("Y")) {
            System.out.println("\n Grade cancelled. Nothing saved.");
            return;
        }

            try {
                gradeManager.addGrade(grade);
                System.out.println("\n✔ Grade saved successfully!");

            } catch (InvalidGradeException ig) {
                LoggerHandler.log("InvalidGradeException — " + ig.getMessage());
                System.out.println("\n❌ ERROR: " + ig.getMessage());
                return;
            }

        System.out.println("\n✔ Grade saved successfully!");
           }catch (StudentNotFoundException | InvalidGradeException snf){
            LoggerHandler.log("InvalidGradeException — " + snf.getMessage());
        System.out.println("Error: "+ snf.getMessage());
        }

    }

}