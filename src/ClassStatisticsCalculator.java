import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClassStatisticsCalculator {

    private GradeManager gradeManager;
    private StudentManager studentManager;

    public ClassStatisticsCalculator(GradeManager gradeManager, StudentManager studentManager) {
        this.gradeManager = gradeManager;
        this.studentManager = studentManager;
    }

    public void printClassStatistics() {
        System.out.println("\nCLASS STATISTICS");
        System.out.println("──────────────────────────────────────────────────────────");

        Grade[] allGrades = getAllGrades();
        if (allGrades.length == 0) {
            System.out.println("No grades recorded in the system.");
            return;
        }

        int totalStudents = studentManager.getStudentCount();
        int totalGrades = allGrades.length;
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Total Grades Recorded: " + totalGrades);
        System.out.println();

        printGradeDistribution(allGrades);
        printStatisticalAnalysis(allGrades);
        printSubjectPerformance(allGrades);
        printStudentTypeComparison();

        System.out.println("\nPress Enter to continue...");
        new java.util.Scanner(System.in).nextLine();

    }

    public Grade[] getAllGrades() {
        ArrayList<Grade> list = new ArrayList<>();
        int total = gradeManager.getTotalGradeCount();

        for (int i = 0; i < total; i++) {
            Grade g = gradeManager.getGradeAt(i);
            if (g != null) {
                list.add(g);
            }
        }

        return list.toArray(new Grade[0]);
    }

    public void printGradeDistribution(Grade[] grades) {
        System.out.println("\nGRADE DISTRIBUTION");
        System.out.println("──────────────────────────────────────────────────────────");

        int[] buckets = new int[5];
        for (Grade g : grades) {
            double score = g.getGrade();
            if (score >= 90.0) buckets[0]++;
            else if (score >= 80.0) buckets[1]++;
            else if (score >= 70.0) buckets[2]++;
            else if (score >= 60.0) buckets[3]++;
            else buckets[4]++;
        }

        String[] labels = {"90-100% (A)", "80-89%  (B)", "70-79%  (C)", "60-69%  (D)", "0-59%   (F)"};
        int total = grades.length;

        for (int i = 0; i < buckets.length; i++) {
            int maxBarLength = 40;
            int barLength = (int) ((double) buckets[i] / total * maxBarLength);
            StringBuilder bar = new StringBuilder();

            for (int j = 0; j < barLength; j++) bar.append("█");
            for (int j = barLength; j < maxBarLength; j++) bar.append("░");

            double percentage = (double) buckets[i] / total * 100.0;
            System.out.printf("%-12s | %-40s %.1f%% (%d grades)%n", labels[i], bar, percentage, buckets[i]);
        }
    }

    public void printStatisticalAnalysis(Grade[] grades) {
        System.out.println("\nSTATISTICAL ANALYSIS");
        System.out.println("──────────────────────────────────────────────────────────");

        double[] values = Arrays.stream(grades).mapToDouble(Grade::getGrade).toArray();

        double mean = calculateMean(values);
        double median = calculateMedian(values.clone());
        double mode = calculateMode(values);
        double stdDev = calculateStdDev(values, mean);

        double min = Arrays.stream(values).min().orElse(0.0);
        double max = Arrays.stream(values).max().orElse(0.0);

        System.out.printf("Mean (Average):     %.1f%%%n", mean);
        System.out.printf("Median:             %.1f%%%n", median);
        System.out.printf("Mode:               %.1f%%%n", mode);
        System.out.printf("Standard Deviation: %.1f%%%n", stdDev);
        System.out.printf("Range:              %.1f%% (%.0f%% - %.0f%%)%n", max - min, min, max);

        Grade highest = grades[0];
        Grade lowest = grades[0];

        for (Grade g : grades) {
            if (g.getGrade() > highest.getGrade()) highest = g;
            if (g.getGrade() < lowest.getGrade()) lowest = g;
        }

        System.out.printf("\nHighest Grade:      %.0f%% (%s - %s)%n", highest.getGrade(), highest.getStudentId(), highest.getSubject().getSubjectName());
        System.out.printf("Lowest Grade:       %.0f%% (%s - %s)%n", lowest.getGrade(), lowest.getStudentId(), lowest.getSubject().getSubjectName());
    }

    public void printSubjectPerformance(Grade[] grades) {
        System.out.println("\nSUBJECT PERFORMANCE");
        System.out.println("──────────────────────────────────────────────────────────");

        ArrayList<String> subjects = new ArrayList<>();
        for (Grade g : grades) {
            String name = g.getSubject().getSubjectName();
            if (!subjects.contains(name)) subjects.add(name);
        }

        ArrayList<Double>[] subjectGradeLists = new ArrayList[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) subjectGradeLists[i] = new ArrayList<>();

        for (Grade g : grades) {
            String name = g.getSubject().getSubjectName();
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).equals(name)) {
                    subjectGradeLists[i].add(g.getGrade());
                    break;
                }
            }
        }

        System.out.println("\nCore Subjects:");
        printSubjectTypeAverage(subjects, subjectGradeLists, true);

        System.out.println("\nElective Subjects:");
        printSubjectTypeAverage(subjects, subjectGradeLists, false);
    }

    public void printSubjectTypeAverage(ArrayList<String> subjects, ArrayList<Double>[] subjectGrades, boolean coreType) {
        String[] core = {"Mathematics", "English", "Science"};
        double sum = 0.0;
        int count = 0;

        for (int i = 0; i < subjects.size(); i++) {
            String subject = subjects.get(i);
            boolean isCore = Arrays.asList(core).contains(subject);

            if (isCore == coreType) {
                double avg = subjectGrades[i].isEmpty() ? 0.0 : subjectGrades[i].stream().mapToDouble(Double::doubleValue).sum() / subjectGrades[i].size();
                sum += avg;
                count++;
                System.out.printf("  %-15s %.1f%%%n", subject + ":", avg);
            }
        }

        double overall = count == 0 ? 0.0 : sum / count;
        System.out.printf("%s Subjects Average: %.1f%%%n", coreType ? "Core" : "Elective", overall);
    }

    public void printStudentTypeComparison() {
        System.out.println("\nSTUDENT TYPE COMPARISON");
        System.out.println("──────────────────────────────────────────────────────────");

        Student[] list = studentManager.getAllStudents();
        double sumReg = 0.0, countReg = 0.0, sumHon = 0.0, countHon = 0.0;

        for (Student s : list) {
            double avg = gradeManager.calculateOverallAverage(s.getStudentId());
            String type = s.getStudentType();
            if ("Regular".equalsIgnoreCase(type)) {
                sumReg += avg;
                countReg++;
            } else if ("Honors".equalsIgnoreCase(type)) {
                sumHon += avg;
                countHon++;
            }
        }

        System.out.printf("Regular Students: %.1f%% average (%d students)%n", countReg == 0 ? 0.0 : sumReg / countReg, (int) countReg);
        System.out.printf("Honors Students:  %.1f%% average (%d students)%n", countHon == 0 ? 0.0 : sumHon / countHon, (int) countHon);
    }

    // ────────────── Statistics Helpers ──────────────

    public double calculateMean(double[] values) {
        return Arrays.stream(values).average().orElse(0.0);
    }

    public double calculateMedian(double[] values) {
        Arrays.sort(values);
        int n = values.length;
        return n % 2 == 0 ? (values[n / 2 - 1] + values[n / 2]) / 2.0 : values[n / 2];
    }

    public double calculateMode(double[] values) {
        double mode = values[0];
        int maxCount = 1;

        for (int i = 0; i < values.length; i++) {
            int count = 1;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] == values[i]) count++;
            }
            if (count > maxCount) {
                maxCount = count;
                mode = values[i];
            }
        }

        return mode;
    }

    public double calculateStdDev(double[] values, double mean) {
        double sum = 0.0;
        for (double v : values) sum += (v - mean) * (v - mean);
        return Math.sqrt(sum / values.length);
    }
}
