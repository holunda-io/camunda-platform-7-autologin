package io.holunda.camunda.platform.login

import jakarta.annotation.PostConstruct
import jakarta.servlet.DispatcherType
import mu.KLogging
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter
import org.camunda.bpm.spring.boot.starter.util.SpringBootProcessEnginePlugin
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import java.util.*

/**
 * Auto configuration of the extension. Activates only if the 'camunda.bpm.login.enabled' is set to 'true'.
 */
@EnableConfigurationProperties(value = [CamundaLoginProperties::class])
@ConditionalOnProperty(value = ["camunda.bpm.login.enabled"], havingValue = "true", matchIfMissing = false)
class CamundaLoginAutoConfiguration {

  companion object : KLogging()

  @PostConstruct
  fun reportActivation() {
    logger.warn { "Developer auto login is enabled by property. Don't use this in production." }
  }

  /**
   * AuthenticationFilter that uses activates the [SessionBasedAuthenticationProvider] to auto login users to the cockpit.
   */
  @Bean
  fun containerBasedAuthenticationFilterRegistrationBean(camundaLoginProperties: CamundaLoginProperties): FilterRegistrationBean<ContainerBasedAuthenticationFilter> {
    return FilterRegistrationBean<ContainerBasedAuthenticationFilter>().apply {
      this.filter = ExtendedContainerBasedAuthenticationFilter(provider = SessionBasedAuthenticationProvider(userId = camundaLoginProperties.userId))
      this.initParameters = mapOf(ProcessEngineAuthenticationFilter.AUTHENTICATION_PROVIDER_PARAM to SessionBasedAuthenticationProvider.FQN)
      this.addUrlPatterns("${camundaLoginProperties.camundaContextPath}/app/*", "${camundaLoginProperties.camundaContextPath}/api/*")
      this.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST))
    }
  }

  /**
   * Create the user, if absent.
   */
  @Bean
  fun createMissingUserForAutoLoginPlugin(camundaLoginProperties: CamundaLoginProperties) = object : SpringBootProcessEnginePlugin() {
    override fun postProcessEngineBuild(processEngine: ProcessEngine) {
      // create user if the user is absent, and we configured to do so.
      if (camundaLoginProperties.createIfAbsent && processEngine.identityService.createUserQuery().userId(camundaLoginProperties.userId).count() == 0L) {
        processEngine.identityService.saveUser(camundaLoginProperties.extractUser())
        logger.info { "Created the auto-login user '${camundaLoginProperties.extractUser().id}'." }
      }
    }
  }
}
