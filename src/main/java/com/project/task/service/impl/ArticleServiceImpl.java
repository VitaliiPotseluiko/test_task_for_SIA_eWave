package com.project.task.service.impl;

import com.project.task.dto.request.ArticleRequestDto;
import com.project.task.dto.response.ArticleResponseDto;
import com.project.task.mapper.Mapper;
import com.project.task.model.Article;
import com.project.task.repository.ArticleRepository;
import com.project.task.service.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private static final int AMOUNT_OF_DAYS = 7;
    private final ArticleRepository articleRepository;
    private final Mapper<ArticleResponseDto, Article, ArticleRequestDto> mapper;

    @Override
    public ArticleResponseDto save(ArticleRequestDto requestDto) {
        return mapper.toDto(articleRepository.save(mapper.toModel(requestDto)));
    }

    @Override
    public ArticleResponseDto getById(Long id) {
        return mapper.toDto(articleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find article by id " + id)));
    }

    @Override
    public ArticleResponseDto update(ArticleRequestDto requestDto, Long id) {
        if (articleRepository.existsById(id)) {
            Article article = articleRepository.findById(id).get();
            article.setContent(requestDto.getContent());
            article.setPublishingDate(requestDto.getPublishingDate());
            article.setTitle(requestDto.getTitle());
            article.setAuthor(requestDto.getAuthor());
            return mapper.toDto(articleRepository.save(article));
        }
        throw new EntityNotFoundException("Article by id " + id + " is not existing");
    }

    @Override
    public List<ArticleResponseDto> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public long getAllForSevenLastDays() {
        return articleRepository.getArticleAmountForLastSevenDays(
                LocalDate.now().minusDays(AMOUNT_OF_DAYS + 1), LocalDate.now()
        );
    }

    @Override
    public void deleteById(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Article by id " + id + " is not existing");
        }
    }
}
