plugins {
  kotlin("jvm")
  `java-library`
}

dependencies {
  compileOnly(Dependencies.detektApi)

  testImplementation(Dependencies.assertj)
  testImplementation(Dependencies.detektTest)
  testImplementation(Dependencies.junitApi)
  testImplementation(Dependencies.junitParams)
  testImplementation(Dependencies.kotlinTest)
  testImplementation(Dependencies.kotlinTest)

  testRuntimeOnly(Dependencies.junitEngine)
}
