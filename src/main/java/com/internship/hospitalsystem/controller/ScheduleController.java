package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Clinic;
import com.internship.hospitalsystem.model.Schedule;
import com.internship.hospitalsystem.model.User;
import com.internship.hospitalsystem.repository.ClinicRepository;
import com.internship.hospitalsystem.repository.ScheduleRepository;
import com.internship.hospitalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @GetMapping
    public String index(Model model){
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "schedules/index";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "schedules/list-schedule :: scheduleList";
    }

    @GetMapping("/list/{keyword}")
    public String list(@PathVariable("keyword") String keyword,Model model){
        List<Schedule> schedules = scheduleRepository.findSchedule(keyword);
        model.addAttribute("schedules", schedules);
        return "schedules/list-schedule :: scheduleList";
    }

    @GetMapping("/add")
    public String showUserForm(Model model)
    {
        List<User> users = userRepository.findStaffs();
        List<Clinic> clinics = clinicRepository.findAll();
        Schedule schedule = new Schedule();
        model.addAttribute("users", users);
        model.addAttribute("clinics", clinics);
        model.addAttribute(schedule);
        return "schedules/add-schedule";
    }

    @PostMapping("/add")
    public String saveSchedule(Schedule schedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedules/add-schedule";
        }
        scheduleRepository.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        Schedule schedule = scheduleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<User> users = userRepository.findStaffs();
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute(schedule);
        model.addAttribute("users", users);
        model.addAttribute("clinics", clinics);
        return "schedules/edit-schedule";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Schedule schedule, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            schedule.setId(id);
            return "redirect:/schedules";
        }
        scheduleRepository.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteSchedule(@PathVariable("id") Long id){
        scheduleRepository.deleteById(id);
        return "redirect:/schedules";
    }
}
