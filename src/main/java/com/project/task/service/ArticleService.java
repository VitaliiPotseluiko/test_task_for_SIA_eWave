package com.project.task.service;

import com.project.task.dto.request.ArticleRequestDto;
import com.project.task.dto.response.ArticleResponseDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ArticleService {
    ArticleResponseDto save(ArticleRequestDto requestDto);

    ArticleResponseDto getById(Long id);

    ArticleResponseDto update(ArticleRequestDto requestDto, Long id);

    List<ArticleResponseDto> getAll(Pageable pageable);

    long getAllForSevenLastDays();

    void deleteById(Long id);
}
