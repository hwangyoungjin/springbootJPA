package jpabook.jpashop.controller;

import jpabook.jpashop.model.Address;
import jpabook.jpashop.model.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(c->{System.out.println(c.toString());});
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(),memberForm.getStreet(),memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/"; //home으로 redirect
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

    @Getter
    @Setter
    static class MemberForm {

        @NotEmpty(message = "회원이름은 필수 입니다.")
        private String name;

        private String city;
        private String street;
        private String zipcode;
    }
}
