package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class TopicResponseHelper {
    private String commentBody;
    private String creatorUsername;
    private Link creatorLink;
    private Link selfLink;
}
