package rv.fedorin.auction.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import rv.fedorin.auction.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author RFedorin
 * @since 01.02.2022
 */
public class FindUsersUsingQueriesTest extends SpringDataJpaTest {

    @Test
    void testFindAll() {
        List<User> userList = userRepository.findAll();
        assertEquals(10, userList.size());
    }

    @Test
    void testFindUser() {
        User beth = userRepository.findByUsername("beth");
        assertEquals("beth", beth.getUsername());
    }

    @Test
    void testFindAllByOrderByNameAsc() {
        List<User> users = userRepository.findAllByOrderByUsernameAsc();
        Comparator<User> nameComparator = Comparator.comparing(User::getUsername);

        String firstName = users.stream()
                .min(nameComparator)
                .orElseThrow(NoSuchElementException::new)
                .getUsername();

        String lastName = users.stream()
                .max(nameComparator)
                .orElseThrow(NoSuchElementException::new)
                .getUsername();

        assertAll(
                () -> assertEquals(10, users.size()),
                () -> assertEquals(firstName, users.get(0).getUsername()),
                () -> assertEquals(lastName, users.get(users.size() - 1).getUsername())
        );
    }

    @Test
    void findByRegistrationDateBetween() {
        User beth = userRepository.findByUsername("beth");
        List<User> userList = userRepository.findByRegistrationDateBetween(
                beth.getRegistrationDate().minusMonths(2),
                beth.getRegistrationDate().plusMonths(2));

        Optional<User> bethInBetween = userList.stream().filter(u -> "beth".equals(u.getUsername())).findFirst();
        assertTrue(bethInBetween.isPresent());
    }
}
