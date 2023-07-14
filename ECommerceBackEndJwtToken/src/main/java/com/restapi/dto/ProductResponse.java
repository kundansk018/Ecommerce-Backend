package com.restapi.dto;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {

	
   
	private int id;
        
    private int categoryId;
    
    private String productName;
        
    private int productPrice;
    
    private String productDesc;
     
    @Column(nullable = false)
    private String productPhoto;
    
    private int quantity;
    
    private int cartId;
    
    private long userId;
    
    public ProductResponse() {
		// TODO Auto-generated constructor stub
	}
    
    
    
    

	public ProductResponse(int id, int categoryId, String productName, int productPrice, String productDesc,
			String productPhoto, int quantity, int cartId, long userId) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDesc = productDesc;
		this.productPhoto = productPhoto;
		this.quantity = quantity;
		this.cartId = cartId;
		this.userId = userId;
	}





	public ProductResponse(int categoryId, String productName, int productPrice, String productDesc,
			String productPhoto, int cartId, long userId) {
		this.categoryId = categoryId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDesc = productDesc;
		this.productPhoto = productPhoto;		
		this.cartId = cartId;
		this.userId = userId;
	}





	public ProductResponse(int categoryId, String productName, int productPrice, String productDesc,
			String productPhoto, int quantity, int cartId, long userId) {
		super();
		this.categoryId = categoryId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDesc = productDesc;
		this.productPhoto = productPhoto;
		this.quantity = quantity;
		this.cartId = cartId;
		this.userId = userId;
	}





	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductPhoto() {
		return productPhoto;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
    
    
}
