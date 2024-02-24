package com.example.java.mongo.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "chats")
public class ChatsModel {
  @Id
  private String id;
  @CreatedDate
  private LocalDateTime createdAt;
  @DBRef
  private List<UsersModel> users;
  @DBRef
  private List<MessagesModel> messages;
}
