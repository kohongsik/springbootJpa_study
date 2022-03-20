package jpaBook.jpaShop.shop.controller;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookForm {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

}
