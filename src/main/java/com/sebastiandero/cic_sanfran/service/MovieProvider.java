package com.sebastiandero.cic_sanfran.service;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Component
public interface MovieProvider {
    List<Movie> getMovies();

    List<Movie> getMovies(String title, String locations);

    void createMovie(Movie movie) throws ParserConfigurationException, IOException, SAXException, TransformerException;
}
