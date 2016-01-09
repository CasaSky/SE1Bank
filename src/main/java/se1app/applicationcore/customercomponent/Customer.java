package se1app.applicationcore.customercomponent;

import se1app.applicationcore.util.EmailType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    private String name;

    private EmailType email;

    // Technische ID der Entität (Auto-generiert)
    // Wir würden besser zusätzlich noch eine fachliche ID definieren
    @Id
    @GeneratedValue
    private Integer id;

    // Definition einer 1:*-Beziehung
    // Kaskadierende Operationen
    // Name des Fremdschlüssels gesetzt, weil wir ihn in einer Query verwenden müssen
    //  (siehe Methode findCustomersByMovie() in Klasse CustomerRepository)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private List<Reservation> reservations = new ArrayList<>();

    public Customer() {}

    public Customer(String name)
    {
        this.name = name;
    }

    public Customer(String name, EmailType email)
    {
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EmailType getEmail() {
        return email;
    }

    public void setEmail(EmailType email) {
        this.email = email;
    }

    public void addReservation(Reservation reservation)
    {
        this.reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Customer){
            Customer toCompare = (Customer) o;
            return this.id.equals(toCompare.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s']",
                id, name);
    }
}