package com.example.HPPO_Backend.controllers.ecom;

import com.example.HPPO_Backend.entity.User;
import com.example.HPPO_Backend.entity.dto.UserUpdateRequest;
import com.example.HPPO_Backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateMe(@AuthenticationPrincipal User user,
                                         @RequestBody UserUpdateRequest request) {
        User updated = userService.updateSelf(user.getId(), request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size) {
        PageRequest pageRequest = (page != null && size != null)
                ? PageRequest.of(page, size)
                : PageRequest.of(0, Integer.MAX_VALUE);
        return ResponseEntity.ok(userService.getUsers(pageRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
