public class StudentManager {
private Student[] students = new Student[50];
    private int studentCounter=0;

public void addStudent (Student student){
    if(studentCounter<50){
    students[studentCounter]= student;
    studentCounter++;}
    System.out.println("Student added successfully!");
} // adds student to array

    public Student findStudent(String studentId){
        for (int i = 0; i < studentCounter; i++) {
            if (students[i] != null && students[i].getStudentId().equals(studentId)) {
                return students[i];
            }
        }
        return null;  // Student not found

    }// returns Student or null

    public void viewAllStudents(){
        students[studentCounter].displayStudentDetails();
    } //displays all students

    // public void viewAllStudents() {
    //        if (studentCount == 0) {
    //            System.out.println("No students in the system.");
    //            return;
    //        }
    //
    //        System.out.println("\n=== All Students ===");
    //        for (int i = 0; i < studentCount; i++) {
    //            if (students[i] != null) {
    //                students[i].displayStudentDetails();
    //                System.out.println("Average Grade: " + students[i].calculateAverageGrade());
    //                System.out.println("Status: " + (students[i].isPassing() ? "Passing" : "Failing"));
    //                System.out.println("------------------------");
    //            }
    //        }
    //        System.out.println("Total Students: " + studentCount);
    //        System.out.println("Class Average: " + getAverageClassGrade());
    //    }

    public double getAverageClassGrade(){
    double sum=0; int count =0;
    for (int i= 0; i< studentCounter; i++){
        if (students[i] != null) {
            double avg = students[i].calculateAverageGrade();
            sum+= avg;
            count++;
        }
    }
        if (count == 0) {
            return 0;
        }
    return sum/ count;
    }// calculates class average
public int getStudentCount(){
    return  studentCounter;
}// returns number of students

}


