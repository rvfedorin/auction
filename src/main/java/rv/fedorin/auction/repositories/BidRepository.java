package rv.fedorin.auction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.fedorin.auction.model.Bid;
import rv.fedorin.auction.model.Item;

import java.util.Set;

/**
 * @author RFedorin
 * @since 28.02.2022
 */
public interface BidRepository extends JpaRepository<Bid, Long> {

    Set<Bid> findByItem(Item item);
}
