package io.holunda.camunda.platform.login

import jakarta.servlet.FilterConfig
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider
import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter

/**
 * Uses provided instance of authentication provider instead of creation of it by reflection.
 */
open class ExtendedContainerBasedAuthenticationFilter(
  private val provider: AuthenticationProvider
) : ContainerBasedAuthenticationFilter() {

  override fun init(filterConfig: FilterConfig) {
    super.authenticationProvider = provider
  }
}
