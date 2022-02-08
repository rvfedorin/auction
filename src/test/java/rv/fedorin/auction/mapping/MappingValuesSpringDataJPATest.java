package rv.fedorin.auction.mapping;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rv.fedorin.auction.model.Address;
import rv.fedorin.auction.model.AuctionType;
import rv.fedorin.auction.model.City;
import rv.fedorin.auction.model.GermanZipcode;
import rv.fedorin.auction.model.Item;
import rv.fedorin.auction.model.MonetaryAmount;
import rv.fedorin.auction.model.User;
import rv.fedorin.auction.repositories.ItemRepository;
import rv.fedorin.auction.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MappingValuesSpringDataJPATest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    void afterAll() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void storeLoadEntities() {
        var city = new City();
        city.setCountry("USA");
        city.setName("Boston");
        city.setZipcode(new GermanZipcode("12345"));

        var user = new User();
        user.setUsername("username");
        user.setHomeAddress(new Address("Flowers Street", city));

        userRepository.save(user);

        var item = new Item();
        item.setName("Some Item");
        item.setMetricWeight(2);
        item.setInitialPrice(new MonetaryAmount(new BigDecimal("1.00"), Currency.getInstance("USD")));
        item.setBuyNowPrice(new MonetaryAmount(BigDecimal.valueOf(1.1), Currency.getInstance("USD")));
        item.setDescription("descriptiondescription");
        itemRepository.save(item);

        List<User> users = userRepository.findAll();
        List<Item> items = (List<Item>) itemRepository.findByMetricWeight(2.0);

        assertAll(
                () -> assertEquals(1, users.size(), "Size"),
                () -> assertEquals("username", users.get(0).getUsername()),
                () -> assertEquals("Flowers Street", users.get(0).getHomeAddress().getStreet()),
                () -> assertEquals("Boston", users.get(0).getHomeAddress().getCity().getName()),
                () -> assertEquals("USA", users.get(0).getHomeAddress().getCity().getCountry()),
                () -> assertEquals(1, items.size()),
                () -> assertEquals("AUCTION: Some Item", items.get(0).getName(), "Auctin name"),
                () -> assertEquals("2.20 USD", items.get(0).getBuyNowPrice().toString(), "Price"),
                () -> assertEquals("descriptiondescription", items.get(0).getDescription(), "Desc"),
                () -> assertEquals(AuctionType.HIGHEST_BID, items.get(0).getAuctionType()),
                () -> assertEquals("descriptiond...", items.get(0).getShortDescription()),
                () -> assertEquals(2.0, items.get(0).getMetricWeight()),
                () -> assertEquals(LocalDate.now(), items.get(0).getCreatedOn()),
                () -> assertTrue(ChronoUnit
                        .SECONDS.between(LocalDateTime.now(), items.get(0).getLastModified()) < 1),
                () -> assertEquals("2.00 EUR", items.get(0).getInitialPrice().toString()),
                () -> assertEquals("12345", users.get(0).getHomeAddress().getCity().getZipcode().getValue())
        );

    }
}


