package rv.fedorin.auction.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author RFedorin
 * @since 28.02.2022
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class Image {

    @org.hibernate.annotations.Parent
    private Item item;

    @Column(nullable = false)
    private String filename;

    private int width;

    private int height;

    public Image(String filename, int width, int height) {
        this.filename = filename;
        this.width = width;
        this.height = height;
    }
}
