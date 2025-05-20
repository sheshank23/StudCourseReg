package com.LearnLoop.StudentCourseRegWeb.Services;

import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import com.LearnLoop.StudentCourseRegWeb.Repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private  final CourseRepository courseRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
