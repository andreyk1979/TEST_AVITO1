package com.amr.project.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class ContactsFacade {

    private final UserService userService;

    public ContactsFacade(UserService userService) {
        this.userService = userService;
    }

    public String getContactsPage (Model model, User user) {
        model.addAttribute("activeUser", user);
        return "contactsPage";
    }
}
