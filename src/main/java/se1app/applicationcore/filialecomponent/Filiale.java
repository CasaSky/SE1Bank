package se1app.applicationcore.filialecomponent;


import se1app.applicationcore.kontocomponent.Konto;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by talal on 31.12.15.
 */
@Entity
//@SequenceGenerator(name="seq", initialValue=1, allocationSize = 99999)
public class Filiale {

    private static final long MAXBUCHUNG = 9999999999L;

    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private final Integer filialNr = 123456; // VEREINFACHUNG eine eizige Filiale

    //@Max(MAXBUCHUNG)
    private long anzahlBuchungen;

    @OneToMany
    @JoinColumn(name="filial_id")
    private List<Konto> konten = new ArrayList<>();

    public Filiale () {}

    public Filiale(long anzahlBuchungen) {
        if (anzahlBuchungen > MAXBUCHUNG)
            throw new IllegalArgumentException("Anzahl an Buchungen soll die Laenge 10 haben!");
        else
            this.anzahlBuchungen = anzahlBuchungen;
    }

    public void addKonto(Konto konto) {
        konten.add(konto);
    }

    public Integer getFilialNr() {
        return filialNr;
    }

    public long getAnzahlBuchungen() {
        return anzahlBuchungen;
    }

    public void incrementBuchung() {
        this.anzahlBuchungen ++;
    }

    public List<Konto> getKonten() {
        return konten;
    }

    public void setKonten(List<Konto> konten) {
        this.konten = konten;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Filiale)) return false;

        Filiale filiale = (Filiale) o;

        if (anzahlBuchungen != filiale.anzahlBuchungen) return false;
        if (!filialNr.equals(filiale.filialNr)) return false;
        return konten.equals(filiale.konten);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + filialNr.hashCode();
        result = 31 * result + (int) (anzahlBuchungen ^ (anzahlBuchungen >>> 32));
        result = 31 * result + konten.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Filiale{" +
                "id=" + id +
                ", filialNr=" + filialNr +
                ", anzahlBuchungen=" + anzahlBuchungen +
                ", konten=" + konten +
                '}';
    }
}
