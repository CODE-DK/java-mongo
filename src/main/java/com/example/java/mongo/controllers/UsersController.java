package com.example.java.mongo.controllers;

import com.example.java.mongo.models.UsersModel;
import com.example.java.mongo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService usersService;

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<UsersModel>> getUsers() {
    var users = usersService.findAll();
    return ResponseEntity.ok(users);
  }

  @GetMapping(path = "/{chatId}", produces = "application/json")
  public ResponseEntity<List<UsersModel>> getUsersByChatId(@PathVariable String chatId) {
    var users = usersService.getUsersByChatId(chatId);
    return ResponseEntity.ok(users);
  }

  @GetMapping(path = "/{username}", produces = "application/json")
  public ResponseEntity<Optional<UsersModel>> getUserByUsername(@PathVariable String username) {
    var user = usersService.getUserByUsername(username);
    return user.map(u -> ResponseEntity.ok(user))
      .orElse(ResponseEntity.notFound().build());
  }

  public record CreateUser(String username, String password, String email) {}

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<UsersModel> createUser(@RequestBody CreateUser createUser) {
    var newUser = usersService.createUser(createUser);
    return ResponseEntity.ok(newUser);
  }

  public record UpdateUser(String password, String email, List<String> chats) {}

  @PatchMapping(path = "/{username}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Optional<UsersModel>> updateUserByUsername(
    @PathVariable String username,
    @RequestBody UpdateUser updateUser
  ) {
    var user = usersService.updateUser(username, updateUser);
    return user.map(u -> ResponseEntity.ok(user))
      .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "/{username}")
  public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
    usersService.deleteByUsername(username);
    return ResponseEntity.noContent().build();
  }
}
