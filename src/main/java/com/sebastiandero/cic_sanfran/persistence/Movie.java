package com.sebastiandero.cic_sanfran.persistence;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {

    private String title;
    private int releaseYear;
    private String locations;
}
