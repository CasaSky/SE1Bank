package se1app.applicationcore.customercomponent;

/**
 * Created by srs on 14.12.15.
 */
public class CustomerNotFoundException extends Exception {
    private static final long serialVersionUID = 5234156875599771228L;

    public CustomerNotFoundException(int customerId){
        super("customer with id " + customerId + " was not found");
    }
}
