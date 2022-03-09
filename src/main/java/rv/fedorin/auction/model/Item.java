package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

/**
 * @author RFedorin
 * @since 07.02.2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {

    /*
       The <code>Item</code> entity defaults to field access, the <code>@Id</code> is on a field.
    */
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    @Setter(AccessLevel.NONE)
    private Long id;

    /*
       The <code>@Access(AccessType.PROPERTY)</code> setting on the <code>name</code> field switches this
       particular property to runtime access through getter/setter methods by the JPA provider.
    */
    @Access(AccessType.PROPERTY)
    @Column(name = "ITEM_NAME") // Mappings are still expected here!
    @Setter(AccessLevel.NONE)
    private String name;

    @Embedded
    private Dimensions dimensions;

    @Embedded
    private Weight weight;

    @NotNull
    @org.hibernate.annotations.Type(
            type = "monetary_amount_eur"
    )
    @org.hibernate.annotations.Columns(columns = {
            @Column(name = "INITIALPRICE_AMOUNT"),
            @Column(name = "INITIALPRICE_CURRENCY", length = 3)
    })
    private MonetaryAmount initialPrice;

    @NotNull
    @org.hibernate.annotations.Type(
            type = "monetary_amount_usd"
    )
    @org.hibernate.annotations.Columns(columns = {
            @Column(name = "BUYNOWPRICE_AMOUNT"),
            @Column(name = "BUYNOWPRICE_CURRENCY", length = 3)
    })
    private MonetaryAmount buyNowPrice;

    @OneToMany(mappedBy = "item",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true) // Includes CascadeType.REMOVE
    @OrderColumn(name = "bid_position", nullable = false)
    private List<Bid> bids = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name = "image")
    @AttributeOverride(
            name = "filename",
            column = @Column(name = "fname", nullable = false)
    )
    private Set<Image> images = new HashSet<>();

    @NotNull
    @Basic(fetch = FetchType.LAZY) // Defaults to EAGER
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING) // Defaults to ORDINAL
    private AuctionType auctionType = AuctionType.HIGHEST_BID;

    @Formula(
            "CONCAT(SUBSTR(DESCRIPTION, 1, 12), '...')"
    )
    private String shortDescription;

    @Formula(
            "(SELECT AVG(B.AMOUNT) FROM bid B WHERE B.ITEM_ID = ID)"
    )
    private BigDecimal averageBidAmount;

    @Column(name = "IMPERIALWEIGHT")
    @ColumnTransformer(
            read = "IMPERIALWEIGHT / 2.20462",
            write = "? * 2.20462"
    )
    private double metricWeight;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDate createdOn;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "item_buyer",
            joinColumns =
            @JoinColumn(name = "item_id"),
            inverseJoinColumns =
            @JoinColumn(nullable = false)
    )
    private User buyer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "item")
    private Set<CategorizedItem> categorizedItems = new HashSet<>();

    public void setName(String name) {
        this.name = !name.startsWith("AUCTION: ") ? "AUCTION: " + name : name;
    }

    public List<Bid> getBids() {
        return Collections.unmodifiableList(bids);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void addCategorizedItem(CategorizedItem categorizedItem) {
        categorizedItems.add(categorizedItem);
    }
}
