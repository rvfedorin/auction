package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue
    private Long id;

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

    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }
}
