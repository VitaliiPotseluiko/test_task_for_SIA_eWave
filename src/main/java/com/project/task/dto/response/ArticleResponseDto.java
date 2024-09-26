package com.project.task.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDate publishingDate;
}
