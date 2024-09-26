package com.project.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ArticleRequestDto {
    @NotBlank(message = "must not be blank")
    @Length(max = 100)
    private String title;
    @NotBlank(message = "must not be blank")
    private String author;
    @NotBlank(message = "must not be blank")
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate publishingDate;
}
