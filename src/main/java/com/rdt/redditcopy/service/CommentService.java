package com.rdt.redditcopy.service;

import com.rdt.redditcopy.controller.GeneralActionsController;
import com.rdt.redditcopy.model.*;
import com.rdt.redditcopy.repository.CommentRepository;
import com.rdt.redditcopy.request.CreateCommentRequest;
import com.rdt.redditcopy.response.CommentResponse;
import com.rdt.redditcopy.response.CommentResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public CommentResponse createCommentResponse(Post post) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(post.getId());
        commentResponse.setBody(post.getComment().getBody());
        commentResponse.setCommentList(post.getCommentList().stream().map(
                        commentPost -> {
                            CommentResponseHelper commentResponseHelper = new CommentResponseHelper();
                            commentResponseHelper.setCommentBody(commentPost.getComment().getBody());
                            commentResponseHelper.setCommentLink(linkTo(methodOn(GeneralActionsController.class).getComment(commentPost.getId())).withSelfRel());
                            return commentResponseHelper;
                        }
                ).toList()
        );
        commentResponse.setSelfLink(linkTo(methodOn(GeneralActionsController.class).getComment(post.getId())).withSelfRel());
        return commentResponse;
    }
}
