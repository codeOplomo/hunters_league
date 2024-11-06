package org.anas.hunters_league.web.vm.mapper;

import org.anas.hunters_league.domain.AppUser;
import org.anas.hunters_league.web.vm.LoginVM;
import org.anas.hunters_league.web.vm.RegisterVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    // Mapping methods from VMs to the entity AppUser
    AppUser toAppUser(RegisterVM registerVM);

    AppUser toAppUser(LoginVM loginVM);

    // Optional: Mapping methods from AppUser entity back to VMs
    RegisterVM toRegisterVM(AppUser appUser);

    LoginVM toLoginVM(AppUser appUser);
}




