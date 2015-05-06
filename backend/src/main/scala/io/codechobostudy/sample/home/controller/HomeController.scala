package io.codechobostudy.sample.home.controller

import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
class HomeController {

  @RequestMapping(Array("/index"))
  def home () : String = {
    return "Hello World"
  }
}
