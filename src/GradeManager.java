public class GradeManager {
    private Grade[] grades = new Grade[200];
    private int gradeCount;

    public void addGrade(Grade grade) {
        grades[gradeCount] = grade;
        gradeCount++;
    }

    public void viewGradesByStudent(String studentId) {

        boolean found = false;
        for (int i = gradeCount - 1; i >= 0; i--) {
            if (grades[i] != null) {
                if (grades[i].getStudentId().equals(studentId)) {
                    found = true;
                    grades[i].displayGradeDetails();
                } else {
                    System.out.println("no grades found");
                }
            }
        }
        if (!found) {
            System.out.println("No grades found for this student.");
        }

    }

    //average of core subjects
    public double calculateCoreAverage(String studentId) {
        double sum = 0, count = 0;
        for (int i = 0; i < gradeCount; i++) {
            if (grades[i] != null) {

                if (grades[i].getStudentId().equals(studentId)) {
                    if (grades[i].getSubject() instanceof CoreSubject) {
                        sum += grades[i].getGrade();
                        count++;

                    }
                }
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;

    }

    // average of electives
    public double calculateElectiveAverage(String studentId) {
        double sum = 0, count = 0;
        for (int i = 0; i < gradeCount; i++) {
            if (grades[i] != null) {

                if (grades[i].getStudentId().equals(studentId)) {
                    if (grades[i].getSubject().getSubjectType().equals("Elective")){
                        sum += grades[i].getGrade();
                        count++;

                    }
                }
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;

    }

    //average of all grades
    public double calculateOverallAverage(String studentId) {
        double sum=0 ;int count =0;
        for (int i = 0; i < gradeCount; i++){
            if(grades[i]!=null){
                if (grades[i].getStudentId().equals(studentId)){
                    sum+= grades[i].getGrade();

                count++;}

            }
        }


            if (count == 0){return 0;}
        return sum/count;
    }

    public int getGradeCount() {
        return gradeCount;
    } //returns total grade count
}
