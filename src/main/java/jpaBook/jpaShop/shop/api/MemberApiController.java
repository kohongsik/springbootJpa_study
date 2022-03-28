package jpaBook.jpaShop.shop.api;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody (to xml or to json)
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = "application/json")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping(value = "/v1/members")
    public CreateMemberResponse saveMemberV1 (@RequestBody @Valid Member member) {
        Long memberId = memberService.join(member);
        CreateMemberResponse createMemberResponse = new CreateMemberResponse();
        createMemberResponse.setId(memberId);
        return createMemberResponse;
    }
    @PostMapping(value = "/v2/members")
    public CreateMemberResponse saveMemberV2 (@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long memberId = memberService.join(member);
        CreateMemberResponse createMemberResponse = new CreateMemberResponse();
        createMemberResponse.setId(memberId);
        return createMemberResponse;
    }
    @PutMapping(value = "/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2 (@RequestBody UpdateMemberRequest request, @PathVariable("id")Long id) {
        memberService.update(id, request.getName());
        return new UpdateMemberResponse(id, request.getName());
    }
    @GetMapping(value = "/v1/members")
    public List<Member> membersV1 () {
        // 응답값에 필요없는 필드가 발생할 수 도,,
        // member -> order -> member 무한루프 에러가 발생.
        // list 를 반환하면 유연성이 확떨어짐 ( 배열 스펙에 맞춰야해서 resultCount 같은 신규 컬럼 추가를 못함)
        return memberService.findMembers();
    }
    @GetMapping(value = "/v2/members")
    public Result<List<MemberDto>> membersV2 () {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
    @Data
    static class UpdateMemberRequest {
        private String name;
    }
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        Long id;
        String name;
    }
    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }
    @Data
    static class CreateMemberResponse {
        private Long id;

    }

}
