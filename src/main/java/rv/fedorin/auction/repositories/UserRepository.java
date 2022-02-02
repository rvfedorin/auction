package rv.fedorin.auction.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import rv.fedorin.auction.model.Projection;
import rv.fedorin.auction.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * @author RFedorin
 * @since 01.02.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    List<User> findAllByOrderByNameAsc();

    Page<User> findAll(Pageable pageable);

    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    User findFirstByOrderByNameAsc();

    User findTopByOrderByRegistrationDateDesc();

    List<User> findByNameIgnoreCase(String name);

    List<User> findByNameContaining(String text);

    List<User> findFirst2ByLevel(int level, Sort sort);

    List<User> findByLevel(int level, Sort sort);

    List<User> findByActive(boolean active, Pageable pageable);

    Streamable<User> findByEmailContaining(String text);

    Streamable<User> findByLevel(int level);

    @Query("select count(u) from User u where u.active = ?1")
    int findNumberOfUsersByActivity(boolean active);

    @Query("select u from User u where u.level = :level and u.active = :active")
    List<User> findByLevelAndActive(@Param("level") int level, @Param("active") boolean active);

    @Query(value = "select count(*) from user where active = ?1", nativeQuery = true)
    int findNumberOfUsersByActivityNative(boolean active);

    @Query("select u.name, LENGTH(u.email) as email_length from #{#entityName} u where u.name like %?1%")
    List<Object[]> findByAsArrayAndSort(String text, Sort sort);

    List<Projection.UserSummary> findByRegistrationDateAfter(LocalDate date);

    List<Projection.UsernameOnly> findByEmail(String email);

    <T> List<T> findByEmail(String email, Class<T> type);
}
