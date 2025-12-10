import Exceptions.InvalidGradeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeManagerTest {
private GradeManager gradeManager;
    @BeforeEach
    void setup() {
        gradeManager = new GradeManager();
    }
    private Subject core(String name) {
        return new CoreSubject(name, name.substring(0,3).toUpperCase() + "101");
    }

    private Subject elective(String name) {
        return new ElectiveSubject(name, name.substring(0,3).toUpperCase() + "101");
    }

    @Test
    void addValidGrade() throws InvalidGradeException {
        Grade g = new Grade("STU001", core("Math"), 85);
        gradeManager.addGrade(g);

        assertEquals(1, gradeManager.getGradeCount("STU001"));
    }
    @Test
void addInvalidGrade() throws InvalidGradeException {
    Grade g = new Grade("STU001", core("Math"), -5);

    Exception ex = assertThrows(InvalidGradeException.class, () -> {
        gradeManager.addGrade(g);
    });

    assertTrue(ex.getMessage().contains("between 0 and 100"));
}
    @Test
    void testAddGrade_InvalidAbove100() throws InvalidGradeException {
        Grade g = new Grade("STU001", core("Math"), 150);

        Exception ex = assertThrows(InvalidGradeException.class, () -> {
            gradeManager.addGrade(g);
        });

        assertTrue(ex.getMessage().contains("between 0 and 100"));
    }
    @Test
    void testGetGradesForStudent_NoGrades() {
        Grade[] result = gradeManager.getGradesForStudent("STU001");
        assertEquals(0, result.length);
    }
    @Test
    void testGetGradesForStudent_OrderNewestFirst() throws InvalidGradeException {
        Grade g1 = new Grade("STU001", core("Math"), 80);   // older
        Grade g2 = new Grade("STU001", core("Science"), 75); // newer

        gradeManager.addGrade(g1);
        gradeManager.addGrade(g2);

        Grade[] result = gradeManager.getGradesForStudent("STU001");

        assertEquals("Science", result[0].getSubject().getSubjectName());
        assertEquals("Math", result[1].getSubject().getSubjectName());
    }

}