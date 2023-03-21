package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class SubResponse {
    private Integer id;
    private String name;
    private String description;
    private List<Integer> subModList;
    private List<SubResponseHelper> topicList;
    private Link selfLink;
}
