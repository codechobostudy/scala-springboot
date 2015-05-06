package io.codechobostudy.sample.product.controller;

import io.codechobostudy.sample.product.domain.Category;
import io.codechobostudy.sample.Application;
import io.codechobostudy.sample.product.fixture.CategoryBuilder;
import io.codechobostudy.sample.product.repository.CategoryRepository;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CategoryControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String apiPrefixUrl = "/api/product/category";
    private static final String contentType = "application/json";

    @Value("${local.server.port}")
    private int port;

    private Category paramCategory;

    @Before
    public void setup(){
        RestAssured.port = port;

        paramCategory = CategoryBuilder.anCategory()
                .withCategoryName("Test Category")
                .withCategoryDesc("Test Category Desc")
                .build();
    }

    @Test
    public void test_category_create(){

        given()
            .contentType(contentType)
            .body(paramCategory)
        .when()
            .post(apiPrefixUrl + "/create")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString(paramCategory.getCategoryName()));
    }

    @Test
    public void test_category_update(){

        Category category = categoryRepository.save(paramCategory);
        category.setCategoryName("Update Category");

        given()
            .contentType(contentType)
            .body(category)
        .when()
            .put(apiPrefixUrl + "/update")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(containsString("Update Category"));
    }

    @Test
    public void test_category_get_all(){

        categoryRepository.save(paramCategory);

        given()
        .when()
            .get(apiPrefixUrl+"/")
        .then()
            .statusCode(HttpStatus.SC_OK);
    }
}
