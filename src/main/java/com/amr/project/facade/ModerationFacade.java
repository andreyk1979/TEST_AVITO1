package com.amr.project.facade;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ModerationService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class ModerationFacade {

    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ReviewMapper reviewMapper;
    private final ModerationService moderationService;

    private final UserService userService;

    private Map<ShopDto, String> shopsForModel;
    private Map<ItemDto, String> itemsForModel;

    public ModerationFacade(ShopMapper shopMapper,
                            ItemMapper itemMapper,
                            ReviewMapper reviewMapper,
                            ModerationService moderationService,
                            UserService userService) {
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
        this.moderationService = moderationService;
        this.userService = userService;
    }

    public String showModerationPage(Model model, User user) throws UnsupportedEncodingException {

        model.addAttribute("activeUser", user);

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

    public String acceptReview(int id) {

        moderationService.acceptReview(id);

        return "redirect:/moderation";
    }

    public String declineReview(ReviewDto review, int id) {

        moderationService.declineReview(id,review.getText());

        return "redirect:/moderation";
    }

    public String acceptItem(int id) {
        moderationService.acceptItem(id);

        return "redirect:/moderation";
    }

    public String declineReview(ItemDto item, int id) { //ошибка в нэйминге. Должен быть declineItem
        moderationService.declineItem(id, item.getDescription());

        return "redirect:/moderation";
    }

    public String acceptShop(int id) {
        moderationService.acceptShop(id);

        return "redirect:/moderation";
    }

    public String declineShop(ShopDto shop, int id) {
        moderationService.declineShop(id, shop.getDescription());

        return "redirect:/moderation";
    }
}
