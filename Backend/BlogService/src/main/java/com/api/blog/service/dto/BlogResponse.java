package com.api.blog.service.dto;

import java.time.LocalDateTime;

import com.api.blog.service.model.Blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {

    private Long id;
    private String blogName;
    private String category;
    private String article;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message; 


    public static BlogResponse fromEntity(Blog blog) {
        return BlogResponse.builder()
                .id(blog.getId())
                .blogName(blog.getBlogName())
                .category(blog.getCategory())
                .article(blog.getArticle())
                .authorName(blog.getAuthorName())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .build();
    }


    public static BlogResponse fromEntity(Blog blog, String message) {
        BlogResponse response = fromEntity(blog);
        response.setMessage(message);
        return response;
    }
}