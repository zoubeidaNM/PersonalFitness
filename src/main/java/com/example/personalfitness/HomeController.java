package com.example.personalfitness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping("/")
    public String index(){
        return "homepage";
    }

    @RequestMapping("/welcome")
    public String welcome(Principal principal, Model model){


        FitnessUser user = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", user);
        return "welcome";
    }


    @RequestMapping("/user")
    public String user(Principal principal, Model model){


        FitnessUser user = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", user);
        return "user";
    }


    @RequestMapping("/trainer")
    public String trainer(Principal principal, Model model){


        FitnessUser user = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", user);
        return "trainer";
    }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new FitnessUser());
        return "registeruser";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") FitnessUser user,
                                          BindingResult result, @RequestParam String role, Model model) {
        if (result.hasErrors()) {
            return "registeruser";
        } else {

            if (userRepository.findByUsername(user.getUsername()) == null) {
                if (role.equalsIgnoreCase("USER")) {
                    userService.saveUser(user);
                } else if (role.equalsIgnoreCase("TRAINER")) {
                    userService.saveTrainer(user);
                }
                model.addAttribute("message", "User Account Successfully Created");
            } else if (userRepository.findByUsername(user.getUsername()) == null) {
                model.addAttribute("error", true);
                model.addAttribute("error_message", "Username already exists. Try again!");

                return "registeruser";
            }
            model.addAttribute("user", user);

            System.out.println(user.getFirstName());
            System.out.println(user.getArea());
            System.out.println(user.getNeedOrSpecialty());

            return "login";
        }
    }


    @RequestMapping("/user/request")
    public String showRequest(Model model){

        Request request = new Request();

        model.addAttribute("request", request);
        return "requestform";
    }

    @PostMapping("/user/request")
    public String processUserRequest(Principal principal, @Valid @ModelAttribute("request") Request request,
                                     BindingResult result, Model model){
        FitnessUser user = userRepository.findByUsername(principal.getName());
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "requestform";
        } else {

            request.processDate();
            request.processTime();
            request.setStatus("waiting");
            requestRepository.save(request);
            user.addRequest(request);
            userRepository.save(user);
            model.addAttribute("user", user);
            return "user";
        }
    }

    @RequestMapping("/trainer/requests/{id}")
    public String showTrainerRequests(@PathVariable("id") long id, Model model){
        model.addAttribute("request", requestRepository.findOne(id));
        return "answerrequest";
    }


    @RequestMapping("/user/comment")
    public String showComment(Model model){

        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "commentform";
    }
    /* ProcessFormText*/
    @PostMapping("/user/comment")
    public String processUserComment(Principal principal, @Valid @ModelAttribute("comment") Comment comment,
                                     BindingResult result, Model model) {
        FitnessUser user = userRepository.findByUsername(principal.getName());
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "commentform";
        }


        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        String username = principal.getName();
        FitnessUser user_current = userRepository.findByUsername(username);
        comment.setUser(user_current);
        comment.setSentby(comment.getUser().getUsername());
        comment.setPosteddate(ourJavaDateObject);
        commentRepository.save(comment);
        user.addComment(comment);
        userRepository.save(user);
        model.addAttribute("user", user);
        return "user";

    }


/* ProcessFormText*/




}
