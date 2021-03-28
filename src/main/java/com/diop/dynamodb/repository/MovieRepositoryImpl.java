package com.diop.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.diop.dynamodb.domain.Movie;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.CustomerErrorConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Log4j2
public class MovieRepositoryImpl implements MovieRepository {

    private DynamoDBMapper mapper;

    @Autowired
    public MovieRepositoryImpl(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public Movie putMovie(Movie movie) {
        log.info("*** Adding movie " + movie.getTitle());
        mapper.save(movie);
        log.info("*** Done");
        return movie;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        log.info("*** Printing movie " + title);
        Optional.ofNullable(title).orElseThrow(() ->new BusinessException(CustomerErrorConstants.ERR_MOVIE_NOT_FOUND));

        Movie movie = mapper.load(Movie.class, title);
        log.info(movie);
        log.info("*** Done");
        return movie;
    }

    @Override
    public List<Movie> printMoviesByRatingAndDateRange(String rating, int startYear, int endYear) {

        List<Movie> movieList = mapper.query(Movie.class, queryWithRatingEqualTo(rating, startYear, endYear));
        return movieList;
    }


    private static DynamoDBQueryExpression<Movie> queryWithRatingEqualTo(String rating, int startYear, int endYear) {

        // No strong consistency when querying a GSI
        HashMap<String, AttributeValue> values = new HashMap<>();
        values.put(":v_rating", new AttributeValue(rating));
        values.put(":v_start", new AttributeValue(String.valueOf(startYear)));
        values.put(":v_end", new AttributeValue(String.valueOf(endYear)));
        DynamoDBQueryExpression<Movie> queryExpression = new
                DynamoDBQueryExpression<Movie>()


                .withKeyConditionExpression("rating = :v_rating and releaseDate between :v_start and :v_end")
                .withExpressionAttributeValues(values);
        ;
        return queryExpression;
    }
}

