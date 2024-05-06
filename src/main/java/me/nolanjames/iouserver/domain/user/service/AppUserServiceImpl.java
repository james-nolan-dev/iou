package me.nolanjames.iouserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.iouserver.domain.user.dto.AppUserRequest;
import me.nolanjames.iouserver.domain.user.dto.AppUserResponse;
import me.nolanjames.iouserver.domain.user.entity.AppUser;
import me.nolanjames.iouserver.domain.user.mapper.AppUserMapper;
import me.nolanjames.iouserver.domain.user.repository.AppUserRepository;
import me.nolanjames.iouserver.domain.verification.service.VerificationService;
import me.nolanjames.iouserver.exception.ApiException;
import me.nolanjames.iouserver.exception.ExceptionMessages;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository<AppUser> appUserRepository;
    private final AppUserMapper mapper;
    private final VerificationService verificationService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;


    @Override
    public AppUserResponse createUser(AppUserRequest appUserRequest) {
        log.info("Adding new user to database");

        AppUser userToSave = mapper.mapToEntity(appUserRequest);
        userToSave.setPassword(passwordEncoder.encode(appUserRequest.password()));

        if (appUserRepository.isEmailUnique(userToSave.getEmail().trim().toLowerCase()) > 0) {
            log.error("User tried registering with an email that already exists");
            throw new ApiException(ExceptionMessages.EMAIL_ALREADY_IN_USE);
        }
        AppUser savedUserEntity = appUserRepository.create(userToSave);
        verificationService.generateAccountVerificationUrl(savedUserEntity.getId());
        savedUserEntity.setEnabled(false);
        savedUserEntity.setNotLocked(true);

        log.info("Added new user to database with id: {}", savedUserEntity.getId());
        return mapper.mapToResponse(savedUserEntity);
    }

    @Override
    public AppUserResponse getUserByEmail(String email) {
        return appUserMapper.mapToResponse(appUserRepository.getUserByEmail(email));
    }
}
