package com.rdt.redditcopy.controller;

import com.rdt.redditcopy.model.Post;
import com.rdt.redditcopy.model.Sub;
import com.rdt.redditcopy.model.User;
import com.rdt.redditcopy.response.*;
import com.rdt.redditcopy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/general")
@RequiredArgsConstructor
public class GeneralActionsController {
    private final SubService subService;
    private final PostService postService;
    private final UserService userService;
    private final TopicService topicService;
    private final CommentService commentService;

    @GetMapping("/sub/")
    public ResponseEntity<AllSubsResponse> getAllSubs(){
        List<Sub> subList = subService.getAllSubs();
        AllSubsResponse allSubsResponse = subService.createAllSubsResponse(subList);
        return ResponseEntity.ok(allSubsResponse);
    }
    @GetMapping("/sub/{subId}/")
    public ResponseEntity<SubResponse> getSub(
            @PathVariable Integer subId
    ){
        Sub sub = subService.getSubById(subId);
        SubResponse subResponse = subService.createSubResponse(sub);
        return ResponseEntity.ok(subResponse);
    }
    @GetMapping("/topic/{topicId}/")
    public ResponseEntity<TopicResponse> getTopic(
            @PathVariable Integer topicId
    ){
        Post post = postService.getPostById(topicId);
        TopicResponse topicResponse = topicService.createTopicResponse(post);
        return ResponseEntity.ok(topicResponse);
    }
    @GetMapping("/comment/{commentId}/")
    public ResponseEntity<CommentResponse> getComment(
            @PathVariable Integer commentId
    ){
        Post post = postService.getPostById(commentId);
        CommentResponse commentResponse = commentService.createCommentResponse(post);
        return ResponseEntity.ok(commentResponse);
    }
    @GetMapping("/user/{userId}/")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable Integer userId
    ){
        User user = userService.getUserById(userId);
        UserResponse userResponse = userService.createUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
