package jpabook.jpashop.service;

import jpabook.jpashop.model.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용 메서드 최적화, 변형감지 등등을 안한다,
@RequiredArgsConstructor //final 필드에 생성자를 통해 인젝션 해준다.
public class MemberService {

    //@RequiredArgsConstructor 를 통해 주입됨
    private final MemberRepository memberRepository;

    //==회원 가입
    //쓰기 이므로 reanOnly 반드시 false로 해야한다.
    @Transactional //기본적으로 readOnly = false
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){ //해당이름의 member가 존재한다면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //id로 하나만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
