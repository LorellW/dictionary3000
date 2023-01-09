package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.entities.UserWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWordsRepository extends JpaRepository<UserWords, Long> {

    @Query("select uw from UserWords uw " +
            "join Word w on uw.word=w.id " +
            "where uw.user = :currentUser " +
            "and "+
            "lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
            "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
    List<UserWords> search(@Param("searchItem") String searchItem, @Param("currentUser") User user);


    List<UserWords> findByUser(User user);

}

