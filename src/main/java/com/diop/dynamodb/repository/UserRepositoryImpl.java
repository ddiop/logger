package com.diop.dynamodb.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.diop.dynamodb.domain.User;
import com.diop.dynamodb.log.errors.exception.BusinessException;
import com.diop.dynamodb.log.errors.exception.CustomerErrorConstants;
import com.diop.dynamodb.log.errors.exception.TechnicalException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Log4j2
public class UserRepositoryImpl implements UserRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserRepositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public User createUser(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    @Override
    public User readUser(String userId) {

        try {
           return Optional.ofNullable(dynamoDBMapper.load(User.class, userId)).orElseThrow(() -> new BusinessException(CustomerErrorConstants.ERR_MOVIE_NOT_FOUND));
           // return dynamoDBMapper.load(User.class, userId);

        } catch (ResourceNotFoundException e) {
            log.info(e.getMessage(),e.getErrorCode()) ;
          throw  new BusinessException(CustomerErrorConstants.ERR_RESOURCE_NOT_FOUND,"dev-user",e.getMessage());
        }catch (AmazonServiceException e) {
            log.error(e.getMessage());
            throw new BusinessException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage());

        } catch (AmazonClientException e) {
            log.error("_______________");
            throw new TechnicalException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }



    @Override
    public User updateUser(User user) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("userId", new ExpectedAttributeValue(new AttributeValue().withS(user.getUserId())));
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression().withExpected(expectedAttributeValueMap);
        dynamoDBMapper.save(user, saveExpression);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("userId", new ExpectedAttributeValue(new AttributeValue().withS(userId)));
        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression().withExpected(expectedAttributeValueMap);
        User user = User.builder()
                .userId(userId)
                .build();
        dynamoDBMapper.delete(user, deleteExpression);
    }

}

