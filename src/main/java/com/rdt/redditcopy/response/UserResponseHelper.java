package com.rdt.redditcopy.response;

import com.rdt.redditcopy.type.PostType;
import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class UserResponseHelper {
    private Integer id;
    private PostType postType;
    private Integer subId;
    private String subName;
    private Link subLink;
    private String content;
    private Link selfLink;
    private Integer postOnId;
    private PostType postOnType;
    private String postOnContent;
}
