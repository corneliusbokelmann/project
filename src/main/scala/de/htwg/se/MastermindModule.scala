package de.htwg.se

import de.htwg.se.controler.controlercomponent.controlerImpl._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.controler.controlercomponent._
import de.htwg.se.model.modelcomponent._
import de.htwg.se.model.fileIoComponent._
import com.google.inject.{AbstractModule, Provider, Inject}

// Implementations


class MastermindModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[FieldInterface]).toInstance(createField(10, 4))
    bind(classOf[FeedbackFieldInterface]).toInstance(createFeedbackField(10, 4)) 
    bind(classOf[ControllerInterface]).toProvider(new Provider[ControllerInterface] {
    @Inject private var fileIo: FileIOInterface = _

    override def get(): ControllerInterface = {
        val field = createField(10, 4) // Adjust the parameters according to your desired grid size
        val feedbackField = createFeedbackField(10, 4) // Adjust the parameters according to your desired grid size
        new Controller(field, feedbackField, fileIo) // Here you pass the FileIOInterface instance
    }
    })
    bind(classOf[ReceiverInterface]).to(classOf[Receiver]) 
    // bind(classOf[GameStateInterface]).to(classOf[GameState])
    // bind(classOf[CommandInterface]).to(classOf[YourConcreteCommandImplementation])
    bind(classOf[InputStrategy]).to(classOf[StandardInput])
    bind(classOf[FileIOInterface]).to(classOf[fileIoJsonImpl.FileIO]) 
  }

  private def createField(guessLength: Int, maxLength: Int): FieldInterface = {
    val matrix = Matrix[Point](Vector.fill(guessLength, maxLength)(Point.EmptyPoint))
    val feedbackMatrix = Matrix[FeedbackInterface](Vector.fill(guessLength, maxLength)(FeedbackInterface.Nothing))
    val feedbackField = FeedbackField(feedbackMatrix, guessLength)
    Field(matrix, feedbackField)
  }

  private def createFeedbackField(guessLength: Int, maxLength: Int): FeedbackFieldInterface = {
    val matrix = Matrix[FeedbackInterface](Vector.fill(guessLength, maxLength)(FeedbackInterface.Nothing))
    FeedbackField(matrix, guessLength)
  }
}
