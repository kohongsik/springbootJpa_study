package jpaBook.jpaShop.shop.common;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
