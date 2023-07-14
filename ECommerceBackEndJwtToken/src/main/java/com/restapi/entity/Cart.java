// this entity only use for testinh purpose
package com.restapi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Cart {

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private int cartId ;
		
		private Long userId;
				
		private int quantity;
		
		private int productId ;
	
	
		public int getCartId() {
			return cartId;
		}


		public void setCartId(int cartId) {
			this.cartId = cartId;
		}



		public Long getUserId() {
			return userId;
		}


		public void setUserId(Long userId) {
			this.userId = userId;
		}


		public int getQuantity() {
			return quantity;
		}


		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}


		public int getProductId() {
			return productId;
		}


		public void setProductId(int productId) {
			this.productId = productId;
		}
		
		
		
	    
	
	
	
}
