package org.anas.hunters_league.service;


import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;


    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    public List<AppUser> searchUsersByNameOrEmail(String searchTerm) {
        return appUserRepository.findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm);
    }

    public AppUser registerUser(AppUser appUser) {

        System.out.println("Password before hashing: " + appUser.getPassword());
        if (appUser.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        String hashedPassword = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt());
        System.out.println("Password after hashing: " + hashedPassword);
        appUser.setPassword(hashedPassword);

        return appUserRepository.save(appUser);
    }

    public AppUser loginUser(AppUser appUser) {
        AppUser existingUser = appUserRepository.findByUsername(appUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (existingUser != null && BCrypt.checkpw(appUser.getPassword(), existingUser.getPassword())) {
            return existingUser;
        }

        throw new RuntimeException("Invalid username or password");
    }

    public Optional<AppUser> getUserById(UUID id) {
        return appUserRepository.findById(id);
    }
}
