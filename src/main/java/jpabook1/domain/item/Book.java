package jpabook1.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")//싱글테이블이라 저장핼때 구분할수있게 설정
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
