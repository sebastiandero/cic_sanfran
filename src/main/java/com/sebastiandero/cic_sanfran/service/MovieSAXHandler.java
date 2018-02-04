package com.sebastiandero.cic_sanfran.service;


import com.sebastiandero.cic_sanfran.persistence.Movie;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieSAXHandler extends DefaultHandler {

    private List<Movie> movies;
    private Movie.MovieBuilder builder;
    private String filterTitle;
    private int filterBeforeYear;

    private String currentValue;
    private boolean isFilteredOut = false;

    public MovieSAXHandler(String filterTitle, int filterBeforeYear) {
        this.filterTitle = filterTitle;
        this.filterBeforeYear = filterBeforeYear;
        movies = new ArrayList<>();
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) {

        if (elementName.equalsIgnoreCase("row")) {
            builder = Movie.builder();
            isFilteredOut = false;
        }
    }

    private int getNumericFromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    @Override
    public void endElement(String s, String s1, String element) {
        // if end of book element add to list
        if (element.equals("row")) {
            if (!isFilteredOut) {
                movies.add(builder.build());
            }
        }
        if (element.equalsIgnoreCase("title")) {
            if (filterTitle != null && !currentValue.contains(filterTitle)) {
                isFilteredOut = true;
            }
            builder.title(currentValue);
        }
        if (element.equalsIgnoreCase("locations")) {
            builder.locations(currentValue);
        }
        if (element.equalsIgnoreCase("release_year")) {

            int parsedYear = getNumericFromString(currentValue);

            if (filterBeforeYear != -1 && parsedYear < filterBeforeYear) {
                isFilteredOut = true;
            }

            builder.releaseYear(parsedYear);
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
