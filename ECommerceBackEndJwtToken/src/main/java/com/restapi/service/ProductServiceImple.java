package com.restapi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restapi.dto.CartResponse;
import com.restapi.dto.ProductResponse;
import com.restapi.entity.Category;
import com.restapi.entity.Product;
import com.restapi.entity.User;
import com.restapi.repository.CategoryRepository;
import com.restapi.repository.PaginatedItemsRepo;
import com.restapi.repository.ProductRepository;

@Service
public class ProductServiceImple implements ProductService
{

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private PaginatedItemsRepo paginatedRepo;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Override
	public Product addProduct(Product product) {

		return productRepo.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		
		
	 Product pro = 	productRepo.findById(product.getProductId()).get();
	 
	 pro.setProductName(product.getProductName());
	 pro.setProductPrice(product.getProductPrice());
	 pro.setProductDesc(product.getProductDesc());
	 pro.setUpdatedDate(product.getUpdatedDate());
	 pro.setCategoryId(product.getCategoryId());
	 
	 if(product.getProductPhoto() == null) {
		 pro.setProductPhoto(pro.getProductPhoto()); 
	 }
	 else {
		 pro.setProductPhoto(product.getProductPhoto());
	 }
		
	Product save = productRepo.save(pro);	
	return save;

	}

	@Override
	public Category addCategory(Category category) {
		
		
		return categoryRepo.save(category);
	}

	@Override
	public List<Product> getAllProduct() {
		
		return productRepo.findAll();
	}

	@Override
	public void deleteProduct(int productId) {

		productRepo.deleteById(productId);
		
	}

	@Override
	public List<Category> getAllCategory() {
	
		return categoryRepo.findAll();
	}

	@Override
	public Category getCategoryById(int categoryId) {
		
		Optional<Category> optionalCategory = categoryRepo.findById( categoryId);
		
		return optionalCategory.get();
	}

	@Override
	public List<Product> searchProduct(String query) {
		
		List<Product> searchData = productRepo.searchProduct(query);
		return searchData;
	}

	@Override
	public List<Product> getProductByCatId(int categoryId) {
		List<Product> productData = productRepo.getProductByCategoryId(categoryId);
		return productData;
	}

	@Override
	public Object getProductByUserIdAndProductId(int productId) {
		
		Authentication authentication = authenticationFacade.getAuthentication();
		User dbUser = (User) authentication.getPrincipal();
		
		ProductResponse product = productRepo.findByProductIdAndCartId(dbUser.getId(), productId);
		if(product == null)
		{
			Optional<Product> list = productRepo.findById(productId);
			
			return list;
			
		}
		return product;
		

	}

	@Override
	public Object getAllProductWithPagination(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Product> page = paginatedRepo.findAll(pageable);
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Product fetch by Pagination Successfully..!!");
		map.put("status", 200);
		map.put("data", page.getContent());
		map.put("TotalPages", page.getTotalPages());
		
		return map;
	}
	
}
