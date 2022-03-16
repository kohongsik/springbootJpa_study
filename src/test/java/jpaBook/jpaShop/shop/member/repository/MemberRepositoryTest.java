package jpaBook.jpaShop.shop.member.repository;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // test에 트렌젝션이 있으면 롤벡을 반환
    @Rollback(false) // rollback false로 넣으면 트렌젝션 커밋 수행.
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUserName("memberA");
        // when
        Long id = memberRepository.save(member);
        Member findMember = memberRepository.find(id);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(findMember).isEqualTo(member); // == 비교. 같은 영속성 컨택스트 에서 조회하기때문에 같음
    }
}