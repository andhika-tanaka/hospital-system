package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Position;
import com.internship.hospitalsystem.model.User;
import com.internship.hospitalsystem.repository.PositionRepository;
import com.internship.hospitalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PositionRepository positionRepository;

    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("users/list-user");
        List<User> users = userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

    @GetMapping("/add")
    public String showUserForm(Model model)
    {
        List<Position> positions = positionRepository.findAll();
        User user = new User();
        model.addAttribute(user);
        model.addAttribute("positions", positions);
        return "users/add-user";
    }

    @PostMapping("/add")
    public String saveUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/add-user";
        }
        user.setCreated_at(LocalDateTime.now());
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Position> positions = positionRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("positions", positions);
        return "users/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit-user";
        }
        user.setUpdated_at(LocalDateTime.now());
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
