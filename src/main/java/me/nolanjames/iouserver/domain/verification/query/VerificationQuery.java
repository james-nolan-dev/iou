package me.nolanjames.iouserver.domain.verification.query;

public class VerificationQuery {
    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerifications (user_id, url, expiration_date)" +
            " VALUES (:userId, :url, :expiration_date)";
}
