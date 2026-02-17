/**
 * 
 */
package com.api.blog.service.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.service.dto.BlogRequest;
import com.api.blog.service.dto.BlogResponse;
import com.api.blog.service.services.BlogService;

import lombok.RequiredArgsConstructor;

/**
 * @author Manimaran
 *
 */

@RestController
@RequestMapping("/api/v1.0/blogsite")
@RequiredArgsConstructor
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@PostMapping("/user/blog/add")
	public ResponseEntity<BlogResponse> createBlog(
			@RequestBody BlogRequest request){
		
		log.info("Creating Blog - " + request.getBlogName());
		
		BlogResponse response = blogService.createBlog(request);
		
		return new ResponseEntity<BlogResponse>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<String> getBlog(@PathVariable String id){
		
		log.info("Deleted Blog - " + id);
		
		blogService.deleteBlog(id);
		
		return new ResponseEntity<String>("Blog Deleted Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/user/getall")
	public ResponseEntity<List<BlogResponse>> getAllBlogsForUser(@RequestParam String authorName){
		
		log.info("Get all blogs for author " + authorName);
		
		List<BlogResponse> blogs = blogService.getBlogsByAuthor(authorName);
		
		return new ResponseEntity<List<BlogResponse>>(blogs, HttpStatus.OK);
		
	}
	
	@GetMapping("/user/getallblogs")
	public ResponseEntity<List<BlogResponse>> getAllBlogs(){
		
		log.info("Get all blogs ");
		
		List<BlogResponse> blogs = blogService.getBlogs();
		
		return new ResponseEntity<List<BlogResponse>>(blogs, HttpStatus.OK);
		
	}
	
	@GetMapping("/blogs/info/{category}")
	public ResponseEntity<List<BlogResponse>> getAllBlogsForCategory(@PathVariable String category){
		
		log.info("Get Blogs for Category " + category);
		
		List<BlogResponse> blogs = blogService.getBlogsByCategory(category);
		
		return new ResponseEntity<List<BlogResponse>>(blogs, HttpStatus.OK);
	}
	
	@GetMapping("/blogs/get/{category}/{fromRange}/{toRange}")
	public ResponseEntity<List<BlogResponse>> getAllBlogsCategoryInRage(@PathVariable String category,
			@PathVariable String fromRange, @PathVariable String toRange){
		
		log.info("Get all blogs of category in given range " + category + " - " + fromRange + " - " + toRange);
		String time = "T00:00:10";
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime fromDate = LocalDateTime.parse(fromRange+time, formatter); 
		LocalDateTime toDate = LocalDateTime.parse(toRange+time, formatter);
		List<BlogResponse> blogs = blogService.getBlogsByCategoryAndDateRange(category, fromDate, toDate);
		
		return new ResponseEntity<List<BlogResponse>>(blogs, HttpStatus.OK);
		
	}
	
	@GetMapping("/blogs/getrange/{fromRange}/{toRange}")
	public ResponseEntity<List<BlogResponse>> getAllBlogsInRange(@PathVariable String fromRange, @PathVariable String toRange){
	
		log.info("Get all blogs in given range- " + fromRange + " - " + toRange);
		String time = "T00:00:10";
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime fromDate = LocalDateTime.parse(fromRange+time, formatter); 
		LocalDateTime toDate = LocalDateTime.parse(toRange+time, formatter);
		List<BlogResponse> blogs = blogService.getBlogsByDateRange(fromDate, toDate);
		
		return new ResponseEntity<List<BlogResponse>>(blogs, HttpStatus.OK);
	}
	
	@PutMapping("/user/blogs/update/{id}")
	public ResponseEntity<BlogResponse> updateBlogById(@PathVariable String id,
			@RequestBody BlogRequest request){
		
		log.info("updating blog with id " + id);
		
		BlogResponse blog = blogService.updateBlog(id, request);
		
		return new ResponseEntity<BlogResponse>(blog, HttpStatus.OK); 
		
	}
}
