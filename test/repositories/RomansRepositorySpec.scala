package repositories

import models.Conversion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RomansRepositorySpec extends AnyFlatSpec with Matchers {

  val dataRepository = new RomansRepository

  "convertToRoman" should "convert the specified arabic number to the correct Roman numerals" in {
    dataRepository.convertToRoman(6) should be (Conversion(6, "VI"))
  }

  "convertToArabic" should "convert the specified Roman numerals to the correct arabic number" in {
    dataRepository.convertToArabic("VI") should be (Conversion(6, "VI"))
  }

}
