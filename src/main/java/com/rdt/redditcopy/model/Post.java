package com.rdt.redditcopy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.rdt.redditcopy.type.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Entity
@Table(name = "_post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends RepresentationModel<Post> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer upvoteCount;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "sub_id", referencedColumnName = "id")
    private Sub sub;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Topic topic;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Comment comment;

    //    @OneToMany(mappedBy = "postOn", cascade = CascadeType.ALL)
//    private List<Comment> commentList;
    @OneToMany(mappedBy = "postOn", cascade = CascadeType.ALL)
    private List<Post> commentList;
    @ManyToOne
    @JoinColumn(name = "post_on_id", referencedColumnName = "id")
    private Post postOn;
    @ManyToMany(mappedBy = "downvotedPostList")
    private List<User> downvotedByUserList;
    @ManyToMany(mappedBy = "upvotedPostList")
    private List<User> upvotedByUserList;
}
