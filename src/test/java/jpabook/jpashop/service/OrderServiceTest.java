package jpabook.jpashop.service;

import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.model.Address;
import jpabook.jpashop.model.Member;
import jpabook.jpashop.model.Order;
import jpabook.jpashop.model.OrderStatus;
import jpabook.jpashop.model.item.Book;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문이 잘 되는지 테스트")
    public void order() throws Exception{
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123"));

        Book book = createBook("시골 jpa", 10100, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격*수량이다",10100*orderCount,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8,book.getStackQuantity());
    }

    @Test
    @DisplayName("주문취소가 잘 되는지")
    public void orderCancel() throws Exception{
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123"));
        Book book = createBook("시골 jpa", 10000, 10);
        int orderCount =2;
        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문 취소시 재고 수량은 바뀌지 않아야 한다",10,book.getStackQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    @DisplayName("예외 테스트 - 상품주문 재고수량 초과하는지")
    public void stockTest() throws Exception{
        //given
        Member member = createMember("회원1", new Address("서울", "강가", "123"));
        Book book = createBook("시골 jpa", 10000, 10);
        int orderCount =11;

        //when
        orderService.order(member.getId(),book.getId(),orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }


    private Book createBook(String name, int price, int stackQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStackQuantity(stackQuantity);
        entityManager.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        entityManager.persist(member);
        return member;
    }

}