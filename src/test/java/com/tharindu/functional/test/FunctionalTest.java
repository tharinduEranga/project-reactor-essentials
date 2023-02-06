package com.tharindu.functional.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class FunctionalTest {
    private final Student kaycee = new Student(1, "kaycee", 95);
    private final Student chigere = new Student(2, "chiggs", 90);
    private final Student louis = new Student(3, "louis", 92);
    private final Student leslie = new Student(4, "leslie", 90);
    private final Student cynthia = new Student(5, "cynthia", 100);
    private final Student will = new Student(6, "will", 70);
    private final Student amaka = new Student(7, "amaka", 42);
    private final Student john = new Student(8, "john", 39);
    private final Student joseph = new Student(9, "joseph", 31);
    private final List<Student> studentList = Arrays.asList(kaycee, chigere, louis, leslie, cynthia, will, amaka, john, joseph);

    @Test
    public void testStudentFilterAndMap() {
        List<Student> students = Arrays.asList(kaycee, chigere, louis, leslie, cynthia, will, amaka, john, joseph);
        students.sort(Comparator.comparing(Student::getOverallGrade));

        var median = students
                .stream()
                .skip(Math.max(0, ((students.size() + 1) / 2) - 1)) //skip the 1st half
                .limit(1 + (1 + students.size()) % 2) //gets only the middle value(or 2 middle values)
                .mapToInt(Student::getOverallGrade)//convert into an int list
                .average() //average of the grades
                .orElse(0);

        System.out.println(median);
    }

    @Test
    public void testListToMapIfTheKeysAreUnique() {
        Map<Integer, Student> studentMap = studentList
                .stream()
                .collect(
                        Collectors.toMap(Student::getId, Function.identity())
                );
        log.info("Unique students map: {}", studentMap);
    }

    @Test
    public void testListToMapIfTheKeysAreNotUnique() {
        Map<String, List<Student>> studentMap = studentList.stream()
                .collect(
                        Collectors.groupingBy(Student::getName)
                );
        log.info("Grouped students map: {}", studentMap);
    }

    @Test
    public void testListToMapSortById() {
        Map<Integer, Student> studentMap = studentList
                .stream()
                .collect(
                        Collectors.toMap(Student::getId, Function.identity())
                )
                .entrySet()
                .stream()
                .sorted((o1, o2) ->
                        Integer.compare(o2.getValue().getOverallGrade(), o1.getValue().getOverallGrade()))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new)
                );
        log.info(studentMap.toString());
    }
}

@AllArgsConstructor
@Data
class Student {
    private Integer id;
    private String name;
    private int overallGrade;
}
