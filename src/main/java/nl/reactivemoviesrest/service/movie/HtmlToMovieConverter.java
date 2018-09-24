package nl.reactivemoviesrest.service.movie;

import nl.reactivemoviesrest.data.document.Cinema;
import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.document.Screening;
import nl.reactivemoviesrest.util.MoviesDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class to convert html document to a list of Movie documents
 */
@Service
public class HtmlToMovieConverter {

    private static final String MOVIE_CONTAINER_CLASS = "city-movie";
    private static final String CINIMAS_CONTAINER_CLASS = "hall-container";
    private static final String CINEMA_TITLE_CLASS = "cinema-link";
    private static final String ATTRIBUTE_KEY_ITEM_PROP = "itemprop";
    private static final String ATTRIBUTE_KEY_CONTENT = "content";
    private static final String ATTRIBUTE_VALUE_START_DATE = "startDate";


    public List<Movie> convert(final Document htmlDocument) {

        final Elements singleMovieElements = htmlDocument.getElementsByClass(MOVIE_CONTAINER_CLASS);
        final String city = htmlDocument.location().substring("https://www.filmladder.nl/".length(), htmlDocument.location().lastIndexOf("/"));

        final List<Movie> movieDocuments = singleMovieElements
                .stream()
                .map(s -> convertToMovieDocument(s, city))
                .collect(Collectors.toList());

        return movieDocuments;
    }


    private Movie convertToMovieDocument(final Element singleMovieElement, final String city) {
        final Movie movie = convertMovie(singleMovieElement);
        movie.setScreenings(convertScreenings(singleMovieElement, city));
        return movie;
    }

    private Movie convertMovie(final Element singleMovieElement) {

        final Movie movie = new Movie();

        final Optional<String> titleElement = singleMovieElement.getElementsByClass(CINEMA_TITLE_CLASS)
                .stream()
                .map(s -> s.text())
                .findFirst();
        movie.setTitle(titleElement.isPresent() ? titleElement.get() : null);

        return movie;
    }

    private List<Screening> convertScreenings(final Element singleMovieElement, final String city) {

        final List<Screening> screenings = new ArrayList<>();

        final Elements cinemaAndScreeningsElements = singleMovieElement.getElementsByClass(CINIMAS_CONTAINER_CLASS);

        for (Element cinemaAndScreeningsElement : cinemaAndScreeningsElements) {

            final Optional<String> cinemaOptional = cinemaAndScreeningsElement.getElementsByClass(CINEMA_TITLE_CLASS)
                    .stream()
                    .map(el -> el.text())
                    .findFirst();

            if (cinemaOptional.isPresent()) {
                final Cinema cinema = new Cinema();
                cinema.setName(cinemaOptional.get());
                cinema.setCity(city);
                screenings.addAll(convertScreeningsForSingleCinema(cinemaAndScreeningsElement, cinema));
            }
        }

        return screenings;
    }

    private List<Screening> convertScreeningsForSingleCinema(final Element singleCinemaAndScreeningsElement, final Cinema cinema) {

        final Screening screening = new Screening();
        screening.setCinema(cinema);

        final List<Screening> screenings = singleCinemaAndScreeningsElement.getElementsByAttributeValue(ATTRIBUTE_KEY_ITEM_PROP, ATTRIBUTE_VALUE_START_DATE)
                .stream()
                .map(el -> el.attr(ATTRIBUTE_KEY_CONTENT))
                .map(st -> buildScreening(st, cinema))
                .filter(sc -> sc != null)
                .collect(Collectors.toList());

        return screenings;
    }

    private Screening buildScreening(final String startDateTimestring, final Cinema cinema) {

        Screening screening = null;
        final String formattedStartDateTimeString = MoviesDateUtil.validateAndClearOfTimezone(startDateTimestring);

        if (StringUtils.isNotBlank(formattedStartDateTimeString)) {
            screening = new Screening();
            screening.setCinema(cinema);
            screening.setStartDateTime(formattedStartDateTimeString);
        }

        return screening;

    }


}