package io.codechobostudy.sample.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.core.env.Environment
import org.springframework.web.servlet.config.annotation.{ResourceHandlerRegistry, ViewControllerRegistry, WebMvcConfigurerAdapter}
import org.springframework.web.servlet.resource.{VersionResourceResolver, AppCacheManifestTransformer, ResourceUrlEncodingFilter}
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver
import org.thymeleaf.templateresolver.{TemplateResolver, ITemplateResolver}

@Configuration
@ComponentScan
class WebConfig extends WebMvcConfigurerAdapter {

  private var environment: Environment = _

  override def addViewControllers(registry: ViewControllerRegistry) {
    registry.addViewController("/").setViewName("index")
  }

  @Bean
  def resourceUrlEncodingFilter: ResourceUrlEncodingFilter = {
    new ResourceUrlEncodingFilter
  }

  override def addResourceHandlers(registry: ResourceHandlerRegistry ) {

    val isDevMode = environment.acceptsProfiles("dev")
    val pathPatterns = "/**"
    var cachePeriod: java.lang.Integer = null
    var useResourceCache = false

    var locations = "classpath:static/"

    if (environment.acceptsProfiles("dev")) {

      val staticLocation = environment.getRequiredProperty("static.path") +"/app/"

      locations = "file:" + staticLocation
      cachePeriod = 0
      useResourceCache = true
    }

    val appCacheTransformer = new AppCacheManifestTransformer
    val versionResolver = new VersionResourceResolver()
      .addFixedVersionStrategy("de4db33f", "/**/*.js", "/**/*.map")
      .addContentVersionStrategy("/**")

    registry.addResourceHandler(pathPatterns)
      .addResourceLocations(locations)
      .setCachePeriod(cachePeriod)
      .resourceChain(useResourceCache)
      .addResolver(versionResolver)
      .addTransformer(appCacheTransformer)
  }

  @Bean
  def templateResolver(resourceResolver: SpringResourceResourceResolver) : ITemplateResolver = {
    val templateResolver = new TemplateResolver
    var prefix = "classpath:view/"
    var cacheable = true

    if(environment.acceptsProfiles("dev")){
      prefix = "file:"+environment.getRequiredProperty("static.path")+"/app/"
      cacheable = false
    }

    templateResolver.setResourceResolver(resourceResolver)
    templateResolver.setPrefix(prefix)
    templateResolver.setSuffix(".html")
    templateResolver.setTemplateMode("LEGACYHTML5")
    templateResolver.setCharacterEncoding("UTF-8")
    templateResolver.setCacheable(cacheable)

    return  templateResolver
  }

  @Autowired
  def setEnvironment(environment: Environment) {
    this.environment = environment
  }
}