package rv.fedorin.auction;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rv.fedorin.auction.model.User;
import rv.fedorin.auction.repositories.UserRepository;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class AuctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionApplication.class, args);
    }
}
