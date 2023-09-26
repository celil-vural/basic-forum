package com.basicforum.model.converter;

import com.basicforum.model.dto.PostDtoWithUsernameAndDate;
import com.basicforum.model.dto.UserDto;
import com.basicforum.model.dto.UserDtoWithPosts;
import com.basicforum.model.entity.Post;
import com.basicforum.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {
    private final PostDtoConverter postDtoConverter;
    public UserDto convertToDto(User user){
        return new UserDto(user.getFirstName(),user.getLastName(),user.getUsername(),user.getEmail());
    }
    public User convertToUser(UserDto userDto){
        User user=new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setUsername(userDto.username());
        return user;
    }
    public Optional<UserDtoWithPosts> convertToDtoWithPosts(User user){
        Set<Post> posts=user.getPosts();
        Set<PostDtoWithUsernameAndDate> postDtos= posts.stream()
                .map(postDtoConverter::convertToPostDtoWithUsernameAndDate).collect(Collectors.toSet());
        return Optional.of(new UserDtoWithPosts(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), postDtos));
    }
}
