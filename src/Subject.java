public abstract class Subject {
private String subjectName;
private String subjectCode;
public static int subjectCount=0;


public abstract void displaySubjectDetails();
public abstract String getSubjectType();

    public Subject(String subjectName,  String subjectCode) {
        subjectCount++;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
