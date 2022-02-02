package rv.fedorin.auction;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import rv.fedorin.auction.model.User;

import java.util.List;

/**
 * @author RFedorin
 * @since 02.02.2022
 */
public class ModifyQueryTest extends SpringDataJpaTest {

    @Test
    void testModifyLevel() {
        int updated = userRepository.updateLevel(5, 4);
        List<User> users = userRepository.findByLevel(4, Sort.by("name"));

        assertAll(
                () -> assertEquals(1, updated),
                () -> assertEquals(3, users.size()),
                () -> assertEquals("katie", users.get(1).getName())
        );
    }

    @Test
    void testDeleteByLevel() {
        int deleted = userRepository.deleteByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("name"));
        assertEquals(0, users.size());
    }

    @Test
    void testDeleteBulkByLevel() {
        int deleted = userRepository.deleteBulkByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("name"));
        assertEquals(0, users.size());
    }
}
