package com.sebastiandero.cic_sanfran.persistence;

import lombok.Builder;
import lombok.Data;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

@Data
@Builder
public class Movie {

    private String title;
    private int    releaseYear;
    private String locations;

    public static Movie parseFromXML(XMLStreamReader reader) {
        MovieBuilder builder = Movie.builder();
        for(int i = 0; i < reader.getAttributeCount(); i++) {
            QName attributeQName = reader.getAttributeName(i);
            String localName = attributeQName.getLocalPart();

            switch (localName) {
                case "title":
                    builder.title(reader.getAttributeValue(i));
                    break;
                case "release_year":
                    builder.releaseYear(getNumericFromString(reader.getAttributeValue(i)));
                    break;
                case "locations":
                    builder.title(reader.getAttributeValue(i));
                    break;
                default:
                    break;
            }
        }
    }

    private static int getNumericFromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ignored) {
            return - 1;
        }
    }
}
