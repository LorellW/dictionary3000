package com.github.lorellw.dictionary3000.testQueries;

import com.github.lorellw.dictionary3000.entities.User;
import org.springframework.data.jpa.repository.Query;

public abstract class Queries {
    @Query("")
    abstract User findByUsername();
}
