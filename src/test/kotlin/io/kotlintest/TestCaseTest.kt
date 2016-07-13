package io.kotlintest

import io.kotlintest.specs.StringSpec

// The tests in this class can be run in isolation, but they infer with other tests due to the
// system properties set.
class TestCaseTest : StringSpec() {

  object TagA : Tag("tagA")
  object TagB : Tag("tagB")

  init {
    val testTaggedA: TestCase = "should be tagged with tagA" { }
    testTaggedA.config(tag = TagA)

    val untaggedTest = "should be untagged" { }

    val testTaggedB = "should be tagged with tagB" { }
    testTaggedB.config(tag = TagB)

    "only tests without excluded tags should be active" {
      System.setProperty("excludeTags", "tagB") // TODO replace with mock
      testTaggedA.isActive shouldBe true
      untaggedTest.isActive shouldBe true
      testTaggedB.isActive shouldBe false
    }.config(ignored = true)

    "only tests with included tags should be active" {
      System.setProperty("includeTags", "tagA") // TODO replace with mock
      testTaggedA.isActive shouldBe true
      untaggedTest.isActive shouldBe false
      testTaggedB.isActive shouldBe false
    }.config(tag = TagA, ignored = true)
  }
}