package com.diop.dynamodb.service;

import com.diop.dynamodb.domain.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User readUser(String userId);

    User updateUser(User user);

    void deleteUser(String userId);

}
