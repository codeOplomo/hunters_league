package org.anas.hunters_league.domain.enums;

public enum Permission {
    // Member Permissions
    CAN_PARTICIPATE,
    CAN_VIEW_RANKINGS,
    CAN_VIEW_COMPETITIONS,

    // Jury Permissions
    CAN_SCORE,

    // Admin Permissions
    CAN_MANAGE_COMPETITIONS,
    CAN_MANAGE_USERS,
    CAN_MANAGE_SPECIES,
    CAN_MANAGE_SETTINGS
}