package rv.fedorin.auction.model;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author RFedorin
 * @since 02.02.2022
 */
public class Projection {

    public interface UserSummary {

        String getUsername();

        @Value("#{target.username} #{target.email}")
        String getInfo();
    }

    public static class UsernameOnly {
        private final String username;

        public UsernameOnly(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
