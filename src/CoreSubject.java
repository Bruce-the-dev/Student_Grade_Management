public class CoreSubject extends Subject {
    final private boolean mandatory=true;

    public CoreSubject(String subjectName, String subjectCode) {
        super(subjectName,subjectCode );
    }

    @Override
    public void displaySubjectDetails() {
        System.out.println("Core Subject info: ");
        System.out.println(getSubjectName());
        System.out.println(getSubjectCode());
    }

    @Override
    public String getSubjectType() {
        return "Core";
    }
public boolean isMandatory(){
        return mandatory;
}
}
