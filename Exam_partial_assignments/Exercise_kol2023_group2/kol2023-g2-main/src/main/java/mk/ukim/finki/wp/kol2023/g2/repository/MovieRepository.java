package mk.ukim.finki.wp.kol2023.g2.repository;

import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    //listMoviesWithRatingLessThenAndGenre
    List<Movie> findMoviesByRatingLessThanAndGenre(Double rating, Genre genre);

    List<Movie> findMoviesByGenre(Genre genre);

    List<Movie> findMoviesByRatingLessThan(Double rating);
}
