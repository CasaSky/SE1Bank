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
import se1app.applicationcore.moviecomponent.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class CustomerComponentTest {

    // KEIN Autowired hier!
    private CustomerComponentInterface customerComponentInterface;
    private MovieComponentInterface movieComponentInterface;

	@Autowired
	private CustomerRepository customerRepository;

    @Autowired
    private MovieRepository movieRepository;

    private int customerHeinzId;
    private Movie movie007;

	@Before
	public void setup() {
		// Testdaten für den Komponententest initialisieren
		Customer customer = new Customer("Heinz");
		customerRepository.save(customer);
        customerHeinzId = customer.getId();

        movie007 = new Movie("007");
        movieRepository.save(movie007);

        // wir instanziieren unsere Komponente selber, um Mock-Abhängigkeiten zu übergeben
        movieComponentInterface = new MovieComponent(movieRepository);
        customerComponentInterface = new CustomerComponent(customerRepository, movieComponentInterface);
	}

	@Test
	public void testGetAllCustomers() {
        List<Customer> customers = customerComponentInterface.getAllCustomers();
        assertThat(customers).hasSize(1);
    }

    @Test
    public void testAddReservation() {
        Customer customer = customerComponentInterface.getCustomer(customerHeinzId);
        assertThat(customer).isNotNull();

        // hier testen wir, ob der Aufruf an die abhängige Komponente MovieComponent korrekt funktioniert
        try {
            assertThat(movieComponentInterface.getNumberOfReservations(movie007.getTitle())).isEqualTo(0);
            customerComponentInterface.addReservation(customerHeinzId, new Reservation(movie007));
            assertThat(movieComponentInterface.getNumberOfReservations(movie007.getTitle())).isEqualTo(1);
        }
        catch(CustomerNotFoundException ex) {
            // kann nicht passieren
        }
        catch(MovieNotFoundException ex) {
            // kann nicht passieren
        }
    }

    // Hier fehlen die Tests der anderen CustomerComponentInterface-Operationen!
}
