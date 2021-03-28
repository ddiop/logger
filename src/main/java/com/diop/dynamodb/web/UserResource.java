package com.diop.dynamodb.web;

import com.diop.dynamodb.domain.User;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.CustomerErrorConstants;
import com.diop.dynamodb.log.errors.exception.TechnicalException;
import com.diop.dynamodb.service.UserService;
import com.diop.dynamodb.support.HttpHeadersCreator;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            User response = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);

        } catch (AmazonClientException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/users/{userId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity readUser(@PathVariable String userId) {



            User response = userService.readUser(userId);
            return ResponseEntity.ok().headers(HttpHeadersCreator.createEntityUpdateAlert("user", response.getUserId())).body(response);


    }

    @RequestMapping(value = "/users", produces = {"application/json"}, method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            User response = userService.updateUser(user);
            return ResponseEntity.ok().headers(HttpHeadersCreator.createEntityUpdateAlert("user", response.getUserId())).body(response);


        } catch (AmazonServiceException e) {
            log.error("aaaaaaaaaaaaaaaaaaaaaaaaaa");
            log.error(e.getMessage(), e);
            log.error("aaaaaaaaaaaaaaaaaaaaaaaaaa");
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/users/{userId}", produces = {"application/json"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
