package nl.reactivemoviesrest.web.controller;

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
public class CollectMoviesController {


    @Autowired
    private MovieScraperService movieScraperService;


    @GetMapping
    public String updateAllMovies() {
        movieScraperService.scrapeMovies();
        return "Movies updated";
    }

}