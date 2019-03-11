package nl.reactivemoviesrest.service.screening;


import nl.reactivemoviesrest.data.document.Cinema;
import nl.reactivemoviesrest.data.document.Screening;
import nl.reactivemoviesrest.util.MoviesDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HtmlToScreeningConverter {


    public List<Screening> convert(final Document html) {

        final List<Screening> result = new ArrayList<>();
        final Elements cityElements = html.getElementById("movie-cities-performances").getElementsByClass("city");

        cityElements.stream().forEach(e -> result.addAll(convertScreeningsPerCity(e)));

        return result;
    }


    private List<Screening> convertScreeningsPerCity(final Element singleCityElement) {

        final List<Screening> screenings = new ArrayList<>();

        final String city = singleCityElement.getElementsByClass("movie-link").text();

        final Elements screeningsByCinemaElements = singleCityElement.getElementsByClass("hall-container");
        screeningsByCinemaElements.stream().forEach(e -> screenings.addAll(convertScreeningsForSingleCinema(e, city)));


        sortScreeningsByStartDateTimeAscending(screenings);

        return screenings;
    }

    private List<Screening> convertScreeningsForSingleCinema(final Element screeningsByCinemaElement, final String city) {

        ArrayList<Screening> screenings = new ArrayList<>();

        final Cinema cinema = new Cinema();
        cinema.setCity(city);
        cinema.setName(screeningsByCinemaElement.getElementsByClass("cinema-link").text());


        screeningsByCinemaElement.getElementsByAttributeValue("itemprop", "startDate").stream()
                .forEach(e -> screenings.add(buildScreening(e.attr("content"), cinema)));

        final Screening screening = new Screening();
        screening.setCinema(cinema);

        return screenings;

    }

    private Screening buildScreening(final String startDateTimestring, final Cinema cinema) {

        Screening screening = null;
        final String formattedStartDateTimeString = MoviesDateUtil.validateAndClearOfTimezone(startDateTimestring);

        if (StringUtils.isNotBlank(formattedStartDateTimeString)) {
            screening = new Screening();
            screening.setCinema(cinema);
            screening.setStartDateTime(formattedStartDateTimeString);
        }

        return screening;

    }

    private void sortScreeningsByStartDateTimeAscending(List<Screening> screenings) {

        Collections.sort(screenings, (s1, s2) -> {

            if (s1 == null || s1.getStartDateTime() == null) {
                return -1;
            } else if (s2 == null || s2.getStartDateTime() == null) {
                return 1;
            } else {
                return MoviesDateUtil.parse(s1.getStartDateTime()).before(MoviesDateUtil.parse(s2.getStartDateTime())) ? -1 : 1;
            }
        });

    }


}
