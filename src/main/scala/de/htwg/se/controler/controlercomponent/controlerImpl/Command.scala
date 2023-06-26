package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.Try
import de.htwg.se.controler.controlercomponent.CommandInterface

trait Command extends CommandInterface{
  def execute(): Try[Unit]
  def undo(): Try[Unit]
}
