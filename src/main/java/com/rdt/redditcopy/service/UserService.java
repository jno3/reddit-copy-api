package com.rdt.redditcopy.service;

import com.rdt.redditcopy.controller.GeneralActionsController;
import com.rdt.redditcopy.model.*;
import com.rdt.redditcopy.repository.UserRepository;
import com.rdt.redditcopy.request.CreateCommentRequest;
import com.rdt.redditcopy.request.CreateTopicRequest;
import com.rdt.redditcopy.request.CreateSubRequest;
import com.rdt.redditcopy.response.SubResponse;
import com.rdt.redditcopy.response.UserResponse;
import com.rdt.redditcopy.response.UserResponseHelper;
import com.rdt.redditcopy.type.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SubService subService;
    private final PostService postService;
    private final CommentService commentService;
    private final TopicService topicService;

    private User getUserByJwt(String bearer) {
        String jwt = bearer.substring(7);
        return userRepository.findByUsername(jwtService.extractUsername(jwt)).orElseThrow();
    }

    public Sub createSub(String bearer, CreateSubRequest createSubRequest) {
        User user = getUserByJwt(bearer);
        Sub sub = subService.createSub(user, createSubRequest);

        sub.getUsersFollowingList().add(user);
        sub.getSuperUsersList().add(user);

        user.getFollowedSubList().add(sub);
        user.getOwnedSubList().add(sub);

        userRepository.save(user);
        return sub;
    }

    public Post createTopic(String bearer, CreateTopicRequest createTopicRequest, Integer subId) {
        Sub sub = subService.getSubById(subId);
        User user = getUserByJwt(bearer);
        return postService.createTopicPost(user, sub, createTopicRequest);
    }

    public Post createComment(String bearer, CreateCommentRequest createCommentRequest, Integer postId) {
        Post post = postService.getPostById(postId);
        Sub sub = post.getSub();
        User user = getUserByJwt(bearer);
        return postService.createCommentPost(user, sub, post, createCommentRequest);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    //    public List<List<Post>> getPostsFromFollowedSubs(String bearer) {
//        User user = getUserByJwt(bearer);
//        List<List<Post>> followedSubPostList = user.getFollowedSubList().stream()
//                .map(
//                        sub -> {
//                            return sub.getPostList().stream().filter(
//                                   post -> post.getPostType().equals(PostType.TOPIC)
//                           ).toList();
//                        }
//                ).toList();
//        return followedSubPostList;
//    }
    public List<SubResponse> getPostsFromFollowedSubs(String bearer) {
        User user = getUserByJwt(bearer);
        List<Sub> subList = user.getFollowedSubList();
        List<SubResponse> subResponseList = subList.stream().map(
                subService::createSubResponse
        ).toList();
        return subResponseList;
    }

    public UserResponse followSlashUnfollowSub(String bearer, Integer subId) {
        User user = getUserByJwt(bearer);
        Sub sub = subService.getSubById(subId);
        if (sub.getUsersFollowingList().contains(user)) {
            user.getFollowedSubList().remove(sub);
            sub.getUsersFollowingList().remove(user);
            subService.saveSub(sub);
        } else {
            user.getFollowedSubList().add(sub);
            sub.getUsersFollowingList().add(user);
            subService.saveSub(sub);
        }
        return createUserResponse(userRepository.save(user));
    }

    public User upvotePost(String bearer, Integer postId) {
        User user = getUserByJwt(bearer);
        Post post = postService.getPostById(postId);
        if (user.getUpvotedPostList().contains(post)) {
            user.getUpvotedPostList().remove(post);
            post.getUpvotedByUserList().remove(user);
            post.setUpvoteCount(post.getUpvoteCount() - 1);
            postService.savePost(post);
            return userRepository.save(user);
        } else if (user.getDownvotedPostList().contains(post)) {
            user.getDownvotedPostList().remove(post);
            user.getUpvotedPostList().add(post);

            post.getDownvotedByUserList().remove(user);
            post.getUpvotedByUserList().add(user);
            post.setUpvoteCount(post.getUpvoteCount() + 2);

            postService.savePost(post);
            return userRepository.save(user);
        } else {
            user.getUpvotedPostList().add(post);
            post.getUpvotedByUserList().add(user);
            post.setUpvoteCount(post.getUpvoteCount() + 1);
            postService.savePost(post);
            return userRepository.save(user);
        }
    }

    public User downvotePost(String bearer, Integer postId) {
        User user = getUserByJwt(bearer);
        Post post = postService.getPostById(postId);
        if (user.getDownvotedPostList().contains(post)) {
            user.getDownvotedPostList().remove(post);
            post.getDownvotedByUserList().remove(user);
            post.setUpvoteCount(post.getUpvoteCount() + 1);
            postService.savePost(post);
            return userRepository.save(user);
        } else if (user.getUpvotedPostList().contains(post)) {
            user.getUpvotedPostList().remove(post);
            user.getDownvotedPostList().add(post);

            post.getDownvotedByUserList().add(user);
            post.getUpvotedByUserList().remove(user);
            post.setUpvoteCount(post.getUpvoteCount() - 2);

            postService.savePost(post);
            return userRepository.save(user);
        } else {
            user.getDownvotedPostList().add(post);
            post.getDownvotedByUserList().add(user);
            post.setUpvoteCount(post.getUpvoteCount() - 1);
            postService.savePost(post);
            return userRepository.save(user);
        }
    }

    public UserResponse createUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setPostList(user.getPostList().stream().map(
                        post -> {
                            UserResponseHelper userResponseHelper = new UserResponseHelper();
                            userResponseHelper.setId(post.getId());
                            userResponseHelper.setPostType(post.getPostType());
                            userResponseHelper.setSubName(post.getSub().getName());
                            userResponseHelper.setSubLink(linkTo(methodOn(GeneralActionsController.class).getSub(post.getSub().getId())).withRel("subLink"));
                            if (post.getPostType() == PostType.TOPIC) {
                                userResponseHelper.setContent(post.getTopic().getTitle());
                                userResponseHelper.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getTopic(post.getId())).withSelfRel());
                            } else {
                                userResponseHelper.setContent(post.getComment().getBody());
                                userResponseHelper.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getComment(post.getId())).withSelfRel());
                            }
                            return userResponseHelper;
                        }
                ).toList()
        );
        userResponse.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getUser(user.getId())).withSelfRel());
        return userResponse;
    }

    public List<Sub> getFollowedSubs(String bearer) {
        User user = getUserByJwt(bearer);
        return user.getFollowedSubList();
    }

    public Integer checkPost(String bearer, Integer postId) {
        User user = getUserByJwt(bearer);
        Post post = postService.getPostById(postId);
        if(user.getUpvotedPostList().contains(post)){
            return 1;
        }else if(user.getDownvotedPostList().contains(post)){
            return -1;
        }else{
            return 0;
        }
    }
}
