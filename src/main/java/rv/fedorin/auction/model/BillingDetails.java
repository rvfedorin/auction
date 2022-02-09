package rv.fedorin.auction.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * @author RFedorin
 * @since 08.02.2022
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "BD_TYPE")
//@org.hibernate.annotations.DiscriminatorFormula(
//        "case when CARDNUMBER is not null then 'CC' else 'BA' end"
//)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BillingDetails {

    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @EqualsAndHashCode.Exclude
    private Long id;

    private String owner;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private User user;
}
