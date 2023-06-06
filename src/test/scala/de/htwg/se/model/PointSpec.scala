package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PointSpec extends AnyWordSpec {
  "The Point trait and its instances" should {
    "be convertible to string" in {
      WhitePoint.toString should be("W")
      BlackPoint.toString should be("B")
      GreenPoint.toString should be("G")
      RedPoint.toString should be("R")
      EmptyPoint.toString should be(" ")
    }
  }
}
