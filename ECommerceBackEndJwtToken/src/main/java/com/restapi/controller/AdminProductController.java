package com.restapi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.RersponseHandler.ResponseHandler;
import com.restapi.dto.CartResponse;
import com.restapi.entity.Cart;
import com.restapi.entity.Category;
import com.restapi.entity.EmailDetails;
import com.restapi.entity.Product;
import com.restapi.exception.UserException;
import com.restapi.service.CartServiceImple;
import com.restapi.service.EmailService;
import com.restapi.service.ProductServiceImple;
import com.restapi.utility.FileUpload;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(originPatterns = "*")
public class AdminProductController {

	@Autowired
	private ProductServiceImple productService;

	@Autowired
	private CartServiceImple cartService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/product/add")
	public ResponseEntity<Object> addProduct(@ModelAttribute Product product,
			@RequestParam("file") MultipartFile multipartFile) throws UserException, IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		String fileCode = FileUpload.saveFile(fileName, multipartFile);

		product.setProductPhoto(fileCode);

		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

		product.setActive(true);
		product.setCreatedDate(timeStamp);
		product.setUpdatedDate(timeStamp);

		Product newProduct = productService.addProduct(product);
		return ResponseHandler.generateResponse("Product Add successfully", HttpStatus.CREATED, newProduct);

	}

	@PostMapping("/product/update")
	public ResponseEntity<Object> updatePrduct(
			@RequestParam(value = "file", required = false) MultipartFile multipartFile,
			@ModelAttribute Product product) throws IOException {

		if (multipartFile != null) {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			String fileCode = FileUpload.saveFile(fileName, multipartFile);

			product.setProductPhoto(fileCode);
		}

		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		product.setActive(true);

		product.setUpdatedDate(timeStamp);
		Product pro = productService.updateProduct(product);

		return ResponseHandler.generateResponse("Product Updated successfully", HttpStatus.OK, pro);
	}

	@GetMapping("/product/getall")
	public ResponseEntity<Object> getAllUsers() {
		List<Product> product = productService.getAllProduct();

		return ResponseHandler.generateResponse("All Products Here..!!", HttpStatus.ACCEPTED, product);
	}

	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int productId) {
		productService.deleteProduct(productId);

		return ResponseHandler.generateResponse("Product Deleted successfully", HttpStatus.ACCEPTED, "DELETE");
	}

	@PostMapping("/category/add")
	public ResponseEntity<Object> addCategory(@ModelAttribute Category category) throws UserException {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

		category.setActive(true);
		category.setCreatedDate(timeStamp);
		category.setUpdatedDate(timeStamp);

		Category newCategory = productService.addCategory(category);
		return ResponseHandler.generateResponse("Product Add successfully", HttpStatus.CREATED, newCategory);

	}

	@GetMapping("/category/getall")
	public ResponseEntity<Object> getAllCategory() {
		List<Category> category = productService.getAllCategory();

		return ResponseHandler.generateResponse("All Category Here..!!", HttpStatus.ACCEPTED, category);
	}

	@GetMapping("/category/getbyid/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable("id") int categoryId) {
		Category user = productService.getCategoryById(categoryId);

		return ResponseHandler.generateResponse("Category By I'd", HttpStatus.OK, user);
	}

	@GetMapping("/product/getproductbycatId/{id}")
	public ResponseEntity<Object> getProductByCatId(@PathVariable("id") int categoryId) {
		List<Product> productData = productService.getProductByCatId(categoryId);

		return ResponseHandler.generateResponse("Product By Category I'd", HttpStatus.OK, productData);
	}

	@GetMapping("/product/search")
	public ResponseEntity<Object> searchProduct(@RequestParam("query") String query) {
		List<Product> productData = productService.searchProduct(query);

		return ResponseHandler.generateResponse("Searched Result...", HttpStatus.OK, productData);
	}

	@GetMapping("/product/getProductDetails")
	public ResponseEntity<Object> getProductByUserndProductId(@RequestParam("productId") int productId) {

		Object product = productService.getProductByUserIdAndProductId(productId);
		return ResponseHandler.generateResponse("Data...", HttpStatus.OK, product);
	}

	@GetMapping("/product/getProductWithPagination/{pageNo}/{pageSize}")
	public ResponseEntity<Object> getAllProductWithPagination(@PathVariable("pageNo") int pageNo,
			@PathVariable("pageSize") int pageSize) {

		Object products = productService.getAllProductWithPagination(pageNo, pageSize);
		System.out.println("Product is .." + products);
		return ResponseHandler.generateResponse("Pagination Data..!!", HttpStatus.OK, products);
	}

	@PostMapping("/cart/add")
	public ResponseEntity<Object> addToCart(@RequestBody Cart cart) throws UserException {
		
		System.out.println("Ctrl Called...!!Add To CART");

		Cart saveCart = cartService.addToCart(cart);

		return ResponseHandler.generateResponse("Added..!!", HttpStatus.CREATED, saveCart);

	}

//	@GetMapping("/cart/get/{id}")
//	public ResponseEntity<Object> getCartItems(@PathVariable("id") int userId) {
//		List<CartResponse> cart = cartService.getCartItems(userId);
//		System.out.println("ctyrl called..");
//		return ResponseHandler.generateResponse("CartItems..!!", HttpStatus.ACCEPTED, cart);
//	}
	
	@GetMapping("/cart/getAllCartItems")
	public ResponseEntity<Object> getCartItems() {
		List<CartResponse> cart = cartService.getCartItems();
		System.out.println("ctyrl called..");
		return ResponseHandler.generateResponse("CartItems..!!", HttpStatus.ACCEPTED, cart);
	}
	
	


	@DeleteMapping("/cart/delete/{id}")
	public ResponseEntity<Object> deleteProductCartItem(@PathVariable("id") int cartId) {
		cartService.deleteCartItem(cartId);

		return ResponseHandler.generateResponse("Product Deleted From Cart successfully", HttpStatus.ACCEPTED,
				"DELETE");
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {
		String status = emailService.sendSimpleMail(details);

		return status;
	}

	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}

}
