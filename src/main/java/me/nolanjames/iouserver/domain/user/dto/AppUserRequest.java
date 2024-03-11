package me.nolanjames.iouserver.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import me.nolanjames.iouserver.exception.ValidationMessages;

public record AppUserRequest(
        @NotEmpty(message = ValidationMessages.FIRST_NAME_MUST_BE_NOT_BLANK)
        String firstName,
        @NotEmpty(message = ValidationMessages.LAST_NAME_MUST_BE_NOT_BLANK)
        String lastName,
        @NotEmpty(message = ValidationMessages.EMAIL_MUST_BE_NOT_BLANK)
        @Email(message = ValidationMessages.INVALID_EMAIL)
        String email,
        @NotEmpty(message = ValidationMessages.PASSWORD_MUST_BE_NOT_BLANK)
        String password
) {
}
