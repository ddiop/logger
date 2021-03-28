package com.diop.dynamodb.service;

import com.diop.dynamodb.domain.Movie;
import com.diop.dynamodb.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
   private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie putMovie(Movie movie) {
        return movieRepository.putMovie(movie);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return this.movieRepository.getMovieByTitle(title);
    }

    @Override
    public List<Movie> printMoviesByRatingAndDateRange(String rating, int startYear, int endYear) {
        return this.movieRepository.printMoviesByRatingAndDateRange(rating, startYear, endYear);
    }
}
