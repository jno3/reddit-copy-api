package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class AllSubsResponseHelper {
    private Integer id;
    private String subName;
    private String subDescription;
    private Link subLink;
}
