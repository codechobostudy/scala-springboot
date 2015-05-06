package io.codechobostudy.sample.product.service;

import io.codechobostudy.sample.Application;
import io.codechobostudy.sample.product.domain.Category;
import io.codechobostudy.sample.product.fixture.CategoryBuilder;
import io.codechobostudy.sample.product.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;

    @Before
    public void setup(){

        category1 = CategoryBuilder
                .anCategory()
                .withCategoryName("Test")
                .withCategoryDesc("Test Category")
                .build();
    }

    @Test
    @Rollback
    public void test_category_save(){

        //when
        Category resultCategory = categoryService.create(category1);

        //then
        assertNotNull(resultCategory.getId());
        assertNotNull(resultCategory.getCreatedAt());
        assertThat(resultCategory.getCategoryName(), is(category1.getCategoryName()));
    }

    @Test
    @Rollback
    public void test_category_update(){

        //given
        Category saveCategory = categoryRepository.save(category1);
        saveCategory.setCategoryName("Update Category");

        //when
        Category resultCategory = categoryService.update(saveCategory);

        //then
        assertThat(resultCategory.getCategoryName(), is(saveCategory.getCategoryName()));
    }

    @Test
    public void test_get_all_category(){

        //given
        categoryRepository.save(category1);

        //when
        List<Category> categories = categoryService.getAllCategory();

        //then
        assertThat(categories.size(), is(1));
    }
}
