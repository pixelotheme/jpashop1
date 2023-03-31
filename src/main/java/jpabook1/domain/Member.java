package jpabook1.domain;


import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //order 엔티티의 member 필드에의해 정의된내용을 보여준다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
}
