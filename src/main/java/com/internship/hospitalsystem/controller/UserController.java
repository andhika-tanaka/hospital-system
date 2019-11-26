package com.internship.hospitalsystem.controller;

import com.internship.hospitalsystem.model.Role;
import com.internship.hospitalsystem.model.User;
import com.internship.hospitalsystem.repository.RoleRepository;
import com.internship.hospitalsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private List<Role> roles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleAuth = authentication.getAuthorities().toString();
        List<Role> roles = new ArrayList<>();
        if (roleAuth.contains("ADMIN")) {
            roles = roleRepository.findStaffs();
        } else if (roleAuth.contains("PEGAWAI")) {
            roles = roleRepository.findPatients();
        }
        return roles;
    }

    private List<User> users() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleAuth = authentication.getAuthorities().toString();
        List<User> users = new ArrayList<>();
        if (roleAuth.contains("ADMIN")) {
            users = userRepository.findStaffs();
        } else if (roleAuth.contains("PEGAWAI")) {
            users = userRepository.findPatients();
        } else {
            users = userRepository.findAll();
        }
        return users;
    }

    @GetMapping
    public String index(Model model) {
        List<User> users = users();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "keyword", required = false) String keywords, Model model) {
        List<User> users;
        if (keywords != null) {
            users = userRepository.searchStaffs(keywords);
        } else {
            users = users();
        }
        model.addAttribute("users", users);
        return "users/list-user :: userList";
    }

    @GetMapping("/add")
    public String showUserForm(Model model) {
        List<Role> roles = roles();
        model.addAttribute("roles", roles);
        User user = new User();
        model.addAttribute(user);
        return "users/add-user";
    }

    @PostMapping("/add")
    public String saveUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/add-user";
        }
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        List<Role> roles = roles();
        model.addAttribute("roles", roles);
        return "users/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "users/edit-user";
        }
        user.setActive(1);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/detail/{id}")
    public String showDetailForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/detail-user";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
