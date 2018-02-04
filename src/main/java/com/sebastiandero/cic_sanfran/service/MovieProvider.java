package com.sebastiandero.cic_sanfran.service;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MovieProvider {
    List<Movie> getMovies();
    List<Movie> getMovies(String title);
    List<Movie> getMovies(String title, int beforeYear);
}
