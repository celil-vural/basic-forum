package com.basicforum.controller;

import com.basicforum.Exception.ContentIsEmpty;
import com.basicforum.Exception.PostNotFoundById;
import com.basicforum.Exception.UserNotFound;
import com.basicforum.model.dto.PostDtoWithUsername;
import com.basicforum.model.dto.PostDtoWithUsernameAndDate;
import com.basicforum.model.dto.UserDtoWithPosts;
import com.basicforum.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;
    @PostMapping("/createPost")
    public ResponseEntity<PostDtoWithUsernameAndDate> createPost(@RequestBody PostDtoWithUsername postDto, @RequestHeader (name="Authorization") String token){
        PostDtoWithUsernameAndDate response= null;
        token=token.substring(7);
        System.out.println(token);
        try {
            response = service.createPost(postDto,token);
        } catch (UserNotFound e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ContentIsEmpty e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDtoWithUsernameAndDate> getPostById(@PathVariable int id){
        try{
            var post = service.getPostById(id);
            return ResponseEntity.ok(post);
        }
        catch (PostNotFoundById e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDtoWithPosts> getPostsByUsername(@PathVariable String username,@RequestHeader (name="Authorization") String token){
        token=token.substring(7);
        try {
            var userDtoWithPosts=service.getPostsByUsername(username,token);
            return ResponseEntity.ok(userDtoWithPosts);
        } catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (ContentIsEmpty e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
