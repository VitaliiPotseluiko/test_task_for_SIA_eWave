package com.project.task.repository;

import com.project.task.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT COUNT(id) FROM articles WHERE publishing_date BETWEEN :first AND :second",
            nativeQuery = true)
    long getArticleAmountForLastSevenDays(LocalDate first, LocalDate second);
}
