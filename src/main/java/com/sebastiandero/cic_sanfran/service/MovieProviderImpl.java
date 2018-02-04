package com.sebastiandero.cic_sanfran.service;

import com.sebastiandero.cic_sanfran.persistence.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    public List<Movie> getMovies(String title, String locations) {
        return parse(title, locations);
    }

    @Override
    public void createMovie(Movie movie) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(dataSet);
        Node rows = doc.getFirstChild().getFirstChild();

        Element newRowElement = doc.createElement("row");

        Element titleElement = doc.createElement("title");
        titleElement.setTextContent(movie.getTitle());

        Element locationsElement = doc.createElement("locations");
        locationsElement.setTextContent(movie.getLocations());

        newRowElement.appendChild(titleElement);
        newRowElement.appendChild(locationsElement);

        rows.appendChild(newRowElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(dataSet);
        transformer.transform(source, result);
    }


    private List<Movie> parse() {
        return parse(null, null);
    }

    private List<Movie> parse(String title, String locations) {
        try {
            MovieSAXHandler handler = new MovieSAXHandler(title, locations);
            SAXParser parser = factory.newSAXParser();
            parser.parse(dataSet, handler);

            return handler.getMovies();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            log.info("Something went wrong parsing the dataSet: ", e);
            return Collections.emptyList();
        }
    }
}
