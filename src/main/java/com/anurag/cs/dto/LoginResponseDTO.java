package com.anurag.cs.dto;

public class LoginResponseDTO {
      private String token; private Long id; private String name; private String role;
      public LoginResponseDTO(String token, Long id, String name, String role) { this.token = token; this.id = id; this.name = name; this.role = role; }
      public String getToken() { return token; }
      public Long getId() { return id; }
      public String getName() { return name; }
      public String getRole() { return role; }
}
