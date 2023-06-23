package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.Try

trait Command {
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}