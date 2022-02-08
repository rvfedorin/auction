package rv.fedorin.auction.repositories;

import rv.fedorin.auction.model.BankAccount;

import java.util.List;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
public interface BankAccountRepository extends BillingDetailsRepository<BankAccount, Long> {

    List<BankAccount> findBySwift(String swift);

}
