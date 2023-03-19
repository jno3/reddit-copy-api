package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class SubResponseHelper {
    private Integer id;
    private String topicTitle;
    private String creatorUsername;
    private Link creatorLink;
    private Link topicLink;
}
