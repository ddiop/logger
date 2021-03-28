package com.diop.dynamodb.web;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.diop.dynamodb.domain.Movie;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.TechnicalException;
import com.diop.dynamodb.service.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Log4j2
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }



    @GetMapping("/movies/{title}")
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable String title) {

            Movie response = movieService.getMovieByTitle(title);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);

    }

    @GetMapping("/movies/{rating}/{startYear}/{endYear}")
    public ResponseEntity<List<Movie>> filtering(@PathVariable String rating, @PathVariable int startYear, @PathVariable int endYear) {
        try {
            List<Movie> response = movieService.printMoviesByRatingAndDateRange(rating, startYear, endYear);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } catch (AmazonServiceException e) {
            log.error(e.getMessage(), e);
            log.error("rrrrrrrrrrrrrrrr");
            log.error( e.getErrorCode());
            log.error("rrrrrrrrrrrrrrrr");
          //  throw new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR,"serveur down", e.getMessage());
            throw new BusinessException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());

        } catch (AmazonClientException e) {
            log.error("zzzzzzzzzzzzz");
            log.error(e.getMessage(), e);
            log.error("zzzzzzzzzzzzz");
            throw new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
