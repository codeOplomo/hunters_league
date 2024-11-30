package org.anas.hunters_league.web.api;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.service.AuthenticationService;
import org.anas.hunters_league.service.JwtService;
import org.anas.hunters_league.web.vm.LoginVM;
import org.anas.hunters_league.web.vm.RegisterVM;
import org.anas.hunters_league.web.vm.VerifyUserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterVM input) {
        try {
            AppUser newUser = authenticationService.signup(input);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully. Please verify your email.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserVM input) {
        try {
            authenticationService.verifyUser(input);
            return ResponseEntity.ok("User verified successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code resent successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVM input) {
        try {
            AppUser user = authenticationService.authenticate(input);
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(Map.of("token", jwtToken, "role", user.getRole()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}


