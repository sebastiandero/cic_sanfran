package com.sebastiandero.cic_sanfran.service;


import com.sebastiandero.cic_sanfran.persistence.Movie;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MovieSAXHandler extends DefaultHandler {

    private List<Movie> movies;
    private Movie.MovieBuilder builder;
    private String filterTitle;
    private String filterLocations;

    private String currentValue;
    private boolean isFilteredOut = false;

    public MovieSAXHandler(String filterTitle, String locations) {
        this.filterTitle = filterTitle;
        this.filterLocations = locations;
        movies = new ArrayList<>();
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) {

        if (elementName.equalsIgnoreCase("row")) {
            builder = Movie.builder();
            isFilteredOut = false;
        }
    }

    @Override
    public void endElement(String s, String s1, String element) {
        // if end of book element add to list
        if (element.equals("row")) {
            if (!isFilteredOut) {
                Movie movie = builder.build();

                if ((filterTitle == null || movie.getTitle() != null) && (filterLocations == null || movie.getLocations() != null)) {
                    movies.add(movie);
                }
            }
        }
        if (element.equalsIgnoreCase("title")) {
            if (filterTitle != null && !currentValue.contains(filterTitle)) {
                isFilteredOut = true;
            }
            builder.title(currentValue);
        }
        if (element.equalsIgnoreCase("locations")) {
            if (filterLocations != null && !currentValue.contains(filterLocations)) {
                isFilteredOut = true;
            }
            builder.locations(currentValue);
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) {
        currentValue = new String(ac, i, j);
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
