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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBlogName() {
		return blogName;
	}


	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getArticle() {
		return article;
	}


	public void setArticle(String article) {
		this.article = article;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public BlogResponse(Long id, String blogName, String category, String article, String authorName,
			LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
		super();
		this.id = id;
		this.blogName = blogName;
		this.category = category;
		this.article = article;
		this.authorName = authorName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.message = message;
	}


	public BlogResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	private BlogResponse(Builder builder) {
        this.id = builder.id;
        this.blogName = builder.blogName;
        this.category = builder.category;
        this.article = builder.article;
        this.authorName = builder.authorName;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.message = builder.message;
    }

    // Static method to get Builder instance
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private Long id;
        private String blogName;
        private String category;
        private String article;
        private String authorName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String message;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder blogName(String blogName) {
            this.blogName = blogName;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder article(String article) {
            this.article = article;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public BlogResponse build() {
            return new BlogResponse(this);
        }
    }
    
}