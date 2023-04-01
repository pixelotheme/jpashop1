package jpabook1.jpashop1.domain;

import jpabook1.jpashop1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private  String name;

    @ManyToMany // 추후에 categoryItem 만들고 다대일, 일대다 로 매핑할 예정
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )//실무에서는 필드추가가 불가해 안쓴다 카테고리와 아이템 사이의 테이블로 연결
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) // 셀프로 양방향 연관관계를 걸었다 (같은 엔티티에 대하여)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 부모는 여러 자식 가능
    private List<Category> child = new ArrayList<>();

    // == 연관관계 편의 메서드
    public void addChildCategory(Category child){
        child.setParent(this);
    }
}
