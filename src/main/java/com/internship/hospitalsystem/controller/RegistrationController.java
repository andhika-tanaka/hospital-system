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
        /*Registration List*/
        List<Registration> registrations = registrationRepository.findAll();
        mav.addObject("registrations", registrations);

        /*Patient List For Combo Box*/
        List<User> patients = userRepository.findPatients();
        mav.addObject("patients", patients);

        /*Doctor List For Combo Box*/
        List<User> doctors = userRepository.findDoctors();
        Registration registration = new Registration();

        /*New Object to Add New Data to Table*/
        mav.addObject(registration);
        mav.addObject("doctors", doctors);
        return mav;
    }

    @PostMapping("/add")
    public String saveRegistration(Registration registration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrations/add-registration";
        }
        registrationRepository.save(registration);
        return "redirect:/registrations";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable("id") Long id){
        registrationRepository.deleteById(id);
        return "redirect:/registrations";
    }
}
