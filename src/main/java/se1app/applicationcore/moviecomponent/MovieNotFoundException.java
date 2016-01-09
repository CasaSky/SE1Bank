package se1app.applicationcore.moviecomponent;

/**
 * Created by srs on 14.12.15.
 */
public class MovieNotFoundException extends Exception {
    private static final long serialVersionUID = 5234151235599771228L;

    public MovieNotFoundException(String movieTitle){
        super("movie with title " + movieTitle + " was not found");
    }
}
