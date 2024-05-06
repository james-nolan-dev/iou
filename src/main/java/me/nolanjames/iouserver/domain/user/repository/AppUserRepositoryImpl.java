package me.nolanjames.iouserver.domain.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nolanjames.iouserver.domain.role.entity.Role;
import me.nolanjames.iouserver.domain.role.repository.RoleRepository;
import me.nolanjames.iouserver.domain.user.entity.AppUser;
import me.nolanjames.iouserver.domain.user.entity.AppUserPrincipal;
import me.nolanjames.iouserver.domain.user.mapper.AppUserRowMapper;
import me.nolanjames.iouserver.exception.ApiException;
import me.nolanjames.iouserver.exception.ExceptionMessages;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static me.nolanjames.iouserver.domain.role.model.RoleType.ROLE_USER;
import static me.nolanjames.iouserver.domain.user.query.AppUserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AppUserRepositoryImpl implements AppUserRepository<AppUser>, UserDetailsService {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;

    @Override
    public AppUser create(AppUser user) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameterSource, holder);

            user.setId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

//            emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);


            return user;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(ExceptionMessages.ERROR);
        }
    }

    @Override
    public Collection<AppUser> list(int page, int pageSize) {
        return null;
    }

    @Override
    public AppUser get(Long id) {
        return null;
    }

    @Override
    public AppUser update(AppUser data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public Integer isEmailUnique(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(AppUser user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = getUserByEmail(email);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in database: {}", email);
            return new AppUserPrincipal(user, roleRepository.getRoleByUserId(user.getId()).getPermission());
        }
    }

    @Override
    public AppUser getUserByEmail(String email) {
        try {
            return jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, Map.of("email", email), new AppUserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException(ExceptionMessages.NO_USER_FOUND_BY_EMAIL + email);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(ExceptionMessages.ERROR);
        }
    }
}
