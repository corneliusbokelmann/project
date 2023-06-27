package de.htwg.se.model.fileIoComponent
import de.htwg.se.model.modelcomponent.modelImpl.{Field, FeedbackField}
import de.htwg.se.model.modelcomponent.modelImpl.Point

case class GameStateData(
  field: Field,
  feedbackField: FeedbackField,
  gameState: String,
  solution: Vector[Point]
)
trait FileIOInterface {
  def save(gameStateData: GameStateData, filePath: String): Unit
  def load(filePath: String): Option[GameStateData]
}
