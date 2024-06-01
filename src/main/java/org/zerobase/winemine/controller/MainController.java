package org.zerobase.winemine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerobase.winemine.domain.item.Item;
import org.zerobase.winemine.service.ItemService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final ItemService itemService;
    @RequestMapping("/")
    public String mainPageNoneLogin(Model model) {
        // 로그인을 안 한 경우
        List<Item> items = itemService.allItemView();
        model.addAttribute("items", items);

        return "main";
    }
}
