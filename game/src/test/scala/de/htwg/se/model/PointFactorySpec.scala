package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PointFactorySpec extends AnyWordSpec {
  "The PointFactory object" should {
    "create points correctly" in {
      PointFactory.createPoint("W") should be(WhitePoint)
      PointFactory.createPoint("B") should be(BlackPoint)
      PointFactory.createPoint("G") should be(GreenPoint)
      PointFactory.createPoint("R") should be(RedPoint)
      PointFactory.createPoint("X") should be(EmptyPoint) // any other string should return EmptyPoint
    }
  }
}