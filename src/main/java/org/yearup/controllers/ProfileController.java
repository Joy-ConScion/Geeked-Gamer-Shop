package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

        Profile profile = profileService.getUserId


    }

}
