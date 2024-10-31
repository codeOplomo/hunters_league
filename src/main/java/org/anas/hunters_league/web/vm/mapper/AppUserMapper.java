package org.anas.hunters_league.web.vm.mapper;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.web.vm.AppUserVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    // Mapping from AppUser entity to AppUserVM
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "nationality", source = "nationality")
    AppUserVM toAppUserVM(AppUser appUser);

    // Optional reverse mapping if needed
    AppUser toAppUser(AppUserVM appUserVM);
}

