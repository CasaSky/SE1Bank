package se1app.applicationcore.facade;

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
import se1app.applicationcore.kontocomponent.KontoComponentInterface;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by talal on 13.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FacadeFunctionTest {

    @Autowired
    KontoComponentInterface kontoComponentInterface;
    @Before
    public void setup() {
        Konto talal = new Konto(5000, "talal");
        Konto kyo = new Konto(5000, "kyo");
        kontoComponentInterface.addKonto(talal);
        kontoComponentInterface.addKonto(kyo);
    }

    @Test
    public void testUeberweise(){
        Integer betrag = 300;
        List<Konto> kontos = kontoComponentInterface.getAlleKonten();
        Konto konto1 = kontos.get(0);
        Konto konto2 = kontos.get(1);
        String name = konto2.getName();
        kontoComponentInterface.ueberweise(konto1.getKontoNummer(), konto2.getKontoNummer(), betrag);

        List<BuchungsPosition> buchungen;
        kontos = kontoComponentInterface.getAlleKonten();
        Konto zielKonto = null;
        for (Konto konto : kontos) {
            if (konto.getName().equalsIgnoreCase(name))
                zielKonto = konto;
        }
        buchungen = zielKonto.getBuchungsPositions();
        BuchungsPosition buchung = new BuchungsPosition(300);
        assertThat(buchungen).contains(buchung);
    }
}
