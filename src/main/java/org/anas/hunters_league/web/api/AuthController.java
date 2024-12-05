package org.anas.hunters_league.web.api;

import jakarta.validation.Valid;
import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.service.AuthenticationService;
import org.anas.hunters_league.service.JwtService;
import org.anas.hunters_league.service.dto.AppUserTokenDTO;
import org.anas.hunters_league.service.dto.AuthResDTO;
import org.anas.hunters_league.service.dto.RegisterDTO;
import org.anas.hunters_league.web.vm.LoginVM;
import org.anas.hunters_league.web.vm.RegisterVM;
import org.anas.hunters_league.web.vm.VerifyUserVM;
import org.anas.hunters_league.web.vm.mapper.AuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVM input) {
        try {
            AppUser user = authenticationService.authenticate(input);
            String jwtToken = jwtService.generateToken(user);

            // Use the AppUserTokenDTO to encapsulate the token
            AppUserTokenDTO response = new AppUserTokenDTO(jwtToken);

            return ResponseEntity.ok(response); // Return the DTO as the response
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterVM input) {
        try {
            AppUser user = authenticationService.signup(input);
            // Create a ResponseDTO object
            RegisterDTO response = new RegisterDTO("Registration successful!", user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterDTO(e.getMessage(), null));
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
}


