package com.github.lorellw.dictionary3000.repositories;

import com.github.lorellw.dictionary3000.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("select w from Word w " +
            "where lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
            "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
    List<Word> search(@Param("searchItem")String searchItem);

//    @Query("select w from Word w " +
//            "where w.enTranslated is FALSE or w.ruTranslated is FALSE")
@Query("select w from Word w " +
        "where lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
        "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
    List<Word> searchUnstudied();

//    @Query("select w from Word w " +
//            "where w.enTranslated is FALSE")
@Query("select w from Word w " +
        "where lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
        "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
            List<Word> searchEnUntranslated();

//    @Query("select w from Word w " +
//            "where w.ruTranslated is FALSE")
@Query("select w from Word w " +
        "where lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
        "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
    List<Word> searchRuUntranslated();

//    @Query("select w from Word w " +
//            "where w.competently is FALSE")
@Query("select w from Word w " +
        "where lower(w.wordRu) like lower(concat('%', :searchItem, '%')) " +
        "or lower(w.wordEn) like lower(concat('%', :searchItem, '%'))")
    List<Word> searchUnwritten();

}
