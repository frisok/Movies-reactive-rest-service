package nl.reactivemoviesrest.data.document;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents IMDB movie details
 */
@Getter
@Setter
public class MovieDetails {

    private String Title;

    private String Year;

    //See also https://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system
    private String Rated;

    private String Runtime;

    private String Plot;

    private String Poster;

    private String Actors;

    private String Director;

}