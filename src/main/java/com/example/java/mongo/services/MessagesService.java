package com.example.java.mongo.services;

import com.example.java.mongo.models.MessagesModel;
import com.example.java.mongo.repositories.MessagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagesService {

  private final MessagesRepository messagesRepository;

  public List<MessagesModel> getMessagesByChatId(String chatId) {
    return messagesRepository.findByChatId(chatId);
  }

  public List<MessagesModel> getMessagesByAuthorIdAndChatId(String authorId, String chatId) {
    return messagesRepository.findByAuthorIdAndChatId(authorId, chatId);
  }

  public void deleteMessagesByChatId(String chatId) {
    messagesRepository.deleteByChatId(chatId);
  }
}
