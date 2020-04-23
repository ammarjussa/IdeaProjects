package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

data class Rational(val rational: String) {
    lateinit var numerator : BigInteger
    lateinit var denominator: BigInteger

    init {
        val spl = rational.split('/')
        if(spl.size == 1) {
            numerator = spl[0].toBigInteger()
            denominator = "1".toBigInteger()
        }
        else {
            numerator = spl[0].toBigInteger()
            denominator = spl[1].toBigInteger()
        }

    }

    operator fun plus(adder: Rational): Rational {
        lateinit var num: BigInteger
        lateinit var dem: BigInteger

        if(this.denominator == adder.denominator) {
            num=this.numerator.plus(adder.numerator)
            dem=this.denominator
            return Rational("${num.toString()}/${dem.toString()}")

        }

        else {
            val thegcd: BigInteger = this.denominator.gcd(adder.denominator)

            val dem = (this.denominator.times(adder.denominator))
                    .divide(thegcd)
            val adding=this.numerator.times(adder.denominator.divide(thegcd))
            val adding2=adder.numerator.times(this.denominator.divide(thegcd))
            val num=adding.plus(adding2)

            return Rational("${num.toString()}/${dem.toString()}")
        }
    }

    operator fun minus(subt : Rational): Rational {
        lateinit var num: BigInteger
        lateinit var dem: BigInteger
        if(this.denominator == subt.denominator) {
            num=this.numerator.minus(subt.numerator)
            dem=this.denominator
            return Rational("${num.toString()}/${dem.toString()}")
        }

        else {
            val thegcd: BigInteger = this.denominator.gcd(subt.denominator)

            val dem = (this.denominator.times(subt.denominator))
                    .divide(thegcd)

            val sub1=this.numerator.times(subt.denominator.divide(thegcd))
            val sub2=subt.numerator.times(this.denominator.divide(thegcd))
            val num=sub1.minus(sub2)

            return Rational("${num.toString()}/${dem.toString()}")
        }
    }

    operator fun times(mult: Rational): Rational {
        var num: BigInteger = this.numerator.times(mult.numerator)
        var dem: BigInteger = this.denominator.times(mult.denominator)

        return Rational("${num.toString()}/${dem.toString()}")
    }

    operator fun div(div : Rational): Rational {
        var temp: BigInteger = div.numerator
        div.numerator=div.denominator
        div.denominator=temp

        return this.times(div)
    }

    operator fun unaryMinus(): Rational {
        var num = -this.numerator
        var dem = this.denominator
        return Rational("${num.toString()}/${dem.toString()}")
    }

    //Left
    operator fun compareTo(twoThirds: Rational): Int {
        print(this.numerator.divide(this.denominator))
        if(this.numerator.divide(this.denominator) <
                twoThirds.numerator.divide(twoThirds.denominator))
            return 1
        return 0
    }

    //Left
    operator fun rangeTo(twoThirds: Rational): Any {
        return 0
    }

    override fun toString(): String {
        if(this.denominator == "1".toBigInteger()) {
            return this.numerator.toString()
        } else {
            var num = this.numerator
            var dem = this.denominator

            val gcd = num.gcd(dem)
            return "${(num/gcd).toString()}/${(dem/gcd).toString()}"
        }
    }

}

infix fun Any.divBy(den: Any): Rational {
    if(den != 0) {
        return Rational("${this}/${den}")
    }

    throw IllegalArgumentException("Denominator = 0 Not allowed")
}

fun String.toRational() : Rational {
    return Rational(this)
}


operator fun Any.contains(half: Rational): Boolean {
    return true
}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    //Left
    println(2000000000L divBy 4000000000L == 1 divBy 2)

    //Left
    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}

