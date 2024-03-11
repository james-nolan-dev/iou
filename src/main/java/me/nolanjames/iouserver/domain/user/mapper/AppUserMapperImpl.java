package me.nolanjames.iouserver.domain.user.mapper;

import me.nolanjames.iouserver.domain.user.dto.AppUserRequest;
import me.nolanjames.iouserver.domain.user.dto.AppUserResponse;
import me.nolanjames.iouserver.domain.user.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapperImpl implements AppUserMapper {
    @Override
    public AppUser mapToEntity(AppUserRequest appUserRequest) {
        return AppUser.builder()
                .firstName(appUserRequest.firstName())
                .lastName(appUserRequest.lastName())
                .email(appUserRequest.email())
                .build();
    }

    @Override
    public AppUserResponse mapToResponse(AppUser appUser) {
        return new AppUserResponse(
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getAddress(),
                appUser.getPhone(),
                appUser.getTitle(),
                appUser.getBio(),
                appUser.getImageUrl(),
                appUser.isEnabled(),
                appUser.isNotLocked(),
                appUser.isUsingMfa(),
                appUser.getCreatedAt()
        );
    }
}
