package com.maltseva.controller;

import com.google.i18n.phonenumbers.*;
import com.maltseva.entity.*;
import com.maltseva.service.contract.*;
import com.maltseva.viewmodel.ContactCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {
    List<Contact> contacts = null;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showAllContacts(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());

        contacts = contactService.findAll(user.getId());
        model.addAttribute("userName", user.getFullName());

        if (contacts != null) {
            model.addAttribute("contact", contacts);
        } else {
            model.addAttribute("message", "Your Phone Book is empty. Please add new contact.");
            return "newContact";
        }
        return "listOfContacts";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchRequest, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());

        contacts = contactService.findBySearch(searchRequest);
        model.addAttribute("userName", user.getFullName());

        if (contacts != null) {
            model.addAttribute("contact", contacts);
        } else {
            model.addAttribute("message", "Unfortunately, your search returned no results. Please try again.");
            return showAllContacts(model, request, response);
        }
        return "listOfContacts";
    }

    @GetMapping("/new")
    public String newContact(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());

        model.addAttribute("userName", user.getFullName());
        model.addAttribute("contact", new Contact());

        return "newContact";
    }

    @GetMapping("/update")
    public String edit(@RequestParam int id, ModelMap model, HttpServletRequest request, HttpServletResponse response){
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());
        Contact contactToEdit = contactService.findById(id);

        model.addAttribute("userName", user.getFullName());
        model.addAttribute("contact", contactToEdit);

        return "newContact";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id, ModelMap model) {
        contactService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/create")
    @Transactional
    public String save(@Valid @ModelAttribute("contact") ContactCreate contact, BindingResult bindingResult, ModelMap model,
                       HttpServletRequest request, HttpServletResponse response){
        User user = userService.findUserByLogin(request.getUserPrincipal().getName());

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber uaCellPhone = parseEnteredPhoneNumber(phoneUtil, contact.getCellPhone());

        if(uaCellPhone == null || !phoneUtil.isValidNumberForRegion(uaCellPhone, "UA")){
            bindingResult.addError(new FieldError("contact", "cellPhone", "cell phone format is incorrect"));
        }

        String homePhoneForParse = contact.getHomePhone();
        Phonenumber.PhoneNumber uaHomePhone = null;
        if(!homePhoneForParse.isEmpty()){
            uaHomePhone = parseEnteredPhoneNumber(phoneUtil, homePhoneForParse);

            if(uaHomePhone == null || !phoneUtil.isValidNumberForRegion(uaHomePhone, "UA")){
                bindingResult.addError(new FieldError("contact", "homePhone", "home phone format is incorrect"));
            }
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("userName", user.getFullName());
            return "newContact";
        }

        Contact newContact = new Contact(user.getId(),
                contact.getLastName(),
                contact.getFirstName(),
                contact.getMiddleName(),
                phoneUtil.format(uaCellPhone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));

        newContact.setId(contact.getId());

        if(!contact.getHomePhone().isEmpty())
            newContact.setHomePhone(phoneUtil.format(uaHomePhone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));

        if(!contact.getAddress().isEmpty())
            newContact.setAddress(contact.getAddress());

        if(!contact.getEmail().isEmpty())
            newContact.setEmail(contact.getEmail());

        contactService.save(newContact);

        return "redirect:/";
    }

    private Phonenumber.PhoneNumber parseEnteredPhoneNumber(PhoneNumberUtil phoneUtil, String phoneNumberForParse){
        Phonenumber.PhoneNumber uaParsedPhoneNumber = null;

        try {
            uaParsedPhoneNumber = phoneUtil.parse(phoneNumberForParse, "UA");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return (uaParsedPhoneNumber == null)
                ? null
                : uaParsedPhoneNumber;
    }
}

