package de.htwg.se.controler

import scala.util.Try

trait Command {
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}