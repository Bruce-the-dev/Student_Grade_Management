public class ElectiveSubject extends Subject {
    private final boolean isMandatory = false;

    public ElectiveSubject(String subjectName, String subjectCode) {
        super(subjectName, subjectCode);
    }
    @Override
    public void displaySubjectDetails() {
        System.out.println("Elective Subject info: ");
        System.out.println(getSubjectName());
        System.out.println(getSubjectCode());

    }

    @Override
    public String getSubjectType() {
        return "Elective";
    }
    public boolean isMandatory(){
        return isMandatory;
    }

}
