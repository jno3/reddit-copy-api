package com.rdt.redditcopy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rdt.redditcopy.type.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends RepresentationModel<User> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    @JsonBackReference
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList;
    @ManyToMany
    @JoinTable(
            name = "_followed_sub",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_id")
    )
    private List<Sub> followedSubList;
    @ManyToMany
    @JoinTable(
            name = "_mods_and_sub",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_id")
    )
    private List<Sub> ownedSubList;
    @ManyToMany
    @JoinTable(
            name = "_downs_and_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )

    private List<Post> downvotedPostList;
    @ManyToMany
    @JoinTable(
            name = "_ups_and_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> upvotedPostList;
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.DEFAULT;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(userType.name()));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
