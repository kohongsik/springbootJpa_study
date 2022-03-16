package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // 트랜젝션이 꼭있어야함.
/* Transactional 은 spring 에서 제공하는 어노테이션으로 써야 쓸 수 있는게 많음. */
@RequiredArgsConstructor // 생성자가 하나있으면 자동으로 인젝션 주입하는 스프링 부트의 특성을 받아 final만 받으면 바로 주입가능.
public class MemberService {
    private final MemberRepository memberRepository;
    /*
    @Autowired
    public void setMemberRepository (MemberRepository memberRepository) {
        // 장점 : 테스트시 mock 같은것을 주입할 수 있슴
        // 단점 : 애플리케이션 로딩 시점에 바뀔 수 있음(실상에 애플리케이션 로딩 시점에 다 설정이 됨)
        this.memberRepository = memberRepository;
    }
    */

    // 아래를 모두 해결해주는게 requiredArgsConstructor
    /*
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService (MemberRepository memberRepository) {
        // set autowired 의 단점을 보완
        // 생성자가 하나밖에 없으면 자동으로 injection을 해준다.
        this.memberRepository = memberRepository;
    }*/


    // 회원가입
    public Long join(Member member) {
        // user name dupl check
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // was 를 여러개 돌리는 상황에서 동시에 들어올때 중복체크가 안될 수 있기때문에 가장좋은건 unique constraints
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    // 회원 전체 조회
    @Transactional(readOnly = true) // 단순조회시 읽기전용으로 선언하면 조금 성능을 최적화 할 수 있음(영속성 컨텍스트 변경감지 등 더티체킹 안해도됨)
    public List<Member> findMembers () {
        return memberRepository.findAll();
    }
    // 회원 전체 조회 페이징 고려
    @Transactional(readOnly = true)
    public List<Member> findMembers (int offset, int size) {
        return memberRepository.findAll(offset, size);
    }
    @Transactional(readOnly = true)
    public Member findMember (Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
