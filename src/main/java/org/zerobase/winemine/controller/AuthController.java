package org.zerobase.winemine.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerobase.winemine.domain.user.User;
import org.zerobase.winemine.service.AuthService;
import org.zerobase.winemine.web.dto.auth.SignupDto;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "signin";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupDto signupDto) {
        // User에 signupDto 넣음
        User user = signupDto.toEntity();

        System.out.println("user : "+user);

        User userEntity = authService.signup(user);
        System.out.println("userEntity : " + userEntity);
        return "signin";
    }
}
