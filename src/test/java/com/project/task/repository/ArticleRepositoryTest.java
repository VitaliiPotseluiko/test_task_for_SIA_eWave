package com.project.task.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleRepositoryTest {
    private static final String ADD_SIX_ARTICLES_TO_DB
            = "classpath:database/article/add-six-articles-to-db.sql";
    private static final String ADD_THREE_ARTICLES_TO_DB
            = "classpath:database/article/add-three-articles-to-db.sql";
    private static final String DELETE_DATA
            = "classpath:database/article/delete-all-articles.sql";
    private static final int DAYS = 7;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Sql(scripts = ADD_SIX_ARTICLES_TO_DB,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = DELETE_DATA, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArticleAmountForLastSevenDays_GetThreeArticle_Ok() {
        long actual = articleRepository.getArticleAmountForLastSevenDays(
                LocalDate.now().minusDays(DAYS), LocalDate.now()
        );

        assertEquals(3, actual);
    }

    @Test
    @Sql(scripts = ADD_THREE_ARTICLES_TO_DB,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = DELETE_DATA, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getArticleAmountForLastSevenDays_Zero_Ok() {
        long actual = articleRepository.getArticleAmountForLastSevenDays(
                LocalDate.now().minusDays(DAYS), LocalDate.now()
        );

        assertEquals(0, actual);
    }
}