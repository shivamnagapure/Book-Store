package com.shivam.catalogservice.web.controllers;

import com.shivam.catalogservice.AbstractIT;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test_data.sql")
class ProductControllerTest extends AbstractIT {

    //    @Test
    //    void shouldReturnProducts(){
    //        given().contentType(ContentType.JSON)
    //                .when()
    //                .get("/api/products")
    //                .then()
    //                .statusCode(200)
    //                .body("data" , hasSize(10))
    //                .body("totalElements" , is(15))
    //                .body( "pageNumber" , is(1))
    //                .body("totalPages" , is(2))
    //                .body("isFirst" , is(true))
    //                .body("isLast" , is(false))
    //                .body("hasNext" , is(true))
    //                .body("hasPrevious" , is(false));
    //
    //    }
}
