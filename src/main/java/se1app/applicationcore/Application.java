package se1app.applicationcore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se1app.applicationcore.customercomponent.Customer;
import se1app.applicationcore.customercomponent.CustomerRepository;
import se1app.applicationcore.customercomponent.Reservation;
import se1app.applicationcore.kontocomponent.Konto;
import se1app.applicationcore.kontocomponent.KontoRepository;
import se1app.applicationcore.moviecomponent.Movie;

import java.util.Arrays;

@SpringBootApplication // das Folgende in dieser Zeile hinzufügen, um die Authentifizierung zu deaktivieren: (exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class Application {

    @Bean
    CommandLineRunner init(KontoRepository kontoRepository) {
        return args -> {
            Konto talal = new Konto(1000, "Talal");
            Konto kyo = new Konto(1000, "Kyo");
            Konto pluto = new Konto(1000, "Pluto");
            kontoRepository.save(Arrays.asList(talal, kyo, pluto));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
