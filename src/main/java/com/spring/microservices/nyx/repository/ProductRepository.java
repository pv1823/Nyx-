package com.spring.microservices.nyx.repository;


import com.spring.microservices.nyx.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCurrency(String currency);
}
