package nl.reactivemoviesrest.service.screening;

import lombok.extern.slf4j.Slf4j;
import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.document.Screening;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ScreeningScraperService {

    @Autowired
    private HtmlToScreeningConverter htmlToScreeningConverter;

    private static final String ERROR_COLLECTING_SCREENING_DATA = "Error collecting screening data";


    public List<Screening> extractScreeningsByMovieTitle(final Movie movie) {
        final Document screeningsHtml = extractHtml(movie.getScreeningsOverviewUrl());
        final List<Screening> screenings = htmlToScreeningConverter.convert(screeningsHtml);
        return screenings;
    }

    public Document extractHtml(final String movieScreeningsUrl) {

        Document html = new Document("");

        try {
            html = Jsoup.connect(movieScreeningsUrl).get();
        } catch (IOException e) {
            log.error(ERROR_COLLECTING_SCREENING_DATA, e);
        }

        return html;
    }
}
