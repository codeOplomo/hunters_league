package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    List<AppUser> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String email);
}
