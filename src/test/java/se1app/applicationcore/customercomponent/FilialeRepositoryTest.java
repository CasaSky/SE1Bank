package se1app.applicationcore.customercomponent;

import org.assertj.core.api.StrictAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se1app.applicationcore.Application;
import se1app.applicationcore.filialecomponent.Filiale;
import se1app.applicationcore.filialecomponent.FilialeRepository;
import se1app.applicationcore.moviecomponent.Movie;
import se1app.applicationcore.util.EmailType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by talal on 09.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FilialeRepositoryTest {

    @Autowired
    private FilialeRepository filialeRepository;

    @Before
    public void setup() {
        Filiale haspa = new Filiale(100);
        filialeRepository.save(haspa);
    }

    @Test
    public void testFindAll(){
        List<Filiale> filiales = filialeRepository.findAll();
        assertThat(filiales).hasSize(1);
    }

    @Test
    public void testFindByFilialNr(){
        Integer DEFAULTFILIALNR = 123456;
        Optional<Filiale> filiale = filialeRepository.findByFilialNr(DEFAULTFILIALNR);
        StrictAssertions.assertThat(filiale.isPresent());
        StrictAssertions.assertThat(filiale.get().getFilialNr()).isEqualTo(DEFAULTFILIALNR);
    }
}
