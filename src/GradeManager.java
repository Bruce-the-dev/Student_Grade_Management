import Exceptions.InvalidGradeException;


public class GradeManager {
    private Grade[] grades = new Grade[200];
    private int gradeCounter;

    public void addGrade(Grade grade) throws InvalidGradeException {
        if (!grade.validateGrade(grade.getGrade())) {
            throw new InvalidGradeException("Grade must be between 0 and 100.");
        }
        grades[gradeCounter] = grade;
        gradeCounter++;
    }

    public void viewGradesByStudent(String studentId) {

        boolean found = false;
        for (int i = gradeCounter - 1; i >= 0; i--) {
            if (grades[i] != null) {
                if (grades[i].getStudentId().equals(studentId)) {
                    found = true;

                    grades[i].displayGradeDetails();
                }
            }
        }
        if (!found) {  System.out.println("—————————————————————————————————————");
            System.out.println("No grades recorded for this student.");
            System.out.println("—————————————————————————————————————");
            System.out.println();
            System.out.println("press enter to continue.....");
        }

    }

    public double calculateCoreAverage(String studentId) {
        double sum = 0, count = 0;
        for (int i = 0; i < gradeCounter; i++) {
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
            return -1;
        }
        return sum / count;

    }

    // average of electives
    public double calculateElectiveAverage(String studentId) {
        double sum = 0, count = 0;
        for (int i = 0; i < gradeCounter; i++) {
            if (grades[i] != null) {

                if (grades[i].getStudentId().equals(studentId)) {
                    if (grades[i].getSubject().getSubjectType().equals("Elective")) {
                        sum += grades[i].getGrade();
                        count++;

                    }
                }
            }
        }
        if (count == 0) {
            return -1;
        }
        return sum / count;

    }

    //average of all grades
    public double calculateOverallAverage(String studentId) {
        if (studentId != null) {

        double coreAvg= calculateCoreAverage(studentId);
        double electiveAvg= calculateElectiveAverage(studentId);
        return (coreAvg+ electiveAvg)/2;
        }
else return -1;
        }

    public int getGradeCount(String studentId) {
        int count = 0;
        for (int i = 0; i < gradeCounter; i++) {
            if (grades[i] != null && grades[i].getStudentId().equals(studentId)) {
                count++;
            }
        }
        return count;
    }


    /**
     * Get all grades for a student as an array
     * @param studentId The student ID
     * @return Array of Grade objects (or empty array if none found)
     */
    public Grade[] getGradesForStudent(String studentId) {
        int count = 0;
        for (int i = 0; i < gradeCounter; i++) {
            if (grades[i] != null && grades[i].getStudentId().equals(studentId)) {
                count++;
            }
        }

        Grade[] studentGrades = new Grade[count];

        //newest first
        int index = 0;
        for (int i = gradeCounter - 1; i >= 0 && index < count; i--) {
            if (grades[i] != null && grades[i].getStudentId().equals(studentId)) {
                studentGrades[index] = grades[i];
                index++;
            }
        }

        return studentGrades;
    }
    public int getTotalGradeCount() {
        return gradeCounter;
    }

    public Grade getGradeAt(int index) {
        return grades[index];
    }


}
