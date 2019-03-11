package nl.reactivemoviesrest.service.etl;

/**
 * Interface for implementations of Extract/Transform/Load pattern
 */
public interface ETL<I,E, T> {


    E extract();

    T transform(E extractedData);

    void load(T transformedData);

}