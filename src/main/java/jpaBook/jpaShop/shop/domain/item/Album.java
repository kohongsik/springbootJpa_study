package jpaBook.jpaShop.shop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DiscriminatorValue("A")
public class Album extends Item{
    private String artist;
    private String etc;
}
