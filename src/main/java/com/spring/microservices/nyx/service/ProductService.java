package com.spring.microservices.nyx.service;

import com.spring.microservices.nyx.config.AppPropertiesBind;
import com.spring.microservices.nyx.entity.ProductEntity;
import com.spring.microservices.nyx.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final AppPropertiesBind appPropertiesBind;

    //Injecting the ProductRepository, AppPropertiesBind beans using constructor.
    public ProductService(ProductRepository productRepository, AppPropertiesBind appPropertiesBind) {
        this.productRepository = productRepository;
        this.appPropertiesBind = appPropertiesBind;
        }

    //Method for Creating product.
    public ProductEntity createProduct(ProductEntity product) {

        //Setting the default currency to the product.
        if(product.getCurrency() == null || product.getCurrency().isBlank()) {
            product.setCurrency(appPropertiesBind.getDefaultCurrency());

        }
        //Saving the Product
        ProductEntity saveProduct = productRepository.save(product);

        //Setting default Feature Flags for publishing the event.
        Boolean publishEvents = appPropertiesBind.getFeatureFlags().getOrDefault("Publish-Product-Events", false);

        //Creating an event on product creation.
        if(Boolean.TRUE.equals(publishEvents)) {
            log.info("Event for creating product, Id = {}, Name = {}", saveProduct.getId(), saveProduct.getName());
        }
        return saveProduct;
    }

    //Methods for getting products accordingly
    @Transactional(readOnly = true)
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
    @Transactional(readOnly = true)
    public ProductEntity getById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("Product with the id: " + id + "is not found."));
    }

    //Updating the product.
    public ProductEntity updateProduct(Long id, ProductEntity updatedProduct) {
        ProductEntity existing = getById(id);

        if(updatedProduct.getName() != null) {
            existing.setName(updatedProduct.getName());
        } else if (updatedProduct.getDescription() != null) {
            existing.setDescription(updatedProduct.getDescription());
        } else if(updatedProduct.getPrice() == null) {
            existing.setPrice(updatedProduct.getPrice());
        } else if (updatedProduct.getCurrency() != null) {
            existing.setCurrency(updatedProduct.getCurrency());
        }

        return productRepository.save(existing);
    }

    //Deleting the product.
    public void deletingProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with the Id:" + id + "is not found");
        }
        productRepository.deleteById(id);
    }

}



