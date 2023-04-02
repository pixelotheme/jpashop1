package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.Address;
import jpabook1.jpashop1.domain.Member;
import jpabook1.jpashop1.domain.Order;
import jpabook1.jpashop1.domain.OrderStatus;
import jpabook1.jpashop1.domain.item.Book;
import jpabook1.jpashop1.domain.item.Item;
import jpabook1.jpashop1.exception.NotEnoughStockException;
import jpabook1.jpashop1.repository.OrderRepository;
import jpabook1.jpashop1.repository.OrderSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("책명", 10000, 20);


        //when
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Long orderId2 = orderService.order(member.getId(), book.getId(), orderCount);
        Long orderId3 = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);


        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getOrderStatus());
        //주문상품의 List 가 단건이어야 한다
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        //assertEquals("주문 수량만큼 재고가 줄어야 한다.", 18, book.getStockQuantity());

        OrderSearch orderSearch = new OrderSearch();
//        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("회원2");

        List<Order> all = orderRepository.findAllByCriteria(orderSearch);

        all.forEach((temp) -> {
            System.out.println("temp = " + temp.toString());
        });



    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given

        //when
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;
        //then

        orderService.order(member.getId(), item.getId(), orderCount);

        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getOrderStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다", 10, item.getStockQuantity());

    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }


}