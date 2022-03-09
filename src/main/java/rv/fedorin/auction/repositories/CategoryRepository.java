package rv.fedorin.auction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.fedorin.auction.model.Category;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
