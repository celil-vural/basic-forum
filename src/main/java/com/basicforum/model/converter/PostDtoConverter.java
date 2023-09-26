package com.basicforum.model.converter;

import com.basicforum.model.dto.PostDto;
import com.basicforum.model.dto.PostDtoWithUsername;
import com.basicforum.model.dto.PostDtoWithUsernameAndDate;
import com.basicforum.model.entity.Post;
import com.basicforum.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostDtoConverter {
    public PostDto convertToPostDto(Post post){
        return new PostDto(post.getTitle(),post.getContent());
    }
    public PostDtoWithUsername convertToPostDtoWithUsername(Post post){
        return new PostDtoWithUsername(post.getTitle(),post.getContent(),post.getUser().getUsername());
    }
    public PostDtoWithUsernameAndDate convertToPostDtoWithUsernameAndDate(Post post){
        return new PostDtoWithUsernameAndDate(post.getTitle(),
                post.getContent(),post.getUser().getUsername(),post.getCreatedDate());
    }
    public Post convertToPost(PostDtoWithUsername postDto,User user){
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        post.setCreatedDate(new Date());
        post.setUser(user);
        return post;
    }
    public Post convertToPost(PostDtoWithUsernameAndDate postDto, User user){
        Post post = new Post();
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        post.setCreatedDate(postDto.date());
        post.setUser(user);
        return post;
    }
}
