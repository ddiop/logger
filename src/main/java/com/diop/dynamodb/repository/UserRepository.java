package com.diop.dynamodb.repository;

import com.diop.dynamodb.domain.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);
    User readUser(String userId);
    User updateUser(User user);
    void deleteUser(String userId);

}
