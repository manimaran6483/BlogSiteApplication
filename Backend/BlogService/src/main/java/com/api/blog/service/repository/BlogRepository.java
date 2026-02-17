package com.api.blog.service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.blog.service.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

	List<Blog> findByCategory(String category);

	List<Blog> findByAuthorName(String authorName);

	Optional<Blog> findByBlogName(String blogName);
	
	@Query("SELECT b FROM Blog b WHERE b.category = :category " + 
		       "AND b.createdAt BETWEEN :fromDate AND :toDate " +
		       "ORDER BY b.createdAt DESC")
		List<Blog> findByCategoryAndDateRange(@Param("category") String category, 
		                                       @Param("fromDate") LocalDateTime fromDate, 
		                                       @Param("toDate") LocalDateTime toDate);

		@Query("SELECT b FROM Blog b WHERE b.createdAt BETWEEN :fromDate AND :toDate " +
		       "ORDER BY b.createdAt DESC")
		List<Blog> findByDateRange(@Param("fromDate") LocalDateTime fromDate, 
		                           @Param("toDate") LocalDateTime toDate);

}
