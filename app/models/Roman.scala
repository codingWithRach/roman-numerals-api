package models

import play.api.libs.json.{Json, OFormat}

case class Roman(roman: String)

object Roman {
  implicit val format: OFormat[Roman] = Json.format[Roman]
}
