package me.nolanjames.iouserver.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.nolanjames.iouserver.domain.shared.HttpResponse;
import me.nolanjames.iouserver.domain.user.dto.AppUserRequest;
import me.nolanjames.iouserver.domain.user.dto.AppUserResponse;
import me.nolanjames.iouserver.domain.user.entity.AppUser;
import me.nolanjames.iouserver.domain.user.service.AppUserService;
import me.nolanjames.iouserver.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static java.time.LocalTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@Valid @RequestBody AppUserRequest request) {
        AppUserResponse response = userService.createUser(request);
        return ResponseEntity.created(getUri())
                .body(HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("user", response))
                        .message("User created successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForm) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.email(), loginForm.password()));
        AppUserResponse appUserResponse = userService.getUserByEmail(loginForm.email());
        return ResponseEntity.ok()
                .body(HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(Map.of("user", appUserResponse))
                        .message("User Login Successful")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString()
        );
    }

}
