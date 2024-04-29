package org.zerobase.winemine.biz.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerobase.winemine.biz.member.entity.Member;
import org.zerobase.winemine.biz.member.model.MemberInput;
import org.zerobase.winemine.biz.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()){
            //현재 userId에 해당하는 데이터 존재
            return false;
        }

        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPhone(parameter.getPhone());
        member.setPassword(parameter.getPassword());
        member.setRegDt(LocalDateTime.now());
        memberRepository.save(member);

        return false;
    }
}
