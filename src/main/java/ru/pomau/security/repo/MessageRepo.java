package ru.pomau.security.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pomau.security.entity.MessageEntity;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<MessageEntity, String> {
    List<MessageEntity> findByChat_IdOrderByCreatedDateAsc(String id);
}
