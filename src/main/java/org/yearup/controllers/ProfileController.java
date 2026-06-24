package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.service.ProfileService;

@CrossOrigin
@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public Profile getUserId(@PathVariable int userId){
        Profile profile = profileService.getById(userId);
        if (profile==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return profile;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Profile> create(@RequestBody Profile profile){
        Profile saved = profileService.create((profile));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Profile update(@PathVariable int userId, @RequestBody Profile profile){
        if (profileService.getById(userId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return profileService.update(userId, profile);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable int userId){
        if (profileService.getById(userId) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }


}
