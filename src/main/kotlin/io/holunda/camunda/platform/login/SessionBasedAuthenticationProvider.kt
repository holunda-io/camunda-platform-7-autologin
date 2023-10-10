package io.holunda.camunda.platform.login

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationUtil

/**
 * Authentication provider retrieving the user id stored in the session. If none is found, falls back to the one provided on construction.
 */
class SessionBasedAuthenticationProvider(
  private val userId: String
) : AuthenticationProvider {

  companion object {
    val FQN: String = SessionBasedAuthenticationProvider::class.java.canonicalName
  }

  override fun extractAuthenticatedUser(request: HttpServletRequest, processEngine: ProcessEngine): AuthenticationResult {
    val authentications = AuthenticationUtil.getAuthsFromSession(request.session)
    val userId = if (authentications != null && authentications.hasAuthenticationForProcessEngine(processEngine.name)) {
      authentications.getAuthenticationForProcessEngine(processEngine.name).name
    } else {
      userId
    }

    return AuthenticationResult(userId, true)
  }

  override fun augmentResponseByAuthenticationChallenge(response: HttpServletResponse, processEngine: ProcessEngine) = Unit
}
