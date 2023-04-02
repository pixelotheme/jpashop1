package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.Delivery;
import jpabook1.jpashop1.domain.Member;
import jpabook1.jpashop1.domain.Order;
import jpabook1.jpashop1.domain.OrderItem;
import jpabook1.jpashop1.domain.item.Item;
import jpabook1.jpashop1.repository.ItemRepository;
import jpabook1.jpashop1.repository.MemberRepository;
import jpabook1.jpashop1.repository.OrderRepository;
import jpabook1.jpashop1.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 - 업무로직
     * 1. 각 회원, 상품 정보 조회
     * 2. 회원 정보의 주소를 Delivery에 넘긴다
     * 3. OrderItem클래스의 createOrderItem 메서드에 상품, 상품가격, 개수 를 넘긴다
     * 4. Order클래스의 createOrder 메서드에 회원, 배송지, 주문상품 을 넘긴다
     * 5. orderRepository의 save 메서드로 order 에 저장한 회원, 배송지, 주문상품정보를 DB에 반영한다
     * */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId); // 주문할 회원정보를 가져온다
        Item item = itemRepository.findOne(itemId); // 주문할 상품을 가져온다

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //상품주문 데이터 넘긴다 - 직접 set으로 생성하지않고 도메인 단에서 생성시킨다
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();

    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);

        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
