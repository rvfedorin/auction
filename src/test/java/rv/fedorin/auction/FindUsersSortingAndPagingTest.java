package rv.fedorin.auction;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import rv.fedorin.auction.model.User;

import java.util.List;

/**
 * @author RFedorin
 * @since 02.02.2022
 */
public class FindUsersSortingAndPagingTest extends SpringDataJpaTest {

    @Test
    void testOrder() {
        User user1 = userRepository.findFirstByOrderByNameAsc();
        User user2 = userRepository.findTopByOrderByRegistrationDateDesc();
        Page<User> userPage = userRepository.findAll(PageRequest.of(1, 3));
        List<User> users = userRepository.findFirst2ByLevel(2, Sort.by("registrationDate"));

        assertAll(
                () -> assertEquals("beth", user1.getName()),
                () -> assertEquals("julius", user2.getName()),
                () -> assertEquals(2, users.size()),
                () -> assertEquals(3, userPage.getSize()),
                () -> assertEquals("beth", users.get(0).getName()),
                () -> assertEquals("marion", users.get(1).getName())
        );
    }

    @Test
    void testFindByLevel() {
        Sort.TypedSort<User> user = Sort.sort(User.class);
        List<User> users = userRepository.findByLevel(3, user.by(User::getRegistrationDate).descending());

        assertAll(
                () -> assertEquals(2, users.size()),
                () -> assertEquals("james", users.get(0).getName())
        );
    }

    @Test
    void testFindByActive() {
        List<User> users = userRepository.findByActive(
                true, PageRequest.of(1, 4, Sort.by("registrationDate")));

        assertAll(
                () -> assertEquals(4, users.size()),
                () -> assertEquals("burk", users.get(0).getName())
        );
    }
}
