package com.diop.dynamodb.service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.diop.dynamodb.domain.User;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.CustomerErrorConstants;
import com.diop.dynamodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User readUser(String userId) {

        Optional.ofNullable( userRepository.readUser(userId)).orElseThrow(() ->new ResourceNotFoundException(CustomerErrorConstants.ERR_RESOURCE_NOT_FOUND));
     // Optional.ofNullable( userRepository.readUser(userId)).orElseThrow(() ->new BusinessException(HttpStatus.NOT_FOUND,CustomerErrorConstants.ERR_MOVIE_NOT_FOUND,userId));

        return
                userRepository.readUser(userId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }




}
