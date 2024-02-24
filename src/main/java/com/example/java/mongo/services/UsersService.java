package com.example.java.mongo.services;

import com.example.java.mongo.controllers.UsersController;
import com.example.java.mongo.models.Roles;
import com.example.java.mongo.models.UsersModel;
import com.example.java.mongo.repositories.ChatsRepository;
import com.example.java.mongo.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

  private final ChatsRepository chatsRepository;
  private final UsersRepository usersRepository;

  public Optional<UsersModel> getUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  public List<UsersModel> findAll() {
    return usersRepository.findAll();
  }

  public UsersModel createUser(UsersController.CreateUser createUser) {
    UsersModel newUser = new UsersModel();
    newUser.setUsername(createUser.username());
    newUser.setEmail(createUser.email());
    newUser.setPassword(createUser.password());
    newUser.setChats(new ArrayList<>());
    newUser.setRoles(List.of(Roles.USER));

    usersRepository.findByUsername(createUser.username())
      .ifPresent(u -> {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
      });
    return usersRepository.save(newUser);
  }

  public void deleteByUsername(String username) {
    usersRepository.deleteByUsername(username);
  }

  public Optional<UsersModel> updateUser(String username, UsersController.UpdateUser updateUser) {
    return usersRepository.findByUsername(username)
      .map(user -> {
        Optional.ofNullable(updateUser.email()).ifPresent(user::setEmail);
        Optional.ofNullable(updateUser.password()).ifPresent(user::setPassword);
        Optional.ofNullable(updateUser.chats()).ifPresent(cts -> {

          if (user.getChats() == null)
            user.setChats(new ArrayList<>());

          if (cts.isEmpty())
            user.getChats().clear();

          for (String chatId : cts) {
            chatsRepository.findById(chatId)
              .ifPresentOrElse(
                chat -> user.getChats().add(chat),
                () -> log.warn("Chat {} not found!", chatId)
              );
          }
        });
        return usersRepository.save(user);
      });
  }

  public List<UsersModel> getUsersByChatId(String chatId) {
    return usersRepository.findByChatsId(chatId);
  }
}
