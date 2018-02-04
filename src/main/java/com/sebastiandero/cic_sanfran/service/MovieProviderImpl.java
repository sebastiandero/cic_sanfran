package com.sebastiandero.cic_sanfran.service;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class MovieProviderImpl implements MovieProvider {

    private SAXParserFactory factory = SAXParserFactory.newInstance();

    private File dataSet = new File(getClass().getResource("/sanfran_movies_dataset.xml").getPath());

    @Override
    public List<Movie> getMovies() {
        return parse();
    }

    @Override
    public List<Movie> getMovies(String title) {
        return parse(title);
    }

    @Override
    public List<Movie> getMovies(String title, int beforeYear) {
        return parse(title, beforeYear);
    }


    private List<Movie> parse() {
        return parse(null, - 1);
    }

    private List<Movie> parse(String title) {
        return parse(title, - 1);
    }


    private List<Movie> parse(String title, int beforeYear) {
        try {
            MovieSAXHandler handler = new MovieSAXHandler(title, beforeYear);
            SAXParser parser = factory.newSAXParser();
            parser.parse(dataSet, handler);

            return handler.getMovies();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.info("Something went wrong parsing the dataSet: ", e);
            return Collections.emptyList();
        }
        /*

        try (FileInputStream fis = new FileInputStream(dataSet)) {
            XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInFact.createXMLStreamReader(fis);

            List<Movie> movies = new ArrayList<>();

            while (reader.hasNext()) {
                reader.next();
                log.info(reader.getElementText());
                Movie.parseFromXMLFiltered(reader, title, beforeYear).ifPresent(movies::add);
            }

            return movies;
        } catch (IOException | XMLStreamException e) {
            log.info("Something went wrong parsing the XML: ", e);
            return Collections.emptyList();
        }
        */
    }
}
