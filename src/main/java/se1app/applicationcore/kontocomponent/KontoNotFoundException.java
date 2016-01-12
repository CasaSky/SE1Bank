package se1app.applicationcore.kontocomponent;
/**
 * Created by talal on 10.01.16.
 */
public class KontoNotFoundException extends Exception {

    private static final long serialVersionUID = 5234156875599771228L;

    public KontoNotFoundException(int kontoId){ super("konto with id " + kontoId + " was not found"); }
}
