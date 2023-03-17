package com.rdt.redditcopy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_sub")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "sub", cascade = CascadeType.ALL)
    private List<Post> postList;
    @ManyToMany(mappedBy = "ownedSubList")
    private List<User> superUsersList;
    @ManyToMany(mappedBy = "followedSubList")
    private List<User> usersFollowingList;

}
