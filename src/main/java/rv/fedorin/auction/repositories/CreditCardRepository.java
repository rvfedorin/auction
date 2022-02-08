package rv.fedorin.auction.repositories;

import rv.fedorin.auction.model.CreditCard;

import java.util.List;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
public interface CreditCardRepository extends BillingDetailsRepository<CreditCard, Long> {

    List<CreditCard> findByExpYear(String expYear);

}
