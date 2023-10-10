package io.holunda.camunda.platform.login

import org.camunda.bpm.engine.impl.persistence.entity.UserEntity
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.util.*

/**
 * Configuration properties to be supplied via properties or yaml file.
 */
@ConfigurationProperties(prefix = "camunda.bpm.login")
data class CamundaLoginProperties(
  /**
   * Flag activating the auto-login feature. Defaults to false.
   */
  val enabled: Boolean = false,
  /**
   * User id of the user that logs in . Defaults to 'nobody'.
   */
  val userId: String = "nobody",
  /**
   * Context camunda Web app is bound to.
   */
  val camundaContextPath: String = "/camunda",
  /**
   * Flag indicating that the user should be created, if it doesn't exist. Defaults to 'false'.
   */
  val createIfAbsent: Boolean = false,
  /**
   * Generate random password (to prevent logins).
   */
  val randomPassword: Boolean = true
) {
  /**
   * Extracts auto-generated local user.
   */
  fun extractUser() = UserEntity().apply {
    id = userId
    firstName = userId
    lastName = "auto-generated"
    password = if (randomPassword) {
      UUID.randomUUID().toString()
    } else {
      userId
    }
    email = "${userId}@local.machine"
  }

}
