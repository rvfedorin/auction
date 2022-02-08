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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MappingValuesJPATest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    void afterAll() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    public void storeLoadEntities() {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            City city = new City();
            city.setName("Boston");
            city.setZipcode(new GermanZipcode("12345"));
            city.setCountry("USA");

            User user = new User();
            user.setUsername("username");
            user.setHomeAddress(new Address("Flowers Street", city));

            Item item = new Item();
            item.setName("Some Item");
            item.setMetricWeight(2);
            item.setInitialPrice(new MonetaryAmount(new BigDecimal("1.00"), Currency.getInstance("USD")));
            item.setBuyNowPrice(new MonetaryAmount(BigDecimal.valueOf(1.1), Currency.getInstance("USD")));
            item.setDescription("descriptiondescription");

            em.persist(user);
            em.persist(item);

            em.getTransaction().commit();
            em.refresh(user);
            em.refresh(item);

            em.getTransaction().begin();

            List<User> users = em.createQuery("select u from User u").getResultList();

            List<Item> items = em.createQuery("select i from Item i where i.metricWeight = :w")
                    .setParameter("w", 2.0)
                    .getResultList();
            em.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, users.size()),
                    () -> assertEquals("username", users.get(0).getUsername()),
                    () -> assertEquals("Flowers Street", users.get(0).getHomeAddress().getStreet()),
                    () -> assertEquals("Boston", users.get(0).getHomeAddress().getCity().getName()),
                    () -> assertEquals("12345", users.get(0).getHomeAddress().getCity().getZipcode().getValue()),
                    () -> assertEquals("USA", users.get(0).getHomeAddress().getCity().getCountry()),
                    () -> assertEquals(1, items.size()),
                    () -> assertEquals("AUCTION: Some Item", items.get(0).getName()),
                    () -> assertEquals("2.20 USD", items.get(0).getBuyNowPrice().toString()),
                    () -> assertEquals("descriptiondescription", items.get(0).getDescription()),
                    () -> assertEquals(AuctionType.HIGHEST_BID, items.get(0).getAuctionType()),
                    () -> assertEquals("descriptiond...", items.get(0).getShortDescription()),
                    () -> assertEquals(2.0, items.get(0).getMetricWeight()),
                    () -> assertEquals(LocalDate.now(), items.get(0).getCreatedOn()),
                    () -> assertTrue(ChronoUnit.SECONDS.between(LocalDateTime.now(), items.get(0).getLastModified()) < 1),
                    () -> assertEquals("2.00 EUR", items.get(0).getInitialPrice().toString())
            );
        } finally {
            em.close();
        }
    }
}
