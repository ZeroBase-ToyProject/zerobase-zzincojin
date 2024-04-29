package org.zerobase.winemine.biz.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerobase.winemine.biz.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
