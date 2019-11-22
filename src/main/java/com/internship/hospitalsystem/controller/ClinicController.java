package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Clinic;
import com.internship.hospitalsystem.model.Position;
import com.internship.hospitalsystem.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/clinics")
public class ClinicController {

    @Autowired
    ClinicRepository clinicRepository;

    @GetMapping
    public String index(Model model){
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute("clinics", clinics);
        return "clinics/index";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute("clinics", clinics);
        return "clinics/list-clinics :: clinicList";
    }

    @GetMapping("/list/{keyword}")
    public String list(@PathVariable("keyword") String keyword,Model model){
        List<Clinic> clinics = clinicRepository.findAll();
        model.addAttribute("clinics", clinics);
        return "clinics/list-clinics :: clinicList";
    }

    @GetMapping("/add")
    public String showClinicForm(Clinic clinic) {
        return "clinics/add-clinic";
    }

    @PostMapping("/add")
    public String saveClinic(Clinic clinic, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "clinics/add-clinic";
        }
        clinicRepository.save(clinic);
        model.addAttribute("clinics", clinicRepository.findAll());
        return "redirect:/clinics";
    }

    @GetMapping("/edit/{id}")
    public String showEditClinicForm(@PathVariable("id") Long id, Model model){
        Clinic clinic = clinicRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("clinic", clinic);
        return "clinics/edit-clinic";
    }

    @PostMapping("/edit/{id}")
    public String editClinic(@PathVariable("id") Long id, Clinic clinic, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            clinic.setId(id);
            return "clinics/edit-clinic";
        }
        clinicRepository.save(clinic);
        return "redirect:/clinics";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteClinics(@PathVariable("id") Long id){
        clinicRepository.deleteById(id);
        return "redirect:/clinics";
    }
}
