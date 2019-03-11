package nl.reactivemoviesrest.service.etl;

/**
 * Base implementation of Extract/Transform/Load pattern
 */
public abstract class BaseETL<I, E, T> implements ETL<I,E, T> {


    public final void extractTransformLoad() {

        final E e = extract();
        final T t = transform(e);
        load(t);
    }

}