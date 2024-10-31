package org.anas.hunters_league.web.api;


import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.service.AppUserService;
import org.anas.hunters_league.web.vm.AppUserVM;
import org.anas.hunters_league.web.vm.mapper.AppUserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;

    public AppUserController(AppUserService appUserService, AppUserMapper appUserMapper) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
    }

    @GetMapping("/search")
    public ResponseEntity<List<AppUserVM>> searchUsersByNameOrEmail(@RequestParam String searchTerm) {
        List<AppUser> users = appUserService.searchUsersByNameOrEmail(searchTerm);
        System.out.println("Users: " + users);
        List<AppUserVM> userVMs = users.stream()
                .map(appUserMapper::toAppUserVM)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userVMs);
    }

}
