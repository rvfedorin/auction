package rv.fedorin.auction.mapping;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rv.fedorin.auction.model.BankAccount;
import rv.fedorin.auction.model.CreditCard;
import rv.fedorin.auction.repositories.BankAccountRepository;
import rv.fedorin.auction.repositories.CreditCardRepository;

import java.util.List;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MappingInheritanceSpringDataJPATest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    void setup() {
        CreditCard creditCard = CreditCard.builder()
                .owner("John Smith")
                .cardNumber("123456789")
                .expMonth("10")
                .expYear("2030")
                .build();
        creditCardRepository.save(creditCard);
        BankAccount bankAccount = BankAccount.builder()
                .owner("Mike Johnson")
                .account("12345")
                .bankName("Delta Bank")
                .swift("BANKXY12")
                .build();
        bankAccountRepository.save(bankAccount);

    }

    @AfterEach
    void clean() {
        creditCardRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    void storeLoadEntities() {
        List<CreditCard> creditCards = creditCardRepository.findByOwner("John Smith");
        List<BankAccount> bankAccounts = bankAccountRepository.findByOwner("Mike Johnson");
        List<CreditCard> creditCards2 = creditCardRepository.findByExpYear("2030");
        List<BankAccount> bankAccounts2 = bankAccountRepository.findBySwift("BANKXY12");

        assertAll(
                () -> assertEquals(1, creditCards.size()),
                () -> assertEquals("123456789", creditCards.get(0).getCardNumber()),
                () -> assertEquals(1, bankAccounts.size()),
                () -> assertEquals("12345", bankAccounts.get(0).getAccount()),
                () -> assertEquals(1, creditCards2.size()),
                () -> assertEquals("John Smith", creditCards2.get(0).getOwner()),
                () -> assertEquals(1, bankAccounts2.size()),
                () -> assertEquals("Mike Johnson", bankAccounts2.get(0).getOwner())
        );
    }
}
