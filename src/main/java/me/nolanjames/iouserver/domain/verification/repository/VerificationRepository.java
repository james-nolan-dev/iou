package me.nolanjames.iouserver.domain.verification.repository;

public interface VerificationRepository {
    void generateAccountVerificationUrl(Long userId, String verificationUrl);
}
