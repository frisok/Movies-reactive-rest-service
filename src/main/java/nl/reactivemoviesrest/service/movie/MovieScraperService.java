package nl.reactivemoviesrest.service.movie;

import lombok.extern.slf4j.Slf4j;
import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.repository.MovieRepository;
import nl.reactivemoviesrest.service.etl.BaseETL;
import nl.reactivemoviesrest.service.moviesdetails.MovieDetailsRESTService;
import nl.reactivemoviesrest.service.screening.ScreeningScraperService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * ETL service that scrapes movies form specified base url and loads them into the database
 */
@Service
@Slf4j
public class MovieScraperService extends BaseETL<String, Document, List<Movie>> {


    private static final String ERROR_COLLECTING_MOVIE_DATA = "Error collecting movie data";
    private static final String BASE_URL = "https://www.filmladder.nl/";
    private static final String PATH_FILMS = "films/nu-in-de-bioscoop";

    @Autowired
    private HtmlToMovieConverter htmlToMovieConverter;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieDetailsRESTService movieDetailsRESTService;

    @Autowired
    private ScreeningScraperService screeningScraperService;


    public void updateMovies() {

        //First delete old documents
        movieRepository.findAll().flatMap(m -> movieRepository.delete(m)).blockLast();

        //Then insert new ones
        extractTransformLoad();

    }

    @Override
    public Document extract() {

        Document html = new Document("");

        try {
            html = Jsoup.connect(BASE_URL + PATH_FILMS).get();
        } catch (IOException e) {
            log.error(ERROR_COLLECTING_MOVIE_DATA, e);
        }

        return html;
    }

    @Override
    public List<Movie> transform(Document extractedData) {

        final List<Movie> result = htmlToMovieConverter.convert(extractedData);

        for (Movie movie : result) {
            movie.setMovieDetails(movieDetailsRESTService.extractMovieDetailsByMovieTitle(movie.getTitle()));
            movie.setScreenings(screeningScraperService.extractScreeningsByMovieTitle(movie));
        }
        return result;
    }

    @Override
    public void load(List<Movie> transformedData) {
        movieRepository.insert(transformedData).blockLast();
    }


}