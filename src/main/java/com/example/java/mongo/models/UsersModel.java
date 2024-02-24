package com.example.java.mongo.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "users")
public class UsersModel {
  @Id
  private String id;
  private String username;
  private String password;
  private String email;
  @Field(targetType = FieldType.STRING)
  private List<Roles> roles;
  @CreatedDate
  private LocalDateTime createdAt;
  @DBRef
  private List<ChatsModel> chats;
}