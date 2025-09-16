import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    int[] marks = new int[3];
    int total;
    double average;

    // Calculate total and average
    void calculate() {
        total = marks[0] + marks[1] + marks[2];
        average = total / 3.0;
    }
}

public class StudentGradeManagement {
    static Student[] students = new Student[50]; // Max 50 students
    static int count = 0;

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            
            do {
                System.out.println("\n--- Student Grade Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. Update Marks");
                System.out.println("3. Remove Student");
                System.out.println("4. View All Students");
                System.out.println("5. Search Student");
                System.out.println("6. Highest Scorer");
                System.out.println("7. Class Average");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
                
                switch (choice) {
                    case 1 -> addStudent(sc);
                    case 2 -> updateMarks(sc);
                    case 3 -> removeStudent(sc);
                    case 4 -> viewAllStudents();
                    case 5 -> searchStudent(sc);
                    case 6 -> highestScorer();
                    case 7 -> classAverage();
                    case 8 -> exitProgram();
                    default -> System.out.println("Invalid option! Try again.");
                }
                
            } while (choice != 8);
        }
    }

    // Add student
    static void addStudent(Scanner sc) {
        if (count >= 50) {
            System.out.println("Cannot add more students (limit reached).");
            return;
        }

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        if (findStudent(roll) != -1) {
            System.out.println("Roll number already exists!");
            return;
        }

        Student s = new Student();
        s.rollNo = roll;

        System.out.print("Enter Name: ");
        s.name = sc.nextLine();

        for (int i = 0; i < 3; i++) {
            int mark;
            do {
                System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
                mark = sc.nextInt();
                if (mark < 0 || mark > 100)
                    System.out.println("Invalid marks! Must be between 0 and 100.");
            } while (mark < 0 || mark > 100);
            s.marks[i] = mark;
        }

        s.calculate();
        students[count++] = s;
        System.out.println("Student added successfully!");
    }

    // Update marks
    static void updateMarks(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        int index = findStudent(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            int mark;
            do {
                System.out.print("Enter New Marks for Subject " + (i + 1) + ": ");
                mark = sc.nextInt();
                if (mark < 0 || mark > 100)
                    System.out.println("Invalid marks! Must be between 0 and 100.");
            } while (mark < 0 || mark > 100);
            students[index].marks[i] = mark;
        }

        students[index].calculate();
        System.out.println("Marks updated successfully!");
    }

    // Remove student
    static void removeStudent(Scanner sc) {
        System.out.print("Enter Roll No to remove: ");
        int roll = sc.nextInt();
        int index = findStudent(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        for (int i = index; i < count - 1; i++) {
            students[i] = students[i + 1];
        }
        count--;
        System.out.println("Student removed successfully!");
    }

    // View all students
    static void viewAllStudents() {
        if (count == 0) {
            System.out.println("No students to display.");
            return;
        }

        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s%n",
                "RollNo", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        for (int i = 0; i < count; i++) {
            Student s = students[i];
            System.out.printf("%-10d %-15s %-10d %-10d %-10d %-10d %-10.2f%n",
                    s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total, s.average);
        }
    }

    // Search student
    static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();
        int index = findStudent(roll);

        if (index == -1) {
            System.out.println("Student not found!");
            return;
        }

        Student s = students[index];
        System.out.printf("Roll No: %d, Name: %s, Marks: [%d, %d, %d], Total: %d, Average: %.2f%n",
                s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total, s.average);
    }

    // Highest scorer
    static void highestScorer() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }

        Student top = students[0];
        for (int i = 1; i < count; i++) {
            if (students[i].total > top.total) {
                top = students[i];
            }
        }

        System.out.println("Highest Scorer: " + top.name + " (Roll No: " + top.rollNo + "), Total Marks: " + top.total);
    }

    // Class average
    static void classAverage() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }

        int totalSum = 0;
        for (int i = 0; i < count; i++) {
            totalSum += students[i].total;
        }

        double avg = totalSum / (double)(count * 3);
        System.out.printf("Class Average: %.2f%n", avg);
    }

    // Exit program
    static void exitProgram() {
        System.out.println("Exiting program...");
        System.out.println("Total Students: " + count);
        classAverage();
    }

    // Find student by roll number
    static int findStudent(int roll) {
        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                return i;
            }
        }
        return -1;
    }
}
