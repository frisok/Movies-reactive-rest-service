package nl.reactivemoviesrest.web.controller;

import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Date;

/**
 *
 */
@RestController
@RequestMapping("/movies")
public class MovieController {


    @Autowired
    private MovieRepository movieRepository;


    @GetMapping()
    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/{city}")
    public Flux<Movie> findByCity(@PathVariable("city") String city) {
        return movieRepository.findByScreeningsCinemaCity(city);
    }

    /**
     * Example request url: http://localhost:8080/movies/search?title=title&city=rotterdam&start_date=01-01-2018&end_date=01-01-2019&child_friendly=false&distance=10&location=somelocation
     *
     * @param title
     * @param city
     * @return
     */
    @GetMapping("/search")
    public Flux<Movie> findByFilter(@RequestParam("title") String title,
                                    @RequestParam("city") String city,
                                    @RequestParam("start_date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                    @RequestParam("end_date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                    @RequestParam("child_friendly") boolean childFriendly,
                                    @RequestParam("distance") int distance,
                                    @RequestParam("location") String location) {
        return movieRepository.findByScreeningsCinemaCity(city);
    }


}