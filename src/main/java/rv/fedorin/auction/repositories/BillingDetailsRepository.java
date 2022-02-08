package rv.fedorin.auction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rv.fedorin.auction.model.BillingDetails;

import java.util.List;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
public interface BillingDetailsRepository<T extends BillingDetails, ID> extends JpaRepository<T, ID> {
    List<T> findByOwner(String owner);
}
