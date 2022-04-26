# Camunda Platform 7 WebApp Auto-Login

*Auto-login feature for development*

[![Build Status](https://github.com/holunda-io/camunda-platform-7-autologin/workflows/Development%20branches/badge.svg)](https://github.com/holunda-io/camunda-platform-7-autologin/actions)
[![sponsored](https://img.shields.io/badge/sponsoredBy-Holisticon-RED.svg)](https://holisticon.de/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.holunda/camunda-platform-7-autologin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/holunda-io/camunda-platform-7-autologin)
![Compatible with: Camunda Platform 7](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%207-26d07c)

## Why should you use it?

Because otherwise, you need to type again and again "admin/admin" or "demo/demo" when developing locally and trying to log-in to local Camunda Webapp.

## Main Features

Allows auto-login by the user configured in the properties.

## Usage

Put the following dependency on your classpath:

```xml
<dependency>
  <groupId>io.holunda</groupId>
  <artifactId>camunda-platform-7-autologin</artifactId>
  <version>0.0.1</version>
</dependency>
```

Activate the auto-login in your `application.yaml`: 

```yml
camunda:
  bpm:
    login:
      enabled: true
      user-id: admin
```

That's it, any user accessing Camunda webapp is now authenticated as 'admin'. Be careful not to use this in production! 

## Additional configuration

There are some more properties, you may need:

```yml
camunda:
  bpm:
    login:
      enabled: true                     # enables the feature, disabled by default
      user-id: admin                    # user id of the user, defaults to 'nobody'
      camunda-context-path: /some-path  # path camunda webapp bound to, defaults to /camunda
      create-if-absent: true            # will create a dummy user using internal identity service, defaults to false
      random-password: false            # flag to control the password of the auto-generated-user, defaults to true. If false is selected, the password is equals to user id.             
```

## License

[![Apache License 2](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

This library is developed under Apache 2.0 License.

## Contribution

If you want to contribute to this project, feel free to do so. 
Start with [Contributing guide](http://holunda.io/camunda-bpm-correlate/snapshot/developer-guide/contribution.html).

