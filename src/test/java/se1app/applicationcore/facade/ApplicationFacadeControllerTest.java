package se1app.applicationcore.facade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se1app.applicationcore.Application;
import se1app.applicationcore.customercomponent.Customer;
import se1app.applicationcore.customercomponent.CustomerRepository;
import se1app.applicationcore.customercomponent.Reservation;
import se1app.applicationcore.moviecomponent.Movie;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("test")
public class ApplicationFacadeControllerTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer mickey;
    private Customer minnie;
    private Customer pluto;

    @Before
    public void setUp() {
        customerRepository.deleteAll();

        mickey = new Customer("Mickey Mouse");
        minnie = new Customer("Minnie Mouse");
        pluto = new Customer("Pluto");
        customerRepository.save(Arrays.asList(mickey, minnie, pluto));
    }

    @Test
    public void canFetchMickey() {
        Integer mickeyId = mickey.getId();

        when().
                get("/customers/{id}", mickeyId).
        then().
                statusCode(HttpStatus.OK.value()).
                body("name", is("Mickey Mouse")).
                body("id", is(mickeyId));
    }

    @Test
    public void canFetchAll() {
        when().
                get("/customers").
        then().
                statusCode(HttpStatus.OK.value()).
                body("name", hasItems("Mickey Mouse", "Minnie Mouse", "Pluto"));
    }

    @Test
    public void canDeletePluto() {
        Integer plutoId = pluto.getId();

        when().
                delete("/customers/{id}", plutoId).
        then().
                statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void canSaveDonald() {
        Customer donald = new Customer("Donald Duck");

        given().
                contentType("application/json").
                body(donald).
        expect().
                statusCode(HttpStatus.CREATED.value()).
        when().
                post("/customers");
    }

    @Test
    public void canAddReservation() {
        Integer mickeyId = mickey.getId();

        when().
                get("/movies/007").
        then().
                statusCode(HttpStatus.NOT_FOUND.value());

        Movie movie007 = new Movie("007");
        Reservation movieReservation007 = new Reservation(movie007);
        given().
                contentType("application/json").
                body(movieReservation007).
        expect().
                statusCode(HttpStatus.CREATED.value()).
        when().
                post("/customers/{id}/reservations", mickeyId);

        when().
                get("/movies/007").
        then().
                statusCode(HttpStatus.OK.value()).
                body(equalTo("1"));
   }
}