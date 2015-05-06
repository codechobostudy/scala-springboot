package io.codechobostudy.sample.product.fixture;

import io.codechobostudy.sample.product.domain.Category;
import io.codechobostudy.sample.product.domain.Category;

public class CategoryBuilder {

    private String categoryName;
    private String categoryDesc;

    public static CategoryBuilder anCategory(){
        return new CategoryBuilder();
    }

    public CategoryBuilder withCategoryName(String categoryName){
        this.categoryName = categoryName;
        return this;
    }

    public CategoryBuilder withCategoryDesc(String categoryDesc){
        this.categoryDesc = categoryDesc;
        return this;
    }

    public Category build(){
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        category.setCategoryDesc(this.categoryDesc);
        return category;
    }
}
