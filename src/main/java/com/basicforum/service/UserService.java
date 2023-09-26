package com.basicforum.service;

import com.basicforum.Exception.ContentIsEmpty;
import com.basicforum.Exception.UserNotFound;
import com.basicforum.Exception.UsernameMismatchException;
import com.basicforum.model.converter.UserDtoConverter;
import com.basicforum.model.dto.UserDto;
import com.basicforum.model.dto.UserDtoWithPosts;
import com.basicforum.model.entity.User;
import com.basicforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserDtoConverter converter;
    protected Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public UserDto getUserInformation(String username, String token) throws ContentIsEmpty,UserNotFound{
        controlUsernameAndTokenIsEmpty(username, token);
        usernameControl(username,token);
        var user = getUserByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFound();
        }
        return converter.convertToDto(user.get());
    }

    private static void controlUsernameAndTokenIsEmpty(String username, String token) throws ContentIsEmpty {
        if (token.isEmpty() || username.isEmpty()) {
            throw  new ContentIsEmpty();
        }
    }

    private void usernameControl(String username, String token) {
        String jwtUsername=jwtService.extractUsername(token);
        if(!jwtUsername.equals(username)){
            throw new UsernameMismatchException();
        }
    }
    public Optional<UserDtoWithPosts> getPostsByUsername(String username, String token) throws UserNotFound,ContentIsEmpty{
        controlUsernameAndTokenIsEmpty(token, username);
        var user=userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFound();
        }
        return converter.convertToDtoWithPosts(user.get());
    }
}
