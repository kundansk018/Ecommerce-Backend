package com.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restapi.dto.CartResponse;
import com.restapi.entity.Cart;
import com.restapi.entity.User;
import com.restapi.repository.CartRepository;

@Service
public class CartServiceImple implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

//Without Token..	
//	public Cart addToCart(Cart cart) {
//		System.out.println("Cart prodId =>"+ cart.getProductId());
//		return cartRepo.save(cart);
//	
//	}

	// With Token..
	public Cart addToCart(Cart cart) {

//		List<Cart> opt = cartRepo.findByProductId();
//		
//		for(Cart opt1:opt) {
//			if(opt1.getQuantity()==cart.getQuantity()) {
//				cart.setQuantity(cart.getQuantity());
//			}
//		}
		
		System.out.println(" fun inside service called");
		Authentication authentication = authenticationFacade.getAuthentication();
		User dbUser = (User) authentication.getPrincipal();
		System.out.println("current user id is " + dbUser.getId()); 
		cart.setUserId(dbUser.getId());
		System.out.println("service called. after object created");

		return cartRepo.save(cart);

	}

//	
//	public Cart addToCart(Cart cart) {
//		
//		Optional<Cart> opt = cartRepo.findByProductId();
//		if(opt.isPresent())
//		{
//			cart.setQuantity(cart.getQuantity());
//		}
//		
//		System.out.println("Cart prodId =>"+ opt.get());
//		return cartRepo.save(cart);
//	
//	}

//	public List<CartResponse> getCartItems(int userId) {
//		
//
//		 List<CartResponse> cartList  =  cartRepo.getCartItems(userId);
//		
//		return cartList;
//	}

	public List<CartResponse> getCartItems() {

		Authentication authentication = authenticationFacade.getAuthentication();
		User dbUser = (User) authentication.getPrincipal();

		System.out.println("user id" + dbUser.getId());
		List<CartResponse> cartList = cartRepo.getCartItems(dbUser.getId());

		return cartList;
	}

	@Override
	public void deleteCartItem(int cartId) {

		cartRepo.deleteById(cartId);

	}

}
