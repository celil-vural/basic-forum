package com.basicforum.Exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UsernameMismatchException extends RuntimeException {
    private final String message;
    public UsernameMismatchException(String message) {
        super(message);
        this.message=message;
    }
    public UsernameMismatchException(){
        super("Username Mismatch");
        this.message="Username Mismatch";
    }
}