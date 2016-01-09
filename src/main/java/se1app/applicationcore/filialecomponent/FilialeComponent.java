package se1app.applicationcore.filialecomponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by talal on 05.01.16.
 */
@Component
public class FilialeComponent implements FilialeComponentInterface{

    private FilialeRepository filialeRepository;
    private final Integer DEFAULTFILIALNR = 123456;

    @Autowired
    public FilialeComponent(FilialeRepository filialeRepository) {
        this.filialeRepository = filialeRepository;
    }
    @Override
    public void erhoeheFilialeStastistik() {

        Optional<Filiale> filiale = filialeRepository.findByFilialNr(DEFAULTFILIALNR);
        if (!filiale.isPresent())
            throw new IllegalArgumentException("Filiale nicht gefunden!");
        else
            filiale.get().incrementBuchung();
    }
}
