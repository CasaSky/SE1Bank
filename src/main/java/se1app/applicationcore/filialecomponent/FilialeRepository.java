package se1app.applicationcore.filialecomponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by talal on 05.01.16.
 */
@Repository
public interface FilialeRepository extends JpaRepository<Filiale, Integer> {
}
