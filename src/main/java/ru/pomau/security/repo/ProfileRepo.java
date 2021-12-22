package ru.pomau.security.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pomau.security.entity.ProfileEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ProfileRepo extends CrudRepository<ProfileEntity, String> {

    ProfileEntity findBySessionKey(String sessionKey);
    ProfileEntity findByPublicKey(String publicKey);
    ProfileEntity findByUsername(String username);
    List<ProfileEntity> findAllByUsernameStartsWith(String name);

}
