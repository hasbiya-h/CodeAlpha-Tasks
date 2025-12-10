import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double marks;
    String grade;

    Student(String name, double marks, String grade) {
        this.name = name;
        this.marks = marks;
        this.grade = grade;
    }
}

public class Main {

    public static String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("============ STUDENT GRADE TRACKER ============");

        while (true) {
            System.out.print("Enter Student Name (or type 'exit' to finish): ");
            String name = sc.nextLine();

            if (name.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter Marks (0-100): ");
            double marks = sc.nextDouble();
            sc.nextLine(); // clear scanner buffer

            String grade = calculateGrade(marks);
            students.add(new Student(name, marks, grade));
        }

        if (students.isEmpty()) {
            System.out.println("No student records entered!");
            return;
        }

        double total = 0;
        double highest = students.get(0).marks;
        double lowest = students.get(0).marks;

        for (Student s : students) {
            total += s.marks;
            if (s.marks > highest) highest = s.marks;
            if (s.marks < lowest) lowest = s.marks;
        }

        double average = total / students.size();

        // SUMMARY REPORT
        System.out.println("\n============ SUMMARY REPORT ============");
        System.out.println("Total Students: " + students.size());
        System.out.println("Average Score: " + average);
        System.out.println("Highest Score: " + highest);
        System.out.println("Lowest Score: " + lowest);

        System.out.println("\nStudent Performance:");
        System.out.printf("%-20s %-10s %-10s%n", "Name", "Marks", "Grade");
        System.out.println("----------------------------------------------");
        for (Student s : students) {
            System.out.printf("%-20s %-10.2f %-10s%n", s.name, s.marks, s.grade);
        }

        System.out.println("----------------------------------------------");
        System.out.println("Thank you for using Student Grade Tracker!");

        sc.close();
    }
}

