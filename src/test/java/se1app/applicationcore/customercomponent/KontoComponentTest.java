package se1app.applicationcore.customercomponent;

import org.assertj.core.api.StrictAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se1app.applicationcore.Application;
import se1app.applicationcore.filialecomponent.Filiale;
import se1app.applicationcore.filialecomponent.FilialeComponent;
import se1app.applicationcore.filialecomponent.FilialeComponentInterface;
import se1app.applicationcore.filialecomponent.FilialeRepository;
import se1app.applicationcore.kontocomponent.*;
import se1app.applicationcore.moviecomponent.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by talal on 05.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class KontoComponentTest {

    // KEIN Autowired hier!
    private KontoComponentInterface kontoComponentInterface;
    private FilialeComponentInterface filialeComponentInterface;

    @Autowired
    private KontoRepository kontoRepository;

    @Autowired
    private FilialeRepository filialeRepository;

    private BuchungsPosition buchungsPosition = new BuchungsPosition(1000);

    private int kontoTalalId;
    private int kontoKyoId;
    private Filiale filiale;

    //private Integer DEFAULTFILIALNR = 123456;

    @Before
    public void setup() {
        // Testdaten für den Komponententest initialisieren
        filiale = new Filiale(100);
        filialeRepository.save(filiale);

        Konto kontoTalal = new Konto(5000, "talal");
        //kontoTalal.addBuchungsPosition(buchungsPosition);
        kontoRepository.save(kontoTalal);
        kontoTalalId = kontoTalal.getId();

        Konto kontoKyo = new Konto(5000, "kyo");
        //kontoTalal.addBuchungsPosition(buchungsPosition);
        kontoRepository.save(kontoKyo);
        kontoKyoId = kontoKyo.getId();

       // movie007 = new Movie("007");
        //movieRepository.save(movie007);

        // wir instanziieren unsere Komponente selber, um Mock-Abhängigkeiten zu übergeben
        //movieComponentInterface = new MovieComponent(movieRepository);
        filialeComponentInterface = new FilialeComponent(filialeRepository);
        kontoComponentInterface = new KontoComponent(kontoRepository, filialeComponentInterface);
    }

    @Test
    public void testGetAllKontos() {
        List<Konto> kontos = kontoComponentInterface.getAlleKonten();
        assertThat(kontos).hasSize(2);
    }

    @Test
    public void testAddBuchungsPosition() {
        Konto kontoTalal = kontoComponentInterface.getKonto(kontoTalalId);
        assertThat(kontoTalal).isNotNull();

        // hier testen wir, ob der Aufruf an die abhängige Komponente MovieComponent korrekt funktioniert
        //try {
            //StrictAssertions.assertThat(kontoComponentInterface.g).isEqualTo(0);
            kontoComponentInterface.addBuchungsPosition(kontoTalal.getKontoNummer(), buchungsPosition);
            Konto kontoTalal2 = kontoComponentInterface.getKonto(kontoTalalId);
            StrictAssertions.assertThat(buchungsPosition).isIn(kontoTalal2.getBuchungsPositions());
        //}
        //catch(CustomerNotFoundException ex) {
            // kann nicht passieren
        //}
        //catch(MovieNotFoundException ex) {
            // kann nicht passieren
        //}
    }
    @Test
    public void testUeberweise() {
        Konto kontoTalal = kontoComponentInterface.getKonto(kontoTalalId);
        assertThat(kontoTalal).isNotNull();

        Konto kontoKyo = kontoComponentInterface.getKonto(kontoKyoId);
        assertThat(kontoKyo).isNotNull();

        assertThat(kontoTalal.getKontostand()).isEqualTo(5000);
        assertThat(kontoKyo.getKontostand()).isEqualTo(5000);
        Integer kontoStandTalal = kontoTalal.getKontostand();
        Integer kontoStandKyo = kontoKyo.getKontostand();

        kontoComponentInterface.ueberweise(kontoTalal.getKontoNummer(), kontoKyo.getKontoNummer(), 200);

        Konto kontoTalal2 = kontoComponentInterface.getKonto(kontoTalalId);
        assertThat(kontoTalal2).isNotNull();
        Konto kontoKyo2 = kontoComponentInterface.getKonto(kontoKyoId);
        assertThat(kontoKyo2).isNotNull();

        assertThat(kontoTalal2.getKontostand()).isEqualTo(kontoStandTalal-200);
        assertThat(kontoKyo2.getKontostand()).isEqualTo(kontoStandKyo+200);



    }


}
