package io.holunda.camunda.platform.login


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CamundaLoginPropertiesTest {

  @Test
  fun `should have correct defaults`() {
    val properties = CamundaLoginProperties()
    assertThat(properties.userId).isEqualTo("nobody")
    assertThat(properties.enabled).isEqualTo(false)
    assertThat(properties.camundaContextPath).isEqualTo("/camunda")
    assertThat(properties.createIfAbsent).isEqualTo(false)
    assertThat(properties.randomPassword).isEqualTo(true)
  }
}
