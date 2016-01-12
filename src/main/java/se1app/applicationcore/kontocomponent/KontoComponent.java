package se1app.applicationcore.kontocomponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se1app.applicationcore.filialecomponent.Filiale;
import se1app.applicationcore.filialecomponent.FilialeComponent;
import se1app.applicationcore.filialecomponent.FilialeComponentInterface;
import se1app.applicationcore.filialecomponent.FilialeRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by talal on 05.01.16.
 */
@Component
public class KontoComponent implements KontoComponentInterface {

    // Autowiring durch Constructor-Injection
    private KontoRepository kontoRepository;
    // Autowiring durch Constructor-Injection
    //private BuchungsPosition
    private FilialeComponentInterface filialeComponentInterface;

    @Autowired
    FilialeRepository filialeRepository;

    @Autowired
    public KontoComponent(KontoRepository kontoRepository, FilialeComponentInterface filialeComponentInterface) {

        this.kontoRepository = kontoRepository;
        this.filialeComponentInterface = filialeComponentInterface;

    }
    @Override
    public List<Konto> getAlleKonten() {
        return kontoRepository.findAll();
    }

    @Override
    public void deleteKonto(Integer kontoId) {

        kontoRepository.delete(kontoId);
    }

    @Override
    public Konto getKonto(Integer kontoId) {
        return kontoRepository.findOne(kontoId);
    }

    @Override
    public void addKonto(Konto newKonto) {

        if (newKonto == null) {
            throw new IllegalArgumentException("KontoToAdd muss be not null!");
        }
        kontoRepository.save(newKonto);
    }

    @Override
    public void addBuchungsPosition(KontoNrTyp kontoNrTyp, BuchungsPosition newBuchungsPosition) {
        if (newBuchungsPosition == null) {
            throw new IllegalArgumentException("BuchungsPosition must be not null!");
        }

        Optional<Konto> konto = kontoRepository.findByKontoNummer(kontoNrTyp);

        if (!konto.isPresent()) {
            throw new IllegalArgumentException("konto must be not null");
        }

        konto.get().addBuchungsPosition(newBuchungsPosition);
        kontoRepository.save(konto.get());

    }

    @Override
    public void ueberweise(KontoNrTyp quellKontonummer, KontoNrTyp zielKontonummer, Integer betrag) {

        if (quellKontonummer == null)
            throw new IllegalArgumentException("Quellkontonummer must be not null!");

        if (zielKontonummer == null)
            throw new IllegalArgumentException("Zielkontonummer must be not null!");

        Optional<Konto> quellKonto = kontoRepository.findByKontoNummer(quellKontonummer);
        Optional<Konto> zielKonto = kontoRepository.findByKontoNummer(zielKontonummer);

        if (!quellKonto.isPresent())
            throw new IllegalArgumentException("Quellkontonummer nicht gefunden!");

        if (!zielKonto.isPresent())
            throw new IllegalArgumentException("Zielkontonummer nicht gefunden!");

        quellKonto.get().buche(-betrag);
        zielKonto.get().buche(betrag);

       /* Filiale filiale = new Filiale(100);
        filialeRepository.save(filiale);
        filialeComponentInterface = new FilialeComponent(filialeRepository);
        filialeComponentInterface.erhoeheFilialeStastistik();*/
    }
}
