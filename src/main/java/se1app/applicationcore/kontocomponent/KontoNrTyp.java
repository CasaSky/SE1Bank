package se1app.applicationcore.kontocomponent;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by talal on 31.12.15.
 */
//@Embeddable
public class KontoNrTyp implements Serializable {

    private static final Integer MAXNUMMER = 100000;
    private static final Integer MINNUMMER = 10000;

    private Integer kontoNummer;

    public KontoNrTyp() {
        generateKontoNummer();
    }

    public KontoNrTyp(Integer kontoNummer) {
        if (kontoNummer - MAXNUMMER < 0 )
            throw new IllegalArgumentException("Die angegebene Kontonummer is nicht gültig!");
        else
            this.kontoNummer = kontoNummer;
    }

    public Integer getKontoNummer() {
        return kontoNummer;
    }

    private void generateKontoNummer() {
        Random rand = new Random();

        this.kontoNummer = rand.nextInt(MAXNUMMER) + MINNUMMER;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KontoNrTyp)) return false;

        KontoNrTyp that = (KontoNrTyp) o;

        return kontoNummer.equals(that.kontoNummer);

    }

    @Override
    public int hashCode() {
        return kontoNummer.hashCode();
    }

    @Override
    public String toString() {
        return "KontoNrTyp{" +
                "kontoNummer=" + kontoNummer +
                '}';
    }

}
