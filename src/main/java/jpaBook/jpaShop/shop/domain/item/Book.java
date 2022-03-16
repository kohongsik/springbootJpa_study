package jpaBook.jpaShop.shop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;
}
