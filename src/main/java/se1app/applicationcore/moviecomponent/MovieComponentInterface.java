package se1app.applicationcore.moviecomponent;

/**
 * Created by srs on 12.12.15.
 */
public interface MovieComponentInterface {

    /**
     * Liefert die Anzahl der für den gegebenen Film (Titel) getätigten Reservierungen.
     */
    int getNumberOfReservations(String movieTitle) throws MovieNotFoundException;

    /**
     * Erhöht den Reservierungstatistik-Zähler des gegebenen Films (Titel).
     */
    void increaseReservationStatistics(String movieTitle) throws MovieNotFoundException;
}
