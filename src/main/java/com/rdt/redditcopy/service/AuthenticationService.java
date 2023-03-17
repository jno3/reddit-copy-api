package com.rdt.redditcopy.service;

import com.rdt.redditcopy.controller.GeneralActionsController;
import com.rdt.redditcopy.model.Token;
import com.rdt.redditcopy.model.User;
import com.rdt.redditcopy.repository.TokenRepository;
import com.rdt.redditcopy.repository.UserRepository;
import com.rdt.redditcopy.request.AuthenticationRequest;
import com.rdt.redditcopy.request.RegisterRequest;
import com.rdt.redditcopy.response.AuthenticationResponse;
import com.rdt.redditcopy.type.TokenType;
import com.rdt.redditcopy.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userType(UserType.DEFAULT)
                .followedSubList(new ArrayList<>())
                .ownedSubList(new ArrayList<>())
                .postList(new ArrayList<>())
                .downvotedPostList(new ArrayList<>())
                .upvotedPostList(new ArrayList<>())
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        Link selfLink = linkTo(methodOn(GeneralActionsController.class).getUser(user.getId())).withSelfRel();
        return AuthenticationResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(jwtToken)
                .selfLink(selfLink)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        Link selfLink = linkTo(methodOn(GeneralActionsController.class).getUser(user.getId())).withSelfRel();
        return AuthenticationResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(jwtToken)
                .selfLink(selfLink)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
