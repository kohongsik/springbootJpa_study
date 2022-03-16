package jpaBook.jpaShop.shop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}
