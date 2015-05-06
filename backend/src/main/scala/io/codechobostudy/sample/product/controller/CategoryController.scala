package io.codechobostudy.sample.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.codechobostudy.sample.product.domain.Category
import io.codechobostudy.sample.product.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMethod, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(Array("/api/product/category"))
class CategoryController {

  @Autowired
  private var objectMapper: ObjectMapper = _

  @Autowired
  private var categoryService: CategoryService = _

  @RequestMapping(value = Array( "/create"),  method = Array(RequestMethod.POST))
  def create(@RequestBody category: Category): String = {
    objectMapper.writeValueAsString(categoryService.create(category))
  }

  @RequestMapping(value = Array("/update"), method = Array(RequestMethod.PUT))
  def update(@RequestBody category: Category): String = {
    val updateCategory = categoryService.update(category)
    objectMapper.writeValueAsString(updateCategory)
  }

  @RequestMapping(Array("/"))
  def getAllCategory: String = {
    objectMapper.writeValueAsString(categoryService.getAllCategory)
  }
}
