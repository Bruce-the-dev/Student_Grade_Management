import Exceptions.InvalidGradeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClassStatisticsCalculatorTest {

    private GradeManager gradeManager;
    private StudentManager studentManager;
    private ClassStatisticsCalculator calculator;
    private Student makeStd(String name) {
        return new RegularStudent(name, 20, name.toLowerCase() + "@mail.com", "12345");
    }
    private Subject core(String name) {
        return new CoreSubject(name, name.toUpperCase() + "101");
    }

    @BeforeEach
    void setUp() throws InvalidGradeException {
        gradeManager = new GradeManager();
        studentManager = new StudentManager();
        calculator = new ClassStatisticsCalculator(gradeManager, studentManager);

        // Add test students
        studentManager.addStudent(makeStd("vite"));

        studentManager.addStudent(makeStd("steve"));
        studentManager.addStudent(makeStd("S03"));

        // Add test grades
        gradeManager.addGrade(new Grade("S01", core("Mathematics"), 85));
        gradeManager.addGrade(new Grade("S01", core("English"), 90));
        gradeManager.addGrade(new Grade("S02", core("Science"), 95));
        gradeManager.addGrade(new Grade("S03", core("History"), 70));
    }

    @Test
    void testCalculateMean() {
        double[] values = {85, 90, 95, 70};
        double mean = calculator.calculateMean(values);
        assertEquals(85.0, mean, 0.01);
    }

    @Test
    void testCalculateMedian() {
        double[] valuesEven = {70, 85, 90, 95};
        assertEquals(87.5, calculator.calculateMedian(valuesEven), 0.01);

        double[] valuesOdd = {70, 85, 95};
        assertEquals(85, calculator.calculateMedian(valuesOdd), 0.01);
    }

    @Test
    void testCalculateMode() {
        double[] values = {90, 85, 90, 70};
        assertEquals(90, calculator.calculateMode(values), 0.01);
    }

    @Test
    void testCalculateStdDev() {
        double[] values = {70, 85, 90, 95};
        double mean = calculator.calculateMean(values);
        double std = calculator.calculateStdDev(values, mean);

        assertEquals(9.354143466934854, std, 0.0001);
    }

    @Test
    void testGetAllGrades() {
        Grade[] allGrades = calculator.getAllGrades();
        assertEquals(4, allGrades.length);
    }
}
