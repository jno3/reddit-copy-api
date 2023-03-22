package com.rdt.redditcopy.service;

import com.rdt.redditcopy.model.*;
import com.rdt.redditcopy.repository.PostRepository;
import com.rdt.redditcopy.repository.SubRepository;
import com.rdt.redditcopy.repository.UserRepository;
import com.rdt.redditcopy.request.CreateCommentRequest;
import com.rdt.redditcopy.request.CreateTopicRequest;
import com.rdt.redditcopy.type.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final SubRepository subRepository;
    private final UserRepository userRepository;
    private final TopicService topicService;
    private final CommentService commentService;

    public Post createTopicPost(User user, Sub sub, CreateTopicRequest CreateTopicRequest) {
        Topic topic = Topic
                .builder()
                .title(CreateTopicRequest.getTitle())
                .body(CreateTopicRequest.getBody())
                .build();

        Post post = Post
                .builder()
                .postType(PostType.TOPIC)
                .upvoteCount(0)
                .topic(topic)
                .commentList(new ArrayList<>())
                .upvotedByUserList(new ArrayList<>())
                .downvotedByUserList(new ArrayList<>())
                .sub(sub)
                .user(user)
                .build();

        topic.setPost(post);
        topicService.saveTopic(topic);
        user.getPostList().add(post);
        userRepository.save(user);
        return postRepository.save(post);
    }


    public Post createCommentPost(User user, Sub sub, Post post, CreateCommentRequest createCommentRequest) {
        Comment comment = Comment
                .builder()
                .body(createCommentRequest.getBody())
                .build();

        Post newPost = Post
                .builder()
                .postType(PostType.COMMENT)
                .upvoteCount(0)
                .comment(comment)
                .commentList(new ArrayList<>())
                .upvotedByUserList(new ArrayList<>())
                .downvotedByUserList(new ArrayList<>())
                .sub(sub)
                .user(user)
                .build();

        comment.setPost(newPost);
        commentService.saveComment(comment);

        user.getPostList().add(newPost);
        userRepository.save(user);

        newPost.setPostOn(post);
        post.getCommentList().add(newPost);
        postRepository.save(post);
        return postRepository.save(newPost);
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public List<Post> getAllPostsFromSub(Integer subId) {
        return subRepository.findById(subId).orElseThrow().getPostList().stream().filter(
                post -> post.getPostType().equals(PostType.TOPIC)
        ).toList();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
