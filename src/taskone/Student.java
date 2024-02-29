package taskone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Student {
    private final Integer id;
    private final String firstName;
    private final Double cgpa;

    public Student(Integer id, String firstName, Double cgpa) {
        this.id = id;
        this.firstName = firstName;
        this.cgpa = cgpa;
    }

    public Integer getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public Double getCgpa() {
        return cgpa;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Student> personList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            String name = scanner.next();
            double cgpa = scanner.nextDouble();

            personList.add(new Student(id, name, cgpa));
        }

        Collections.sort(personList, new StudentComparator());

        personList.forEach((p) -> System.out.println(p.getFirstName()));
    }
}
