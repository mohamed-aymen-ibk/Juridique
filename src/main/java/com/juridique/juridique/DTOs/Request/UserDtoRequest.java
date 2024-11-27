// UserDtoRequest.java
package com.juridique.juridique.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoRequest {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 70, message = "Name must be between 4 and 70 characters")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotNull(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private String role;

    private boolean isActive;
}