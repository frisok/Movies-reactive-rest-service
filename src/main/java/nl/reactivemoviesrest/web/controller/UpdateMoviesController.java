package nl.reactivemoviesrest.web.controller;

import com.google.common.base.Stopwatch;
import nl.reactivemoviesrest.service.movie.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
public class UpdateMoviesController {


    @Autowired
    private MovieScraperService movieScraperService;


    /**
     * Example request: curl -XPOST -H 'Authorization: 95de3112-59f6-4851-a072-d12d83fa077a' http://localhost:8080/updatemovies
     */
    @PostMapping("/updatemovies")
    @CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://85.214.209.74","http://friso.amsterdam"})
    public HttpEntity<String> updateAllMovies() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        //movieScraperService.updateMovies();
        final HttpEntity<String> response = ResponseEntity.ok("Movies updated. Update took: " + stopwatch.stop().elapsed().getSeconds() + " sec");

        return response;


    }

}