package org.zerobase.winemine.biz.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerobase.winemine.biz.member.entity.Member;
import org.zerobase.winemine.biz.member.model.MemberInput;
import org.zerobase.winemine.biz.member.repository.MemberRepository;
import org.zerobase.winemine.biz.member.service.MemberService;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register(){
        System.out.println("get!!!!!!!!!");
        return "member/register";
    }
    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request
                                , MemberInput parameter){

        boolean result = memberService.register(parameter);

        model.addAttribute("result", result);

        return "member/register_complete";
    }
}
