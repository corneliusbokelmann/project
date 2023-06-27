package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.modelcomponent.modelImpl._

class PointSpec extends AnyWordSpec {
  "The Point enum and its instances" should {
    "be convertible to string" in {
      Point.WhitePoint.symbol should be("W")
      Point.GreenPoint.symbol should be("G")
      Point.RedPoint.symbol should be("R")
      Point.BluePoint.symbol should be("B")
      Point.YellowPoint.symbol should be("Y")
      Point.OrangePoint.symbol should be("O")
      Point.PinkPoint.symbol should be("P")
      Point.MagentaPoint.symbol should be("M")
      Point.CyanPoint.symbol should be("C")
      Point.EmptyPoint.symbol should be(" ")
    }
  }
}
