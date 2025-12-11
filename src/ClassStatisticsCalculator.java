import java.util.*;

public class ClassStatisticsCalculator {

    private GradeManager gradeManager;
    private StudentManager studentManager;

    public ClassStatisticsCalculator(GradeManager gradeManager, StudentManager studentManager) {
        this.gradeManager = gradeManager;
        this.studentManager = studentManager;
    }

    // ───────────────────────────────────────────────────────────────────────────────
    // MAIN ENTRY — prints everything
    // ───────────────────────────────────────────────────────────────────────────────
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

    // ───────────────────────────────────────────────────────────────────────────────
    // COLLECT ALL GRADES
    // ───────────────────────────────────────────────────────────────────────────────
    private Grade[] getAllGrades() {
        List<Grade> list = new ArrayList<>();
        for (int i = 0; i < gradeManager.getTotalGradeCount(); i++) {
            Grade g = gradeManager.getGradeAt(i);
            if (g != null) list.add(g);
        }
        return list.toArray(new Grade[0]);
    }


    // ───────────────────────────────────────────────────────────────────────────────
    // 1. GRADE DISTRIBUTION (Histogram)
    // ───────────────────────────────────────────────────────────────────────────────
    private void printGradeDistribution(Grade[] grades) {

        System.out.println("\nGRADE DISTRIBUTION");
        System.out.println("──────────────────────────────────────────────────────────");

        int[] buckets = new int[5];

        for (Grade g : grades) {
            double score = g.getGrade();

            if (score >= 90) buckets[0]++;
            else if (score >= 80) buckets[1]++;
            else if (score >= 70) buckets[2]++;
            else if (score >= 60) buckets[3]++;
            else buckets[4]++;
        }

        int total = grades.length;

        String[] labels = {
                "90-100% (A)",
                "80-89%  (B)",
                "70-79%  (C)",
                "60-69%  (D)",
                "0-59%   (F)"
        };

        for (int i = 0; i < buckets.length; i++) {

            int barLength = (int) (((double) buckets[i] / total) * 40);
            String bar = "█".repeat(barLength);

            double percentage = ((double) buckets[i] / total) * 100;

            System.out.printf("%-12s | %-40s %.1f%% (%d grades)%n",
                    labels[i], bar, percentage, buckets[i]);
        }
    }


    // ───────────────────────────────────────────────────────────────────────────────
    // 2. STATISTICAL ANALYSIS
    // Mean, Median, Mode, Standard deviation, Range
    // ───────────────────────────────────────────────────────────────────────────────
    private void printStatisticalAnalysis(Grade[] grades) {

        System.out.println("\nSTATISTICAL ANALYSIS");
        System.out.println("──────────────────────────────────────────────────────────");

        double[] values = Arrays.stream(grades).mapToDouble(Grade::getGrade).toArray();

        double mean = Arrays.stream(values).average().orElse(0);
        double median = calculateMedian(values);
        double mode = calculateMode(values);
        double stdDev = calculateStdDev(values, mean);
        double min = Arrays.stream(values).min().orElse(0);
        double max = Arrays.stream(values).max().orElse(0);

        System.out.printf("Mean (Average):     %.1f%%%n", mean);
        System.out.printf("Median:             %.1f%%%n", median);
        System.out.printf("Mode:               %.1f%%%n", mode);
        System.out.printf("Standard Deviation: %.1f%%%n", stdDev);
        System.out.printf("Range:              %.1f%% (%.0f%% - %.0f%%)%n", (max - min), min, max);

        // Highest and lowest grade with student & subject
        Grade highest = grades[0];
        Grade lowest = grades[0];
        for (Grade g : grades) {
            if (g.getGrade() > highest.getGrade()) highest = g;
            if (g.getGrade() < lowest.getGrade()) lowest = g;
        }

        System.out.printf("\nHighest Grade:      %.0f%% (%s - %s)%n",
                highest.getGrade(), highest.getStudentId(), highest.getSubject().getSubjectName());

        System.out.printf("Lowest Grade:       %.0f%% (%s - %s)%n",
                lowest.getGrade(), lowest.getStudentId(), lowest.getSubject().getSubjectName());
    }


