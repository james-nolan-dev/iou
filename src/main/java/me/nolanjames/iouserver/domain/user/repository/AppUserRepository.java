package me.nolanjames.iouserver.domain.user.repository;

import me.nolanjames.iouserver.domain.user.entity.AppUser;

import java.util.Collection;

public interface AppUserRepository<T extends AppUser> {
    T create(T data);

    Collection<T> list(int page, int pageSize);

    T get(Long id);

    T update(T data);

    Boolean delete(Long id);

    Integer isEmailUnique(String email);
}
