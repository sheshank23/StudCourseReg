package com.LearnLoop.StudentCourseRegWeb.Repository;

import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import com.LearnLoop.StudentCourseRegWeb.Models.Enrollment;
import com.LearnLoop.StudentCourseRegWeb.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    @Query("SELECT e.course FROM Enrollment e WHERE e.student = :user")
    List<Course> findCoursesByUser(@Param("user") User user);
}