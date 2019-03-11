package nl.reactivemoviesrest.service.movie;

import nl.reactivemoviesrest.data.document.Movie;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    private static final String MOVIE_CONTAINER_CLASS = "movies";
    private static final String HTML_TAG_UL = "ul";
    private static final String HTML_TAG_LI = "li";


    public List<Movie> convert(final Document htmlDocument) {

        List<Movie> movieDocuments = new ArrayList<>();

        final Optional<Element> allMoviesElementOptional = htmlDocument.getElementsByClass(MOVIE_CONTAINER_CLASS)
                .stream()
                .filter(e -> HTML_TAG_UL.equalsIgnoreCase(e.tag().getName()))
                .findFirst();

        if (allMoviesElementOptional.isPresent()) {
            movieDocuments = allMoviesElementOptional.get()
                    .getElementsByTag(HTML_TAG_LI)
                    .stream()
                    .map(s -> convertToMovieDocument(s))
                    .filter(m -> StringUtils.isNotBlank(m.getTitle()))
                    .collect(Collectors.toList());
        }

        return movieDocuments;
    }


    private Movie convertToMovieDocument(final Element singleMovieElement) {

        final Movie movie = new Movie();

        movie.setTitle(singleMovieElement.attr("data-name"));
        movie.setScreeningsOverviewUrl(singleMovieElement.getElementsByAttributeValue("itemprop", "url").attr("href"));

        return movie;
    }

}