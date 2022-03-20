package jpaBook.jpaShop.shop.controller;

import jpaBook.jpaShop.shop.domain.item.Book;
import jpaBook.jpaShop.shop.domain.item.Item;
import jpaBook.jpaShop.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }
    @PostMapping(value = "/items/new")
    public String create(BookForm bookForm) {

        // setter 보다 create 함수를 클래스내에서 만들어서 사용.. (domain model pattern)
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        itemService.save(book);

        return "redirect:/";
    }
    @GetMapping(value = "/items")
    public String list (Model model) {
        List<Item> items = itemService.findALl();
        model.addAttribute("items", items);
        return "/items/itemList";
    }
    @GetMapping("/items/{bookId}/edit")
    public String updateItemForm (@PathVariable Long bookId, Model model) {
        Book book = (Book) itemService.find(bookId);
        BookForm bookForm = new BookForm();
        bookForm.setId(book.getId());
        bookForm.setName(book.getName());
        bookForm.setPrice(book.getPrice());
        bookForm.setStockQuantity(book.getStockQuantity());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setIsbn(book.getIsbn());


        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }
    @PostMapping("/items/{bookId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm bookForm) {
        Book book = new Book();
        // Book book = (Book) itemService.find(bookForm.getId());
        // book.setId(bookForm.getId()); // id의 파라미터 변조가 많이 발생해서, 권한체크를 해야함.
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        // itemService.save(book);
        // 준영속상태로 있음 : dirty checking 이 안됨 .. 아무리 수정해도 업데이트 쿼리 생성이 안됨.
        // find 이후 영속석컨텍스트에서 관리를 하거나, merge를 해야함.
        return "redirect:/items";

    }
}
