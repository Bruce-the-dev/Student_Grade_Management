public abstract class Student {
    private String studentId;

    private String name;
    private int age;
    private String email;
    private String phone;
    private String status;
    static int studentCounter;

    public Student() {
    }

    public Student(String name, int age, String email, String phone) {
        studentCounter++;
//        this.studentId = "STU" +studentCounter;
        this.studentId = "STU" + String.format("%03d", studentCounter); // FIXED ID generator

        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public abstract void displayStudentDetails();

    public abstract String getStudentType();

    public abstract double getPassingGrade();

    double calculateAverageGrade(double avg) {
        return avg;
    }

    boolean isPassing(double grade) {
       return calculateAverageGrade(grade)>= grade;
}


    public static int getStudentCounter() {
        return studentCounter;
    }

    public static void setStudentCounter(int studentCounter) {
        Student.studentCounter = studentCounter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
