package com.api.blog.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequest {

    @NotBlank(message = "Blog name is required")
//    @Size(min = 20, max = 200, message = "Blog name must be at least 20 characters")
    private String blogName;

    @NotBlank(message = "Category is required")
//    @Size(min = 20, max = 100, message = "Category must be at least 20 characters")
    private String category;

    @NotBlank(message = "Article is required")
//    @Size(min = 1000, message = "Article must be at least 1000 characters")
    private String article;

    @NotBlank(message = "Author name is required")
    @Size(min = 3, max = 50, message = "Author name must be between 3 and 50 characters")
    private String authorName;
}