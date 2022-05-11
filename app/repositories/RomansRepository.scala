package repositories

import models.Conversion
import javax.inject.Singleton

@Singleton
class RomansRepository {
  private val romans: Map[Int, String] = Map( 1 -> "I", 5 -> "V")
  private val sortedArabics: Seq[Int] = romans.keys.toSeq.sortWith( _ > _ )

  def convertToRoman( arabic: Int ): Conversion = {
    var workingNum: Int = arabic
    var roman = ""
    for ( arabicNum <- sortedArabics ) {
      while (workingNum >= arabicNum) {
        workingNum -= arabicNum
        roman += romans(arabicNum)
      }
    }
    Conversion(arabic, roman)
  }

  def convertToArabic( roman: String ): Conversion = {
    var workingRoman: String = roman
    var arabic = 0
    val romanChars: Seq[String] = sortedArabics.map(arabic => romans(arabic))
    for (romanChar <- romanChars) {
      while (workingRoman.slice(0, romanChar.length) == romanChar) {
        arabic += romans.filter(_._2 == romanChar).keys.head
        workingRoman = workingRoman.substring( romanChar.length )
      }
    }
    Conversion(arabic, roman)
  }
}
