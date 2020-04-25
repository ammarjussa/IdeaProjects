package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.math.BigInteger.ZERO

data class Rational(val num: BigInteger, val den: BigInteger): Comparable<Rational> {
    private var numerator : BigInteger = num
    private var denominator: BigInteger = den

    init {
        if(den == "0".toBigInteger()) {
            throw IllegalArgumentException("Denominator cannot be zero")
        }
        val gcd = numerator.abs().gcd(denominator.abs())

        val sign = numerator.signum() * denominator.signum()

        numerator = (numerator.abs() / gcd) * sign.toBigInteger()
        denominator = denominator.abs() / gcd
    }

    operator fun plus(adder: Rational): Rational {
        lateinit var nu: BigInteger
        lateinit var de: BigInteger


        if(this.denominator == adder.denominator) {
            nu=this.numerator.plus(adder.numerator)
            de=this.denominator
            return Rational(nu,de)

        }

        else {
            val thegcd: BigInteger = this.denominator.gcd(adder.denominator)

            de = (this.denominator.times(adder.denominator))
                    .divide(thegcd)
            val adding=this.numerator.times(adder.denominator.divide(thegcd))
            val adding2=adder.numerator.times(this.denominator.divide(thegcd))
            nu=adding.plus(adding2)

            return Rational(nu, de)
        }
    }

    operator fun minus(subt : Rational): Rational {
        lateinit var nu: BigInteger
        lateinit var de: BigInteger
        if(this.denominator == subt.denominator) {
            nu=this.numerator.minus(subt.numerator)
            de=this.denominator
            return Rational(nu,de)
        }

        else {
            val thegcd: BigInteger = this.denominator.gcd(subt.denominator)

            de = (this.denominator.times(subt.denominator))
                    .divide(thegcd)

            val sub1=this.numerator.times(subt.denominator.divide(thegcd))
            val sub2=subt.numerator.times(this.denominator.divide(thegcd))
            nu=sub1.minus(sub2)

            return Rational(nu,de)
        }
    }

    operator fun times(mult: Rational): Rational {
        var nu: BigInteger = this.numerator.times(mult.numerator)
        var de: BigInteger = this.denominator.times(mult.denominator)

        return Rational(nu,de)
    }

    operator fun div(div : Rational): Rational {
        var temp: BigInteger = div.numerator
        div.numerator=div.denominator
        div.denominator=temp

        return this.times(div)
    }

    operator fun unaryMinus(): Rational {
        var nu = -this.numerator
        var de = this.denominator
        return Rational(nu,de)
    }

    override fun compareTo(other: Rational): Int {
        val lhs = this.numerator * other.denominator
        val rhs = this.denominator * other.numerator
        return when {
            lhs < rhs -> -1
            lhs > rhs -> 1
            else -> 0
        }
    }


    override fun toString(): String {
        return if(this.denominator == "1".toBigInteger()) {
            this.numerator.toString()
        } else {
            var num = this.numerator
            var dem = this.denominator

            val gcd = num.gcd(dem)
            if(dem < ZERO) {
                "${(-num/gcd).toString()}/${(-dem/gcd).toString()}"
            }


            else "${(num/gcd).toString()}/${(dem/gcd).toString()}"
        }
    }


    override fun hashCode(): Int {
        return this.toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false

        if (other !is Rational)
            return false

        return (this.numerator == other.numerator) &&
                (this.denominator == other.denominator)
    }

    operator fun rangeTo(end: Rational): ClosedRange<Rational> {
        return object : ClosedRange<Rational> {
            override val endInclusive: Rational = end
            override val start: Rational = this@Rational
        }
    }

}


infix fun Int.divBy(divisor: Int) : Rational {
    return Rational(this.toBigInteger(), divisor.toBigInteger())
}

infix fun Long.divBy(divisor: Long) : Rational {
    return Rational(this.toBigInteger(), divisor.toBigInteger())
}

infix fun BigInteger.divBy(divisor: BigInteger) : Rational {
    return Rational(this, divisor)
}

fun String.toRational() : Rational {
    val str = this.split('/')

    if(str.size == 1) {
        return Rational(str[0].toBigInteger(), "1".toBigInteger())
    }
    return Rational(str[0].toBigInteger(), str[1].toBigInteger())
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

private operator fun Rational.contains(half: Rational): Boolean {
    if(this < half) return true
    return false
}






