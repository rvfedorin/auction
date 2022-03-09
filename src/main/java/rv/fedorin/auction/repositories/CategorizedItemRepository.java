package rv.fedorin.auction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.fedorin.auction.model.CategorizedItem;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
public interface CategorizedItemRepository extends JpaRepository<CategorizedItem, Long> {
}
