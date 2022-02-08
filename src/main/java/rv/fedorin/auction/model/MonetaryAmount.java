package rv.fedorin.auction.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Embeddable;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MonetaryAmount implements Serializable {

    private BigDecimal value;
    private Currency currency;

    @Override
    public String toString() {
        return value + " " + currency;
    }

    public static MonetaryAmount fromString(String s) {
        String[] split = s.split(" ");
        if (split.length < 2) {
            throw new IllegalArgumentException("There are wrong value: " + s);
        }
        return new MonetaryAmount(
                new BigDecimal(split[0]),
                Currency.getInstance(split[1])
        );
    }
}
