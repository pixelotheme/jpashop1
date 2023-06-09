package jpabook1.jpashop1.domain.item;

import jpabook1.jpashop1.domain.Category;
import jpabook1.jpashop1.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // == 비즈니스 로직 == //
    /**
    * stock 증가*/
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
