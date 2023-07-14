package com.restapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.restapi.entity.Product;

@Repository
public interface PaginatedItemsRepo extends PagingAndSortingRepository<Product,Integer> {

}
