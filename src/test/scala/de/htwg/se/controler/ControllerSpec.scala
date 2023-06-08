package de.htwg.se.controler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ControllerSpec extends AnyWordSpec {
    "A Field in Mastermind game" when {
        "undo a point" should {
            val emptyField = new de.htwg.se.model.Field(4, 6, de.htwg.se.model.PointFactory.createPoint(" "))
            val controller = Controller(emptyField, null)
            val field = new de.htwg.se.model.Field(4, 6, de.htwg.se.model.PointFactory.createPoint(" "))
            field.put(de.htwg.se.model.PointFactory.createPoint("R"), 0, 0)
            controller.undoLastMove()
            "have the last game state" in {
                field should be(emptyField)
            }
        }
        "set a gamestate" should {
            val emptyField = new de.htwg.se.model.Field(4, 6, de.htwg.se.model.PointFactory.createPoint(" "))
            val controller = Controller(emptyField, null)
            val gameState = new PlayState()
            controller.setGameState(gameState)
            "have the given gamestate" in {
                controller.getGameState should be(gameState)
            }
        }
    }
}

