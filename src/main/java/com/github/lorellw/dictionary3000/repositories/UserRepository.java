package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
