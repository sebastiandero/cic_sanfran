package com.sebastiandero.cic_sanfran;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import com.sebastiandero.cic_sanfran.service.MovieProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieRestController {

    @Autowired
    private MovieProvider movieProvider;

    @GetMapping
    List<Movie> getAllMovies(@RequestParam(name = "title", required = false) String title,
                             @RequestParam(name = "locations", required = false) String locations) {

        log.info("Request received: with these parameters: title:" + title + " locations:" + locations);

        return movieProvider.getMovies(title, locations);
    }

    @PostMapping
    void addMovie(@RequestParam(name = "title") String title, @RequestParam(name = "locations") String locations) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidRequestParamException();
        }
        if (locations == null || locations.trim().isEmpty()) {
            throw new InvalidRequestParamException();
        }

        try {
            movieProvider.createMovie(title, locations);
        } catch (ParserConfigurationException | IOException | TransformerException | SAXException e) {
            log.info("Something went wrong: ", e);
        }
    }
}
