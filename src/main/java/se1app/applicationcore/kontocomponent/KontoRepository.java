package se1app.applicationcore.kontocomponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by talal on 05.01.16.
 */
@Repository
public interface KontoRepository extends JpaRepository<Konto, Integer> {

    Optional<Konto> findByKontoNummer(KontoNrTyp kontoNr);
    Optional<Konto> findByName(String name);

}
