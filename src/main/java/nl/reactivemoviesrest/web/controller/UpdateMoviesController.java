package nl.reactivemoviesrest.web.controller;

import com.google.common.base.Stopwatch;
import nl.reactivemoviesrest.service.movie.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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


    @PostMapping
    public HttpEntity<String> updateAllMovies() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        movieScraperService.updateMovies();
        return ResponseEntity.ok("Movies updated. Update took: " + stopwatch.stop() + " ms");
    }

}