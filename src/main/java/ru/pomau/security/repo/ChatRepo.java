package ru.pomau.security.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pomau.security.interfaces.ChatsObject;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ChatRepo extends CrudRepository<ChatEntity, String> {
    List<ChatEntity> findByUsersOrderByLastMessageDesc(ProfileEntity profile, Pageable pageable);
    List<ChatEntity> findByUsers(ProfileEntity user);
    Set<ChatEntity> findByUsersAndStatusLessThan(ProfileEntity profile, Integer status);
}
