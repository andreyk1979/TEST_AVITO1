package com.amr.project.webapp.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.amr.project.facade.ModerationFacade;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.User;

@Controller
@RequestMapping("/moderation")
public class ModerationController {

    private final ModerationFacade moderationFacade;

    public ModerationController(ModerationFacade moderationFacade) {
        this.moderationFacade = moderationFacade;
    }

    @GetMapping()
    public String showModerationPage(Model model, @AuthenticationPrincipal User user) throws UnsupportedEncodingException {

        return moderationFacade.showModerationPage(model, user);
    }

    @PostMapping("/accept_review/{id}")
    public String acceptReview(@PathVariable(name = "id") int id) {

        return moderationFacade.acceptReview(id);
    }

    @PostMapping("/decline_review/{id}")
    public String declineReview(@ModelAttribute("review_and_reason") ReviewDto review, @PathVariable(name = "id") int id) {
        return moderationFacade.declineReview(review, id);
    }

    @PostMapping("/accept_item/{id}")
    public String acceptItem(@PathVariable(name = "id") int id) {
        return moderationFacade.acceptItem(id);
    }

    @PostMapping("/decline_item/{id}")
    public String declineReview(@ModelAttribute("item_and_reason") ItemDto item, @PathVariable(name = "id") int id) { //ошибка в нэйминге. Должен быть declineItem
        return moderationFacade.declineReview(item, id);
    }

    @PostMapping("/accept_shop/{id}")
    public String acceptShop(@PathVariable(name = "id") int id) {
        return moderationFacade.acceptShop(id);
    }

    @PostMapping("/decline_shop/{id}")
    public String declineShop(@ModelAttribute("item_and_reason") ShopDto shop, @PathVariable(name = "id") int id) {
        return moderationFacade.declineShop(shop, id);
    }
}

