package controllers

import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import repositories.RomansRepository

@Singleton
class RomansController  @Inject()(val controllerComponents: ControllerComponents, dataRepository: RomansRepository) extends BaseController {

  def convertToRoman(arabic: Int): Action[AnyContent] = Action {
    Ok(Json.toJson(dataRepository.convertToRoman(arabic)))
  }

  def convertToArabic(roman: String): Action[AnyContent] = Action {
    Ok(Json.toJson(dataRepository.convertToArabic(roman)))
  }
}
