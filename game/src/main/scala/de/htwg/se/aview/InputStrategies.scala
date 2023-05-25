package de.htwg.se.aview

import de.htwg.se.controler.Controller
import de.htwg.se.model.{Point, PointFactory}

class StandardInput extends InputStrategy {
  override def handleInput(input: String, controller: Controller): Unit = {
    input match {
      case "q" =>
      case _ =>
        val chars = input.toCharArray
        val color = chars(0).toString
        val point = PointFactory.createPoint(color)
        val x = chars(1).toString.toInt
        val y = chars(2).toString.toInt
        controller.makeMove(point, x, y)
    }
  }
}