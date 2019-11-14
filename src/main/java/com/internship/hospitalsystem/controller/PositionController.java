package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Position;
import com.internship.hospitalsystem.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    PositionRepository positionRepository;

    @GetMapping
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView("positions/list-position");
        List<Position> positions = positionRepository.findAll();
        mav.addObject("positions", positions);
        return mav;
    }

    @GetMapping("/add")
    public String showPositionForm(Position position) {
        return "positions/add-position";
    }

    @PostMapping("/add")
    public String savePosition(Position position, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "positions/add-position";
        }
        positionRepository.save(position);
        model.addAttribute("positions", positionRepository.findAll());
        return "redirect:/positions";
    }

    @GetMapping("/edit/{id}")
    public String showEditPositionForm(@PathVariable("id") Long id, Model model){
        Position position = positionRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("position", position);
        return "positions/edit-position";
    }

    @PostMapping("/edit/{id}")
    public String editMedicine(@PathVariable("id") Long id, Position position, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            position.setId(id);
            return "positions/edit-position";
        }
        positionRepository.save(position);
        return "redirect:/positions";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePosition(@PathVariable("id") Long id){
        positionRepository.deleteById(id);
        return "redirect:/positions";
    }
}
