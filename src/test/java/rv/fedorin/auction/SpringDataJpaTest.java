package rv.fedorin.auction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rv.fedorin.auction.model.User;
import rv.fedorin.auction.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

/**
 * @author RFedorin
 * @since 01.02.2022
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class SpringDataJpaTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void beforeAll() {
        userRepository.saveAll(generateUser());
    }

    @AfterAll
    void afterAll() {
        userRepository.deleteAll();
    }

    private static List<User> generateUser() {
        List<User> users = new ArrayList<>();

        User john = new User("john", LocalDate.of(2020, Month.APRIL, 13));
        john.setEmail("john@somedomain.com");
        john.setLevel(1);
        john.setActive(true);

        User mike = new User("mike", LocalDate.of(2020, Month.JANUARY, 18));
        mike.setEmail("mike@somedomain.com");
        mike.setLevel(3);
        mike.setActive(true);

        User james = new User("james", LocalDate.of(2020, Month.MARCH, 11));
        james.setEmail("james@someotherdomain.com");
        james.setLevel(3);
        james.setActive(false);

        User katie = new User("katie", LocalDate.of(2021, Month.JANUARY, 5));
        katie.setEmail("katie@somedomain.com");
        katie.setLevel(5);
        katie.setActive(true);

        User beth = new User("beth", LocalDate.of(2020, Month.AUGUST, 3));
        beth.setEmail("beth@somedomain.com");
        beth.setLevel(2);
        beth.setActive(true);

        User julius = new User("julius", LocalDate.of(2021, Month.FEBRUARY, 9));
        julius.setEmail("julius@someotherdomain.com");
        julius.setLevel(4);
        julius.setActive(true);

        User darren = new User("darren", LocalDate.of(2020, Month.DECEMBER, 11));
        darren.setEmail("darren@somedomain.com");
        darren.setLevel(2);
        darren.setActive(true);

        User marion = new User("marion", LocalDate.of(2020, Month.SEPTEMBER, 23));
        marion.setEmail("marion@someotherdomain.com");
        marion.setLevel(2);
        marion.setActive(false);

        User stephanie = new User("stephanie", LocalDate.of(2020, Month.JANUARY, 18));
        stephanie.setEmail("stephanie@someotherdomain.com");
        stephanie.setLevel(4);
        stephanie.setActive(true);

        User burk = new User("burk", LocalDate.of(2020, Month.NOVEMBER, 28));
        burk.setEmail("burk@somedomain.com");
        burk.setLevel(1);
        burk.setActive(true);

        users.add(john);
        users.add(mike);
        users.add(james);
        users.add(katie);
        users.add(beth);
        users.add(julius);
        users.add(darren);
        users.add(marion);
        users.add(stephanie);
        users.add(burk);

        return users;
    }

    private static String generateString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static LocalDate generateLocalDate() {
        Random random = new Random();
        int year = random.nextInt(50) + 1950;
        int month = random.nextInt(11) + 1;
        int day = random.nextInt(27) + 1;

        return LocalDate.of(year, month, day);
    }

    private static User createRandomUser() {
        var user = new User(generateString(), generateLocalDate());
        StringJoiner emailJoiner = new StringJoiner("@");
        emailJoiner.add(generateString());
        emailJoiner.add(generateString() + ".ru");
        user.setEmail(emailJoiner.toString());
        user.setLevel(1);
        user.setActive(true);

        return user;
    }
}
