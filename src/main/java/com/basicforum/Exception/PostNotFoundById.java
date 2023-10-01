package com.basicforum.Exception;

import lombok.Getter;

@Getter
public class PostNotFoundById extends RuntimeException {
    private final String message;
    public PostNotFoundById(int id){
        super("Post with id " + id + " not found");
        this.message = "Post with id " + id + " not found";
    }
    public PostNotFoundById(String message){
        super(message);
        this.message = message;
    }
}
