package io.codechobostudy.sample.product.repository

import io.codechobostudy.sample.product.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.lang.Long
import java.util.List

trait CategoryRepository extends JpaRepository[Category, Long]{

  def findByCategoryName(categoryName: String) : List[Category]

}
