package com.example.java.mongo.services;

import com.example.java.mongo.models.ChatsModel;
import com.example.java.mongo.repositories.ChatsRepository;
import com.example.java.mongo.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatsService {

  private final UsersRepository usersRepository;
  private final ChatsRepository chatsRepository;

  public List<ChatsModel> getChats() {
    return chatsRepository.findAll();
  }

  public List<ChatsModel> getChatsByUsername(String username) {
    var user = usersRepository.findByUsername(username)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    return chatsRepository.findByUsersId(user.getId());
  }

  public void deleteChatsByUsername(String username) {
    usersRepository.findByUsername(username)
      .ifPresent(usersModel -> chatsRepository.deleteByUsersId(usersModel.getId()));
  }

  public void deleteChatById(String id) {
    chatsRepository.deleteById(id);
  }

  public ChatsModel createChat() {
    var chat = new ChatsModel();
    chat.setMessages(new ArrayList<>());
    chat.setUsers(new ArrayList<>());
    return chatsRepository.save(chat);
  }

  public ChatsModel addUserToChat(String chatId, String userId) {
    var chat = chatsRepository.findById(chatId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found!"));
    var user = usersRepository.findById(userId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

    if (chat.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
      chat.getUsers().add(user);
      return chatsRepository.save(chat);
    }
    return chat;
  }

  public ChatsModel removeUserFromChat(String chatId, String userId) {
    var chat = chatsRepository.findById(chatId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found!"));
    var user = usersRepository.findById(userId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

    if (chat.getUsers().stream().anyMatch(u -> u.getId().equals(user.getId()))) {
      chat.getUsers().remove(user);
      return chatsRepository.save(chat);
    }
    return chat;
  }
}
