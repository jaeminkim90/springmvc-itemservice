package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // 필드 변수를 주입받는 생성자를 자동으로 만들어준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 상품 상세
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품 등록 폼
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // 상품 등록 데이터 처리
    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    // 상품 등록 데이터 처리
    // V1에 @ModelAttribute 이용하여 리팩토링
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        // 파라미터로 받는 Model도 생략 가능하다.

        /* @ModelAttribute가 아래 내용을 자동으로 처리한다.
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity); */

        itemRepository.save(item);

        // @ModelAttribute의 name 속성을 이용해 model.addAttribute()도 자동으로 처리해 준다.
        // model.addAttribute("item", item);

        return "basic/item";
    }

    // 상품 등록 데이터 처리
    // V2에서 @ModelAttribute의 파라미터를 생략하는 방식으로 리팩토링 -> 클래스명인 Item을 이름으로 사용한다
    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        // 파라미터로 받는 Model도 생략 가능하다.

        /* @ModelAttribute가 아래 내용을 자동으로 처리한다.
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity); */

        itemRepository.save(item);

        // @ModelAttribute의 name 속성을 이용해 model.addAttribute()도 자동으로 처리해 준다.
        // model.addAttribute("item", item);

        return "basic/item";
    }

    @PostConstruct
    public void init() {

        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
        itemRepository.save(new Item("ItemC", 30000, 30));
        itemRepository.save(new Item("ItemD", 40000, 40));
    }
}
