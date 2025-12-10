
public class HonorsStudent extends Student {
private double passingGrade=60.0;
private boolean honorsEligible= true;

    public HonorsStudent(String name,int age,String email,String phone){
        super(name,age,email,phone);
    }



    @Override
    public void displayStudentDetails() {
        System.out.println("\n=== Honors Student Details ===");
        System.out.println("\n✓ Student added successfully!");
        System.out.println("  Student ID: " +getStudentId());
        System.out.println("  Name: " + getName());
        System.out.println("  Type: " + getStudentType());
        System.out.println("  Age: "+ getAge());
        System.out.println("  Email: "+getEmail());
        System.out.println("  Passing grade: "+getPassingGrade());
        System.out.println("  Honors ELegible: "+ checkHonorsEligibility());
        System.out.println("  Status: " + getStatus());


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

    public boolean isHonorsEligible() {
        return honorsEligible;
    }

    public void setHonorsEligible(boolean honorsEligible) {
        this.honorsEligible = honorsEligible;
    }
}
