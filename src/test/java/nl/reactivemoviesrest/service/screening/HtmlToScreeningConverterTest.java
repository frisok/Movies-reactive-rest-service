package nl.reactivemoviesrest.service.screening;

import nl.reactivemoviesrest.data.document.Movie;
import nl.reactivemoviesrest.data.document.Screening;
import org.hamcrest.core.Is;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class HtmlToScreeningConverterTest {

    @InjectMocks
    private HtmlToScreeningConverter htmlToScreeningConverter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvert() throws IOException {
        final Document html = Jsoup.parse(new File("src/test/resources/filmladder_screenings_per_movie_20190311.html"), "UTF-8");

        final List<Screening> result = htmlToScreeningConverter.convert(html);

        Assert.assertThat(result.size(), Is.is(146));
    }
}