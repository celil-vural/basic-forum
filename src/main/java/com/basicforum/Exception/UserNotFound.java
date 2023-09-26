package com.basicforum.Exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserNotFound extends Exception{
    private final String message;
    public UserNotFound(String message){
        super(message);
        this.message=message;
    }
    public UserNotFound(){
        super("User not found");
        this.message="User not found";
    }
}
