package io.codechobostudy.sample.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.core.env.Environment
import org.springframework.web.servlet.config.annotation.{ResourceHandlerRegistry, WebMvcConfigurerAdapter}

@Configuration
@ComponentScan
class WebConfig extends WebMvcConfigurerAdapter {

  private var environment: Environment = _

  override def addResourceHandlers(registry: ResourceHandlerRegistry ) {

    var pathPatterns = "/view/**"
    var locations = "classpath:view/"
    var cachePeriod = 31556926

    if (environment.acceptsProfiles("dev")) {
      val frontendSrcPath = environment.getRequiredProperty("frontend.src.path")
      pathPatterns = "/view/**"
      locations = "file:" + frontendSrcPath
      cachePeriod = 0
    }

    registry.addResourceHandler(pathPatterns).addResourceLocations(locations).setCachePeriod(cachePeriod)
  }

  @Autowired
  def setEnvironment(environment: Environment) {
    this.environment = environment
  }
}