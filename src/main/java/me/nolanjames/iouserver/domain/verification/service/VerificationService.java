package me.nolanjames.iouserver.domain.verification.service;

public interface VerificationService {

    void generateAccountVerificationUrl(Long userId);
}
