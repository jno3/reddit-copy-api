package com.rdt.redditcopy.response;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
public class AllSubsResponse {
    private List<AllSubsResponseHelper> allSubsInfo;
    private Link selfLink;
}
