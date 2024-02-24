package com.example.java.mongo.repositories;

import com.example.java.mongo.models.ChatsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatsRepository extends MongoRepository<ChatsModel, String> {

    void deleteByUsersId(String userId);

    List<ChatsModel> findByUsersId(String userId);
}
