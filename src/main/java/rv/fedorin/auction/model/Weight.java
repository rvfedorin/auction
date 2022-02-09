package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author RFedorin
 * @since 09.02.2022
 */
@Embeddable
@AttributeOverride(
        name = "name",
        column = @Column(name = "weight_name"))
@AttributeOverride(
        name = "symbol",
        column = @Column(name = "weight_symbol"))
@Getter
@Setter
//@Builder
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Weight extends Measurement {

    @NotNull
    @Column(name = "weight")
    private BigDecimal value;
}
