package com.LearnLoop.StudentCourseRegWeb.Repository;

import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleContainingIgnoreCase(String keyword);
}

