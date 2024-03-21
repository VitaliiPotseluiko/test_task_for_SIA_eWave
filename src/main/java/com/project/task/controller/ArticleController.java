package com.project.task.controller;

import com.project.task.dto.request.ArticleRequestDto;
import com.project.task.dto.response.ArticleResponseDto;
import com.project.task.service.ArticleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ArticleResponseDto create(@RequestBody @Valid ArticleRequestDto requestDto) {
        return articleService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ArticleResponseDto update(@RequestBody @Valid ArticleRequestDto requestDto,
                                     @PathVariable Long id) {
        return articleService.update(requestDto, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articleService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ArticleResponseDto get(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<ArticleResponseDto> getAll(Pageable pageable) {
        return articleService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/statistic")
    public long getAllByStatistic() {
        return articleService.getAllForSevenLastDays();
    }
}
