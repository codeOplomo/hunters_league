package org.anas.hunters_league.web.api;

import jakarta.validation.Valid;
import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.service.AppUserService;
import org.anas.hunters_league.web.vm.LoginVM;
import org.anas.hunters_league.web.vm.RegisterVM;
import org.anas.hunters_league.web.vm.mapper.AuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final AuthMapper authMapper;

    public AuthController(AppUserService appUserService, AuthMapper authMapper) {
        this.appUserService = appUserService;
        this.authMapper = authMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterVM> registerUser(@Valid @RequestBody RegisterVM registerVM) {
        AppUser appUser = authMapper.toAppUser(registerVM);
        AppUser registeredUser = appUserService.registerUser(appUser);
        RegisterVM registeredUserVM = authMapper.toRegisterVM(registeredUser);
        return new ResponseEntity<>(registeredUserVM, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginVM> loginUser(@Valid @RequestBody LoginVM loginVM) {
        AppUser appUser = authMapper.toAppUser(loginVM);
        AppUser loggedInUser = appUserService.loginUser(appUser);
        LoginVM loggedInUserVM = authMapper.toLoginVM(loggedInUser);
        return ResponseEntity.ok(loggedInUserVM);
    }
}


