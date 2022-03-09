package rv.fedorin.auction.many.to.many;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rv.fedorin.auction.model.CategorizedItem;
import rv.fedorin.auction.model.Category;
import rv.fedorin.auction.model.Item;
import rv.fedorin.auction.repositories.CategorizedItemRepository;
import rv.fedorin.auction.repositories.CategoryRepository;
import rv.fedorin.auction.repositories.ItemRepository;

/**
 * @author RFedorin
 * @since 03.03.2022
 */
@SpringBootTest
public class CategorizedItemTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategorizedItemRepository categorizedItemRepository;

    @Test
    void createCategoryAndItem() {
        Category someCategory =  Category.builder().name("Some Category").build();
        Category otherCategory = Category.builder().name("Other Category").build();

        categoryRepository.save(someCategory);
        categoryRepository.save(otherCategory);

        Item someItem = Item.builder().name("Some Item").build();
        Item otherItem = Item.builder().name("Other Item").build();

        itemRepository.save(someItem);
        itemRepository.save(otherItem);

        CategorizedItem linkOne = new CategorizedItem("John Smith", someCategory, someItem);
        CategorizedItem linkTwo = new CategorizedItem("John Smith", someCategory, otherItem);
        CategorizedItem linkThree = new CategorizedItem("John Smith", otherCategory, someItem);

        categorizedItemRepository.save(linkOne);
        categorizedItemRepository.save(linkTwo);
        categorizedItemRepository.save(linkThree);

    }
}
