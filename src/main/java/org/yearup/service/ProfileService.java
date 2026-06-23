package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

import java.util.List;

@Service
public class ProfileService
{
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository)
    {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getById(int userId) {
        return profileRepository.findById(userId)
                .orElse(null);
    }

    public Profile create(Profile profile)
    {
        return profileRepository.save(profile);
    }

    public Profile update(int userId, Profile profile) {
        Profile existing = profileRepository.findById(userId).orElseThrow();
        existing.setFirstName(profile.getFirstName());
        existing.setLastName(profile.getLastName());
        existing.setPhone(profile.getPhone());
        existing.setEmail(profile.getEmail());
        existing.setAddress(profile.getAddress());
        existing.setCity(profile.getCity());
        existing.setState(profile.getState());
        existing.setZip(profile.getZip());
        return profileRepository.save(existing);

    }

    public boolean delete(int userId) {
        if (profileRepository.existsById(userId)) {
            profileRepository.deleteById(userId);
            return true;
        }
        return false;
    }

}
