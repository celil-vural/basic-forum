package com.basicforum.model.dto;

import java.util.Set;

public record UserDtoWithPosts (String firstName, String lastName, String username, String email, Set<PostDtoWithUsernameAndDate> posts) {
}
