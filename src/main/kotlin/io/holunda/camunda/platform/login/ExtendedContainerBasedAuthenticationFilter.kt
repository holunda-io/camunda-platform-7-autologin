package io.holunda.camunda.platform.login

import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationService
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter
import javax.servlet.FilterConfig

/**
 * Uses provided instance of authentication provider instead of creation of it by reflection.
 */
open class ExtendedContainerBasedAuthenticationFilter(
  private val provider: AuthenticationProvider
) : ContainerBasedAuthenticationFilter() {

  override fun init(filterConfig: FilterConfig) {
    super.authenticationProvider = provider
    super.userAuthentications = AuthenticationService()
  }
}
