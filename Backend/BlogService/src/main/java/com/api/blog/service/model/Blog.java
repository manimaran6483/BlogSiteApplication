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
	// @Size(min = 20, max = 200, message = "Blog name must be at least 20
	// characters")
	@Column(nullable = false, length = 200)
	private String blogName;

	@NotBlank(message = "Category is required")
	// @Size(min = 20, max = 100, message = "Category must be at least 20
	// characters")
	@Column(nullable = false, length = 100)
	private String category;

	@NotBlank(message = "Article is required")
	// @Size(min = 1000, message = "Article must be at least 1000 characters")
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

	public Blog(Long id, String blogName, String category, String article, String authorName, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.blogName = blogName;
		this.category = category;
		this.article = article;
		this.authorName = authorName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Blog(Builder builder) {
		this.id = builder.id;
		this.blogName = builder.blogName;
		this.category = builder.category;
		this.article = builder.article;
		this.authorName = builder.authorName;
		this.createdAt = builder.createdAt;
		this.updatedAt = builder.updatedAt;
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

		public Blog build() {
			return new Blog(this);
		}
	}

}