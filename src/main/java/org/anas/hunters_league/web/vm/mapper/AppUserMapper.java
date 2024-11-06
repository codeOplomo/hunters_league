package org.anas.hunters_league.web.vm.mapper;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.web.vm.AppUserVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserVM toAppUserVM(AppUser appUser);

    // Optional reverse mapping if needed
    AppUser toAppUser(AppUserVM appUserVM);
}

