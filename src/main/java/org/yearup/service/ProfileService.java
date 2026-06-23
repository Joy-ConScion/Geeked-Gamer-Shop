package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public Profile getById(int userId) {
        return profileRepository.findById(userId)
                .orElse(null);
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile update(int userId) {
        if (!profileRepository.existsById(userId)) {return null;}
        profile.setCategoryId(userId);
        return profileRepository.save(userId);

    }

    public boolean delete(int userId) {
        if (profileRepository.existsById(userId)) {
            profileRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
