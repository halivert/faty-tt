package escom.tt.ceres.ceresmobile.tools

import android.util.Log
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Functions.gcd
import java.lang.Integer.parseInt
import kotlin.math.abs

class Fraction() {
  private var sign: Int = 1
  var numerator: Int = 0
  var denominator: Int = 1

  constructor(numerator: Int, denominator: Int) : this() {
    if (denominator == 0) throw IllegalArgumentException("Denominator can't be zero")

    val divisor = gcd(abs(numerator), abs(denominator))
    sign = if (numerator * denominator >= 0) 1 else -1
    this.numerator = abs(numerator / divisor)
    this.denominator = abs(denominator / divisor)
  }

  constructor(fractionString: String) : this() {
    val fraction = fractionString.trim().reduceWS()
    val numString: String
    var denString = "1"
    var intString = "0"
    if (fraction.contains('/')) {
      val fractionSplit = fraction.split('/')
      denString = fractionSplit[1].trim()
      val fractionLeftSide = fractionSplit[0].trim()
      if (fractionLeftSide.contains(' ')) {
        val leftSideSplit = fractionLeftSide.split(' ')
        numString = leftSideSplit[1]
        intString = leftSideSplit[0]
      } else {
        numString = fractionLeftSide
      }
    } else {
      numString = fraction
    }

    try {
      var num = parseInt(numString)
      val den = parseInt(denString)
      val int = parseInt(intString)

      if (den == 0)
        throw IllegalArgumentException("Denominator can't be zero")

      num += int * den
      sign = if (num * den >= 0) 1 else -1
      numerator = abs(num)
      denominator = abs(den)
    } catch (e: Exception) {
      Log.e(ERROR, e.message)
    }
  }

  operator fun plus(b: Fraction): Fraction {
    val den = denominator * b.denominator
    val num = numerator * b.denominator + b.numerator * denominator

    return Fraction(num, den).apply {
      simplify()
    }
  }

  fun simplify() {
    if (denominator == 0) throw IllegalArgumentException("Denominator can't be zero")

    val divisor = gcd(abs(numerator), abs(denominator))
    numerator = abs(numerator / divisor)
    denominator = abs(denominator / divisor)
  }

  override fun toString(): String {
    if (denominator != 1)
      return "${if (sign == 1) "" else "-"}$numerator/$denominator"
    return "${if (sign == 1) "" else "-"}$numerator"
  }

  fun toString(human: Boolean = false): String {
    if (human) {
      val int = numerator / denominator
      val num = numerator % denominator

      val intString = if (int != 0) "$int " else ""
      val signString = if (sign == 1) "" else "-"
      val numString = if (num != 0) "$num" else ""
      val denString =
          if (num != 0 && denominator != 1) "/$denominator"
          else ""

      return "$intString$signString$numString$denString"
    } else
      return this.toString()
  }

  operator fun times(b: Fraction): Fraction {
    if (denominator == 0) throw IllegalArgumentException("Denominator can't be zero")
    if (b.denominator == 0) throw IllegalArgumentException("Denominator can't be zero")

    val num = numerator * b.numerator * (sign * b.sign)
    val den = denominator * b.denominator

    return Fraction(num, den).apply {
      simplify()
    }
  }

  private fun String.reduceWS(): String {
    var lastWS = false
    var result = ""
    for (i in 0 until length) {
      if (lastWS) {
        if (!this[i].isWhitespace())
          result += this[i]
      } else
        result += this[i]
      lastWS = this[i].isWhitespace()
    }

    return result
  }

}