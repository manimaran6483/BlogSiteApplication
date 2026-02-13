package com.api.blog.service.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Blog name is required")
//    @Size(min = 20, max = 200, message = "Blog name must be at least 20 characters")
    @Column(nullable = false, length = 200)
    private String blogName;


    @NotBlank(message = "Category is required")
//    @Size(min = 20, max = 100, message = "Category must be at least 20 characters")
    @Column(nullable = false, length = 100)
    private String category;


    @NotBlank(message = "Article is required")
//    @Size(min = 1000, message = "Article must be at least 1000 characters")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String article;


    @NotBlank(message = "Author name is required")
    @Size(min = 3, max = 50, message = "Author name must be between 3 and 50 characters")
    @Column(nullable = false, length = 50)
    private String authorName;


    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    

}