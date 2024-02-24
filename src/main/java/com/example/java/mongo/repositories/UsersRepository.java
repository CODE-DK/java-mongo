package com.example.java.mongo.repositories;

import com.example.java.mongo.models.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<UsersModel, String> {

  Optional<UsersModel> findByUsername(String username);

  void deleteByUsername(String username);

  List<UsersModel> findByChatsId(String chatId);
}
