package se1app.applicationcore.customercomponent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import se1app.applicationcore.moviecomponent.Movie;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    @ManyToOne
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Movie movie;

    public Reservation() {}

    public Reservation(Movie movie)
    {
        this.movie = movie;
    }

    public Integer getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Reservation){
            Reservation toCompare = (Reservation) o;
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
                "Reservation[id=%d]",
                id);
    }
}