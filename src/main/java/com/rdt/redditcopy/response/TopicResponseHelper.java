package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class TopicResponseHelper {
    private Integer id;
    private String commentBody;
    private Integer creatorId;
    private String creatorUsername;
    private Integer subId;
    private String subName;
    private Integer subCommentNumber;
    private Link creatorLink;
    private Link selfLink;
}
