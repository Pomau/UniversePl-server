package ru.pomau.security.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.TokenEntity;

import java.util.List;

@Repository
public interface TokenRepo extends CrudRepository<TokenEntity, String> {
    TokenEntity findByChat_Id(String id);
}
