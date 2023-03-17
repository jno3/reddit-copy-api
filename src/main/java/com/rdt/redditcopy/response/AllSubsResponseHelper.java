package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class AllSubsResponseHelper {
    private String subName;
    private Link subLink;
}
