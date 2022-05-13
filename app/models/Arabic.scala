package models

import play.api.libs.json.{Json, OFormat}

case class Arabic(arabic: Int)

object Arabic {
  implicit val format: OFormat[Arabic] = Json.format[Arabic]
}


