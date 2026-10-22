
package com.example.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@RestController
public class AttendanceApplication {

    private final List<Map<String, String>> records =
            new CopyOnWriteArrayList<>();

    public AttendanceApplication() {
        records.add(Map.of(
                "student", "Arun",
                "status", "Present"
        ));
    }

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Student Attendance App is running!";
    }

    @GetMapping("/attendance")
    public List<Map<String, String>> getAttendance() {
        return new ArrayList<>(records);
    }

    @PostMapping("/attendance")
    public Map<String, String> markAttendance(
            @RequestBody Map<String, String> request) {

        String student = request.get("student");
        String status = request.get("status");

        if (student == null || student.isBlank()
                || status == null
                || !(status.equalsIgnoreCase("Present")
                || status.equalsIgnoreCase("Absent"))) {
            throw new IllegalArgumentException(
                    "Provide a student name and Present or Absent status."
            );
        }

        Map<String, String> record = Map.of(
                "student", student,
                "status", status
        );

        records.add(record);
        return record;
    }
}
