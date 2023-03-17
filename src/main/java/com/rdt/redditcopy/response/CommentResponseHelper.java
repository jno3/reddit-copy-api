package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class CommentResponseHelper {
    private String commentBody;
    private Link commentLink;
}
