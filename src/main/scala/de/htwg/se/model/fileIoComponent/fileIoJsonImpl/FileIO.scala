package de.htwg.se.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.model.fileIoComponent.FileIOInterface
import de.htwg.se.model.fileIoComponent.GameStateData
import de.htwg.se.model.modelcomponent.modelImpl.{Matrix, Point, Field, FeedbackField}
import de.htwg.se.model.modelcomponent.FeedbackInterface
import play.api.libs.json._

import java.nio.file.{Files, Paths}

class FileIO extends FileIOInterface {

  // Implicitly provide JSON reads and writes for GameStateData
  implicit val gameStateDataFormat: Format[GameStateData] = Json.format[GameStateData]

  implicit val feedbackInterfaceFormat: Format[FeedbackInterface] = new Format[FeedbackInterface] {
    override def writes(feedback: FeedbackInterface): JsValue = JsString(feedback.toString)

    override def reads(json: JsValue): JsResult[FeedbackInterface] = {
      json.validate[String].flatMap {
        case "Nothing" => JsSuccess(FeedbackInterface.Nothing)
        case "ColorCorrect" => JsSuccess(FeedbackInterface.ColorCorrect)
        case "PositionCorrect" => JsSuccess(FeedbackInterface.PositionCorrect)
        case _ => JsError("Invalid FeedbackInterface value")
      }
    }
  }

  implicit val pointFormat: Format[Point] = new Format[Point] {
    override def writes(point: Point): JsValue = Json.obj("symbol" -> point.symbol)

    override def reads(json: JsValue): JsResult[Point] = {
      val symbol = (json \ "symbol").as[String]
      JsSuccess(Point.valueFromSymbol(symbol).get)
    }
  }

  implicit def matrixFormat[A: Format]: Format[Matrix[A]] = new Format[Matrix[A]] {
    override def writes(matrix: Matrix[A]): JsValue = {
      val rowsJson = matrix.rows.map(row => Json.toJson(row)(Writes.seq[A]))
      Json.obj("rows" -> rowsJson)
    }

    override def reads(json: JsValue): JsResult[Matrix[A]] = {
      val rowsJson = (json \ "rows").as[Seq[JsValue]]
      val rows = rowsJson.map(rowJson => Json.fromJson[Seq[A]](rowJson)(Reads.seq[A]).map(_.toVector))
      if (rows.forall(_.isSuccess)) {
        JsSuccess(Matrix(rows.flatMap(_.asOpt).toVector))
      } else {
        JsError("Invalid matrix format")
      }
    }
  }

  implicit val feedbackFieldFormat: Format[FeedbackField] = Json.format[FeedbackField]
  implicit val fieldFormat: Format[Field] = Json.format[Field]

  override def save(gameStateData: GameStateData, filePath: String): Unit = {
    val data = Json.toJson(gameStateData).toString()
    Files.write(Paths.get(filePath), data.getBytes)
  }

  override def load(filePath: String): Option[GameStateData] = {
    try {
      val jsonData = new String(Files.readAllBytes(Paths.get(filePath)))
      Json.parse(jsonData).validate[GameStateData] match {
        case JsSuccess(gameStateData, _) => Some(gameStateData)
        case JsError(_) => None
      }
    } catch {
      case _: Exception => None
    }
  }
}
