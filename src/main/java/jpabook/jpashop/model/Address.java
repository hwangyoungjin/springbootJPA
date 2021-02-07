package jpabook.jpashop.model;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipCode;

    protected Address() { //필요없지만 Jpa 스펙상 넣어줘야 한다
        // 사용못하도록 protected까진 접근제어자 사용 가능 -> public은 사용안하는게 좋다
    }

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
