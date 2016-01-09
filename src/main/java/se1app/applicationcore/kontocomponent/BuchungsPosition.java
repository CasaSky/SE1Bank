package se1app.applicationcore.kontocomponent;

import javax.persistence.*;
/**
 * Created by talal on 31.12.15.
 */
@Entity
public class BuchungsPosition {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer gebuchterBetrag;

    public BuchungsPosition() {}
    public BuchungsPosition(Integer gebuchterBetrag) {this.gebuchterBetrag = gebuchterBetrag;}

    public Integer getGebuchterBetrag() {
        return gebuchterBetrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuchungsPosition)) return false;

        BuchungsPosition that = (BuchungsPosition) o;

        return gebuchterBetrag.equals(that.gebuchterBetrag);

    }

    @Override
    public int hashCode() {
        return gebuchterBetrag.hashCode();
    }

    @Override
    public String toString() {
        return "BuchungsPosition{" +
                "gebuchterBetrag=" + gebuchterBetrag +
                '}';
    }
}
