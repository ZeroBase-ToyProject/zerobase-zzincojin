package org.zerobase.winemine.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zerobase.winemine.config.auth.PrincipalDetailsService;

@Configuration // IoC 등록
@EnableWebSecurity // 해당 파일로 시큐리티 활성화, springSecurityFilterChain가 자동으로 포함
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;




    @Bean
    public BCryptPasswordEncoder encoder() {
        // DB 패스워드 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 서버 인증정보 저장 안할 경우
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())

                // form 기반 로그인 사용
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/signin")
                                .loginProcessingUrl("/signin") // 스프링 시큐리티가 로그인 자동 진행 (Post)
                                .defaultSuccessUrl("/",true)

                )
                //User 로그인 검증 및 데이터처리
                .userDetailsService(principalDetailsService);




        return http.build();

    }

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); // 이 코드 삭제하면 기존 시큐리티가 가진 모든 기능 비활성화
        http.csrf().disable(); // csrf 토큰 비활성화 코드

        http.authorizeRequests()
                .antMatchers("/main/**").authenticated() // 로그인을 요구함
                .anyRequest().permitAll() // 그게 아닌 모든 주소는 인증 필요 없음
                .and()
                .formLogin()
                .loginPage("/signin") // 인증필요한 주소로 접속하면 이 주소로 이동시킴 (Get)
                .loginProcessingUrl("/signin") // 스프링 시큐리티가 로그인 자동 진행 (Post)
                .defaultSuccessUrl("/main"); // 로그인이 정상적이면 "/" 로 이동
    }*/

}
