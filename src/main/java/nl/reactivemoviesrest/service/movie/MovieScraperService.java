package nl.reactivemoviesrest.service.movie;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.repository.MovieRepository;
import nl.reactivemoviesrest.service.etl.BaseETL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * ETL service that scrapes movies form specified base url and loads them into the database
 */
@Service
@Slf4j
public class MovieScraperService extends BaseETL<String, Document, List<Movie>> {


    private static final List<String> cities = Arrays.asList(new String[]{"amsterdam", "rotterdam", "utrecht"});
    private static final String ERROR_COLLECTING_MOVIE_DATA = "Error collecting movie data";
    private static final String BASE_URL = "https://www.filmladder.nl/";
    private static final String PATH_FILMS = "/films";

    @Autowired
    private HtmlToMovieConverter htmlToMovieConverter;
    @Autowired
    private MovieRepository movieRepository;


    public void updateMovies() {

        //First delete old documents
        movieRepository.findAll().flatMap(m -> movieRepository.delete(m)).blockLast();

        //Then insert new ones
        for (String city : cities) {
            extractTransformLoad(city);
        }
    }

    @Override
    public Document extract(final String city) {

        Document html = new Document("");

        try {
            html = Jsoup.connect(BASE_URL + city + PATH_FILMS).get();
        } catch (IOException e) {
            log.error(ERROR_COLLECTING_MOVIE_DATA, e);
        }

        return html;
    }

    @Override
    public List<Movie> transform(Document extractedData) {
        return htmlToMovieConverter.convert(extractedData);
    }

    @Override
    public void load(List<Movie> transformedData) {
        movieRepository.insert(transformedData).blockLast();
    }


}