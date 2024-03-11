package me.nolanjames.iouserver.domain.user.dto;

import java.time.LocalDateTime;

public record AppUserResponse(
        String firstName,
        String lastName,
        String email,
        String address,
        String phone,
        String title,
        String bio,
        String imageUrl,
        boolean enabled,
        boolean isNotLocked,
        boolean isUsingMfa,
        LocalDateTime createdAT
) {
}
