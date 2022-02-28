package rv.fedorin.auction.mapping;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rv.fedorin.auction.model.Image;
import rv.fedorin.auction.model.Item;
import rv.fedorin.auction.repositories.ItemRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author RFedorin
 * @since 28.02.2022
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MappingCollectionsSpringDataJPATest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void storeLoadEntities() {
        Item item = new Item();
        item.setName("Foo");
        item.addImage(new Image("background.jpg", 10, 10));
        item.addImage(new Image("foreground.jpg", 10, 10));
        item.addImage(new Image("landscape.jpg", 10, 10));
        item.addImage(new Image("portrait.jpg", 10, 10));
        item.addImage(new Image("portrait.jpg", 10, 10)); // duplicate

        itemRepository.save(item);

        Item item2 = itemRepository.findItemWithImages(item.getId());

        List<Item> items2 = itemRepository.findAll();
        Set<String> images = itemRepository.findImagesNative(item.getId());

        assertAll(
                () -> assertEquals(4, item2.getImages().size()),
                () -> assertEquals(1, items2.size()),
                () -> assertEquals(4, images.size())
        );

    }
}
