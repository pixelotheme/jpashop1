package jpabook1.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //dtype 컬럼일때 해당 어노테이션 value의 클래스를 가져온다
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //상속관계
    private String name;
    private int price;
    private int stockQuantity;

    //나머지 컬럼들은 상속관계의 각 엔티티에 있다
}