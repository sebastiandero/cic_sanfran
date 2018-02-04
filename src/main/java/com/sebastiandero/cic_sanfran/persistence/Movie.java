package com.sebastiandero.cic_sanfran.persistence;

import lombok.Builder;
import lombok.Data;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.util.Optional;

@Data
@Builder
public class Movie {

    private String title;
    private int    releaseYear;
    private String locations;

    public static Optional<Movie> parseFromXML(XMLStreamReader reader) {
        return parseFromXMLFiltered(reader, null, - 1);
    }

    private static int getNumericFromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ignored) {
            return - 1;
        }
    }

    public static Optional<Movie> parseFromXMLFiltered(XMLStreamReader reader, String filterTitle, int beforeYear) {
        MovieBuilder builder = Movie.builder();

        for(int i = 0; i < reader.getAttributeCount(); i++) {
            QName attributeQName = reader.getAttributeName(i);
            String localName = attributeQName.getLocalPart();

            switch (localName) {
                case "title":

                    String titleFromXML = reader.getAttributeValue(i);

                    if (filterTitle != null) {
                        if (!titleFromXML.contains(filterTitle)) {
                            return Optional.empty();
                        }
                    }

                    builder.title(titleFromXML);

                    break;
                case "release_year":
                    int releaseYearFromXML = getNumericFromString(reader.getAttributeValue(i));

                    if (beforeYear > 0) {
                        if (releaseYearFromXML < beforeYear) {
                            return Optional.empty();
                        }
                    }

                    builder.releaseYear(releaseYearFromXML);

                    break;
                case "locations":
                    builder.title(reader.getAttributeValue(i));
                    break;
                default:
                    break;
            }
        }

        return Optional.ofNullable(builder.build());
    }
}
