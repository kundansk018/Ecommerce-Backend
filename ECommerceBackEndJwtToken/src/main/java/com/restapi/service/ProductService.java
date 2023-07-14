package com.restapi.service;

import java.util.List;

import com.restapi.dto.ProductResponse;
import com.restapi.entity.Category;
import com.restapi.entity.Product;
import com.restapi.entity.User;

public interface ProductService {
	
	Product addProduct(Product product);
	
	Product updateProduct(Product product);
	
	List<Product> getAllProduct();
	
	Category addCategory(Category category);
	
	void deleteProduct(int productId);
	
	List<Product> getProductByCatId(int categoryId);
	
	List<Category> getAllCategory();
	
	Category getCategoryById(int categoryId);
	
	List<Product> searchProduct(String query);
	
//	List<ProductResponse> getProducts(long userId , int cartId);
	
	Object getProductByUserIdAndProductId(int productId);
	
	Object getAllProductWithPagination(int pageNo , int pageSize);
}
