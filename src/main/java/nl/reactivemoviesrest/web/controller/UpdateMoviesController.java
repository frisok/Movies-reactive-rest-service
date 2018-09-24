package nl.reactivemoviesrest.web.controller;

import com.google.common.base.Stopwatch;
import nl.reactivemoviesrest.service.movie.MovieScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping
    public String updateAllMovies() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        movieScraperService.updateMovies();
        return "Movies updated. Update took: " + stopwatch.stop() + " ms";
    }

}