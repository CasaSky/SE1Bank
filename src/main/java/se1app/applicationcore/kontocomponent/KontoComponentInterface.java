package se1app.applicationcore.kontocomponent;

import java.util.List;

/**
 * Created by talal on 31.12.15.
 */
public interface KontoComponentInterface {
    /**
     * Liefert alle Konten
     * @return Liste von Konten, leere Liste, falls kein Konto vorhanden.
     */
    List<Konto> getAlleKonten();

    /**
     * Löscht ein Konto
     */
    void deleteKonto(Integer kontoId);

    /**
     * Sucht ein Konto
     */
    Konto getKonto(Integer kontoId);

    /**
     * Fügt der Komponente ein Konto hinzu.
     */
    void addKonto(Konto newKonto);

    /**
     * Fügt einem Konto eine neue Buchungsposition hinzu.
     * @param newBuchungsPosition neue Buchungsposition, die hinzugefügt werden soll;
     *                            muss (vorhanden) Konto enthalten.
     */
    void addBuchungsPosition(KontoNrTyp kontoNrTyp, BuchungsPosition newBuchungsPosition);


    void ueberweise(KontoNrTyp quellKontonummer, KontoNrTyp zielKontonummer, Integer betrag);
}
