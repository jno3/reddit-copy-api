package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class TopicResponse {
    private Integer id;
    private String title;
    private String body;
    private Integer creatorId;
    private String creatorUsername;
    private Integer subId;
    private String subName;
    private Link creatorLink;
    private List<TopicResponseHelper> commentList;
    private Link selfLink;
}
