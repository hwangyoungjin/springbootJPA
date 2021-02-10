package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "책이름은 필수입니다.")
    private String name;

    @Min(value = 1000, message = "가격은 1000원 이상입니다.")
    private int price;

    @Min(value = 1,message = "1개 이상 입력해주세요")
    private int stockQuantity;

    private String author;
    private String isbn;
}
