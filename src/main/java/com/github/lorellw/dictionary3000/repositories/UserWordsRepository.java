package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.entities.UserWords;
import com.github.lorellw.dictionary3000.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWordsRepository extends JpaRepository<UserWords, Long> {

    @Query("""
            SELECT uw FROM UserWords uw\s
            JOIN Word w ON uw.word = w.id\s
            WHERE uw.user = :currentUser\s
            AND(\s
            LOWER(w.wordRu) LIKE LOWER(CONCAT('%', :searchItem, '%'))\s
            OR\s
            LOWER(w.wordRu) LIKE LOWER(CONCAT('%', :searchItem, '%')))
            """)
    List<UserWords> search(@Param("searchItem") String searchItem,
                           @Param("currentUser") User user);

    @Query("""
            SELECT uw FROM UserWords uw\s
            WHERE uw.user = :currentUser\s
            AND\s
            uw.enTranslated is FALSE
            """)
    List<UserWords> searchEnUntranslated(@Param("currentUser") User user);

    @Query("""
            SELECT uw FROM UserWords uw\s
            WHERE uw.user = :currentUser\s
            AND\s
            uw.ruTranslated is FALSE
            """)
    List<UserWords> searchRuUntranslated(@Param("currentUser") User user);

    @Query("""
            SELECT uw FROM UserWords uw\s
            WHERE uw.user = :currentUser\s
            AND(\s
            uw.enTranslated is FALSE\s
            OR\s
            uw.ruTranslated is FALSE)
            """)
    List<UserWords> searchUntranslated(@Param("currentUser") User user);

    @Query("""
            SELECT uw FROM UserWords uw\s
            WHERE uw.user = :currentUser\s
            AND\s
            uw.competently is FALSE
            """)
    List<UserWords> searchUnwritten(@Param("currentUser") User user);

    List<UserWords> findByUser(User user);

    List<UserWords> findByWord(Word word);

}

