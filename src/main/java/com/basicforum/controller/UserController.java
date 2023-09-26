package com.basicforum.controller;

import com.basicforum.Exception.ContentIsEmpty;
import com.basicforum.Exception.UserNotFound;
import com.basicforum.model.dto.UserDto;
import com.basicforum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserInformation(@PathVariable String username, @RequestHeader (name="Authorization") String token){
        token=token.substring(7);
        try {
            var userDto=userService.getUserInformation(username,token);
            return ResponseEntity.ok(userDto);
        } catch (ContentIsEmpty e) {
            return ResponseEntity.badRequest().build();
        } catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }
}
