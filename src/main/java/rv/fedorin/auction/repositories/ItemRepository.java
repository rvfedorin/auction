package rv.fedorin.auction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rv.fedorin.auction.model.Item;

import java.util.Set;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Iterable<Item> findByMetricWeight(double weight);

    @Query("select i from Item i inner join fetch i.images where i.id = :id")
    Item findItemWithImages(@Param("id") Long id);

    @Query(value = "SELECT fname FROM image WHERE item_id = ?1", nativeQuery = true)
    Set<String> findImagesNative(Long id);
}
