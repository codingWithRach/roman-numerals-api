package repositories

import models.{Conversion, Roman}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RomansRepositorySpec extends AnyFlatSpec with Matchers {

  val dataRepository = new RomansRepository
  val emptyConversion: Conversion = Conversion(0, "")

  "convertToRoman" should "convert the specified arabic number to the correct Roman numerals" in {
    dataRepository.convertToRoman(1) should be (Conversion(1, "I"))
    dataRepository.convertToRoman(7) should be (Conversion(7, "VII"))
    dataRepository.convertToRoman(14) should be (Conversion(14, "XIV"))
    dataRepository.convertToRoman(1972) should be (Conversion(1972, "MCMLXXII"))
  }

  it should "return an empty conversion if passed an invalid arabic number" in {
    dataRepository.convertToRoman(0) should be (emptyConversion)
    dataRepository.convertToRoman(-42) should be (emptyConversion)
  }

  "convertToArabic" should "convert the specified Roman numerals to the correct arabic number" in {
    dataRepository.convertToArabic("IV") should be (Conversion(4, "IV"))
    dataRepository.convertToArabic("VIII") should be (Conversion(8, "VIII"))
    dataRepository.convertToArabic("XXVII") should be (Conversion(27, "XXVII"))
    dataRepository.convertToArabic("MCMLXXII") should be (Conversion(1972, "MCMLXXII"))
  }

  "convertToArabic" should "correctly convert lowercase Roman numerals" in {
    dataRepository.convertToArabic("iii") should be (Conversion(3, "III"))
  }

  it should "return an empty conversion if passed an invalid Roman numeral" in {
    dataRepository.convertToArabic("") should be (emptyConversion)
    dataRepository.convertToArabic("hello") should be (emptyConversion)
  }

  "convertToRoman followed by convertToArabic" should "result in the original arabic number" in {
    val conversion: Conversion = dataRepository.convertToRoman(6)
    dataRepository.convertToArabic(conversion.roman) should be (conversion)
  }

  "convertToArabic followed by convertToRoman" should "result in the original Roman numeral" in {
    val conversion: Conversion = dataRepository.convertToArabic("XII")
    dataRepository.convertToRoman(conversion.arabic) should be (conversion)
  }

  "addRoman" should "save details of a new Roman in valid format" in {
    val newRoman: Conversion = Conversion(roman="Caesar", arabic=150344)
    dataRepository.addRoman(newRoman) should be (Some(newRoman))
    dataRepository.newRomans.head should be (newRoman)
  }

 it should "fail to save details of a duplicate new Roman" in {
    val newRoman: Conversion = Conversion(roman="Caesar", arabic=150344)
    dataRepository.addRoman(newRoman)
    dataRepository.addRoman(newRoman) should be (None)
    dataRepository.newRomans.size should be (1)
  }

  "getRomans" should "return an empty set when no Romans have been added" in {
    val dataRepository = new RomansRepository
    dataRepository.newRomans.size should be (0)
  }

  it should "return an set of Romans when Romans have been added" in {
    val caesar: Conversion = Conversion(roman="Caesar", arabic=150344)
    val caligula: Conversion = Conversion(roman="Caligula", arabic=240141)
    val dataRepository = new RomansRepository
    dataRepository.addRoman(caesar)
    dataRepository.addRoman(caligula)
    dataRepository.getRomans should be (Set(caesar, caligula))
    dataRepository.getRomans.size should be (2)
  }

}
