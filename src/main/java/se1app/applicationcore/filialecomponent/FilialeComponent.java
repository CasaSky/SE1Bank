package se1app.applicationcore.filialecomponent;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by talal on 05.01.16.
 */
public class FilialeComponent implements FilialeComponentInterface{

    private FilialeRepository filialeRepository;
    @Autowired
    public FilialeComponent() {
    }
    @Override
    public void erhoeheFilialeStastistik() {

        Filiale filiale = filialeRepository.getOne(1);
        if (filiale == null)
            throw new IllegalArgumentException("Filiale nicht gefunden!");

        filiale.incrementBuchung();
    }
}
