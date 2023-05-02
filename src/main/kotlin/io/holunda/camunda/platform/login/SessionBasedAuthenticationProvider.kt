package io.holunda.camunda.platform.login

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.rest.security.auth.AuthenticationProvider
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationUtil
import org.camunda.bpm.webapp.impl.security.auth.Authentications
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
