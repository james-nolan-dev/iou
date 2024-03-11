package me.nolanjames.iouserver.domain.user.mapper;

import me.nolanjames.iouserver.domain.user.dto.AppUserRequest;
import me.nolanjames.iouserver.domain.user.dto.AppUserResponse;
import me.nolanjames.iouserver.domain.user.entity.AppUser;

public interface AppUserMapper {

    AppUser mapToEntity(AppUserRequest appUserRequest);

    AppUserResponse mapToResponse(AppUser appUser);
}
