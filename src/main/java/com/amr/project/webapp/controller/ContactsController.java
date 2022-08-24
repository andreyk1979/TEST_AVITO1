package com.amr.project.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.amr.project.facade.ContactsFacade;
import com.amr.project.model.entity.User;

@Controller
public class ContactsController {
    private final ContactsFacade contactsFacade;

    public ContactsController(ContactsFacade contactsFacade) {
        this.contactsFacade = contactsFacade;
    }

    @GetMapping("/contacts")
    public String getContactsPage (Model model, @AuthenticationPrincipal User user) {
        return contactsFacade.getContactsPage(model, user);
    }
}
