package org.anas.hunters_league.repository;

import org.anas.hunters_league.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsername(String username);
    List<AppUser> findByFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String email);

    Optional<AppUser> findByCin(String cin);

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT a FROM AppUser a WHERE a.username = :username OR a.email = :email OR a.cin = :cin")
    Optional<AppUser> findByUsernameOrEmailOrCin(@Param("username") String username, @Param("email") String email, @Param("cin") String cin);

    Optional<AppUser> findByVerificationCode(String verificationCode);
}
