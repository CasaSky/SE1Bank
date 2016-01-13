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
import se1app.applicationcore.filialecomponent.Filiale;
import se1app.applicationcore.kontocomponent.BuchungsPosition;
import se1app.applicationcore.kontocomponent.Konto;
import se1app.applicationcore.kontocomponent.KontoComponentInterface;
import se1app.applicationcore.kontocomponent.KontoRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@ActiveProfiles("test")
public class ApplicationFacadeControllerTest {

    @Autowired
    private KontoRepository kontoRepository;

    private Konto talal;
    private Konto kyo;
    private Konto pluto;

    @Autowired
    private KontoComponentInterface kontoComponentInterface;

    @Before
    public void setUp() {
        kontoRepository.deleteAll();

        talal = new Konto(1000, "Talal Tabia");
        kyo = new Konto(1000, "Kyo Lee");
        pluto = new Konto(1000, "Pluto");
        kontoRepository.save(Arrays.asList(talal, kyo, pluto));
    }

    @Test
    public void canFetchTalal() {
        Integer talalId = talal.getId();

        when().
                get("/kontos/{id}", talalId).
        then().
                statusCode(HttpStatus.OK.value()).
                body("name", is("Talal Tabia")).
                body("id", is(talalId));
    }

    @Test
    public void canFetchAll() {
        when().
                get("/kontos").
        then().
                statusCode(HttpStatus.OK.value()).
                body("name", hasItems("Talal Tabia", "Kyo Lee", "Pluto"));
    }

    @Test
    public void canDeletePluto() {
        Integer plutoId = pluto.getId();

        when().
                delete("/kontos/{id}", plutoId).
        then().
                statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void canSaveDonald() {
        Konto donald = new Konto(10000, "Donald Duck");

        given().
                contentType("application/json").
                body(donald).
        expect().
                statusCode(HttpStatus.CREATED.value()).
        when().
                post("/kontos");
    }

    @Test
    public void canDoTransaction() {

        Integer betrag = 300;
                when().
                        post("/transactions/{betrag}", betrag).
                        then().
                                statusCode(HttpStatus.OK.value()).
                                body("buchungsPositions.get(0).gebuchterBetrag", equalTo(betrag));

    }

    /*@Test
    public void canAddBuchungsPosition() {
        Integer mickeyId = mickey.getId();

        when().
                get("/filiale/123456").
        then().
                statusCode(HttpStatus.NOT_FOUND.value());

        Filiale haspa = new Filiale(100);
        BuchungsPosition buchungsPosition = new BuchungsPosition(1000);
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
   }*/
}