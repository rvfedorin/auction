package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import rv.fedorin.auction.converter.ZipcodeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class City {

    @NotNull
    @Column(nullable = false, length = 5) // Override VARCHAR(255)
    @Convert(converter = ZipcodeConverter.class, attributeName = "city.zipcode")
    private Zipcode zipcode;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String country;

}
