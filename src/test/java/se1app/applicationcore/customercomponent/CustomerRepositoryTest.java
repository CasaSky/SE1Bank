package se1app.applicationcore.customercomponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se1app.applicationcore.Application;
import se1app.applicationcore.moviecomponent.Movie;
import se1app.applicationcore.util.EmailType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	private Movie spectre = new Movie("Spectre");

	@Before
	public void setup() {
		Movie goldfinger = new Movie("Goldfinger");
		Movie minions = new Movie("Minions");

		Customer stefan = new Customer("Stefan", new EmailType("stefan.sarstedt@haw-hamburg.de"));
		Reservation reservation = new Reservation(spectre);
		stefan.addReservation(reservation);
		reservation.setCustomer(stefan);
		reservation = new Reservation(goldfinger);
		stefan.addReservation(reservation);
		reservation.setCustomer(stefan);
		// Kaskadierendes Speichern der Reservierungen durch entsprechende 'Cascade'-Angabe in Customer!
		customerRepository.save(stefan);

		Customer ina = new Customer("Ina");
		reservation = new Reservation(spectre);
		ina.addReservation(reservation);
		reservation.setCustomer(ina);
		customerRepository.save(ina);

		Customer michel = new Customer("Michael");
		reservation = new Reservation(minions);
		michel.addReservation(reservation);
		reservation.setCustomer(michel);
		customerRepository.save(michel);
	}

	@Test
	public void testFindAll(){
		List<Customer> customers = customerRepository.findAll();
		assertThat(customers).hasSize(3);
	}

	@Test
	public void testFindByName(){
		Optional<Customer> customer = customerRepository.findByName("Stefan");
		assertThat(customer.isPresent());
		assertThat(customer.get().getName().equals("Stefan"));
		assertThat(customer.get().getReservations()).hasSize(2);
	}

	@Test
	public void testFindByMovie(){
		List<Customer> customers = customerRepository.findCustomersByMovie(spectre.getTitle());
		assertThat(customers).hasSize(2);

		// Vergleich aufgrund der (hier technischen - besser fachlichen) IDs der Entitäten
		// Die Customer-Objekte sind unterschiedlich, obwohl sie denselben Customer darstellen!
		Customer stefan =  customerRepository.findByName("Stefan").get();
		Customer ina =  customerRepository.findByName("Ina").get();
		assertThat(customers).extracting(customer -> customer.getId())
				.contains(stefan.getId(), ina.getId());

		// Alternativ, da equals der Entitäten entsprechend definiert
		assertThat(customers).contains(
				customerRepository.findByName("Stefan").get(),
				customerRepository.findByName("Ina").get());
	}
}
