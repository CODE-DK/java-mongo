package com.example.java.mongo.controllers;

import com.example.java.mongo.models.MessagesModel;
import com.example.java.mongo.services.MessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessagesController {

  private final MessagesService messagesService;

  @GetMapping(path = "/{chatId}", produces = "application/json")
  public ResponseEntity<List<MessagesModel>> getMessagesByChatId(@PathVariable String chatId) {
    var messages = messagesService.getMessagesByChatId(chatId);
    return ResponseEntity.ok(messages);
  }

  @GetMapping(path = "/{chatId}/{username}", produces = "application/json")
  public ResponseEntity<List<MessagesModel>> getMessagesByAuthorIdAndChatId(
    @PathVariable String chatId,
    @PathVariable String username
  ) {
    var messages = messagesService.getMessagesByAuthorIdAndChatId(username, chatId);
    return ResponseEntity.ok(messages);
  }

  @DeleteMapping(path = "/{chatId}")
  public ResponseEntity<Void> deleteMessagesByChatId(@PathVariable String chatId) {
    messagesService.deleteMessagesByChatId(chatId);
    return ResponseEntity.noContent().build();
  }
}
