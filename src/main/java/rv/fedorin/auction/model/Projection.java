package rv.fedorin.auction.model;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author RFedorin
 * @since 02.02.2022
 */
public class Projection {

    public interface UserSummary {

        String getName();

        @Value("#{target.name} #{target.email}")
        String getInfo();
    }

    public static class UsernameOnly {
        private final String name;

        public UsernameOnly(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
