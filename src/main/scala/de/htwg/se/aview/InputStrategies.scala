package de.htwg.se.aview

import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.model.modelcomponent.{FieldInterface, PointFactoryInterface}


class StandardInput extends InputStrategy {
  override def handleInput(input: String, controller: ControllerInterface): Unit = {
    input match {
      case "q" =>
      case "u" => controller.undoLastMove()
      case _ =>
        val chars = input.toCharArray
        val color = chars(0).toString
        val pointFactory = new PointFactoryInterface
        val point = pointFactory.createPoint(color)
        val x = chars(1).toString.toInt
        val y = chars(2).toString.toInt
        controller.makeMove(point, x, y)
    }
  }
}
