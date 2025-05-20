package com.LearnLoop.StudentCourseRegWeb.Controllers;

import com.LearnLoop.StudentCourseRegWeb.Models.User;
import com.LearnLoop.StudentCourseRegWeb.Repository.UserRepository;
import com.LearnLoop.StudentCourseRegWeb.Services.CourseService;
import com.LearnLoop.StudentCourseRegWeb.Services.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepo;
    private final CourseService courseService;
    private final EmailService emailService;

//    @Autowired
//    private ProductService productService;

    @GetMapping({"/", "/home"})
    public String homePage() {
        return "HomePage";
    }

//    @GetMapping("/products")
//    public String productPage(Model model) {
//        model.addAttribute("productList", productService.getAllProduct());
//
//        return "Products";
//    }
//
//    @GetMapping("/contactUs")
//    public String contactPage(Model model) {
//        model.addAttribute("message", new Message());
//
//        return "ContactUs";
//    }
//
//    @GetMapping("/aboutUs")
//    public String aboutUs() {
//        return "AboutUs";
//    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User formUser, HttpSession session, Model model) {
        var userOpt = userRepo.findByEmail(formUser.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(formUser.getPassword())) {
                session.setAttribute("loggedInUser", user);

                emailService.sendEmail(user.getEmail(),
                        "Login Successful - LearnLoop",
                        "Hi " + user.getUsername() + ",\n\nYou successfully logged in to LearnLoop.");

                if (user.getRole() == User.Role.ADMIN) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/student/home";
                }
            }
        }

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered");
            return "signup";
        }

        emailService.sendEmail(user.getEmail(),
                "signed up Successful - LearnLoop",
                "Hi " + user.getUsername() + ",\n\nYou successfully signed up in to LearnLoop. now explore the courses");

        user.setRole(User.Role.STUDENT); // Default role
        userRepo.save(user);
        return "redirect:/login";
    }

    // 🔴 Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/courses")
    public String CoursePage(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());

        return "courses";
    }

//    @GetMapping("/contactUs")
//    public String contactPage(Model model) {
//        model.addAttribute("message", new Message());
//
//        return "ContactUs";
//    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }

}
