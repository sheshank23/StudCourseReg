package com.LearnLoop.StudentCourseRegWeb.Controllers;


import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import com.LearnLoop.StudentCourseRegWeb.Services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/search")
    public List<Map<String, Object>> searchByTitle(@RequestParam String keyword) {
        List<Course> courses = courseService.searchCourses(keyword);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Course course : courses) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", course.getId());
            map.put("title", course.getTitle());
            result.add(map);
        }

        return result;
    }
}
