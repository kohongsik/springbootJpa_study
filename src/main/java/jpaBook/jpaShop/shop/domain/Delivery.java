package jpaBook.jpaShop.shop.domain;

import jpaBook.jpaShop.shop.domain.common.Address;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Setter(AccessLevel.NONE)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus status;

    public void setAddress (Address address) {
        // 러퍼런스 새로 할당.
        this.address = new Address(address.getCity(), address.getCity(), address.getZipcode());
    }
}
