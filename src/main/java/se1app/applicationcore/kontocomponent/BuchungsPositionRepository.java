package se1app.applicationcore.kontocomponent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by talal on 12.01.16.
 */
@Repository
public interface BuchungsPositionRepository extends JpaRepository<BuchungsPosition, Integer> {
}
