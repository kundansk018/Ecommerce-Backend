package com.restapi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restapi.config.AppConstants;
import com.restapi.entity.Role;
import com.restapi.entity.User;
import com.restapi.exception.UserException;
import com.restapi.repository.RoleRepo;
import com.restapi.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImplement implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public User signUp(User user)throws UserException {
		
		Optional<User> opt = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
	
		if(opt.isPresent())
		{
			throw new UserException("Acc already Exists..!!"+user.getEmail());
		}
		
		return userRepo.save(user);
	}

	
	@Override
	public User logIn(User user) throws UserException {
	
		Optional<User> opt1 = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		System.out.println("This Opt1 => " + opt1.get());
		
			if(opt1.isPresent())
			{
				return opt1.get();
			}
			else
			{
				throw new UserException("Acc does not exits with this mail "+user.getEmail());
			}
		
	
	}

	
	@Override
	public User getUserById(Long userId) {
		
				
		Optional<User> optionalUser =  userRepo.findById(userId);
		return optionalUser.get();
	}

	@Override
	public List<User> getAllUsers() {
		
		
		return userRepo.findAll();
	}

//	@Override
//	public User updateUser(User user) {
//		
//		User presetUser  =  userRepo.findById(user.getId()).get();
//		presetUser.setFirstName(user.getFirstName());
//		presetUser.setLastName(user.getLastName());
//		presetUser.setEmail(user.getEmail());
//		presetUser.setUsername(null);
//		
//		User updateUser = userRepo.save(presetUser);
//		
//		return updateUser;
//	}

	@Override
	public void deleteUser(Long userId) {

		userRepo.deleteById(userId);		
	}

	@Override
	public void deleteAllUser() {
		
		userRepo.deleteAll();
		
	}

	
	@Override
	public User registerUser(User userDto) {

		
		User user = modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);
		System.out.println("Serviced Called...!!");
		User newUser = this.userRepo.save(user);

		return newUser;
		
	
	}
	

		
	
}
