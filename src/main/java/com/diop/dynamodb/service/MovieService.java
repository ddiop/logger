package com.diop.dynamodb.service;

import com.diop.dynamodb.domain.Movie;
import com.diop.dynamodb.domain.User;

import java.util.List;

public interface MovieService {
    Movie putMovie(Movie movie);
    Movie getMovieByTitle(String title);
    List<Movie> printMoviesByRatingAndDateRange(String rating, int startYear, int endYear);
}
