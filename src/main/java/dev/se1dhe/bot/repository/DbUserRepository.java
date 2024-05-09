package dev.se1dhe.bot.repository;


import dev.se1dhe.bot.model.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbUserRepository extends JpaRepository<DbUser, Long> {
    List<DbUser> findByUserNameContainingIgnoreCase(String name);
}
