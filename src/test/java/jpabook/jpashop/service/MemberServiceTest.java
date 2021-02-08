package jpabook.jpashop.service;

import jpabook.jpashop.model.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 기능")
    public void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("Kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    @DisplayName("중복 회원 에러 확인")
    public void error() throws Exception{
        //given
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("kim1");
        member2.setName("kim1");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생 해야 한다.

        //then
        fail("예외가 발생해야 한다."); //예외발생안하면 fail에 도착해서 테스트 실해
    }
}