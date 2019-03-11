package nl.reactivemoviesrest.service.moviesdetails;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import nl.reactivemoviesrest.data.document.MovieDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service that extracts movie detials from OMDB REST service
 */
@Log4j2
@Service
public class MovieDetailsRESTService {


    @Value("movie.details.rest.url:http://www.omdbapi.com/?i=tt3896198&apikey=41fbe576&plot=full&t=")
    private String movieDetailsRestUrl;


    public MovieDetails extractMovieDetailsByMovieTitle(final String movieTitle) {

        MovieDetails result;

        final ResponseEntity<String> response = callRest(movieTitle);

        if (response.getStatusCode().is2xxSuccessful()) {

            result = new Gson().fromJson(response.getBody(), new TypeToken<MovieDetails>() {
            }.getType());

        } else {
            result = new MovieDetails();
        }

        return result;
    }

    private ResponseEntity<String> callRest(final String movieTitle) {

        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        try {
            response = new RestTemplate().getForEntity("http://www.omdbapi.com/?i=tt3896198&apikey=41fbe576&plot=full&t=" + movieTitle.replace(" ", "+"), String.class);
        } catch (final Exception e) {
            log.warn("Error when calling " + movieDetailsRestUrl, e);
        }

        return response.getBody().contains("Error") ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : response;
    }

}