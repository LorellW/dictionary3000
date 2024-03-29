package com.github.lorellw.dictionary3000.testRepo;

import com.github.lorellw.dictionary3000.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
