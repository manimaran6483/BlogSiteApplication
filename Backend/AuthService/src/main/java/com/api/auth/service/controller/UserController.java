/**
 * 
 */
package com.api.auth.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.auth.service.model.User;
import com.api.auth.service.service.UserServiceImpl;
import com.api.auth.service.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtUtil jwtUtil;

	static class RegisterRequest {
		public String name;
		public String email;
		public String password;
	}

	static class LoginRequest {
		public String email;
		public String password;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
		try {
			User u = userService.register(req.name, req.email, req.password);
			Map<String, Object> m = new HashMap<>();
			m.put("message", "User registered successfully");
			m.put("id", u.getId());
			return ResponseEntity.ok(m);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body("error "  + ex.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest req) {
		try {
			User u = userService.authenticate(req.email, req.password);
			// Generate a valid JWT token
			String token = jwtUtil.generateToken(u.getEmail());
			Map<String, Object> m = new HashMap<>();
			m.put("message", "Login successful");
			m.put("token", token);
			m.put("userId", u.getId());
			m.put("emailId", u.getEmail());
			m.put("name", u.getName());
			return ResponseEntity.ok(m);
		} catch (Exception ex) {
			return ResponseEntity.status(401).body("error "+ ex.getMessage());
		}
	}

}
