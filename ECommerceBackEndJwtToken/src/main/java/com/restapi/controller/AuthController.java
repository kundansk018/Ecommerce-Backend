package com.restapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dto.JwtAuthRequest;
import com.restapi.dto.JwtAuthResponse;
import com.restapi.entity.User;
import com.restapi.exception.UserException;
import com.restapi.security.JwtTokenHelper;
import com.restapi.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
@CrossOrigin(originPatterns = "*")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
	
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, User.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	
	}

	private void authenticate(String username, String password) throws UserException, Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		
		try {
			
		authenticationManager.authenticate(authenticationToken);
		

		} catch (BadCredentialsException e) {
			System.err.println(e);
			System.out.println("Invalid Detials !!");
			throw new UserException("Invalid username or password !!");
		}

	}

	@PostMapping("/auth/register")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
		System.out.println("Ctrl Called...!!");
		user.setUsername(user.getEmail());
		
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}
	
	
}
