package com.basicforum.model.dto;

import java.util.Date;

public record PostDtoWithUsernameAndDate(String title, String content, String username, Date date) {
}
