package se1app.applicationcore.kontocomponent;

import se1app.applicationcore.filialecomponent.Filiale;
import sun.swing.BakedArrayList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by talal on 31.12.15.
 */
// Define a sequence - might also be in another class:
//@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)

    @Entity
    public class Konto {
        @Id
        @GeneratedValue
        private Integer id;
        @Column(unique = true)
        private KontoNrTyp kontoNummer;
        private Integer kontostand;
        private String name;

   /* @PrePersist
    public void ensureId() {
        KontoNrTyp kontoNrTyp = new KontoNrTyp(1100000);
        kontoNummer = kontoNrTyp.getKontoNummer().intValue();
    }*/
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="konto_id")
    private List<BuchungsPosition> buchungsPositions = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="konto_id")
    private Filiale filiale;

    public Konto() {}

    public Konto(Integer kontostand, String name) {
        this.name = name;
        this.kontostand = kontostand;
        this.kontoNummer = new KontoNrTyp();
    }

    public Konto(Integer kontostand, String name, Integer kontonummer) {
        this.name = name;
        this.kontostand = kontostand;
        this.kontoNummer = new KontoNrTyp(kontonummer);
    }

    public void buche(Integer betrag) {
        buchungsPositions.add(new BuchungsPosition(betrag));
        kontostand+=betrag;
        //filiale.plusAnzahlBuchungen();
    }
    public List<BuchungsPosition> getBuchungsPositions() {
        return buchungsPositions;
    }

    public void setKontostand(Integer neuerKontostand) { this.kontostand = neuerKontostand; }

    public void addBuchungsPosition(BuchungsPosition buchungsPosition) {
        buchungsPositions.add(buchungsPosition);
    }

    public Integer getKontostand() {
        return kontostand;
    }

    public KontoNrTyp getKontoNummer() {
        return kontoNummer;
    }

    public Filiale getFiliale() { return filiale; }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Konto)) return false;

        Konto konto = (Konto) o;

        if (!id.equals(konto.id)) return false;
        if (!kontoNummer.equals(konto.kontoNummer)) return false;
        if (!kontostand.equals(konto.kontostand)) return false;
        if (!name.equals(konto.name)) return false;
        if (!buchungsPositions.equals(konto.buchungsPositions)) return false;
        return filiale.equals(konto.filiale);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + kontoNummer.hashCode();
        result = 31 * result + kontostand.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + buchungsPositions.hashCode();
        result = 31 * result + filiale.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Konto{" +
                "id=" + id +
                ", kontoNummer=" + kontoNummer +
                ", kontostand=" + kontostand +
                ", name='" + name + '\'' +
                ", buchungsPositions=" + buchungsPositions +
                ", filiale=" + filiale +
                '}';
    }
}
