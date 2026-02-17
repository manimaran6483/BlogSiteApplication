/**
 * 
 */
package com.api.blog.service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.blog.service.dto.BlogRequest;
import com.api.blog.service.dto.BlogResponse;
import com.api.blog.service.exception.BlogNotFoundException;
import com.api.blog.service.model.Blog;
import com.api.blog.service.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author Administrator
 *
 */

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    
    private static final Logger log = LoggerFactory.getLogger(BlogService.class);
    
    

    public BlogService(BlogRepository blogRepository) {
		super();
		this.blogRepository = blogRepository;
	}

	@Transactional
    public BlogResponse createBlog(BlogRequest request) {
        log.info("Creating new blog: {}", request.getBlogName());

        Blog blog = Blog.builder()
                .blogName(request.getBlogName())
                .category(request.getCategory())
                .article(request.getArticle())
                .authorName(request.getAuthorName())
                .createdAt(LocalDateTime.now())
                .build();

        Blog savedBlog = blogRepository.save(blog);

        log.info("Blog created successfully with ID: {}", savedBlog.getId());

        return BlogResponse.fromEntity(savedBlog, "Blog created successfully!");
    }

    public List<BlogResponse> getBlogsByCategory(String category) {
        log.info("Fetching blogs for category: {}", category);

        List<Blog> blogs = blogRepository.findByCategory(category);

        if (blogs.isEmpty()) {
            log.warn("No blogs found for category: {}", category);
        }

        return blogs.stream()
                .map(BlogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<BlogResponse> getBlogsByAuthor(String authorName) {
        log.info("Fetching blogs for author: {}", authorName);

        List<Blog> blogs = blogRepository.findByAuthorName(authorName);

        if (blogs.isEmpty()) {
            log.info("Author {} has no blogs yet", authorName);
        }

        return blogs.stream()
                .map(BlogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBlog(String id) {
        log.info("Attempting to delete blog: {}", id);

        // Find the blog
        Blog blog = blogRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new BlogNotFoundException(
                        "Blog not found with name: " + id
                ));

        // Delete it
        blogRepository.delete(blog);

        log.info("Blog deleted successfully: {}", id);
    }

    public List<BlogResponse> getBlogsByCategoryAndDateRange(
            String category,
            LocalDateTime fromDate,
            LocalDateTime toDate) {
        
        log.info("Fetching blogs - Category: {}, From: {}, To: {}", 
                category, fromDate, toDate);

        List<Blog> blogs = blogRepository.findByCategoryAndDateRange(
                category, fromDate, toDate
        );

        if (blogs.isEmpty()) {
            log.warn("No blogs found for given criteria");
        }

        return blogs.stream()
                .map(BlogResponse::fromEntity)
                .collect(Collectors.toList());
    }

	public BlogResponse updateBlog(String id, BlogRequest request) {

		Blog blog = blogRepository.findById(Long.valueOf(id))
				 .orElseThrow(() -> new BlogNotFoundException(
	                        "Blog not found: " + id
	                ));
		
		Blog newBlog = Blog.builder()
				.id(Long.valueOf(id))
                .blogName(request.getBlogName())
                .category(request.getCategory())
                .article(request.getArticle())
                .authorName(request.getAuthorName())
                .createdAt(blog.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
		
        Blog savedBlog = blogRepository.save(newBlog);

        log.info("Blog updated successfully with ID: {}", savedBlog.getId());

        return BlogResponse.fromEntity(savedBlog, "Blog updated successfully!");
        
	}

	public List<BlogResponse> getBlogs() {
		List<Blog> blogList = blogRepository.findAll();
		return blogList.stream()
                .map(BlogResponse::fromEntity)
                .collect(Collectors.toList());
	}

	public List<BlogResponse> getBlogsByDateRange(LocalDateTime fromDate, LocalDateTime toDate) {

		log.info("Fetching blogs -  From: {}, To: {}", 
               fromDate, toDate);

        List<Blog> blogs = blogRepository.findByDateRange(fromDate, toDate
        );

        if (blogs.isEmpty()) {
            log.warn("No blogs found for given criteria");
        }

        return blogs.stream()
                .map(BlogResponse::fromEntity)
                .collect(Collectors.toList());
	}
}
