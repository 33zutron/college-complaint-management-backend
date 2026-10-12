package com.anurag.cs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
      @NotBlank(message = "Name is required")
      @Size(max = 100, message = "Name is too long")
      private String name;
      private String role;
      @NotBlank(message = "Email is required")
      @Email(message = "Enter a valid email address")
      private String email;
      @NotBlank(message = "Password is required")
      @Size(min = 6, message = "Password must be at least 6 characters")
      private String password;

      public RegisterDTO() {
      }

      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getRole() {
            return role;
      }

      public void setRole(String role) {
            this.role = role;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }
}
