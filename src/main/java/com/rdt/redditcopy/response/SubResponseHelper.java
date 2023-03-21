package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class SubResponseHelper {
    private Integer id;
    private String topicTitle;
    private Integer creatorId;
    private String creatorUsername;
    private Integer subId;
    private String subName;
    private Link creatorLink;
    private Link topicLink;
}
