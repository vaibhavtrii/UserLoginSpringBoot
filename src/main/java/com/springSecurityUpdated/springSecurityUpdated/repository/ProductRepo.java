package com.springSecurityUpdated.springSecurityUpdated.repository;

import com.springSecurityUpdated.springSecurityUpdated.model.product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<product, Integer> {

}
