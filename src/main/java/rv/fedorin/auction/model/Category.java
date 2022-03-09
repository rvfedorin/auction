package rv.fedorin.auction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

/**
 * @author RFedorin
 * @since 03.03.2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "category")
    private Set<CategorizedItem> categorizedItems = new HashSet<>();

//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @Builder.Default
//    @ManyToMany(cascade = CascadeType.PERSIST)
//    @MapKeyColumn(name = "item_id")
//    @JoinTable(
//            name = "category_item",
//            joinColumns = @JoinColumn(name = "category_id", nullable = true),
//            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = true)
//    )
//    private Map<Item, User> itemAddedBy = new HashMap<>();

    public void addCategorizedItem(CategorizedItem categorizedItem) {
        categorizedItems.add(categorizedItem);
    }
}
