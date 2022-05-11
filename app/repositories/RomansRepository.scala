package repositories

import models.Conversion
import javax.inject.Singleton

@Singleton
class RomansRepository {
  private val romans: Map[Int, String] = Map( 1 -> "I", 5 -> "V")


  def convertToRoman( arabic: Int ): Conversion = {
    val arabics: Seq[Int] = romans.keys.toSeq.sortWith( _ > _ )
    var workingNum: Int = arabic
    var roman = ""
    for ( arabicNum <- arabics ) {
      while (workingNum >= arabicNum) {
        workingNum -= arabicNum
        roman += romans(arabicNum)
      }
    }
    Conversion(arabic, roman)
  }

  def convertToArabic( roman: String ): Conversion = {
    Conversion(1, roman)
  }
}