    // ───────────────────────────────────────────────────────────────────────────────
    // 3. SUBJECT PERFORMANCE (core & elective)
    // ───────────────────────────────────────────────────────────────────────────────
    private void printSubjectPerformance(Grade[] grades) {

        System.out.println("\nSUBJECT PERFORMANCE");
        System.out.println("──────────────────────────────────────────────────────────");

        Map<String, List<Double>> map = new LinkedHashMap<>();

        for (Grade g : grades) {
            String subjectName = g.getSubject().getSubjectName();
            map.putIfAbsent(subjectName, new ArrayList<>());
            map.get(subjectName).add(g.getGrade());
        }

        // Core Subjects
        System.out.println("\nCore Subjects:");
        printSubjectTypeAverage(map, true);

        // Elective Subjects
        System.out.println("\nElective Subjects:");
        printSubjectTypeAverage(map, false);
    }


    private void printSubjectTypeAverage(Map<String, List<Double>> map, boolean core) {

        double sum = 0;
        int count = 0;

        for (String subject : map.keySet()) {
            boolean isCore =
                    subject.equals("Mathematics") ||
                            subject.equals("English") ||
                            subject.equals("Science");

            if (isCore == core) {
                List<Double> grades = map.get(subject);
                double avg = grades.stream().mapToDouble(x -> x).average().orElse(0);

                sum += avg;
                count++;

                System.out.printf("  %-15s %.1f%%%n", subject + ":", avg);
            }
        }

        double overall = count == 0 ? 0 : sum / count;
        System.out.printf("%s Subjects Average: %.1f%%%n",
                core ? "Core" : "Elective", overall);
    }


    // ───────────────────────────────────────────────────────────────────────────────
    // 4. STUDENT TYPE COMPARISON
    // ───────────────────────────────────────────────────────────────────────────────
    private void printStudentTypeComparison() {

        System.out.println("\nSTUDENT TYPE COMPARISON");
        System.out.println("──────────────────────────────────────────────────────────");

        Student[] list = studentManager.getAllStudents();

        double sumReg = 0, countReg = 0;
        double sumHon = 0, countHon = 0;

        for (Student s : list) {
            double avg = gradeManager.calculateOverallAverage(s.getStudentId());

            if (s.getStudentType().equalsIgnoreCase("Regular")) {
                sumReg += avg;
                countReg++;
            } else if (s.getStudentType().equalsIgnoreCase("Honors")) {
                sumHon += avg;
                countHon++;
            }
        }

        System.out.printf("Regular Students: %.1f%% average (%d students)%n",
                (countReg == 0 ? 0 : sumReg / countReg), (int) countReg);

        System.out.printf("Honors Students:  %.1f%% average (%d students)%n",
                (countHon == 0 ? 0 : sumHon / countHon), (int) countHon);
    }


    // ───────────────────────────────────────────────────────────────────────────────
    // HELPER FUNCTIONS — median, mode, std dev
    // ───────────────────────────────────────────────────────────────────────────────
    private double calculateMedian(double[] values) {
        Arrays.sort(values);
        int n = values.length;
        if (n % 2 == 0)
            return (values[n / 2 - 1] + values[n / 2]) / 2.0;
        return values[n / 2];
    }

    private double calculateMode(double[] values) {
        Map<Double, Integer> freq = new HashMap<>();
        for (double v : values) freq.put(v, freq.getOrDefault(v, 0) + 1);

        double mode = values[0];
        int max = 0;

        for (double key : freq.keySet()) {
            if (freq.get(key) > max) {
                max = freq.get(key);
                mode = key;
            }
        }
        return mode;
    }

    private double calculateStdDev(double[] values, double mean) {
        double sum = 0;
        for (double v : values) {
            sum += Math.pow(v - mean, 2);
        }
        return Math.sqrt(sum / values.length);
    }
}
