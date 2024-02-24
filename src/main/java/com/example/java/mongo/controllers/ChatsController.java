package com.example.java.mongo.controllers;

import com.example.java.mongo.models.ChatsModel;
import com.example.java.mongo.services.ChatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatsController {

  private final ChatsService chatsService;

  @PostMapping(produces = "application/json")
  public ResponseEntity<ChatsModel> createChat() {
    var newChat = chatsService.createChat();
    return ResponseEntity.ok(newChat);
  }

  public record AddUserToChatRequest(String userId) {}

  @PutMapping(path = "/{chatId}/add", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ChatsModel> addUserToChat(
    @PathVariable String chatId,
    @RequestBody AddUserToChatRequest userToChat
  ) {
    var updatedChat = chatsService.addUserToChat(chatId, userToChat.userId);
    return ResponseEntity.ok(updatedChat);
  }

  public record RemoveUserFromChatRequest(String userId) {}

  @PutMapping(path = "/{chatId}/remove", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ChatsModel> removeUserFromChat(
    @PathVariable String chatId,
    @RequestBody RemoveUserFromChatRequest userToChat
  ) {
    var updatedChat = chatsService.removeUserFromChat(chatId, userToChat.userId);
    return ResponseEntity.ok(updatedChat);
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<ChatsModel>> getChatsByUsername(
    @RequestParam(required = false) String username
  ) {
    List<ChatsModel> chats;
    if (username == null) {
      chats = chatsService.getChats();
    } else {
      chats = chatsService.getChatsByUsername(username);
    }
    return ResponseEntity.ok(chats);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteChatsByUsername(@RequestParam String username) {
    chatsService.deleteChatsByUsername(username);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteChatById(@PathVariable String id) {
    chatsService.deleteChatById(id);
    return ResponseEntity.noContent().build();
  }
}
