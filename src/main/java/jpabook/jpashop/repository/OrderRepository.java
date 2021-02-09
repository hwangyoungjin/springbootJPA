package jpabook.jpashop.repository;

import jpabook.jpashop.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order){
        entityManager.persist(order);
    }

    public Order findOne(Long id){
        return entityManager.find(Order.class, id);
    }

    /**
     * =검색로직=
     * 현재는 권장하지 않는 방법
     * 나중에 Querydsl로 바꿀꺼임
     */
//    public List<Order> findAll(OrderSearch orderSearch){ // order과 관련된 member 조인
//
//    }

}
