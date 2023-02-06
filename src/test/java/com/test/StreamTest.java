package com.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {
    private static final Student kaycee = new Student(1, "kaycee", Grade.ELITE);
    private static final Student chigere = new Student(2, "chiggs", Grade.GOOD);
    private static final Student louis = new Student(3, "louis", Grade.POOR);
    private static final Student leslie = new Student(4, "leslie", Grade.FAIR);
    private static final Student cynthia = new Student(5, "cynthia", Grade.EXCELLENT);
    private static final Student will = new Student(6, "will", Grade.GOOD);
    private static final Student amaka = new Student(7, "amaka", Grade.EXCELLENT);
    private static final Student john = new Student(8, "john", Grade.ELITE);
    private static final Student joseph = new Student(9, "joseph", Grade.EXCELLENT);

    private static final List<Student> studentList = Arrays.asList(kaycee, chigere, louis, leslie, cynthia, will, amaka, john, joseph);

    @Test
    public void testSortMultiple() {
        //sort list of the student grades by rating
        List<Student> sortedStudents = studentList
                .stream()
                .sorted(Comparator.comparing(Student::getName))
                .sorted(Collections.reverseOrder(Comparator.comparing(o -> o.getGrade().rating)))
                .collect(Collectors.toList());
        System.out.println(sortedStudents);
    }

    @Test
    public void testGroupBy() {
        //group students by rating
        Map<Grade, List<Student>> groupedStudents = studentList
                .stream()
                .collect(Collectors.groupingBy(Student::getGrade));
        System.out.println(groupedStudents);
    }

    @Test
    public void testGetMinGrade() {
        Optional<Student> optionalStudent = studentList
                .stream()
                .min(Comparator.comparingInt(student -> student.getGrade().rating));
        System.out.println(optionalStudent.isPresent() ? optionalStudent.get() : "No students");
    }
}

enum Grade {
    POOR("Poor", 0), FAIR("Fair", 1), GOOD("Good", 2),
    EXCELLENT("Excellent", 3), ELITE("Elite", 4);

    public final String name;
    public final int rating;

    Grade(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }
}

@AllArgsConstructor
@Data
class Student {
    private Integer id;
    private String name;
    private Grade grade;
}
