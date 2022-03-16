package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // test code에서는 기본이 commit 이 아니라 rollback임

class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Test // 회원가입
    public void join () throws Exception {
        // given
        Member member = Member
                .builder()
                .name("kim")
                .build();

        // when
        Long saveId = memberService.join(member); // insert 쿼리를 보고싶으면 @Rollback(false)로 설정
        em.flush(); // rollback 하면서 콘솔에 볼 수 있음.
        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test // 중복 회원 예외
    public void checkDuplicatedMember () throws Exception {
        // given
        Member member = Member
                .builder()
                .name("kim1")
                .build();
        Member dupl = Member
                .builder()
                .name("kim1")
                .build();

        // when
        memberService.join(member);

        // then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(dupl);
        });
    }

}