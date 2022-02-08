package rv.fedorin.auction.repositories;

import org.springframework.data.repository.CrudRepository;
import rv.fedorin.auction.model.Item;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findByMetricWeight(double weight);
}
