package com.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restapi.dto.CartResponse;
import com.restapi.dto.ProductResponse;
import com.restapi.entity.Product;
import com.restapi.entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{

	Optional<Product> findByProductId(int productId);
	
	@Query("select p from Product p where " +
	"p.productName LIKE CONCAT('%',:query,'%')"+
			"Or p.productDesc like concat('%',:query,'%')")
	List<Product> searchProduct(String query);
	
	List<Product> getProductByCategoryId(int categoryId);
	

	@Query("select new com.restapi.dto.ProductResponse(p.productId , p.categoryId , p.productName , p.productPrice , p.productDesc  , p.productPhoto , c.quantity ,c.cartId , u.id) from Cart as c INNER JOIN Product p ON c.productId = p.productId INNER JOIN User as u ON c.userId=u.id where p.productId = :productId AND u.id = :userId")
	ProductResponse findByProductIdAndCartId(Long userId , int productId);

	
}
