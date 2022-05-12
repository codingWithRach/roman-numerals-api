package repositories

import models.Conversion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RomansRepositorySpec extends AnyFlatSpec with Matchers {

  val dataRepository = new RomansRepository

  "convertToRoman" should "convert the specified arabic number to the correct Roman numerals" in {
    dataRepository.convertToRoman(1) should be (Conversion(1, "I"))
    dataRepository.convertToRoman(7) should be (Conversion(7, "VII"))
    dataRepository.convertToRoman(14) should be (Conversion(14, "XIV"))
    dataRepository.convertToRoman(1972) should be (Conversion(1972, "MCMLXXII"))
  }

  "convertToArabic" should "convert the specified Roman numerals to the correct arabic number" in {
    dataRepository.convertToArabic("IV") should be (Conversion(4, "IV"))
    dataRepository.convertToArabic("VIII") should be (Conversion(8, "VIII"))
    dataRepository.convertToArabic("XXVII") should be (Conversion(27, "XXVII"))
    dataRepository.convertToArabic("MCMLXXII") should be (Conversion(1972, "MCMLXXII"))
  }

  "convertToRoman followed by convertToArabic" should "result in the original arabic number" in {
    val conversion: Conversion = dataRepository.convertToRoman(6)
    dataRepository.convertToArabic(conversion.roman) should be (conversion)
  }

  "convertToArabic followed by convertToRoman" should "result in the original Roman numeral" in {
    val conversion: Conversion = dataRepository.convertToArabic("XII")
    dataRepository.convertToRoman(conversion.arabic) should be (conversion)
  }

}
