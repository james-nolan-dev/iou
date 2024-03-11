package me.nolanjames.iouserver.domain.verification.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.iouserver.exception.ApiException;
import me.nolanjames.iouserver.exception.ExceptionMessages;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import static me.nolanjames.iouserver.constant.AppConstants.ACCOUNT_VERIFICATION_HOURS;
import static me.nolanjames.iouserver.domain.verification.query.VerificationQuery.INSERT_ACCOUNT_VERIFICATION_URL_QUERY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VerificationRepositoryImpl implements VerificationRepository {

    private final NamedParameterJdbcTemplate jdbc;


    @Override
    public void generateAccountVerificationUrl(Long userId, String verificationUrl) {
        try {
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of(
                    "userId", userId,
                    "url", verificationUrl,
                    "expiration_date", Timestamp.valueOf(LocalDateTime.now().plusHours(ACCOUNT_VERIFICATION_HOURS))));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(ExceptionMessages.ERROR);
        }
    }


}
