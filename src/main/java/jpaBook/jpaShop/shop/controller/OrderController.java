package jpaBook.jpaShop.shop.controller;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderSearch;
import jpaBook.jpaShop.shop.domain.item.Item;
import jpaBook.jpaShop.shop.service.ItemService;
import jpaBook.jpaShop.shop.service.MemberService;
import jpaBook.jpaShop.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping(value = "/order")
    public String createForm (Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findALl();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }
    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
    @GetMapping(value = "/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,  Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }
    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable(value = "orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
