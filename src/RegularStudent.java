public class RegularStudent extends Student {

    private double passingGrade = 50;

    public RegularStudent(String name,int age,String email,String phone){
        super(name,age,email,phone);
    }


    @Override
    public void displayStudentDetails() {
        System.out.println(getStudentId());
        System.out.println(getName());
        System.out.println(getAge());
        System.out.println(getEmail());
        System.out.println(getPhone());
        System.out.println(getStudentType());
    }

    @Override
    public String getStudentType() {
return "Regular";
    }

    @Override
    public double getPassingGrade() {
return passingGrade;
    }
}
