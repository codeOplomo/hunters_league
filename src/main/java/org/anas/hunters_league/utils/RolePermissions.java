package org.anas.hunters_league.utils;

import org.anas.hunters_league.domain.enums.Permission;
import org.anas.hunters_league.domain.enums.Role;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RolePermissions {
    public static Set<Permission> getPermissionsForRole(Role role) {
        Set<Permission> permissions = new HashSet<>();

        switch (role) {
            case MEMBER:
                permissions.addAll(Arrays.asList(
                        Permission.CAN_PARTICIPATE,
                        Permission.CAN_VIEW_RANKINGS,
                        Permission.CAN_VIEW_COMPETITIONS
                ));
                break;
            case JURY:
                permissions.addAll(Arrays.asList(
                        Permission.CAN_PARTICIPATE,
                        Permission.CAN_VIEW_RANKINGS,
                        Permission.CAN_VIEW_COMPETITIONS,
                        Permission.CAN_SCORE
                ));
                break;
            case ADMIN:
                permissions.addAll(Arrays.asList(
                        Permission.CAN_PARTICIPATE,
                        Permission.CAN_VIEW_RANKINGS,
                        Permission.CAN_VIEW_COMPETITIONS,
                        Permission.CAN_SCORE,
                        Permission.CAN_MANAGE_COMPETITIONS,
                        Permission.CAN_MANAGE_USERS,
                        Permission.CAN_MANAGE_SPECIES,
                        Permission.CAN_MANAGE_SETTINGS
                ));
                break;
        }

        return permissions;
    }
}
