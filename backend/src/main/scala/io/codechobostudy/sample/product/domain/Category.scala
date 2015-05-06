package io.codechobostudy.sample.product.domain

import javax.persistence.Entity
import scala.beans.BeanProperty


@Entity
class Category extends BaseEntity{

  @BeanProperty
  var categoryName: String = _

  @BeanProperty
  var categoryDesc: String = _
}
