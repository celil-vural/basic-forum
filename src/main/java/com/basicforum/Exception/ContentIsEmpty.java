package com.basicforum.Exception;

import lombok.Getter;

@Getter
public class ContentIsEmpty extends RuntimeException {
    private final String message;
    public ContentIsEmpty(){
        super("Content is empty");
        this.message="Content is empty";
    }
    public ContentIsEmpty(String message){
        super(message);
        this.message=message;
    }
}
