package rv.fedorin.auction.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author RFedorin
 * @since 03.03.2022
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "category_item")
@Immutable
public class CategorizedItem {

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Id implements Serializable {
        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "item_id")
        private Long itemId;
    }

    @EmbeddedId
    private Id id = new Id();

    @Column(updatable = false)
    @NotNull
    private String addedBy;

    @Column(updatable = false)
    @NotNull
    @CreationTimestamp
    private LocalDateTime addedOn;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
            name = "category_id",
            insertable = false, updatable = false)
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
            name = "item_id",
            insertable = false, updatable = false)
    private Item item;

    public CategorizedItem(String addedByUsername,
                           Category category,
                           Item item) {

        // Set fields
        this.addedBy = addedByUsername;
        this.category = category;
        this.item = item;

        // Set identifier values
        this.id.categoryId = category.getId();
        this.id.itemId = item.getId();

        // Guarantee referential integrity if made bidirectional
        category.addCategorizedItem(this);
        item.addCategorizedItem(this);
    }
}
