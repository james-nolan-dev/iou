package me.nolanjames.iouserver.form;

import jakarta.validation.constraints.NotEmpty;

public record LoginForm(
        @NotEmpty
        String email,
        @NotEmpty
        String password
) {
}


