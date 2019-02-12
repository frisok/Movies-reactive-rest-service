package nl.reactivemoviesrest.data.repository;

import nl.reactivemoviesrest.data.document.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 *
 */
@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    Flux<Movie> findByScreeningsCinemaCity(String city);

    Flux<Movie> findByScreeningsCinemaCity(String city, Pageable pageable);

    Flux<Movie> findByScreeningsCinemaCityNot(String city, Pageable pageable);

    Mono<Long> countByScreeningsCinemaCity(String city);

    Mono<Long> countByScreeningsCinemaCityNot(String city);

}