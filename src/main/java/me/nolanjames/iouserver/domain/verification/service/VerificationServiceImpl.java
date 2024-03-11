package me.nolanjames.iouserver.domain.verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.iouserver.domain.verification.repository.VerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

import static me.nolanjames.iouserver.domain.verification.model.VerificationType.ACCOUNT;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;

    @Override
    public void generateAccountVerificationUrl(Long userId) {
        String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
        verificationRepository.generateAccountVerificationUrl(userId, verificationUrl);
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }
}
