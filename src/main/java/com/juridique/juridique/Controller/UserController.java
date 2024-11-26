package com.juridique.juridique.Controller;


import com.juridique.juridique.DTOs.Request.UserDtoRequest;
import com.juridique.juridique.DTOs.Response.UserDtoResponse;
import com.juridique.juridique.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public UserDtoResponse getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public List<UserDtoResponse> getAll() {
        return userService.findAll();
    }

    @PostMapping
    public UserDtoResponse create(@RequestBody @Valid UserDtoRequest user) {
        return userService.create(user);
    }

    @PutMapping("{id}")
    public UserDtoResponse update(@RequestBody @Valid UserDtoRequest user, @PathVariable Long id) {
        return userService.update(user, id);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
