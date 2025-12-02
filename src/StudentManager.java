import Exceptions.StudentNotFoundException;

public class StudentManager {
    private Student[] students = new Student[50];
    private int studentCounter = 0;

    public void addStudent(Student student) {
        if (studentCounter < 50) {
            students[studentCounter] = student;
            studentCounter++;
        }
        System.out.println("Student added successfully!");
    } // adds student to array

    public Student findStudent(String studentId) throws StudentNotFoundException {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getStudentId().equals(studentId)) {
                return students[i];
            }
        }
        throw new StudentNotFoundException("Student with Id "+ studentId +" doesn't exist");  // Student not found

    }// returns Student or null

    public void viewAllStudents() {
        students[studentCounter].displayStudentDetails();
    } //displays all students



    public int getStudentCount() {
        return studentCounter;
    }

    public Student getStudentByIndex(int index) {
        if (index >= 0 && index < studentCounter) {
            return students[index];
        }
        return null;
    }

    public double getAverageClassGrade(GradeManager gradeManager) {

        double total = 0;
        int count = 0;

        for (int i = 0; i < studentCounter; i++) {
            Student s = students[i];

            double avg = gradeManager.calculateOverallAverage(s.getStudentId());

            if (avg >= 0) {
                total += avg;
                count++;
            }
        }

        if (count == 0) {return 0;}

        return total / count;
    }
}


