package com.basicforum.service;

import com.basicforum.Exception.ContentIsEmpty;
import com.basicforum.Exception.PostNotFoundById;
import com.basicforum.Exception.UserNotFound;
import com.basicforum.Exception.UsernameMismatchException;
import com.basicforum.model.converter.PostDtoConverter;
import com.basicforum.model.dto.PostDtoWithUsername;
import com.basicforum.model.dto.PostDtoWithUsernameAndDate;
import com.basicforum.model.dto.UserDtoWithPosts;
import com.basicforum.model.entity.Post;
import com.basicforum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final PostDtoConverter converter;
    public PostDtoWithUsernameAndDate createPost(PostDtoWithUsername postDto, String token)
            throws UsernameMismatchException,UserNotFound,ContentIsEmpty{
        controlUsernameAndTokenIsEmpty(postDto.username(), token);
        usernameControl(postDto.username(), token);
        var user=userService.getUserByUsername(postDto.username());
        if(user.isEmpty()){
            throw new UserNotFound();
        }
        if(postDto.title().isEmpty() || postDto.content().isEmpty()){
            throw new ContentIsEmpty();
        }
        Post post=converter.convertToPost(postDto,user.get());
        Post dbPost=postRepository.save(post);
        return converter.convertToPostDtoWithUsernameAndDate(dbPost);
    }

    private void usernameControl(String username, String token) {
        String jwtUsername=jwtService.extractUsername(token);
        if(!jwtUsername.equals(username)){
            throw new UsernameMismatchException();
        }
    }
    private static void controlUsernameAndTokenIsEmpty(String username, String token) throws ContentIsEmpty {
        if (token.isEmpty() || username.isEmpty()) {
            throw  new ContentIsEmpty();
        }
    }

    public PostDtoWithUsernameAndDate getPostById(int id) throws PostNotFoundById {
        var post=postRepository.findById(id);
        if(post.isEmpty()){
            throw new PostNotFoundById(id);
        }
        return converter.convertToPostDtoWithUsernameAndDate(post.get());
    }

    public UserDtoWithPosts getPostsByUsername(String username, String token) throws UserNotFound,ContentIsEmpty{
        controlUsernameAndTokenIsEmpty(token, username);
        Optional<UserDtoWithPosts> userDtoWithPosts=userService.getPostsByUsername(username,token);
        UserDtoWithPosts response;
        if(userDtoWithPosts.isEmpty()){
            throw new UserNotFound();
        }
        if(userDtoWithPosts.get().posts().isEmpty()){
            UserDtoWithPosts details=userDtoWithPosts.get();
            response=new UserDtoWithPosts(details.firstName(),details.lastName(),details.username(),details.email(),new HashSet<>());
        }else{
            response=userDtoWithPosts.get();
        }
        return response;
    }
}
