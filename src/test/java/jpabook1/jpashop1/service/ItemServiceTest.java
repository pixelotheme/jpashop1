package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.item.Book;
import jpabook1.jpashop1.domain.item.Item;
import jpabook1.jpashop1.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 아이템_저장() throws Exception {
        //given
        Book book = new Book();
        book.setName("책이름");
        book.setAuthor("저자");
        book.setIsbn("1101");
        book.setPrice(20000);
        book.setStockQuantity(10000);
        System.out.println("book1111 = " + book.getId()); //flush 전에는 id 값이 없다
        //when
        Long bookId = itemService.saveItem(book);// book이 item을 상속 받았기 때문에 가능
        Item findOne = itemRepository.findOne(bookId);
        //then
        em.flush();
        //같은 영속성 컨텍스트이다 - book은 영속화뒤 id 를 갖고있는 상태로 반환된다
        assertEquals(book, findOne);

        System.out.println("findOne = " + findOne.getId());
        System.out.println("book = " + book.getId());
    }
}