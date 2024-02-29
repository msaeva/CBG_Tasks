package taskone;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

    //    public int compare(Student o1, Student o2) {
//        int result = o2.getCgpa().compareTo(o1.getCgpa());
//
//        if (result != 0) {
//            return result;
//        }
//        result = o1.getFirstName().compareTo(o2.getFirstName());
//        if (result != 0) return result;
//        return Integer.compare(o1.getId(), o2.getId());
//    }

    @Override
    public int compare(Student o1, Student o2) {
        return Comparator.comparing(Student::getCgpa).reversed()
                .thenComparing(Student::getFirstName)
                .thenComparing(Student::getId)
                .compare(o1, o2);
    }
}
