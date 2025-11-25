import java.time.Instant;

public class Grade {
    static int gradeCounter;
    String gradeId;
    String studentId;
    Subject subject;
    double grade;
    String date;

    public Grade(double grade, String studentId, Subject subject) {
        gradeCounter++;
        this.gradeId = "GRD" + String.format("%03d", gradeCounter);
        this.grade = grade;
        this.studentId = studentId;
        this.subject = subject;
        this.date= Instant.now().toString();
    }

    public Grade() {

    }

    public void displayGradeDetails(){
    System.out.println(getStudentId());
    System.out.println(getGradeId());
    System.out.println(getGrade());
    System.out.println(getSubject().getSubjectName());
    System.out.println(getSubject().getSubjectCode());
    System.out.println(getSubject().getSubjectType());
}
public String getLetterGrade(){
       double g= this.grade;
    if (g >= 90) return "A";
    if (g >= 80) return "B";
    if (g >= 70) return "C";
    if (g >= 60) return "D";
    return "F";

}
    public String getDate() {
        return date;
    }

    public double getGrade() {
        return grade;
    }

    public String getGradeId() {
        return gradeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public Subject getSubject() {
        return subject;
    }

    public static int getGradeCounter() {
        return gradeCounter;
    }
    }
