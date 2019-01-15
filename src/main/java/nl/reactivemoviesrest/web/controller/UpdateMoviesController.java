package nl.reactivemoviesrest.web.controller;

import com.google.common.base.Stopwatch;
import nl.reactivemoviesrest.service.movie.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/updatemovies")
public class UpdateMoviesController {


    @Autowired
    private MovieScraperService movieScraperService;


    /**
     * Example request: curl -XPOST -H 'Authorization: 95de3112-59f6-4851-a072-d12d83fa077a' http://localhost:8080/updatemovies
     */
    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public HttpEntity<String> updateAllMovies() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        movieScraperService.updateMovies();
        return ResponseEntity.ok("Movies updated. Update took: " + stopwatch.stop() + " ms");
    }

}