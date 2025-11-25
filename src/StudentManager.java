public abstract class StudentManager {
    private String studentId;

    private  String name;
    private int age;
    private String email;
    private int phone;
    private String status = "Active";

    static int studentCounter;

    abstract void displayStudentDetails();

    abstract void getStudentType();

    abstract void getPassingGrade();
    int CalculateAverageGrade(int avg){
return avg;
    }
    int IsPassing(int grade){
        return grade;
    }

    public static int getStudentCounter() {
        return studentCounter;
    }

    public static void setStudentCounter(int studentCounter) {
        StudentManager.studentCounter = studentCounter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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


