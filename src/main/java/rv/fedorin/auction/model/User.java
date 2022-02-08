package rv.fedorin.auction.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author RFedorin
 * @since 01.02.2022
 */
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {

    public User(String name, LocalDate registrationDate) {
        this.username = name;
        this.registrationDate = registrationDate;
    }

    @EqualsAndHashCode.Exclude
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private LocalDate registrationDate;

    private String email;

    private int level;

    private boolean active;

    @Embedded
    private Address homeAddress;
}
