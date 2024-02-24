package com.example.java.mongo.repositories;

import com.example.java.mongo.models.MessagesModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends MongoRepository<MessagesModel, String> {

  List<MessagesModel> findByChatId(String chatId);

  List<MessagesModel> findByAuthorIdAndChatId(String authorId, String chatId);

  void deleteByChatId(String chatId);
}
