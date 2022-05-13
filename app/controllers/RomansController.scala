package controllers

import models.{Arabic, Conversion, Roman}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Result}
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import repositories.RomansRepository

@Singleton
class RomansController  @Inject()(val controllerComponents: ControllerComponents, dataRepository: RomansRepository) extends BaseController {

  private val emptyConversion = Conversion(0, "")

  def convertToRoman(arabic: Int): Action[AnyContent] = Action {
    getResponse(dataRepository.convertToRoman(arabic), "Number cannot be converted into Roman numerals")
  }

  def convertBodyToRoman(): Action[AnyContent] = Action {
    implicit request => {
      val arabic: Option[Arabic] = request.body.asJson.flatMap(Json.fromJson[Arabic](_).asOpt)
      if (arabic.isEmpty) {
        BadRequest(Json.toJson("Request body not in required format"))
      } else {
        getResponse(dataRepository.convertToRoman(arabic.get.arabic), "Number cannot be converted into Roman numerals")
      }
    }
  }

  def convertToArabic(roman: String): Action[AnyContent] = Action {
    getResponse(dataRepository.convertToArabic(roman), "Not a valid Roman numeral")
  }

  def convertBodyToArabic(): Action[AnyContent] = Action {
    implicit request => {
      val roman: Option[Roman] = request.body.asJson.flatMap(Json.fromJson[Roman](_).asOpt)
      if (roman.isEmpty) {
        BadRequest(Json.toJson("Request body not in required format"))
      } else {
        getResponse(dataRepository.convertToArabic(roman.get.roman), "Not a valid Roman numeral")
      }
    }
  }

  def getResponse(conversion: Conversion, message: String): Result = {
    if (conversion == emptyConversion) {
        BadRequest(Json.toJson(message))
      } else {
        Ok(Json.toJson(conversion))
      }
    }

}
