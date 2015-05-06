package io.codechobostudy.sample.product.domain;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CategoryDomainTest {

    @Test
    public void test_get_set_domain(){

        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("TEST");

        assertThat(category.getId(), is(1L));
        assertThat(category.getCategoryName(), is("TEST"));
    }
}
