package com.diop.dynamodb.repository;

import com.diop.dynamodb.domain.Movie;
import com.diop.dynamodb.domain.User;

import java.util.List;

public interface MovieRepository {
     Movie putMovie(Movie movie);
     Movie getMovieByTitle(String title);
     List<Movie> printMoviesByRatingAndDateRange( String rating, int startYear, int endYear);

}
