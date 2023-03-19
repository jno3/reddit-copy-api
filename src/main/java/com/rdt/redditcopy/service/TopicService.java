package com.rdt.redditcopy.service;

import com.rdt.redditcopy.controller.GeneralActionsController;
import com.rdt.redditcopy.model.Post;
import com.rdt.redditcopy.model.Topic;
import com.rdt.redditcopy.repository.TopicRepository;
import com.rdt.redditcopy.response.TopicResponse;
import com.rdt.redditcopy.response.TopicResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }
    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }
    public Topic getTopicById(Integer topicId) {
        return topicRepository.findById(topicId).orElseThrow();
    }

    public TopicResponse createTopicResponse(Post post) {
        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setId(post.getId());
        topicResponse.setTitle(post.getTopic().getTitle());
        topicResponse.setBody(post.getTopic().getBody());
        topicResponse.setCreatorId(post.getUser().getId());
        topicResponse.setCreatorUsername(post.getUser().getUsername());
        topicResponse.setCreatorLink(linkTo(methodOn(GeneralActionsController.class).getUser(post.getUser().getId())).withRel("creatorLink"));
        topicResponse.setCommentList(
                post.getCommentList().stream().map(
                        postComment -> {
                            TopicResponseHelper topicResponseHelper = new TopicResponseHelper();
                            topicResponseHelper.setId(postComment.getId());
                            topicResponseHelper.setCommentBody(postComment.getComment().getBody());
                            topicResponseHelper.setCreatorId(postComment.getUser().getId());
                            topicResponseHelper.setCreatorUsername(postComment.getUser().getUsername());
                            topicResponseHelper.setSubCommentNumber(postComment.getCommentList().size());
                            topicResponseHelper.setCreatorLink(linkTo(methodOn(GeneralActionsController.class).getUser(postComment.getUser().getId())).withRel("creatorLink"));
                            topicResponseHelper.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getComment(postComment.getId())).withSelfRel());
                            //^^^^ MIGHT BE WRONG
                            return topicResponseHelper;
                        }
                ).toList()
        );
        return topicResponse;
    }

    public List<TopicResponse> createUnauthHomepageResponse(List<Post> postList) {
        List<TopicResponse> topicResponseList = postList.stream().map(
                this::createTopicResponse
        ).toList();
        return topicResponseList;
    }
}
