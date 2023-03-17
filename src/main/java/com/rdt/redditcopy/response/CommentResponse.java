package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class CommentResponse {
    private Integer id;
    private String body;
    private List<CommentResponseHelper> commentList;
    private Link selfLink;
}
