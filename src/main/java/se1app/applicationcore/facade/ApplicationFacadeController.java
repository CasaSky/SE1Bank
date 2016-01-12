package se1app.applicationcore.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se1app.applicationcore.customercomponent.Customer;
import se1app.applicationcore.customercomponent.CustomerComponentInterface;
import se1app.applicationcore.customercomponent.CustomerNotFoundException;
import se1app.applicationcore.customercomponent.Reservation;
import se1app.applicationcore.filialecomponent.FilialeComponentInterface;
import se1app.applicationcore.kontocomponent.BuchungsPosition;
import se1app.applicationcore.kontocomponent.Konto;
import se1app.applicationcore.kontocomponent.KontoComponentInterface;
import se1app.applicationcore.kontocomponent.KontoNotFoundException;
import se1app.applicationcore.moviecomponent.MovieComponentInterface;
import se1app.applicationcore.moviecomponent.MovieNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RestController
class ApplicationFacadeController {

   // @Autowired
    //private CustomerComponentInterface customerComponentInterface;

    @Autowired
    private KontoComponentInterface kontoComponentInterface;

    @Autowired
    private FilialeComponentInterface filialeComponentInterface;


    @RequestMapping("/kontos")
    public List<Konto> getALLKontos() {
        return kontoComponentInterface.getAlleKonten();
    }

    @RequestMapping(value = "/kontos/{id}", method = RequestMethod.GET)
    public Konto getKonto(@PathVariable("id") Integer id) {
        return kontoComponentInterface.getKonto(id);
    }

    @RequestMapping(value = "/kontos/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Integer id) {
        kontoComponentInterface.deleteKonto(id);
    }

    @RequestMapping(value = "/kontos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Konto addKonto(@RequestBody Konto konto) {
        kontoComponentInterface.addKonto(konto);
        return konto;
    }

    @RequestMapping(value = "/kontos/{id}/buchungsposition", method = RequestMethod.POST)
    public ResponseEntity<?> addBuchungsPosition(@PathVariable("id") Integer kontoId, @RequestBody BuchungsPosition buchungsPosition) {
        try {
            Konto konto = kontoComponentInterface.getKonto(kontoId);
            if (konto == null)
                throw new KontoNotFoundException(kontoId);
            else {
                kontoComponentInterface.addBuchungsPosition(konto.getKontoNummer(), buchungsPosition);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        catch(KontoNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BuchungsPosition ueberweise(@RequestBody String betrag) {
            if (betrag == null )
                throw new IllegalArgumentException("Betrag darf nicht null!");
            Konto talal = new Konto(1000, "Talal");
            Konto kyo = new Konto(1000, "Kyo");
            Integer talalId = talal.getId();
            Integer kyoId = kyo.getId();
            kontoComponentInterface.addKonto(talal);
            kontoComponentInterface.addKonto(kyo);
            Konto konto1 = kontoComponentInterface.getKonto(talalId);
            Konto konto2 = kontoComponentInterface.getKonto(kyoId);
            kontoComponentInterface.ueberweise(talal.getKontoNummer(), kyo.getKontoNummer(), Integer.parseInt(betrag));
            return new BuchungsPosition(Integer.parseInt(betrag));
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public List<BuchungsPosition> getBuchungen() {

        List<BuchungsPosition> buchungen;
            List<Konto> kontos = kontoComponentInterface.getAlleKonten();
            Konto zielKonto = null;
            for (Konto konto : kontos) {
                if (konto.getName().equalsIgnoreCase("Kyo"))
                    zielKonto = konto;
            }
                buchungen = zielKonto.getBuchungsPositions();
                return buchungen;
    }
}