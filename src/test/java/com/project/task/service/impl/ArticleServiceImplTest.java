package com.project.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.project.task.dto.request.ArticleRequestDto;
import com.project.task.dto.response.ArticleResponseDto;
import com.project.task.mapper.Mapper;
import com.project.task.model.Article;
import com.project.task.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(value = MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private Mapper<ArticleResponseDto, Article, ArticleRequestDto> mapper;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    public void save_SaveArticle_Ok() {
        ArticleRequestDto requestDto = new ArticleRequestDto();
        requestDto.setTitle("Title1");
        requestDto.setContent("Content1");
        requestDto.setAuthor("Author1");
        requestDto.setPublishingDate(LocalDate.parse("2024-03-21"));

        Article article = new Article();
        article.setId(1L);
        article.setContent(requestDto.getContent());
        article.setAuthor(requestDto.getAuthor());
        article.setTitle(requestDto.getTitle());
        article.setPublishingDate(requestDto.getPublishingDate());

        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setId(1L);
        responseDto.setContent(article.getContent());
        responseDto.setAuthor(article.getAuthor());
        responseDto.setTitle(article.getTitle());
        responseDto.setPublishingDate(article.getPublishingDate());

        ArticleResponseDto expected = new ArticleResponseDto();
        expected.setId(1L);
        expected.setContent(requestDto.getContent());
        expected.setAuthor(requestDto.getAuthor());
        expected.setTitle(requestDto.getTitle());
        expected.setPublishingDate(requestDto.getPublishingDate());

        when(mapper.toModel(requestDto)).thenReturn(any());
        when(articleRepository.save(article)).thenReturn(article);
        when(mapper.toDto(article)).thenReturn(responseDto);

        ArticleResponseDto actual = articleService.save(requestDto);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void getById_getArticleByIdEqualsOne_Ok() {
        Article article = new Article();
        article.setId(1L);
        article.setContent("Content1");
        article.setAuthor("Author1");
        article.setTitle("Title1");
        article.setPublishingDate(LocalDate.parse("2024-03-21"));

        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setId(article.getId());
        responseDto.setTitle(article.getTitle());
        responseDto.setContent(article.getContent());
        responseDto.setAuthor(article.getAuthor());
        responseDto.setPublishingDate(article.getPublishingDate());

        ArticleResponseDto expected = new ArticleResponseDto();
        expected.setId(responseDto.getId());
        expected.setTitle(responseDto.getTitle());
        expected.setContent(responseDto.getContent());
        expected.setAuthor(responseDto.getAuthor());
        expected.setPublishingDate(responseDto.getPublishingDate());

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(mapper.toDto(article)).thenReturn(responseDto);

        ArticleResponseDto actual = articleService.getById(1L);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void getById_getArticleByNotExistingId_NotOk() {
        String expected = "Can't find article by id 4";
        when(articleRepository.findById(4L)).thenReturn(Optional.empty());

        Exception actual = assertThrows(EntityNotFoundException.class,
                () -> articleService.getById(4L));

        assertEquals("EntityNotFoundException", actual.getClass().getSimpleName());
        assertEquals(expected, actual.getMessage());
    }

    @Test
    public void update_UpdateArticleByIdOne_Ok() {
        ArticleRequestDto requestDto = new ArticleRequestDto();
        requestDto.setTitle("Title2");
        requestDto.setContent("Content2");
        requestDto.setAuthor("Author2");
        requestDto.setPublishingDate(LocalDate.parse("2024-03-30"));

        Article article = new Article();
        article.setId(1L);
        article.setContent("Content1");
        article.setAuthor("Author1");
        article.setTitle("Title1");
        article.setPublishingDate(LocalDate.parse("2024-03-21"));

        Article savedArticle = new Article();
        savedArticle.setId(1L);
        savedArticle.setContent(requestDto.getContent());
        savedArticle.setAuthor(requestDto.getAuthor());
        savedArticle.setTitle(requestDto.getTitle());
        savedArticle.setPublishingDate(requestDto.getPublishingDate());

        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setId(savedArticle.getId());
        responseDto.setContent(savedArticle.getContent());
        responseDto.setAuthor(savedArticle.getAuthor());
        responseDto.setPublishingDate(savedArticle.getPublishingDate());
        responseDto.setTitle(savedArticle.getTitle());

        ArticleResponseDto expected = new ArticleResponseDto();
        expected.setId(responseDto.getId());
        expected.setContent(responseDto.getContent());
        expected.setAuthor(responseDto.getAuthor());
        expected.setTitle(responseDto.getTitle());
        expected.setPublishingDate(responseDto.getPublishingDate());

        when(articleRepository.existsById(1L)).thenReturn(true);
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(savedArticle)).thenReturn(savedArticle);
        when(mapper.toDto(savedArticle)).thenReturn(responseDto);

        ArticleResponseDto actual = articleService.update(requestDto, 1L);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual));
    }

    @Test
    public void update_UpdateArticleByNotExistingId_NotOk() {
        String expected = "Article by id 5 is not existing";
        when(articleRepository.existsById(5L)).thenReturn(false);

        Exception actual = assertThrows(EntityNotFoundException.class,
                () -> articleService.update(new ArticleRequestDto(), 5L));

        assertEquals("EntityNotFoundException", actual.getClass().getSimpleName());
        assertEquals(expected, actual.getMessage());
    }

    @Test
    public void deleteById_DeleteArticleByNotExistingId_NotOk() {
        String expected = "Article by id 6 is not existing";
        when(articleRepository.existsById(6L)).thenReturn(false);

        Exception actual = assertThrows(EntityNotFoundException.class,
                () -> articleService.update(new ArticleRequestDto(), 6L));

        assertEquals("EntityNotFoundException", actual.getClass().getSimpleName());
        assertEquals(expected, actual.getMessage());
    }

    @Test
    public void deleteById_DeleteArticleIdEqualsOne_Ok() {
        when(articleRepository.existsById(1L)).thenReturn(true);

        articleService.deleteById(1L);

        verify(articleRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void getAll_getOneArticle_Ok() {
        Article article = new Article();
        article.setId(1L);
        article.setContent("Content1");
        article.setAuthor("Author1");
        article.setTitle("Title1");
        article.setPublishingDate(LocalDate.parse("2024-03-21"));

        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setId(article.getId());
        responseDto.setContent(article.getContent());
        responseDto.setContent(article.getAuthor());
        responseDto.setTitle(article.getTitle());
        responseDto.setPublishingDate(article.getPublishingDate());

        Pageable pageable = PageRequest.of(0, 10);
        List<Article> articles = List.of(article);
        Page<Article> articlePage = new PageImpl<>(articles, pageable, articles.size());

        when(articleRepository.findAll(pageable)).thenReturn(articlePage);
        when(mapper.toDto(article)).thenReturn(responseDto);

        List<ArticleResponseDto> actual = articleService.getAll(pageable);

        assertEquals(1, actual.size());
        assertEquals(responseDto, actual.get(0));
    }
}