package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Schedule;
import com.internship.hospitalsystem.model.User;
import com.internship.hospitalsystem.repository.ScheduleRepository;
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
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("users/list-user");
        List<Schedule> schedules = scheduleRepository.findAll();
        mav.addObject("schedules", schedules);
        return mav;
    }

    @GetMapping("/add")
    public String showUserForm(Model model)
    {
        List<User> users = userRepository.findStaffs();
        Schedule schedule = new Schedule();
        model.addAttribute(schedule);
        model.addAttribute("users", users);
        return "schedules/add-schedule";
    }

    @PostMapping("/add")
    public String saveSchedule(Schedule schedule,User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedules/add-schedule";
        }

        schedule.setCreated_at(LocalDateTime.now());
        scheduleRepository.save(schedule);
        userRepository.save(user);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        Schedule schedule = scheduleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<User> users = userRepository.findStaffs();
        model.addAttribute(schedule);
        model.addAttribute("users", users);
        return "schedules/edit-schedule";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit-medicine";
        }
        user.setUpdated_at(LocalDateTime.now());
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/delete")
    public String deleteSchedule(@RequestParam(name = "id") Long id){
        scheduleRepository.deleteById(id);
        return "index";
    }
}
