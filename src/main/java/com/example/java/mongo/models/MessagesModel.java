package com.example.java.mongo.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class MessagesModel {
  @Id
  private String id;
  private String message;
  @CreatedDate
  private LocalDateTime createdAt;
  @DBRef
  private ChatsModel chat;
  @DBRef
  private UsersModel author;
}
