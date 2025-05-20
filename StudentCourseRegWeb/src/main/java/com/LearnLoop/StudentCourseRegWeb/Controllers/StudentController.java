package com.LearnLoop.StudentCourseRegWeb.Controllers;

import com.LearnLoop.StudentCourseRegWeb.Models.Course;
import com.LearnLoop.StudentCourseRegWeb.Models.User;
import com.LearnLoop.StudentCourseRegWeb.Services.CourseService;
import com.LearnLoop.StudentCourseRegWeb.Services.EmailService;
import com.LearnLoop.StudentCourseRegWeb.Services.EnrollmentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class StudentController {

//    @Autowired
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final EmailService emailService;

    @GetMapping("/student/home")
    public String showStudentHome(Model model, HttpSession session) {
        List<Course> courses = courseService.getAllCourses();
        User student = (User) session.getAttribute("loggedInUser");

        model.addAttribute("user", student);
        model.addAttribute("courses", courses);
        return "studentHome"; // your student_home.html
    }

    @GetMapping("/student/course/{id}")
    public String viewCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        return "course-detail";
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(@PathVariable Long courseId, HttpSession session, RedirectAttributes redirectAttributes) {
        User student = (User) session.getAttribute("loggedInUser");
        Optional<Course> course = courseService.getCourseById(courseId);

        if (student != null && course.isPresent()) {
            enrollmentService.enrollStudent(student, course.get());
//            emailService.sendEnrollmentEmail(student.getEmail(), student.getName(), course.getTitle());

            redirectAttributes.addFlashAttribute("success", "Enrolled in course successfully!");

            emailService.sendEmail(
                    student.getEmail(),
                    "Course Enrollment Confirmation - LearnLoop",
                    "Hi " + student.getUsername() + ",\n\nYou have successfully enrolled in the course: " + course.get().getTitle() + ".\n\nHappy Learning!\n\n- LearnLoop Team"
            );
        }

        return "redirect:/student/enrolled-courses";
    }

    @GetMapping("/student/enrolled-courses")
    public String showEnrolledCourses(HttpSession session, Model model) {
        User student = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", student);
        if (student != null) {
            List<Course> enrolledCourses = enrollmentService.getEnrollmentsByStudent(student);
            model.addAttribute("courses", enrolledCourses);
        }
        return "enrolled-courses";
    }



}
