package com.project.task.mapper.impl;

import com.project.task.dto.request.ArticleRequestDto;
import com.project.task.dto.response.ArticleResponseDto;
import com.project.task.mapper.Mapper;
import com.project.task.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper implements Mapper<ArticleResponseDto, Article, ArticleRequestDto> {
    @Override
    public ArticleResponseDto toDto(Article model) {
        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setId(model.getId());
        responseDto.setTitle(model.getTitle());
        responseDto.setAuthor(model.getAuthor());
        responseDto.setContent(model.getContent());
        responseDto.setPublishingDate(model.getPublishingDate());
        return responseDto;
    }

    @Override
    public Article toModel(ArticleRequestDto requestDto) {
        Article article = new Article();
        article.setAuthor(requestDto.getAuthor());
        article.setTitle(requestDto.getTitle());
        article.setContent(requestDto.getContent());
        article.setPublishingDate(requestDto.getPublishingDate());
        return article;
    }
}
