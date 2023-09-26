package com.basicforum.Exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserAlreadyExists extends Exception{
    private final String message;
    public UserAlreadyExists(){
        super("User already exists");
        this.message="User already exists";
    }
    public UserAlreadyExists(String message){
        super(message);
        this.message=message;
    }
}
