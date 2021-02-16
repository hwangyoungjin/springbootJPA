package jpabook.jpashop;

import jpabook.jpashop.model.*;
import jpabook.jpashop.model.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 *  총 주문 2개, 각 주문의 아이템 2개씩
 *  1. userA
 *    - jpa1 book
 *    - jpa2 book
 *  2. userB
 *    - spring1 book
 *    - spring2 book
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("jpa1 book");
            book1.setPrice(100000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("jpa2 book");
            book2.setPrice(200000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,100000,1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,400000,2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("부산", "2", "2222"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("Spring1 book");
            book1.setPrice(20000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("Spring2 book");
            book2.setPrice(40000);
            book2.setStockQuantity(300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1,40000,2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2,120000,3);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
            em.persist(order);
        }
    }
}
