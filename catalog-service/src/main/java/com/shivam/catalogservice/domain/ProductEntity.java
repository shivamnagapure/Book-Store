package com.shivam.catalogservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @SequenceGenerator(
            name = "product_id_generator",
            sequenceName = "product_id_seq") // Only sequenceName must match your database sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Product Code is Required")
    private String code;

    /*
       @NOTNULL
       null -> not allowed
       "" -> allowed
       " " -> allowed
    */
    @Column(nullable = false)
    @NotEmpty(message = "Product Name is Required")
    private String name;

    private String description;

    private String imageUrl;

    /* @NOTEMPTY
       ❌ null → NOT allowed
       ❌ "" → NOT allowed
       ✅ " " → allowed
    */
    @NotNull(message = "Product Price is Required") // NOT null , NOT empty (length > 0)
    @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal price;
}
