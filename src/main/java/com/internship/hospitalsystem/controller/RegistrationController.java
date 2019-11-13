package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Registration;
import com.internship.hospitalsystem.model.User;
import com.internship.hospitalsystem.repository.RegistrationRepository;
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
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("registrations/list-registration");
        List<Registration> registrations = registrationRepository.findAll();
        mav.addObject("positions", registrations);
        return mav;
    }

    @GetMapping("/add")
    public String showUserRegistrationForm(Model model)
    {
        List<User> patients = userRepository.findPatients();
        List<User> doctors = userRepository.findDoctors();
        Registration registration = new Registration();
        model.addAttribute(registration);
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        return "registrations/add-registration";
    }

    @PostMapping("/add")
    public String saveRegistration(Registration registration,User doctor, User patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrations/add-registration";
        }
        registration.setCreatedAt(LocalDateTime.now());
        registration.setStatus(Registration.status.VISIT);
        registrationRepository.save(registration);
        userRepository.save(doctor);
        userRepository.save(patient);
        return "redirect:/registrations";
    }

    @RequestMapping(value = "/delete")
    public String deleteRegistration(@RequestParam(name = "id") Long id){
        registrationRepository.deleteById(id);
        return "redirect:/registrations";
    }
}
