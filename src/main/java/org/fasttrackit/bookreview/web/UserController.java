package org.fasttrackit.bookreview.web;

import org.fasttrackit.bookreview.domain.User;
import org.fasttrackit.bookreview.exception.ResourceNotFoundException;
import org.fasttrackit.bookreview.persistance.UserRepository;
import org.fasttrackit.bookreview.security.CurrentUser;
import org.fasttrackit.bookreview.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}