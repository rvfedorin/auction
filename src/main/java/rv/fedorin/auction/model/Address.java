package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Address {

    @NotNull // Ignored for DDL generation!
    @Column(nullable = false) // Used for DDL generation!
    private String street;

    @Embedded
    @NotNull
    @AttributeOverride(
            name = "name",
            column = @Column(name = "CITY", nullable = false)
    )
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "CITY"))
    })
    private City city;

}
