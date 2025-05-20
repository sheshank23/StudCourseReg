package com.LearnLoop.StudentCourseRegWeb.Services;

import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import com.LearnLoop.StudentCourseRegWeb.Models.Enrollment;
import com.LearnLoop.StudentCourseRegWeb.Models.User;
import com.LearnLoop.StudentCourseRegWeb.Repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public void enrollStudent(User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);
    }

    public List<Course> getEnrollmentsByStudent(User student) {
        return enrollmentRepository.findCoursesByUser(student);
    }
}
