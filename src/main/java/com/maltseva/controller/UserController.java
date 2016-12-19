package com.maltseva.controller;

import com.maltseva.entity.User;
import com.maltseva.service.contract.*;
import com.maltseva.viewmodel.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(ModelMap model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Error! Login or password is incorrect. Please try again");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        model.addAttribute("mode", "MODE_HOME");
        return "login";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("mode", "MODE_REGISTER");
        return "register";
    }

    @PostMapping("/add")
    @Transactional
    public String addUser(@Valid @ModelAttribute("user") UserRegister user, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "MODE_REGISTER");
            return "register";
        }

        if (userService.findUserByLogin(user.getLogin()) != null) {
            model.addAttribute("mode", "ERROR_DUPLICATE");
            return "register";
        }

        User newUser = new User(user.getFullName(), user.getLogin(), user.getPassword());
        userService.save(newUser);
        model.addAttribute("mode", "MODE_HOME");
        return "login";
    }

    @PostMapping("/delete")
    public String deleteUser(ModelMap model, @ModelAttribute int id){
        return "register";
    }
}
