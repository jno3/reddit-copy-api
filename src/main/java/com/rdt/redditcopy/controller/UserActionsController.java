package com.rdt.redditcopy.controller;

import com.rdt.redditcopy.model.Post;
import com.rdt.redditcopy.model.Sub;
import com.rdt.redditcopy.model.User;
import com.rdt.redditcopy.request.CreateCommentRequest;
import com.rdt.redditcopy.request.CreateTopicRequest;
import com.rdt.redditcopy.request.CreateSubRequest;
import com.rdt.redditcopy.response.CommentResponse;
import com.rdt.redditcopy.response.SubResponse;
import com.rdt.redditcopy.response.TopicResponse;
import com.rdt.redditcopy.response.UserResponse;
import com.rdt.redditcopy.service.CommentService;
import com.rdt.redditcopy.service.SubService;
import com.rdt.redditcopy.service.TopicService;
import com.rdt.redditcopy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserActionsController {
    private final UserService userService;
    private final SubService subService;
    private final TopicService topicService;
    private final CommentService commentService;

    @PostMapping("/sub/")
    public ResponseEntity<SubResponse> createSub(
            @RequestBody CreateSubRequest createSubRequest,
            @RequestHeader("Authorization") String bearer
    ) {

        Sub sub = userService.createSub(bearer, createSubRequest);
        SubResponse subResponse = subService.createSubResponse(sub);
        return ResponseEntity.ok(subResponse);
    }
    @PostMapping("/sub/{subId}/")
    public ResponseEntity<TopicResponse> createTopic(
            @RequestBody CreateTopicRequest CreateTopicRequest,
            @RequestHeader("Authorization") String bearer,
            @PathVariable Integer subId
    ) {
        Post post = userService.createTopic(bearer, CreateTopicRequest, subId);
        TopicResponse topicResponse = topicService.createTopicResponse(post);
        return ResponseEntity.ok(topicResponse);
    }

    @PostMapping("/post/{postId}/")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @RequestHeader("Authorization") String bearer,
            @PathVariable Integer postId
    ) {
        Post post = userService.createComment(bearer, createCommentRequest, postId);
        CommentResponse commentResponse = commentService.createCommentResponse(post);
        return ResponseEntity.ok(commentResponse);
    }

    @PatchMapping("/sub/follow-unfollow/{subId}/")
    public ResponseEntity<UserResponse> followSub(
            @RequestHeader("Authorization") String bearer,
            @PathVariable Integer subId
    ) {
        return ResponseEntity.ok(userService.followSlashUnfollowSub(bearer, subId));
    }

    @PatchMapping("/post/upvote/{postId}")
    public ResponseEntity<UserResponse> upvotePost(
            @RequestHeader("Authorization") String bearer,
            @PathVariable Integer postId
    ) {
        UserResponse userResponse = userService.createUserResponse(userService.upvotePost(bearer, postId));
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/sub/downvote/{postId}")
    public ResponseEntity<UserResponse> downVotePost(
            @RequestHeader("Authorization") String bearer,
            @PathVariable Integer postId
    ) {
        UserResponse userResponse = userService.createUserResponse(userService.downvotePost(bearer, postId));
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/post/")
    public ResponseEntity<List<List<Post>>> getPostsFromFollowedSubs(
            @RequestHeader("Authorization") String bearer
    ) {
        return ResponseEntity.ok(userService.getPostsFromFollowedSubs(bearer));
    }
}
