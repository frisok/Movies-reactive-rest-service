package nl.reactivemoviesrest.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.repository.MovieRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

/**
 *
 */
@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping()
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Flux<Movie>> findAll() {
        return new ResponseEntity(movieRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{city}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Flux<Movie>> findByCity(@PathVariable("city") String city) {
        final Flux<Movie> result = StringUtils.equalsIgnoreCase(city, "all") ? movieRepository.findAll() : movieRepository.findByScreeningsCinemaCity(city);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Flux<Movie>> findPaginatedByCity(@RequestParam(value = "city", required = true) String city,
                                                           @RequestParam(value = "pageIndex", required = true) int pageIndex,
                                                           @RequestParam(value = "pageSize", required = true) int pageSize) {
        final PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        final Flux<Movie> result = StringUtils.equalsIgnoreCase(city, "all") ? movieRepository.findByScreeningsCinemaCityNot(city, pageRequest) : movieRepository.findByScreeningsCinemaCity(city, pageRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/count")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Mono<Long>> countByCity(@RequestParam(value = "city", required = true) String city) {
        final Mono<Long> result = StringUtils.equalsIgnoreCase(city, "all") ? movieRepository.countByScreeningsCinemaCityNot(city) : movieRepository.countByScreeningsCinemaCity(city);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    /**
     * Example request url: http://localhost:8080/movies/search?title=title&city=rotterdam&start_date=01-01-2018&end_date=01-01-2019&child_friendly=false&distance=10&location=somelocation
     *
     * @param title
     * @param city
     * @return
     */
    @GetMapping("/search")
    public Flux<Movie> findByFilter(@RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "city", required = false) String city,
                                    @RequestParam(value = "start_date", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                    @RequestParam(value = "end_date", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                    @RequestParam(value = "child_friendly", required = false) boolean childFriendly,
                                    @RequestParam(value = "distance", required = false) int distance,
                                    @RequestParam(value = "location", required = false) String location) {
        return movieRepository.findByScreeningsCinemaCity(city);
    }

    /**
     * Example request url: http://localhost:8080/movies/title?title=wom
     *
     * @param title
     * @return
     */
    @GetMapping("/title")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Flux<Movie>> findByTitle(@RequestParam("title") String title) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final Movie probe = new Movie();
        probe.setTitle(title);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", contains().ignoreCase());
        final Flux<Movie> result = movieRepository.findAll(Example.of(probe, matcher));

        log.info(String.format("'findByFilter' took %s ms", stopWatch.getTime()));
        stopWatch.stop();

        return new ResponseEntity(result, HttpStatus.OK);
    }

}