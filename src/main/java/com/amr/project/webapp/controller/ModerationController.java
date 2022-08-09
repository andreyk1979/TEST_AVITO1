package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/moderation")
public class ModerationController {

    private ShopMapper shopMapper;
    private ItemMapper itemMapper;
    private ReviewMapper reviewMapper;
    private ModerationService moderationService;

    private Map<ShopDto, String> shopsForModel;
    private Map<ItemDto, String> itemsForModel;

    @Autowired
    public ModerationController(ShopMapper shopMapper, ItemMapper itemMapper, ReviewMapper reviewMapper, ModerationService moderationService) {
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
        this.moderationService = moderationService;
    }

    @GetMapping()
    public String showModerationPage(Model model) throws UnsupportedEncodingException {
        shopsForModel = new HashMap<>();
        itemsForModel = new HashMap<>();

        List<ShopDto> shops = shopMapper.toDtoList(moderationService.getShopsToBeModerated());
        for (ShopDto shop : shops) {
            ImageDto image = shop.getLogo();
            byte[] byteLogo = Base64.getEncoder().encode(image.getPicture());
            shopsForModel.put(shop, new String(byteLogo, "UTF-8"));
        }
        shops.size();
        model.addAttribute("shopsForModeration", shopsForModel);

        List<ItemDto> items = itemMapper.toDtoList(moderationService.getItemsToBeModerated());
        for (ItemDto item : items) {

            // Для отображение у модератора главной картинки

//            Optional<ImageDto> mainImage = item.getImages().stream().filter(ImageDto::getIsMain).findFirst();
//            byte[] byteLogo = Base64.getEncoder().encode(mainImage.get().getPicture());

            ImageDto image = item.getImages().get(0);
            byte[] byteLogo = Base64.getEncoder().encode(image.getPicture());
            itemsForModel.put(item, new String(byteLogo, "UTF-8"));
        }
        model.addAttribute("itemsForModeration", itemsForModel);

        List<ReviewDto> reviewsForModel = reviewMapper.toDtoList(moderationService.getReviewsToBeModerated());
        model.addAttribute("reviewsForModeration", reviewsForModel);


        model.addAttribute("reason_review", new ReviewDto());
        model.addAttribute("reason_item", new ItemDto());
        model.addAttribute("reason_shop", new ShopDto());

        return "moderatorPage";
    }

    @PostMapping("/accept_review/{id}")
    public String acceptReview(@PathVariable(name = "id") int id) {

        moderationService.acceptReview(id);

        return "redirect:/moderation";
    }

    @PostMapping("/decline_review/{id}")
    public String declineReview(@ModelAttribute("review_and_reason") ReviewDto review, @PathVariable(name = "id") int id) {

        moderationService.declineReview(id,review.getText());

        return "redirect:/moderation";
    }

    @PostMapping("/accept_item/{id}")
    public String acceptItem(@PathVariable(name = "id") int id) {
        moderationService.acceptItem(id);

        return "redirect:/moderation";
    }

    @PostMapping("/decline_item/{id}")
    public String declineReview(@ModelAttribute("item_and_reason") ItemDto item, @PathVariable(name = "id") int id) { //ошибка в нэйминге. Должен быть declineItem
        moderationService.declineItem(id,item.getDescription());

        return "redirect:/moderation";
    }

    @PostMapping("/accept_shop/{id}")
    public String acceptShop(@PathVariable(name = "id") int id) {
        moderationService.acceptShop(id);

        return "redirect:/moderation";
    }

    @PostMapping("/decline_shop/{id}")
    public String declineShop(@ModelAttribute("item_and_reason") ShopDto shop, @PathVariable(name = "id") int id) {
        moderationService.declineShop(id,shop.getDescription());

        return "redirect:/moderation";
    }
}

