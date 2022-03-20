package jpaBook.jpaShop.shop.controller;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.domain.common.Address;
import jpaBook.jpaShop.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String craeteForm (Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm.html";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        /*
            일반적으로 spring bean validate 에러가 발생하면
            함수가 exception이 나서 종료되지만,
            BindingResult 가 인자로 들어가 있으면
            종료가 나지않고 에러 상태가 BindingResult에 담겨서
            추가 처리를 할 수 있다.
        */
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getCity(), memberForm.getZipcode());

        Member member = Member.builder().name(memberForm.getName()).build();
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }
    @GetMapping(value = "/members")
    public String list (Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
