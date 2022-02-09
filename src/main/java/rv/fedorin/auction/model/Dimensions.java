package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
        column = @Column(name = "dimensions_name"))
@AttributeOverride(
        name = "symbol",
        column = @Column(name = "dimension_symbol"))
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Dimensions extends Measurement {

    @NotNull
    private BigDecimal depth;

    @NotNull
    private BigDecimal height;

    @NotNull
    private BigDecimal width;
}
