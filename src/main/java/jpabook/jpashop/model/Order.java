package jpabook.jpashop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //dn에서 사용하는 테이블명
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") // DB에서의 컬럼명
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // DB에서의 FK 컬럼명
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //자바 8이상부터 Hibernate가 지원 - 값들어갈때 시간 자동 저장됨
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.getOrderItems().add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.setDelivery(delivery);
        delivery.setOrder(this);
    }
}
