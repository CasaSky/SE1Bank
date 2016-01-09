package se1app.applicationcore.moviecomponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieComponent implements MovieComponentInterface {

    private MovieRepository movieRepository;

    @Autowired
    public MovieComponent(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    @Override
    public int getNumberOfReservations(String movieTitle) throws MovieNotFoundException {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie == null)
        {
            throw new MovieNotFoundException(movieTitle);
        }
        return movie.getNumberOfReservations();
    }

    @Override
    public void increaseReservationStatistics(String movieTitle) throws MovieNotFoundException {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie == null)
        {
            throw new MovieNotFoundException(movieTitle);
        }

        movie.increaseReservationStatistics();
        movieRepository.save(movie);
    }
}
