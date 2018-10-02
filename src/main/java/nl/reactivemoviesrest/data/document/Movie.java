package nl.reactivemoviesrest.data.document;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 *
 */
@Document(collection = "movie")
@Getter
@Setter
public class Movie {

    @Id
    private String id;

    private String title;

    private MovieDetails movieDetails;

    private String trailerLink;

    private List<Screening> screenings;
}