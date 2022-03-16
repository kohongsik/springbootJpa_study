package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
@Repository // component scan 에 의해서 자동으로 spring bean 으로 등록
public class MemberRepository {
    // PersistenceContext // spring boot에서 엔티티 펙토리 등록 후, 엔티티 메니저 자동 주입
    // PersistenceContext 는 spring data jpa의 지원을 받아 autoWired 로 서 받을 수 도 있음. -> requiredArgsConstructor 가능
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public List<Member> findAll(int offset, int size) {
        return em.createQuery("select m from Member m", Member.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
    public List<Member> findLikeName(String name) {
        return em.createQuery("select m from Member m where m.name like :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
