package com.example.java_training_capstone.repository;

import com.example.java_training_capstone.entity.Product;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import com.google.cloud.spring.data.spanner.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Product repository.
 */
@Repository
public interface ProductRepository extends SpannerRepository<Product, String> {

    /**
     * Find by is active true list.
     *
     * @return the list
     */
    @Query("SELECT * FROM product WHERE is_active = true")
    List<Product> findByIsActiveTrue();

    /**
     * Find by id and is active true optional.
     *
     * @param id the id
     * @return the optional
     */
    @Query("SELECT * FROM product WHERE id = @id AND is_active = true")
    Optional<Product> findByIdAndIsActiveTrue(@Param("id") String id);

    /**
     * Find by category and is active true list.
     *
     * @param category the category
     * @return the list
     */
    @Query("SELECT * FROM product WHERE LOWER(category) = LOWER(@category) AND is_active = true")
    List<Product> findByCategoryAndIsActiveTrue(@Param("category") String category);

    /**
     * Find by name and is active true optional.
     *
     * @param name the name
     * @return the optional
     */
    @Query("SELECT * FROM product WHERE LOWER(name) = LOWER(@name) AND is_active = true")
    Optional<Product> findByNameAndIsActiveTrue(@Param("name") String name);

}
