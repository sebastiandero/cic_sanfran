package com.sebastiandero.cic_sanfran.service;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class MovieProviderImpl implements MovieProvider {

    private File dataSet = new File(getClass().getResource("/sanfran_movies_dataset.xml").getPath());

    @Override
    public List<Movie> getMovie() {
        return parse();
    }

    @Override
    public List<Movie> getMovie(String title) {
        return Collections.singletonList(new Movie());
    }

    @Override
    public List<Movie> getMovie(String title, int beforeYear) {
        return Collections.singletonList(new Movie());
    }


    private List<Movie> parse() {
        return parse(null, - 1);
    }


    private List<Movie> parse(String title, int beforeYear) {

        try (FileInputStream fis = new FileInputStream(dataSet)) {
            XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
            XMLStreamReader reader = xmlInFact.createXMLStreamReader(fis);

            List<Movie> movies = new ArrayList<>();

            while (reader.hasNext()) {
                reader.next();

                Movie.parseFromXMLFiltered(reader, title, beforeYear);
                movies.add(Movie.parseFromXML(reader));
            }

            return movies;
        } catch (IOException | XMLStreamException e) {
            log.info("Something went wrong parsing the XML: ", e);
            return Collections.emptyList();
        }
    }
}
