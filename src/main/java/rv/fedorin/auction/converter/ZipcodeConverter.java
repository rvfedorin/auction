package rv.fedorin.auction.converter;

import rv.fedorin.auction.model.GermanZipcode;
import rv.fedorin.auction.model.SwissZipcode;
import rv.fedorin.auction.model.Zipcode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
@Converter
public class ZipcodeConverter implements AttributeConverter<Zipcode, String> {
    @Override
    public String convertToDatabaseColumn(Zipcode zipcode) {
        return zipcode.getValue();
    }

    @Override
    public Zipcode convertToEntityAttribute(String s) {
        if (s.length() == 5) {
            return new GermanZipcode(s);
        } else if (s.length() == 4) {
            return new SwissZipcode(s);
        }
        throw new IllegalArgumentException("Unsupported zip code in database " + s);
    }
}
