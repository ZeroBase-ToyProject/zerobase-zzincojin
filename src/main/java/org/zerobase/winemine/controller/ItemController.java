package org.zerobase.winemine.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.zerobase.winemine.config.auth.PrincipalDetails;
import org.zerobase.winemine.domain.item.Item;
import org.zerobase.winemine.domain.user.User;
import org.zerobase.winemine.service.ItemService;
import org.zerobase.winemine.service.UserPageService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final UserPageService userPageService;



    // 메인 페이지 (로그인 유저) - 관리자, 유저로 로그인
    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // 관리자
            int adminId = principalDetails.getUser().getId();
            List<Item> items = itemService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(adminId));

            return "/main";
        } else {
            // 유저
            int userId = principalDetails.getUser().getId();
            List<Item> items = itemService.allItemView();
            model.addAttribute("items", items);
            model.addAttribute("user", userPageService.findUser(userId));

            return "/main";
        }
    }

    // 상품 등록 페이지 - 관리자만 가능
    @GetMapping("/item/new")
    public String itemSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // 관리자
            model.addAttribute("user", principalDetails.getUser());

            return "/admin/itemForm";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }

    // 상품 등록 (POST) - 관리자만 가능
    @PostMapping("/item/new/pro")
    public String itemSave(Item item, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile,Model model) throws Exception {
        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // 관리자
            model.addAttribute("user", principalDetails.getUser());
            itemService.saveItem(item, imgFile);

            return "redirect:/main";
        } else {
            // 일반 회원이면 거절 -> main
            return "redirect:/main";
        }
    }


    // 상품 상세 페이지 - 관리자, 유저 가능
    @GetMapping("/item/view/{itemId}")
    public String ItemView(Model model, @PathVariable("itemId") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails.getUser().getRole().equals("ROLE_ADMIN")) {
            // 관리자
            User user = principalDetails.getUser();

            model.addAttribute("item", itemService.itemView(id));
            model.addAttribute("user", user);

            return "itemView";
        } else {
            // 유저
            User user = principalDetails.getUser();

            // 페이지에 접속한 유저를 찾아야 함
            User loginUser = userPageService.findUser(user.getId());


            model.addAttribute("item", itemService.itemView(id));
            model.addAttribute("user", user);

            return "itemView";
        }
    }

    // 상품 상세 페이지 - 로그인 안 한 유저
    @GetMapping("/item/view/nonlogin/{id}")
    public String nonLoginItemView(Model model, @PathVariable("id") Integer id) {
        // 로그인 안 한 유저
        model.addAttribute("item", itemService.itemView(id));
        return "itemView";

    }


    // 상품 리스트 페이지 - 로그인 유저
    @GetMapping("/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        User user = userPageService.findUser(principalDetails.getUser().getId());

        Page<Item> items = null;

        if (searchKeyword == null) {  // 검색이 들어왔을 때
            items = itemService.allItemViewPage(pageable);
        } else {  // 검색이 들어오지 않았을 때
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("user", user);

        return "itemList";
    }

    // 상품 리스트 페이지 - 로그인 안 한 유저
    @GetMapping("/nonlogin/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword) {

        Page<Item> items = null;

        if (searchKeyword == null) {  // 검색이 들어왔을 때
            items = itemService.allItemViewPage(pageable);
        } else {  // 검색이 들어오지 않았을 때
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "itemList";
    }
}
