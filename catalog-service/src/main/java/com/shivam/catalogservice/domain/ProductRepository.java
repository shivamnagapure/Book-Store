package com.shivam.catalogservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
interface ProductRepository extends JpaRepository<ProductEntity , Long> {
    Optional<ProductEntity> findByCode(String code);
}
