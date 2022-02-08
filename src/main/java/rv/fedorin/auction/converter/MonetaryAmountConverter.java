package rv.fedorin.auction.converter;

import rv.fedorin.auction.model.MonetaryAmount;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Converter
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String s) {
        return MonetaryAmount.fromString(s);
    }
}
