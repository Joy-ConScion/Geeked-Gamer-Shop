package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Profile getUserId(Principal principal) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        Profile profile = profileService.getById(userId);
        if (profile == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return profile;
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> create(@RequestBody Profile profile) {
        Profile saved = profileService.create((profile));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Profile update(Principal principal, @RequestBody Profile profile) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        if (profileService.getById(userId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return profileService.update(userId, profile);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> delete(Principal principal) {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        if (profileService.getById(userId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }


}
