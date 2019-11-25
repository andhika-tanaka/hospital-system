package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Role;
import com.internship.hospitalsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping
    public String index(Model model){
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "roles/index";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "roles/list-role :: roleList";
    }

    @GetMapping("/list/{keyword}")
    public String list(@PathVariable("keyword") String keyword, Model model){
        List<Role> roles = roleRepository.findRole(keyword);
        model.addAttribute("roles", roles);
        return "roles/list-role :: roleList";
    }

    @GetMapping("/add")
    public String showRoleForm(Model model) {
        Role role = new Role();
        model.addAttribute(role);
        return "roles/add-role";
    }

    @PostMapping("/add")
    public String saveRole(Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "roles/add-role";
        }
        role.setRole("ROLE_" + role.getTitle());
        roleRepository.save(role);
        model.addAttribute("roles", roleRepository.findAll());
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable("id") Long id, Model model){
        Role role = roleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("role", role);
        return "roles/edit-role";
    }

    @PostMapping("/edit/{id}")
    public String editMedicine(@PathVariable("id") Long id, Role role, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            role.setId(id);
            return "roles/edit-roles";
        }
        role.setRole("ROLE_" + role.getTitle());
        roleRepository.save(role);
        return "redirect:/roles";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePosition(@PathVariable("id") Long id){
        roleRepository.deleteById(id);
        return "redirect:/roles";
    }
}
