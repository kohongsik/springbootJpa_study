package jpaBook.jpaShop.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpaBook.jpaShop.shop.domain.common.Address;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    @Column(name="member_name")
    private String name;

    @Embedded
    private Address address;
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void addOrder (Order order) {
        this.orders.add(order);
        order.setMember(this);
    }
}
