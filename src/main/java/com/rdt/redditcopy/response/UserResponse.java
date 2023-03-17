package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class UserResponse {
    private Integer id;
    private String username;
    private List<UserResponseHelper> postList;
    private Link selfLink;
}
