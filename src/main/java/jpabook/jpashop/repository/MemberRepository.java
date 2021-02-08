package jpabook.jpashop.repository;
import jpabook.jpashop.model.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Member member){
        entityManager.persist(member);
    }

    public Member findOne(Long id){ //type과 pk
        return entityManager.find(Member.class, id);
    }

    public List<Member> findAll(){ //jpql from의 대상이 테이블이 아닌 엔티티
        return entityManager.createQuery("select  m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return entityManager.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
