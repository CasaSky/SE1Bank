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
import se1app.applicationcore.kontocomponent.BuchungsPosition;
import se1app.applicationcore.kontocomponent.Konto;
import se1app.applicationcore.kontocomponent.KontoNrTyp;
import se1app.applicationcore.kontocomponent.KontoRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by talal on 07.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class KontoRepositoryTest {

    @Autowired
    private KontoRepository kontoRepository;

    private BuchungsPosition buchungsPosition = new BuchungsPosition(1000);

    @Before
    public void setup() {
        Konto talal = new Konto(5000, "talal");
        talal.addBuchungsPosition(buchungsPosition);

        Konto kyo = new Konto(5000, "kyo");
        kyo.addBuchungsPosition(buchungsPosition);
        //TO DO SET KONTO FÜR BUCHUNGPOSITION
        kontoRepository.save(talal);
        kontoRepository.save(kyo);
    }

    @Test
    public void testFindAll(){
        List<Konto> kontos = kontoRepository.findAll();
        assertThat(kontos).hasSize(2);
        //assertThat(konto.getFiliale().getFilialNr() == 1);
        //assertThat(konto.getKontoNummer() == new KontoNrTyp(1));
    }

    @Test
    public void testFindByKontoNummer() {
        Optional<Konto> kontoA = kontoRepository.findByName("talal");
        assertThat(kontoA.isPresent());
        KontoNrTyp kontoNr = kontoA.get().getKontoNummer();
        //Optional<Konto> kontoB = kontoRepository.findByKontoNummer(kontoNr);
        //assertThat(kontoB.isPresent());
    }
}
