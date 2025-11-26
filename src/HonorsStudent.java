public class HonorsStudent extends Student{
private double passingGrade=60.0;
private boolean honorsEligible;

    public HonorsStudent(String name,int age,String email,String phone){
        super(name,age,email,phone);
    }



    @Override
    public void displayStudentDetails() {
        System.out.println("\n=== Honors Student Details ===");
            System.out.println(getName());
            System.out.println(getAge());
            System.out.println(getEmail());
            System.out.println(getPhone());
            System.out.println(getStudentType());
        System.out.println(getPassingGrade());


    }

    public boolean checkHonorsEligibility() {
        return calculateAverageGrade() >= 85;
    }


    @Override
    public String getStudentType() {
        return "Honors";
    }

    @Override
    public double getPassingGrade() {
        return passingGrade;
    }
}
