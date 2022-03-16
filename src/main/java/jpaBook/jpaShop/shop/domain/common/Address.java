package jpaBook.jpaShop.shop.domain.common;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {
    // (임베디드)값타입은 세터를 없애고 기본 생성자가 모든필드를 담는걸로 해야한다.
    private String city;
    private String street;
    private String zipcode;
    protected Address () {
        // jpa 스펙상 기본생성자가 무조건 필요함, 외부 접근 안되도록 만듬
    }
    public Address (String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
