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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    RegistrationRepository registrationRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String index(Model model) {
        /*New Object to Add New Data to Table*/
        Registration registration = new Registration();
        model.addAttribute(registration);

        /*Patient List For Combo Box*/
        List<User> patients = userRepository.findPatients();
        model.addAttribute("patients", patients);

        /*Doctor List For Combo Box*/
        List<User> doctors = userRepository.findDoctors();
        model.addAttribute("doctors", doctors);

        /*Registration List*/
        List<Registration> registrations = registrationRepository.findByOrderByCheckupDateAscDoctorsAscNumberAsc();
        model.addAttribute("registrations", registrations);

        return "/registrations/index";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "keyword") String keyword, Model model) {
        List<Registration> registrations;
        if (keyword != null) {
            registrations = registrationRepository.findRegistration(keyword);
        } else {
            registrations = registrationRepository.findByOrderByCheckupDateAscDoctorsAscNumberAsc();
        }
        model.addAttribute("registrations", registrations);
        return "registrations/list-registration :: registrationList";
    }

    @PostMapping("/add")
    public String saveRegistration(Registration registration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registrations/data-registration";
        }
        Integer number = 0;

        Integer check = registrationRepository.last_number(registration.getCheckupDate(), registration.getDoctors().getId());
        if (check == null) {
            number = 1;
        } else {
            number = check + 1;
        }

        registration.setNumber(number);
        registrationRepository.save(registration);
        return "registrations/data-registration";
    }

    @GetMapping("/data")
    public ModelAndView data() {
        ModelAndView mav = new ModelAndView("registrations/data-registration");
        Registration registration = registrationRepository.findTopByOrderByIdDesc();
        mav.addObject(registration);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistration(@PathVariable("id") Long id) {
        registrationRepository.deleteById(id);
        return "redirect:/registrations";
    }
}
