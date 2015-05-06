package io.codechobostudy.sample.product.service

import io.codechobostudy.sample.product.domain.Category
import io.codechobostudy.sample.product.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CategoryService {

  @Autowired
  private var categoryRepository: CategoryRepository = _

  def create(category: Category): Category = {
    categoryRepository.save(category)
  }

  def update(category: Category): Category = {
    val updateCategory = categoryRepository.findOne(category.getId)
    updateCategory.setCategoryName(category.categoryName)
    updateCategory.setCategoryDesc(category.categoryDesc)

    categoryRepository.save(updateCategory)
  }

  def getAllCategory: java.util.List[Category] = {
    categoryRepository.findAll()
  }
}
