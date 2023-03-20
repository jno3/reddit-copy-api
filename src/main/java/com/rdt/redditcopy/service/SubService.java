package com.rdt.redditcopy.service;

import com.rdt.redditcopy.controller.GeneralActionsController;
import com.rdt.redditcopy.model.Sub;
import com.rdt.redditcopy.model.User;
import com.rdt.redditcopy.repository.SubRepository;
import com.rdt.redditcopy.request.CreateSubRequest;
import com.rdt.redditcopy.response.AllSubsResponse;
import com.rdt.redditcopy.response.AllSubsResponseHelper;
import com.rdt.redditcopy.response.SubResponse;
import com.rdt.redditcopy.response.SubResponseHelper;
import com.rdt.redditcopy.type.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class SubService {
    private final SubRepository subRepository;

    public Sub createSub(User user, CreateSubRequest createSubRequest) {
        Sub sub = Sub
                .builder()
                .name(createSubRequest.getName())
                .postList(new ArrayList<>())
                .superUsersList(new ArrayList<>())
                .usersFollowingList(new ArrayList<>())
                .description(createSubRequest.getDescription())
                .build();
        sub.getSuperUsersList().add(user);
        sub.getUsersFollowingList().add(user);
        return subRepository.save(sub);
    }

    public Sub getSubById(Integer id) {
        return subRepository.findById(id).orElseThrow();
    }

    public List<Sub> getAllSubs() {
        return subRepository.findAll();
    }

    public void saveSub(Sub sub) {
        subRepository.save(sub);
    }

    public SubResponse createSubResponse(Sub sub) {
        SubResponse subResponse = new SubResponse();
        subResponse.setId(sub.getId());
        subResponse.setName(sub.getName());
        subResponse.setDescription(sub.getDescription());
        subResponse.setTopicList(sub.getPostList().stream().filter(
                                post -> post.getPostType() == PostType.TOPIC
                        ).toList()
                        .stream().map(
                                post -> {
                                    SubResponseHelper subResponseHelper = new SubResponseHelper();
                                    subResponseHelper.setId(post.getId());
                                    subResponseHelper.setTopicTitle(post.getTopic().getTitle());
                                    subResponseHelper.setCreatorId(post.getUser().getId());
                                    subResponseHelper.setCreatorUsername(post.getUser().getUsername());
                                    subResponseHelper.setCreatorLink(linkTo(methodOn(GeneralActionsController.class).getUser(post.getUser().getId())).withRel("creatorLink"));
                                    subResponseHelper.setTopicLink(linkTo(methodOn(GeneralActionsController.class).getTopic(post.getId())).withSelfRel());
                                    return subResponseHelper;
                                }
                        ).toList()
        );
        subResponse.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getSub(sub.getId())).withSelfRel());
        return subResponse;
    }

    public AllSubsResponse createAllSubsResponse(List<Sub> subList) {
        AllSubsResponse allSubsResponse = new AllSubsResponse();
        allSubsResponse.setAllSubsInfo(subList.stream().map(
                        sub -> {
                            AllSubsResponseHelper allSubsResponseHelper = new AllSubsResponseHelper();
                            allSubsResponseHelper.setId(sub.getId());
                            allSubsResponseHelper.setSubName(sub.getName());
                            allSubsResponseHelper.setSubLink(linkTo(methodOn(GeneralActionsController.class).getSub(sub.getId())).withSelfRel());
                            return allSubsResponseHelper;
                        }
                ).toList()
        );
        allSubsResponse.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getAllSubs()).withSelfRel());
        return allSubsResponse;
    }
}
