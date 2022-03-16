package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class MemberRepository {
    @PersistenceContext // spring boot에서 엔티티 펙토리 등록 후, 엔티티 메니저 자동 주입
    private EntityManager em;
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
