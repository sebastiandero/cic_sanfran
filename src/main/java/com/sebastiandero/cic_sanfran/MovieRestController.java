package com.sebastiandero.cic_sanfran;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import com.sebastiandero.cic_sanfran.service.MovieProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieRestController {

    @Autowired
    private MovieProvider movieProvider;

    @GetMapping
    List<Movie> getAllMovies(@RequestParam(name = "title", defaultValue = "") String title,
                             @RequestParam(name = "beforeYear", defaultValue = "") String beforeYearString) {

        log.info("Request received: with these parameters: title:" + title + " beforeYear:" + beforeYearString);

        int beforeYear = parseIfNumeric(beforeYearString).orElse(-1);

        return movieProvider.getMovies(title, beforeYear);
    }

    private Optional<Integer> parseIfNumeric(String beforeYear) {
        Integer parsed = null;

        try {
            parsed = Integer.parseInt(beforeYear);
        } catch (NumberFormatException ignored) {
        }

        return Optional.ofNullable(parsed);
    }
}
