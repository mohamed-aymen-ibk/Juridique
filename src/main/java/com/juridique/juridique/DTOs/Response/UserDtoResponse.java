// UserDtoResponse.java
package com.juridique.juridique.DTOs.Response;

import com.juridique.juridique.Model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private boolean isActive;
}