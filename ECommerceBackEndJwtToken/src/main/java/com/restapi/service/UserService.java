package com.restapi.service;

import java.util.List;


import com.restapi.entity.User;
import com.restapi.exception.UserException;


public interface UserService {
	
	User signUp(User user) throws UserException;
//
	User logIn(User login) throws UserException;

	User getUserById(Long userId);

    List<User> getAllUsers();

//    User updateUser(User user);

    void deleteUser(Long userId);

    void deleteAllUser();
    
    User registerUser(User user) ;
       

	
    
}
