package me.nolanjames.iouserver.domain.user.service;

import me.nolanjames.iouserver.domain.user.dto.AppUserRequest;
import me.nolanjames.iouserver.domain.user.dto.AppUserResponse;

public interface AppUserService {
    AppUserResponse createUser(AppUserRequest appUserRequest);
    AppUserResponse getUserByEmail(String email);
}
