package io.codechobostudy.sample.product.repository;

import io.codechobostudy.sample.Application;
import io.codechobostudy.sample.product.domain.Category;
import io.codechobostudy.sample.Application;
import io.codechobostudy.sample.product.domain.Category;
import io.codechobostudy.sample.product.fixture.CategoryBuilder;
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
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    Category category1;
    Category category2;

    @Before
    public void setup(){

        category1 = CategoryBuilder
                .anCategory()
                .withCategoryName("Test")
                .withCategoryDesc("Test Category")
                .build();

        category2 = CategoryBuilder
                .anCategory()
                .withCategoryName("Test2")
                .withCategoryDesc("Test Category2")
                .build();
    }

    @Test
    @Rollback
    public void test_category_save(){

        //when
        Category resultCategory = categoryRepository.save(category1);

        //then
        assertNotNull(resultCategory.getId());
        assertNotNull(resultCategory.getCreatedAt());
        assertThat(resultCategory.getCategoryName(), is(category1.getCategoryName()));
    }

    @Test
    @Rollback
    public void test_category_update(){

        //given
        Category category = categoryRepository.save(category1);
        Long categoryId = category.getId();

        //when
        category.setCategoryName("UpdateName");
        categoryRepository.save(category1);

        //then
        assertThat(category.getId(), is(categoryId));
        assertThat(category.getCategoryName(), is("UpdateName"));
    }

    @Test
    @Rollback
    public void test_find_by_name_one(){

        //given
        categoryRepository.save(category1);

        //when
        List<Category> categories = categoryRepository.findByCategoryName(category1.getCategoryName());

        //then
        assertThat(categories.size(), is(1));
    }
}
