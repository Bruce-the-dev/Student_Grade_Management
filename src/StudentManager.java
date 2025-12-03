import Exceptions.StudentNotFoundException;

import java.util.Arrays;

public class StudentManager {
    private Student[] students = new Student[50];
    private int studentCounter = 0;

    public void addStudent(Student student) {
        if (studentCounter < 50) {
            students[studentCounter] = student;
            studentCounter++;
        }
        System.out.println("Student added successfully!");
    }

    public Student findStudent(String studentId) throws StudentNotFoundException {
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getStudentId().equals(studentId)) {
                return students[i];
            }
        }
        throw new StudentNotFoundException("Student with Id " + studentId + " doesn't exist");  // Student not found

    }// returns Student or null


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
        if (count == 0) {
            return 0;
        }
        return total / count;
    }
    public  Student[] findStudentByName(String name){
       name= name.toLowerCase();
        Student[] results = new Student[studentCounter];
        int count = 0;
        for (int i = 0; i <studentCounter ; i++) {
            Student s = students[i];
            if (s != null) {
            if (s.getName().toLowerCase().contains(name)){
              results[count]=s;
                count++;
            }}
        }
        return Arrays.copyOf(results, count);
    }
    public Student[] searchByGradeRange(double min, double max, GradeManager gradeManager) {
        Student[] results = new Student[studentCounter];
        int count = 0;

        for (int i = 0; i < studentCounter; i++) {
            Student s = students[i];

            if (s == null) continue;

            double avg = gradeManager.calculateOverallAverage(s.getStudentId());
            if (avg < 0) continue;  // no grades

            if (avg >= min && avg <= max) {
                results[count++] = s;
            }
        }

        return Arrays.copyOf(results, count);
    }

}


